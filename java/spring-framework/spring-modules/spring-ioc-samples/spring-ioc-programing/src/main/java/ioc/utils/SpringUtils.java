package ioc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("spring-utils")
public class SpringUtils implements ApplicationContextAware, EnvironmentAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringUtils.class);
	
	//non-static
	private ApplicationContext applicationContext;
	private Environment environment;
	
	private SpringUtils() {
	}
	
	private static SpringUtils utils; //singleton instance
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		LOGGER.info("inject ApplicationContext {} to SpringUtils: {}", applicationContext, this);
		utils = applicationContext.getBean(SpringUtils.class);
	}
	
	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static <T> T getBean(Class<T> requiredType) {
		return utils.getApplicationContext().getBean(requiredType);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) utils.getApplicationContext().getBean(beanName);
	}
	
	public static String findConfigXml(Class<?> clazz, String filename) {
		return clazz.getResource(filename).toExternalForm();
	}
	
	public static void printAllBeanDefinitionName(ApplicationContext context) {
		for(String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.println(beanDefinitionName);
		}
	}
	
	public static void printAllBeanDefinition(ApplicationContext context) {
		for(String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.println(context.getBean(beanDefinitionName));
		}
	}
	
	public static void printAllBeanDefinitionDetail(ApplicationContext context) {
		List<Map<String, Object>> list = new ArrayList<>();
		for(String beanDefinitionName : context.getBeanDefinitionNames()) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("beanName", beanDefinitionName);
			map.put("beanClass", context.getBean(beanDefinitionName));
			list.add(map);
		}
		printMapList(list);
	}
	
	public static void printMapList(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println(map);
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
	}
}