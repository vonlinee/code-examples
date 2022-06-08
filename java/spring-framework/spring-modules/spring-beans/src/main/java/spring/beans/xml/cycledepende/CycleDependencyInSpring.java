package spring.beans.xml.cycledepende;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CycleDependencyInSpring {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
		context.setConfigLocation("xml/spring-circular-denpendency.xml");
		context.refresh();
		
		// ConfigurableListableBeanFactory
		
		A a = context.getBean(A.class);
		// org.springframework.beans.factory.support.DefaultListableBeanFactory@25084a1e: 
		// defining beans [a,b,org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.context.event.internalEventListenerProcessor,org.springframework.context.event.internalEventListenerFactory]; 
		// root of factory hierarchy
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		A a1 = beanFactory.getBean(A.class);
		
		System.out.println(a == a1); // true
		
		System.out.println(a);
		context.close();
	}
}



