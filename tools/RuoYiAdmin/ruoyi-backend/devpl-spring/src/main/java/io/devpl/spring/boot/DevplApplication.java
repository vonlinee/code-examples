package io.devpl.spring.boot;

import io.devpl.spring.context.SpringContext;
import io.devpl.spring.data.jdbc.DynamicDataSource;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.BootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ResourceLoader;

@Setter
public class DevplApplication extends SpringApplication {

    private boolean reportInternal = true;
    private BootstrapContext bootstrapContext;

    public DevplApplication(Class<?>[] primarySources) {
        super(primarySources);
    }

    @Override
    public ConfigurableApplicationContext run(String... args) {
        return super.run(args);
    }

    /**
     * Static helper that can be used to run a {@link SpringApplication} from the
     * specified source using default settings.
     * @param primarySource the primary source to load
     * @param args          the application arguments (usually passed from a Java main method)
     * @return the running {@link ApplicationContext}
     */
    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        return run(new Class<?>[]{primarySource}, args);
    }

    /**
     * Static helper that can be used to run a {@link SpringApplication} from the
     * specified sources using default settings and user supplied arguments.
     * @param primarySources the primary sources to load
     * @param args           the application arguments (usually passed from a Java main method)
     * @return the running {@link ApplicationContext}
     */
    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return new DevplApplication(primarySources).run(args);
    }

    /**
     * 在ApplicationContext的后置处理中进行Bean注册，最开始注册Bean的地方
     * @param context Spring上下文对象
     */
    @Override
    protected void postProcessApplicationContext(ConfigurableApplicationContext context) {
        super.postProcessApplicationContext(context);
        SpringContext springContext = bootstrapContext.get(SpringContext.class);
        ResourceLoader resourceLoader = getResourceLoader();
        if (resourceLoader == null) {
            resourceLoader = context;
        }
        springContext.setResourceLoader(resourceLoader);
        springContext.setClassLoader(getClassLoader());
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        springContext.setConversionService(beanFactory.getConversionService());
        springContext.setApplication(this);
        beanFactory.registerSingleton("Devpl::spring-context", springContext);
        DynamicDataSource dataSource = bootstrapContext.get(DynamicDataSource.class);
        beanFactory.registerSingleton("dynamicDataSource", dataSource);
    }
}
