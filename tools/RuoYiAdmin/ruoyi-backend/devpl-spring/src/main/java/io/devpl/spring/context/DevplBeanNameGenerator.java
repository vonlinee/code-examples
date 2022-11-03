package io.devpl.spring.context;

import io.devpl.spring.utils.DevplConstant;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

/**
 * 自定义的Bean的名称策略
 */
@Setter
public class DevplBeanNameGenerator implements BeanNameGenerator {

    public static final DevplBeanNameGenerator INSTANCE = new DevplBeanNameGenerator();

    @Nullable
    private BeanNameGenerator delegate;

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return DevplConstant.NAME + "-" + definition.getBeanClassName();
    }
}
