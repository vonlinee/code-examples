package io.devpl.spring.context;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.BootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 * 持有对spring中一些关键组件的引用
 */
@Setter
@Getter
public final class SpringContext {

    private SpringContext() {
    }

    private static class SpringContextHolder {
        private static final SpringContext INSTANCE = new SpringContext();
    }

    public static SpringContext get() {
        return SpringContextHolder.INSTANCE;
    }

    private final Logger log = LoggerFactory.getLogger(SpringContext.class);

    private SpringApplication application;

    private ApplicationArguments applicationArguments;

    /**
     * Spring应用上下文环境
     */
    private ConfigurableListableBeanFactory beanFactory;

    private ApplicationContext applicationContext;

    private BootstrapContext bootstrapContext;

    private ConfigurableEnvironment environment;

    private ResourceLoader resourceLoader;

    @Nullable
    private ConversionService conversionService;

    /**
     * org.springframework.core.io.DefaultResourceLoader#classLoader
     */
    private ClassLoader classLoader;
}
