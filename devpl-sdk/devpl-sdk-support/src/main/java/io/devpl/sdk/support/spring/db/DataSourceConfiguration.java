package io.devpl.sdk.support.spring.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Import(DataSourceRegistrar.class)
@Configuration(value = "devpl-datasource-configuration", proxyBeanMethods = false)
public class DataSourceConfiguration {

    // @Bean
    public DynamicDataSource dynamicDataSource() {
        return new DynamicDataSource();
    }
}
