package org.example.springboot.config;

import org.example.springboot.bean.Model;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
//import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class OptionalBeanConfiguration {
	
//	@Bean("standardEvaluationContext")
//	public StandardEvaluationContext standardEvaluationContext() {
//		return new StandardEvaluationContext();
//	}
//	
//	@Bean("restTemplate")
//	public RestTemplate restTemplate() {
//		return new RestTemplate();
//	}
//	
//	@Bean(initMethod="init", destroyMethod="destroy")
//	@Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode=ScopedProxyMode.INTERFACES)
//	public Model model() {
//		return new Model();
//	}
}
