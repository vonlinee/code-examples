package io.devpl.toolkit.config;

import com.zaxxer.hikari.HikariDataSource;
import io.devpl.toolkit.codegen.JDBCDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AppConfiguration {

    /**
     * 用于查询数据库元数据
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/devpl");
        ds.setUsername("root");
        ds.setPassword("123456");
        ds.setDriverClassName(JDBCDriver.MYSQL5.getDriverClassName());
        return new JdbcTemplate(ds);
    }
}
