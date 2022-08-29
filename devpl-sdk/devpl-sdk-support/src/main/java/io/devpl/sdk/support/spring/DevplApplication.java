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
        log.info("refresh Spring ApplicationContext [{}]", applicationContext.getClass().getSimpleName());
        try {
            applicationContext.refresh();
        } catch (Exception exception) {
            log.error("刷新ApplicationContext失败，退出", exception);
            System.exit(0);
        }
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
        log.info("{} afterRefresh", this);
    }

    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return new DevplApplication(primarySources).run(args);
    }
}
