package ioc.bean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IoCContainer {
	
	@Bean
	public BeanContainer beanContainer() {
		return new BeanContainer();
	}
	
	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IoCContainer.class);
		
		
	}
}
