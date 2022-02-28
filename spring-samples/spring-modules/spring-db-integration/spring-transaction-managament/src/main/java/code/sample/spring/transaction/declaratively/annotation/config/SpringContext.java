package code.sample.spring.transaction.declaratively.annotation.config;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContext implements ApplicationContextAware{
	
	private static ApplicationContext context = null;
	
	private AtomicInteger changeCount = new AtomicInteger(0);

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContext.context = applicationContext;
		changeCount.incrementAndGet();
	}
	
	public static <T> T getBean(Class<T> requiredType) {
		return context.getBean(requiredType);
	}
}
