package org.example;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("org.example.Setter")
public class MyProcessor extends AbstractProcessor {

    private Messager messager;
    private JavacTrees javacTrees;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.javacTrees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Setter.class);
        elementsAnnotatedWith.forEach(e -> {
            JCTree tree = javacTrees.getTree(e);
            tree.accept(new TreeTranslator() {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    List<JCTree.JCVariableDecl> jcVariableDeclList = List.nil();
                    // 在抽象树中找出所有的变量
                    for (JCTree jcTree : jcClassDecl.defs) {
                        if (jcTree.getKind().equals(Tree.Kind.VARIABLE)) {
                            JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) jcTree;
                            jcVariableDeclList = jcVariableDeclList.append(jcVariableDecl);
                        }
                    }

                    jcVariableDeclList.forEach(jcVariableDecl -> {
                        messager.printMessage(Diagnostic.Kind.NOTE, jcVariableDecl.getName() + " has been processed");
                        jcClassDecl.defs = jcClassDecl.defs.prepend(createSetterMethodDeclaration(jcVariableDecl));
                    });
                    super.visitClassDef(jcClassDecl);
                }
            });
        });
        return true;
    }

    private JCTree.JCMethodDecl createSetterMethodDeclaration(JCTree.JCVariableDecl jcVariableDecl) {
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        // this.a = a;
        JCTree.JCFieldAccess lhs = treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName());
        JCTree.JCIdent rhs = treeMaker.Ident(jcVariableDecl.getName());
        JCTree.JCExpressionStatement aThis = treeMaker.Exec(treeMaker.Assign(lhs, rhs));
        statements.append(aThis);
        JCTree.JCBlock block = treeMaker.Block(0, statements.toList());
        // 生成入参
        JCTree.JCVariableDecl param = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), jcVariableDecl.getName(), jcVariableDecl.vartype, null);
        List<JCTree.JCVariableDecl> parameters = List.of(param);
        // 生成返回对象
        JCTree.JCExpression methodType = treeMaker.Type(new Type.JCVoidType());
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), getNewMethodName(jcVariableDecl.getName()), methodType, List.nil(), parameters, List.nil(), block, null);
    }

    private Name getNewMethodName(Name name) {
        String s = name.toString();
        return names.fromString("set" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
    }
}