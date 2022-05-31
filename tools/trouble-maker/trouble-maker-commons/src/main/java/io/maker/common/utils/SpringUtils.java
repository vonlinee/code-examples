package io.maker.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component(value = "spring-context-holder")
public class SpringUtils implements ApplicationContextAware, EnvironmentAware {

	private static ApplicationContext applicationContext = null;

	private static Environment environment = null;
	
	@Override
	public void setEnvironment(Environment environment) {
		SpringUtils.environment = environment;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.applicationContext = applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		if (applicationContext.containsBean(beanName)) {
			return (T) applicationContext.getBean(beanName);
		} 
		return null;
	}

	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}
}
