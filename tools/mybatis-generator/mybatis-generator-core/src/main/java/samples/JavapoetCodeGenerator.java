package samples;

import com.squareup.javapoet.*;
import samples.meta.ClassDefinition;
import samples.meta.MethodDefinition;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.List;

public class JavapoetCodeGenerator {

    public static void main(String[] args) throws IOException {
        //定义方法描述信息
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        FieldSpec.builder(TypeName.DOUBLE, "name", Modifier.PUBLIC);

        ClassName className = ClassName.get("java.util1", "Map", "Entry");

        System.out.println(className);


        //定义类信息
        TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(main)
                .build();
        //用上面的类生成Java源代码文件
        JavaFile javaFile = JavaFile.builder("com.example.helloworld", typeSpec)
                .build();
        javaFile.writeTo(System.out); //指定控制台输出，也可指定为文件
    }

    public static void generate(ClassDefinition definition) {
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(definition.getClassName())
                .addModifiers(Modifier.PUBLIC);
        List<MethodDefinition> mds = definition.getMethodDefinitions();
        for (MethodDefinition md : mds) {
            MethodSpec method = MethodSpec.methodBuilder(md.getMethodName())
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                    .build();
            typeBuilder.addMethod(method);
        }
    }
}
