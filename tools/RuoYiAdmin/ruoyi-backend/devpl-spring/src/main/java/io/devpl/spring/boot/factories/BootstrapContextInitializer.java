package io.devpl.spring.boot.factories;

import io.devpl.spring.context.SpringContext;
import io.devpl.spring.data.jdbc.DataSourceManager;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.BootstrapRegistryInitializer;

/**
 * 初始化BootstrapContext
 */
public class BootstrapContextInitializer implements BootstrapRegistryInitializer {

    /**
     * 初始化Bootstrap上下文
     * @param registry BootstrapContext实例
     */
    @Override
    public void initialize(BootstrapRegistry registry) {
        registry.registerIfAbsent(SpringContext.class, context -> {
            SpringContext springContext = new SpringContext();
            springContext.setBootstrapContext(context);
            return springContext;
        });
        registry.register(DataSourceManager.class, context -> new DataSourceManager());
    }
}
