package org.example.order.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    Retryer retryer() {
        return new Retryer.Default(100, 1000, 4);
    }
}
