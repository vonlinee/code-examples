package sample.spring.advanced.bean;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import sample.spring.advanced.GlobalConfig;

public class TestBeanMain {
	
	public static void main(String[] args) {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(GlobalConfig.class);
		
		
		ObjectProvider<Model> provider = context.getBeanProvider(Model.class);
		
		Model model = provider.getObject();
		
		System.out.println(model);
		
	}
	
}
