package io.devpl.spring.utils;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("devpl-spring-utils")
public final class SpringUtils implements ApplicationContextAware, BeanFactoryPostProcessor {

    private static ApplicationContext context;
    private static BeanFactory beanFactory;

    /**
     * 获取对象
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws org.springframework.beans.BeansException
     */
    public static <T> T getBean(String name) throws BeansException {
        return (T) context.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     * @param clz
     * @return
     * @throws org.springframework.beans.BeansException
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        return (T) context.getBean(clz);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     * @param name
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return context.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     * @param name
     * @return boolean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return context.isSingleton(name);
    }

    /**
     * @param name
     * @return Class 注册对象的类型
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return context.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     * @param name
     * @return
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return context.getAliases(name);
    }

    /**
     * 获取aop代理对象
     * @param invoker
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }

    /**
     * 获取当前的环境配置，无配置返回null
     * @return 当前的环境配置
     */
    public static String[] getActiveProfiles() {
        return context.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     * @return 当前的环境配置
     */
    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return activeProfiles.length != 0 ? activeProfiles[0] : null;
    }

    /**
     * 获取配置文件中的值
     * @param key 配置文件的key
     * @return 当前的配置文件的值
     */
    public static String getRequiredProperty(String key) {
        return context.getEnvironment().getRequiredProperty(key);
    }

    /**
     * 获取多个Bean实例
     * @param type Class实例
     * @param <T>  bean类型
     * @return Map
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return context.getBeansOfType(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.context = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }
}
