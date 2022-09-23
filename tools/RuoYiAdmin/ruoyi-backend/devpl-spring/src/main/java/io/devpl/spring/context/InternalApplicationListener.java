package io.devpl.spring.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class InternalApplicationListener implements ApplicationListener<ApplicationEvent> {
	
	private static final Logger log = LoggerFactory.getLogger(InternalApplicationListener.class);
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			log.info("刷新上下文");
		}
	}
}
