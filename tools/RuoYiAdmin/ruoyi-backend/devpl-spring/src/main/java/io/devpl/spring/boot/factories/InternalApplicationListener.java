package io.devpl.spring.boot.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 事件监听器
 */
public class InternalApplicationListener implements ApplicationListener<ApplicationEvent> {

    private static final Logger log = LoggerFactory.getLogger(InternalApplicationListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info("响应事件");
        if (event instanceof ContextRefreshedEvent) {
            log.info("刷新上下文");
        }
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            ApplicationEnvironmentPreparedEvent envPreparedEvent = (ApplicationEnvironmentPreparedEvent) event;
            ConfigurableEnvironment environment = envPreparedEvent.getEnvironment();
        }
    }
}
