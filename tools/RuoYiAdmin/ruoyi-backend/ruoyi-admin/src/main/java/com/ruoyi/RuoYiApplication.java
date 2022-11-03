package com.ruoyi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动程序
 * @author ruoyi
 */
@SpringBootApplication
public class RuoYiApplication {

    private static final Logger LOG = LoggerFactory.getLogger(RuoYiApplication.class);

    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        ConfigurableApplicationContext context = SpringApplication.run(RuoYiApplication.class, args);

        LOG.info("Swagger地址: http://localhost:8888/swagger-ui/index.html#/");
    }
}
