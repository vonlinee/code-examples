package io.spring.boot.common.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiuration {

    @Bean(name = "springJdbcTransactionManager")
    public PlatformTransactionManager springJdbcTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
