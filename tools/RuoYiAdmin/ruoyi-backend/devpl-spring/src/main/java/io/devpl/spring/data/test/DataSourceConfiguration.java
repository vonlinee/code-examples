package io.devpl.spring.data.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(DataSourceRegistrar.class)
@Configuration(value = "devpl-datasource-configuration", proxyBeanMethods = false)
public class DataSourceConfiguration {

    // @Bean
    public DynamicDataSource dynamicDataSource() {
        return new DynamicDataSource();
    }
}
