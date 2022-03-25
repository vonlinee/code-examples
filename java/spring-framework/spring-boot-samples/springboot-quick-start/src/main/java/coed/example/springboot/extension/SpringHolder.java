package coed.example.springboot.extension;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

@Component("spring-holder")
public class SpringHolder implements ApplicationContextAware, EnvironmentAware, ResourceLoaderAware,
		BeanClassLoaderAware, BeanNameAware, ImportAware {

	private String name;
	private ClassLoader classLoader;
	private ResourceLoader resourceLoader;
	private Environment environment;
	private ApplicationContext applicationContext;
	private AnnotationMetadata importMetadata;

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		this.importMetadata = importMetadata;
	}

	@Override
	public void setBeanName(String name) {
		this.name = name;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public AnnotationMetadata getImportMetadata() {
		return importMetadata;
	}
}
