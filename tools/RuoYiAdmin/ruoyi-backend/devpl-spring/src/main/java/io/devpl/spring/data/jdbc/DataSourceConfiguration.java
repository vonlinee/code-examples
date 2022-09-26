package io.devpl.spring.data.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * 数据源配置
 */
@Configuration(proxyBeanMethods = false)
@Import(DataSourceRegistrar.class)
public class DataSourceConfiguration {

    @Bean
    public DataSourceManager dataSourceManager() {
        return new DataSourceManager();
    }

    @Bean
    @DependsOn("devpl-datasource-manager")
    public DataSource dynamicDataSource(DataSourceManager manager) {
        DevplRoutingDataSource dynamicDataSource = new DevplRoutingDataSource();
        return dynamicDataSource;
    }
}
