package io.maker.common.spring.db;

import java.util.Map;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class BeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry,
			BeanNameGenerator importBeanNameGenerator) {
		BeanDefinition definition = null;
		registry.registerBeanDefinition(importBeanNameGenerator.generateBeanName(definition, registry), definition);
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		
	}

	public BeanDefinition createBeanDefinition(Class<?> beanClass, Map<String, Object> fields) {
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(beanClass);
		beanDefinition.setSynthetic(true);
		// 注入属性
		MutablePropertyValues mpv = beanDefinition.getPropertyValues();
		for (Map.Entry<String, Object> entry : fields.entrySet()) {
			mpv.addPropertyValue(entry.getKey(), entry.getValue());
		}
		return beanDefinition;
	}
}
