package io.devpl.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

/**
 * 自定义的Bean的名称策略
 */
public class DevplBeanNameGenerator implements BeanNameGenerator {

    private final String prefix = "devpl";

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return prefix + "-" + definition.getBeanClassName();
    }
}
