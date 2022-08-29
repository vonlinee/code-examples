package io.devpl.sdk.support.spring.context;

import io.devpl.sdk.support.spring.DevplApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 在spring.factories文件中配置此类
 * 在整个启动流程中接收不同执行点事件通知的监听者
 */
@Slf4j
public class DevplBootstrapListener implements SpringApplicationRunListener {

    private final SpringApplication application;
    private final String[] args;

    // 必须定义此构造，否则会报错
    public DevplBootstrapListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting() {
        log.info("starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        log.info("environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("【上下文准备完毕】 BeanDefinitionLoader");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("【上下文加载完毕】");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log.info("started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("failed");
    }
}
