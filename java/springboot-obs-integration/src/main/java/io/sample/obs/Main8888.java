package io.sample.obs;

import io.sample.obs.config.OBSProperties;
import io.sample.obs.config.ObsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(value = {OBSProperties.class})
public class Main8888 {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main8888.class, args);
    }
}
