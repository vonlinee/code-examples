package io.devpl.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApplication {

    private static final Logger LOG = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLazyInitialization(false);
        app.setAllowBeanDefinitionOverriding(true);
        ConfigurableApplicationContext context = app.run(args);
        ServerProperties serverInfo = context.getBean(ServerProperties.class);
        LOG.info("启动成功，访问地址 => {} {}", serverInfo.getAddress(), serverInfo.getPort());
    }
}