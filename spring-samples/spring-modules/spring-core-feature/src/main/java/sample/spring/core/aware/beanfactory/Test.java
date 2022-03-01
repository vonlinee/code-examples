package sample.spring.core.aware.beanfactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sample.spring.core.aware.beanname.Shape;

import java.net.URL;
import java.util.Objects;

public class Test {
    public static void main(String[] args) {
        URL url = sample.spring.core.aware.beanname.Test.class.getResource("spring-config.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext(Objects.requireNonNull(url).toExternalForm());
        sample.spring.core.aware.beanname.Shape shape = (Shape) context.getBean("triangle");
        shape.draw();
    }
}
