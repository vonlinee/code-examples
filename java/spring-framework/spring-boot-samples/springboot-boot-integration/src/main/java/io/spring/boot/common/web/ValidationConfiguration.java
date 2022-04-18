package io.spring.boot.common.web;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 快速失败返回模式(只要有一个验证失败，则返回)
 */
@Configuration
public class ValidationConfiguration {

	/**
	 * JSR和Hibernate validator的校验只能对Object的属性进行校验 不能对单个的参数进行校验 spring 在此基础上进行了扩展
	 * 添加了MethodValidationPostProcessor拦截器 可以实现对方法参数的校验
	 * 
	 * @return
	 */
	@Bean
	@DependsOn(value = {"validator", "validatorFactory"})
	public MethodValidationPostProcessor methodValidationPostProcessor(Validator validator,
			ValidatorFactory validatorFactory) {
		MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
		postProcessor.setValidator(validator);
		postProcessor.setOptimize(true);
		postProcessor.setValidatorFactory(validatorFactory);
		postProcessor.setProxyTargetClass(true);
		return postProcessor;
	}

	@Bean
	public ValidatorFactory validatorFactory() {
		// 校验器的工作模式设置: validator模式为快速失败返回
		// validator.fail_fast:快速失败返回模式(只要有一个验证失败，则返回异常)
		// validator.normal:普通模式(会校验完所有的属性，然后返回所有的验证失败信息)
		return Validation.byProvider(HibernateValidator.class).configure()
				.addProperty("hibernate.validator.fail_fast", "true").buildValidatorFactory();
	}

	@Bean
	@DependsOn(value = { "validatorFactory" }) // beanName
	public Validator validator(ValidatorFactory validatorFactory) {
		Validator validator = validatorFactory.getValidator();
		return validator;
	}
}
