package io.devpl.spring.boot.factories.listener;

import org.springframework.boot.BootstrapContextClosedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 响应BootstrapContext关闭事件，在上下文准备好(contextPrepared)后触发
 * 1.将BootstrapContext中包含的Bean注册到ApplicationContext中
 */
public class BootstrapContextClosedListener implements ApplicationListener<BootstrapContextClosedEvent> {
    @Override
    public void onApplicationEvent(BootstrapContextClosedEvent event) {

    }
}
