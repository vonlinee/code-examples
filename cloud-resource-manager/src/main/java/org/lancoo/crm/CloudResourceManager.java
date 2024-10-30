package org.lancoo.crm;

import cn.hutool.extra.spring.EnableSpringUtil;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

/**
 *
 */
@EnableScheduling
@EnableSpringUtil
@EnableWebSocketMessageBroker
@SpringBootApplication
public class CloudResourceManager {
    public static void main(String[] args) {
        SpringApplication.run(CloudResourceManager.class);
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName) {
        return (registry) -> registry.config().commonTags("application", applicationName);
    }
}
