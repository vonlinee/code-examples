package sample.java.newfeature.lambda;

import sample.java.io.FileUtils;
import sun.security.action.GetPropertyAction;

import java.security.AccessController;
import java.util.PropertyPermission;
import java.util.function.Predicate;

/**
 * Oracle JDK 8 / OpenJDK 8对lambda表达式在运行时的实现方式是动态生成跟匿名内部类相似形式的类
 * 而负责生成代码的类位于 java.lang.invoke.InnerClassLambdaMetafactory
 *
 * 生成的类如1.txt所示文本
 */
@SuppressWarnings("restriction")
public class TestLambda {

    public static void main(String[] args) {
        //-Djdk.internal.lambda.dumpProxyClasses=<path_to_your_dump_directory>
        setLambdaDumpProxyClassLocation();
        TestLambda main = new TestLambda();
        main.lambda("AAA", str -> str.length() > 5);
    }

    public static void setLambdaDumpProxyClassLocation() {
        System.setProperty("jdk.internal.lambda.dumpProxyClasses", "");
    }

    public void lambda(String value, Predicate<String> predicate) {
        boolean result = predicate.test(value);
        System.out.println(result);
    }
}
