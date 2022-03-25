package sample.spring.advanced.jdbc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.ui.Model;

import sample.spring.advanced.GlobalConfig;

public class SpringDbMain {
	
	public static void main(String[] args) {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(GlobalConfig.class);
		
		
	}
	
}
