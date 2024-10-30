package org.example.spring.extension;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CircularDependencyResolver implements SmartInstantiationAwareBeanPostProcessor, ApplicationContextAware {

    ApplicationContext context;

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
        if (beanFactory instanceof DefaultSingletonBeanRegistry) {
            DefaultSingletonBeanRegistry registry = (DefaultSingletonBeanRegistry) beanFactory;
            if (registry.isSingletonCurrentlyInCreation(beanName)) {

            }
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
