package io.devpl.sdk.support.spring.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration(value = "devpl-datasource-configuration", proxyBeanMethods = false)
public class DataSourceConfiguration {

    // @Bean
    public DataSource embedDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("");
        dataSource.setJdbcUrl("");
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }
}
