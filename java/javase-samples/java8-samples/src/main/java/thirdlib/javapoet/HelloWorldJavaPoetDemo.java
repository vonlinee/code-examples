package thirdlib.javapoet;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class HelloWorldJavaPoetDemo {

    public static void main(String[] args) {
        // `JavaFile` 代表 Java 文件
        JavaFile javaFile = JavaFile.builder("com.walfud.howtojavapoet",
                        // TypeSpec 代表一个类
                        TypeSpec.classBuilder("Clazz")
                                // 给类添加一个属性
                                .addField(FieldSpec.builder(int.class, "mField", Modifier.PRIVATE).build())
                                // 给类添加一个方法
                                .addMethod(MethodSpec.methodBuilder("method").addModifiers(Modifier.PUBLIC).returns(void.class)
                                        .addStatement("System.out.println(str)").build())
                                .build())
                .build();
        System.out.println(javaFile.toString());
    }
}
