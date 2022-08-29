package io.devpl.sdk.support.spring;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 基于SpringBoot
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class DevplApplication extends SpringApplication {

    private String appName;

    public DevplApplication(Class<?>... primarySources) {
        super(null, primarySources);
    }

    @Override
    protected void refresh(ConfigurableApplicationContext applicationContext) {
        applicationContext.refresh();
    }

    /**
     * SpringApplication#afterRefresh是空实现
     *
     * @param context
     * @param args
     */
    @Override
    protected void afterRefresh(ConfigurableApplicationContext context, ApplicationArguments args) {
        log.info("afterRefresh ======================");
    }

    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return new DevplApplication(primarySources).run(args);
    }
}
