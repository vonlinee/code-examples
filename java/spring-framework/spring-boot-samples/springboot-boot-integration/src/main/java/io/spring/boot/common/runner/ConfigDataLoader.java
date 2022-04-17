package io.spring.boot.common.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

/**
 * 加载配置数据
 */
public class ConfigDataLoader  implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(ConfigDataLoader.class);
	
	@Override
	public void run(String... args) throws Exception {
		log.info("start to load config data >>>>>>>>>>>>>>>>>> ");
	}
}
