package sample.spring.advanced.bean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 在spring初始化bean的时候，如果bean实现了InitializingBean接口，会自动调用afterPropertiesSet方法
 */
@Component
public class Model implements InitializingBean {
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Model::afterPropertiesSet");
	}
}
