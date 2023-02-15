import com.squareup.javapoet.*;
import org.junit.Test;
import org.samples.bean.Student;
import utils.GithubJavaParser;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Main {

    public static File newFile(String path) {
        return new File(new File("src/main/java"), path);
    }


    @Test
    public void test2() throws IOException {
        final File file = newFile("org/samples/bean/Student.java");

        GithubJavaParser.parseFile(file);
    }

    @Test
    public void test4() throws IOException {
        final File file = newFile("org/samples/bean/Student.java");

        GithubJavaParser.parseProject("C:\\Users\\vonline\\Desktop\\untitled");
    }

    @Test
    public void test1() throws IOException {
        ClassName className = ClassName.get("org.utils", "Main");
        TypeName typeName = TypeName.get(String.class);
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(className, typeName);

        FieldSpec fieldSpec = FieldSpec.builder(parameterizedTypeName, "nameList", Modifier.PRIVATE).build();

        final TypeSpec type = TypeSpec.classBuilder("Main")
                .addField(fieldSpec)
                .build();

        final JavaFile javaFile = JavaFile.builder("aaaa.bbb.ccc", type).build();

        javaFile.writeTo(System.out);
    }

    /**
     * 运行时获取泛型类型信息
     *
     * @throws NoSuchFieldException
     */
    @Test
    public void test3() throws NoSuchFieldException {
        Field field = Student.class.getDeclaredField("addresses");
        final Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) genericType).getRawType();
            System.out.println(rawType);
            Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
            System.out.println(actualTypeArguments[0]);
        }
        System.out.println(genericType);
    }
}
