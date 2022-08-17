package io.spring.boot.common.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * 在ApplicationContext#refresh之前调用此类的方法
 *
 * BeanDefinitionRegistryPostProcessor
 * BeanFactoryPostProcessor
 */
@Slf4j
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        log.info("BeanDefinitionRegistry {}", registry);
        // 获取所有的BeanDefinition
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("ConfigurableListableBeanFactory {}", beanFactory);
    }
}
