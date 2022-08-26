package io.devpl.sdk.support.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component(value = "spring-utils")
public class SpringUtils implements ApplicationContextAware, EnvironmentAware, BeanClassLoaderAware, InitializingBean {

    private static ApplicationContext context;

    private static ClassLoader classLoader;

    private static Environment environment;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        SpringUtils.classLoader = classLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        SpringUtils.environment = environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
