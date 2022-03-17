package sample.spring.aop.xml.beforeandafter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    private static ApplicationContext context;

    public static void main(String[] args) {
    	String string = Main.class.getResource("spring-aop-config.xml").toExternalForm();
        context = new ClassPathXmlApplicationContext(string);
        Performer performer = (Performer) context.getBean("performer");
        performer.perform();
    }
}
