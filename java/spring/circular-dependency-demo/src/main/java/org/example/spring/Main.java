package org.example.spring;

import org.example.spring.bean.A;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @see org.springframework.beans.factory.support.DefaultSingletonBeanRegistry
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory
 * @see org.springframework.context.ApplicationContextAware
 * @see org.springframework.beans.factory.support.AbstractBeanFactory#doGetBean(String, Class, Object[], boolean)
 * @see org.springframework.beans.factory.support.AbstractBeanFactory#doResolveBeanClass(RootBeanDefinition, Class[])
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#initializeBean(String, Object, RootBeanDefinition)
 */
@ComponentScan
public class Main {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.setAllowCircularReferences(false);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(beanFactory);
        context.register(Main.class);
        context.refresh();

        A bean = context.getBean(A.class);

        System.out.println(bean);
    }
}
