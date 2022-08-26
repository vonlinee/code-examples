package io.devpl.sdk.support.spring;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class DevplApplication extends SpringApplication {

    public DevplApplication(Class<?>... primarySources) {
        super(null, primarySources);
    }

    /**
     * SpringApplication#afterRefresh是空实现
     * @param context
     * @param args
     */
    @Override
    protected void afterRefresh(ConfigurableApplicationContext context, ApplicationArguments args) {

    }

    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return new DevplApplication(primarySources).run(args);
    }
}
