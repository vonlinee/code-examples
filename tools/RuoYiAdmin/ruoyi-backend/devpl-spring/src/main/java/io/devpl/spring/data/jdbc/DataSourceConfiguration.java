package io.devpl.spring.data.jdbc;

import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据源配置
 */
@Configuration(proxyBeanMethods = false)
public class DataSourceConfiguration {

    // @Bean
    public DataSource dynamicDataSource() {
        return new DevplRoutingDataSource();
    }
}
