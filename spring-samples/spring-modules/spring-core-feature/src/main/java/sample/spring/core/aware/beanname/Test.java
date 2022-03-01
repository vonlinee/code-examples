package sample.spring.core.aware.beanname;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sample.spring.core.Main;

import java.net.URL;
import java.util.Objects;

public class Test {
    public static void main(String[] args) {
        URL url = Test.class.getResource("spring-config.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext(Objects.requireNonNull(url).toExternalForm());
        Shape shape = (Shape) context.getBean("triangle");
        shape.draw();
    }
}
