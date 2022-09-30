package io.devpl.spring.boot.factories.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 多数据源注册，在Environment准备完成后立刻进行注册
 * 注册所有的数据源信息
 */
public class EnvironmentPreparedListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final Logger log = LoggerFactory.getLogger(EnvironmentPreparedListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

    }
}
