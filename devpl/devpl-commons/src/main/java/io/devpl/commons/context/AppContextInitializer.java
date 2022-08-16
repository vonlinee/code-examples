package io.devpl.commons.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

/**
 * https://www.cnblogs.com/duanxz/p/11239291.html
 * @author Administrator
 *
 */
public class AppContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {
	
	private static final Logger log = LoggerFactory.getLogger(AppContextInitializer.class);
	
	/**
	 * 在spring容器刷新之前执行的一个回调函数
	 */
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		log.info("applicationContext => " + applicationContext);
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE; // 越小越先执行
	}
}
