package thirdlib.jdt;

import org.eclipse.jdt.core.dom.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class JdtTest1 {

    public static void main(String[] args) throws Exception {
        String content = read("D:\\Temp\\AdviceInvocationController.java"); //java源文件
        //创建解析器，设置Java语言规范（JLS）版本
        ASTParser parsert = ASTParser.newParser(AST.JLS3);
        //设定解析器的源代码字符
        parsert.setSource(content.toCharArray());
        //使用解析器进行解析并返回AST上下文结果(CompilationUnit为根节点)
        CompilationUnit result = (CompilationUnit) parsert.createAST(null);
        //获取类型
        List types = result.types();
        //取得类型声明
        TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
        //##############获取源代码结构信息#################
        //引用import
        List importList = result.imports();
        //取得包名
        PackageDeclaration packetDec = result.getPackage();
        //取得类名
        String className = typeDec.getName().toString();
        //取得函数(Method)声明列表
        MethodDeclaration methodDec[] = typeDec.getMethods();
        //取得函数(Field)声明列表
        FieldDeclaration fieldDec[] = typeDec.getFields();
        //输出包名
        System.out.println("包:");
        System.out.println(packetDec.getName());
        //输出引用import
        System.out.println("引用import:");
        for (Object obj : importList) {
            ImportDeclaration importDec = (ImportDeclaration) obj;
            System.out.println(importDec.getName());
        }
        //输出类名
        System.out.println("类:");
        System.out.println(className);
        //循环输出函数名称
        System.out.println("========================");
        System.out.println("函数:");
        for (MethodDeclaration method : methodDec) {
            System.out.println(method.getName());
            System.out.println("body:");
            System.out.println(method.getBody());
            System.out.println("Javadoc:" + method.getJavadoc());

            System.out.println("Body:" + method.getBody());

            System.out.println("ReturnType:" + method.getReturnType());
            System.out.println("=============");
            System.out.println(method);
        }

        System.out.println();
        System.out.println();
        System.out.println();

        //循环输出变量
        System.out.println("变量:");
        for (FieldDeclaration fieldDecEle : fieldDec) {
            //public static
            for (Object modifiObj : fieldDecEle.modifiers()) {
                MarkerAnnotation ma = (MarkerAnnotation) modifiObj;
                System.out.print(ma + "-");
            }
            System.out.println(fieldDecEle.getType());
            for (Object obj : fieldDecEle.fragments()) {
                VariableDeclarationFragment frag = (VariableDeclarationFragment) obj;
                System.out.println("[FIELD_NAME:]" + frag.getName());
            }
        }
    }

    private static String read(String filename) throws IOException {
        File file = new File(filename);
        byte[] b = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        int read = fis.read(b);
        System.out.println("读取字节数=" + read);
        return new String(b);
    }
}
