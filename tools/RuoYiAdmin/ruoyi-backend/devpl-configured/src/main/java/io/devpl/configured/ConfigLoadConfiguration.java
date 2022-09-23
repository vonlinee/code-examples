package io.devpl.configured;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "io.devpl.configured.repository") // 扫描 @Repository 注解
@EntityScan(basePackages = "io.devpl.configured.entity") // 扫描 @Entity 注解
@Configuration(proxyBeanMethods = false)
public class ConfigLoadConfiguration {

}
