package code.example.spring.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import code.example.spring.beans.factory.Student;

public class Main {
	
	private static ApplicationContext context;

	public static void main(String[] args) throws Exception {
		context = new ClassPathXmlApplicationContext("spring.xml");
		Student student1 = context.getBean(Student.class);
		Student student2 = context.getBean(Student.class);
		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student1 == student2);
		
		Main main = context.getBean(Main.class);
		System.out.println(main);
	}
}
