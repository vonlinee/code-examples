package io.devpl.sdk.api.config;

import io.devpl.sdk.api.entity.Model;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OptionalBeanConfiguration {

    @Bean
    public Model model() {
        return new Model(30, "zs");
    }
}
