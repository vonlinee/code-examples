package io.maker.codegen.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		return context.getBean(name, requiredType);
	}

	public static <T> T getBean(Class<T> requiredType) {
		return context.getBean(requiredType);
	}
	
	public static boolean containsBeanDefinition(String name) {
		
		return context.containsBeanDefinition(name);
	}
	
	public static boolean containsBean(String name) {
		return context.containsBean(name);
	}
}
