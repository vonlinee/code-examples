package code.example.spring.autowiring.byname;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("autowire-byname.xml");
		Shape shape = (Shape) context.getBean("triangle");
		shape.draw();
	}
}