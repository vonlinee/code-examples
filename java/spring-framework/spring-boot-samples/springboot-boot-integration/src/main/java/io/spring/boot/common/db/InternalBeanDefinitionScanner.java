package io.spring.boot.common.db;

import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class InternalBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

	public InternalBeanDefinitionScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	public InternalBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
		super(registry, useDefaultFilters);
	}

	protected void registerFilters() {
		addIncludeFilter(new AnnotationTypeFilter(null));
	}

	@Override
	protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
		return super.doScan(basePackages);
	}
}
