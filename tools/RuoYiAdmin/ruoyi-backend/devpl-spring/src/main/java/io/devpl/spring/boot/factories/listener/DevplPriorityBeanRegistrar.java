package io.devpl.spring.boot.factories.listener;

import io.devpl.spring.boot.DevplSpringApplication;
import io.devpl.spring.context.SpringContext;
import io.devpl.spring.data.jdbc.DataSourceManager;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.Ordered;

/**
 * 在ApplicationContext创建之前注册一些Bean实例
 */
public class DevplPriorityBeanRegistrar implements SpringApplicationRunListener, Ordered {

    private final SpringApplication application;
    private final String[] args;

    public DevplPriorityBeanRegistrar(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        if (application instanceof DevplSpringApplication) {
            ((DevplSpringApplication) application).setBootstrapContext(bootstrapContext);
        }
        bootstrapContext.registerIfAbsent(DataSourceManager.class, context -> new DataSourceManager());
        bootstrapContext.registerIfAbsent(SpringContext.class, context -> SpringContext.INSTANCE);
        SpringContext.INSTANCE.setBootstrapContext(bootstrapContext);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
