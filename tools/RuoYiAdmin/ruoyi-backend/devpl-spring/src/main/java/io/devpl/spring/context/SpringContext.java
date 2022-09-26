package io.devpl.spring.context;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.Objects;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 */
@Setter
@Getter
public class SpringContext {

    private final Logger log = LoggerFactory.getLogger(SpringContext.class);

    public static final SpringContext INSTANCE = new SpringContext();

    private SpringContext() {
        log.info("Initialize SpringContext => {}", this);
    }

    private SpringApplication application;

    private ApplicationArguments applicationArguments;

    /**
     * Spring应用上下文环境
     */
    private ConfigurableListableBeanFactory beanFactory;

    private ApplicationContext applicationContext;

    private ConfigurableBootstrapContext bootstrapContext;

    private ConfigurableEnvironment environment;

    private ResourceLoader resourceLoader;

    @Nullable
    private ConversionService conversionService;

    /**
     * org.springframework.core.io.DefaultResourceLoader#classLoader
     */
    private ClassLoader classLoader;

    /**
     * 获取对象
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws org.springframework.beans.BeansException
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     * @param clz
     * @return
     * @throws org.springframework.beans.BeansException
     */
    public <T> T getBean(Class<T> clz) throws BeansException {
        return (T) beanFactory.getBean(clz);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     * @param name
     * @return boolean
     */
    public boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     * @param name
     * @return boolean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    /**
     * @param name
     * @return Class 注册对象的类型
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return Objects.requireNonNull(beanFactory.getType(name));
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     * @param name
     * @return
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

    /**
     * 获取aop代理对象
     * @param invoker
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }

    /**
     * 获取当前的环境配置，无配置返回null
     * @return 当前的环境配置
     */
    public String[] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     * @return 当前的环境配置
     */
    public String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return activeProfiles.length != 0 ? activeProfiles[0] : null;
    }

    /**
     * 获取配置文件中的值
     * @param key 配置文件的key
     * @return 当前的配置文件的值
     */
    public String getRequiredProperty(String key) {
        return applicationContext.getEnvironment().getRequiredProperty(key);
    }

    /**
     * 获取多个Bean实例
     * @param type Class实例
     * @param <T>  bean类型
     * @return Map
     */
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return beanFactory.getBeansOfType(type);
    }
}
