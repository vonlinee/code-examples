package utils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.utils.ParserCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class GithubJavaParser {

    /**
     * 解析工程下的所有Java文件
     *
     * @param path 工程根目录
     */
    public static void parseProject(String path) {
        Path root = Paths.get(path);
        // only parsing
        ProjectRoot projectRoot = new ParserCollectionStrategy().collect(root);
        projectRoot.getSourceRoots().forEach(sourceRoot -> {
            System.out.println(sourceRoot);
            try {
                // 解析source root
                sourceRoot.tryToParse();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 获取解析后的编译单元列表
            List<CompilationUnit> cuList = sourceRoot.getCompilationUnits();
            cuList.forEach(GithubJavaParser::parseOneFile);
        });
    }

    /**
     * 解析单个Java文件
     *
     * @param cu 编译单元
     */
    public static void parseOneFile(CompilationUnit cu) {
        // 类型声明
        NodeList<TypeDeclaration<?>> types = cu.getTypes();
        for (TypeDeclaration<?> type : types) {
            System.out.println("## " + type.getName());

            TypeInfo typeInfo = TypeInfo.register(type);

            // 成员
            NodeList<BodyDeclaration<?>> members = type.getMembers();
            // members.forEach(GithubJavaParser::processNode);
        }
    }

    public static ParseResult<CompilationUnit> parseFile(File file) {
        JavaParser javaParser = new JavaParser();
        ParseResult<CompilationUnit> result;
        try {
            result = javaParser.parse(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Optional<CompilationUnit> parseResult = result.getResult();
        if (parseResult.isPresent()) {
            // 每一个java文件就是一个编译单元
            CompilationUnit compilationUnit = parseResult.get();

            String packageName = null;
            final Optional<PackageDeclaration> packageDeclarationOptional = compilationUnit.getPackageDeclaration();

            if (packageDeclarationOptional.isPresent()) {
                final PackageDeclaration packageDeclaration = packageDeclarationOptional.get();
                packageName = packageDeclaration.getNameAsString();
            }

            NodeList<ImportDeclaration> imports = compilationUnit.getImports();

            ImportInfo importInfo = ImportInfo.extract(imports);
            importInfo.setPackageName(packageName);
            // 返回编译单元中的所有顶级类型声明
            NodeList<TypeDeclaration<?>> types = compilationUnit.getTypes();
            for (TypeDeclaration<?> type : types) {
                if (type.isTopLevelType()) {
                    NodeList<BodyDeclaration<?>> members = type.getMembers();
                    for (BodyDeclaration<?> member : members) {
                        if (member.isFieldDeclaration()) {
                            FieldDeclaration fd = member.asFieldDeclaration();
                            if (fd.isStatic()) {

                            } else {
                                NodeList<VariableDeclarator> variables = fd.getVariables();
                                VariableDeclarator variableDeclarator = variables.get(0);
                                final Type variableDeclaratorType = variableDeclarator.getType();

                                if (variableDeclaratorType.isClassOrInterfaceType()) {
                                    ClassOrInterfaceType ciType = variableDeclaratorType.asClassOrInterfaceType();

                                    final SimpleName name = ciType.getName();

                                    ciType.getTypeArguments().ifPresent(typeArguments -> {
                                        // 判断泛型个数
                                        if (isCollectionType(name.getIdentifier())) {
                                            // 泛型都是 ClassOrInterfaceType，包括String，不会是基础类型
                                            // 泛型可能还存在嵌套泛型，比如List<Map<String, Object>
                                            Type typeArg = typeArguments.get(0);
                                            if (typeArg.isClassOrInterfaceType()) {
                                                ClassOrInterfaceType classOrInterfaceType = typeArg.asClassOrInterfaceType();
                                                SimpleName typeArgName = classOrInterfaceType.getName();

                                                String typeName = importInfo.get(typeArgName);
                                                System.out.println(typeName);
                                            }
                                        } else if (isMapType(name.getIdentifier())) {

                                        }
                                    });
                                }

                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static boolean isCollectionType(String identifier) {
        return "List".equals(identifier) || "Set".equals(identifier) || "Collection".equals(identifier);
    }

    public static boolean isMapType(String identifier) {
        return identifier != null && identifier.contains("Map");
    }

    /**
     * 处理类型,方法,成员
     *
     * @param node
     */
    public static void processNode(Node node) {
        if (node instanceof TypeDeclaration) {
            // 类型声明
            // do something with this type declaration

        } else if (node instanceof MethodDeclaration) {
            // 方法声明
            // do something with this method declaration
            String methodName = ((MethodDeclaration) node).getName().getIdentifier();
            System.out.println("方法: " + methodName);

        } else if (node instanceof FieldDeclaration) {
            // 成员变量声明
            // do something with this field declaration
            // 注释
            Comment comment = node.getComment().orElse(null);

            // 变量
            NodeList<VariableDeclarator> variables = ((FieldDeclaration) node).getVariables();
            SimpleName fieldName = variables.get(0).getName();
            if (comment != null) {
                System.out.print(handleComment(comment.getContent()));
            }
            System.out.print("\t");
            System.out.print(fieldName);
            System.out.println();
        }

        for (Node child : node.getChildNodes()) {
            processNode(child);
        }
    }

    private static boolean handleComment(String content) {
        return false;
    }
}
