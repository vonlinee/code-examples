package sample.spring.transaction.solution.declaratively.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.contains("AccountService")) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.contains("AccountService")) {
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

	
	
}
