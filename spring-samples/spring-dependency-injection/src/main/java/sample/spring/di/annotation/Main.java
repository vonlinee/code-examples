package sample.spring.di.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(EntryConfiguration.class);
		A a = context.getBean(A.class);
		System.out.println(a);
	}
}
