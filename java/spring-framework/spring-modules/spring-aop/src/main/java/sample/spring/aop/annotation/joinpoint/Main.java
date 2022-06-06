package sample.spring.aop.annotation.joinpoint;

import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Main {
	
	private static ApplicationContext context;

	public static void main(String[] args) {
		URL resource = Main.class.getResource("abc.xml");
		context = new FileSystemXmlApplicationContext(resource.toExternalForm());
		Performer performer = (Performer) context.getBean("performer");
		performer.perform("K.K.", 42);
	}
}
