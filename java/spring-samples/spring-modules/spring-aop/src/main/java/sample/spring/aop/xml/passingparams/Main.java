package sample.spring.aop.xml.passingparams;

import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Main {
    private static ApplicationContext context;

    public static void main(String[] args) {
    	URL resource = Main.class.getResource("spring-config.xml");
        context = new FileSystemXmlApplicationContext(resource.toExternalForm());
        
        Thinker thinkerImpl = (Thinker) context.getBean("thinkerImpl");
        thinkerImpl.thinkOfSomething("Wanna be a java developer");
    }
}
