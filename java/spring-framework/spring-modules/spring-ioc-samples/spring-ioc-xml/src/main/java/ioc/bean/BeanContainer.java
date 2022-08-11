package ioc.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanContainer {
	
	@Bean
	public Object bean_aaa() {
		Object object = new Object();
		System.out.println("BeanContainer => " + object);
		return object;
	}
}
