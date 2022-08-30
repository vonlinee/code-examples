package io.devpl.sdk.support.spring.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * https://blog.csdn.net/weixin_30266829/article/details/99953687
 */
@Configuration
public class EmbedH2Configuration {

    @Bean
    public H2ConsoleProperties properties() {
        H2ConsoleProperties h2ConsoleProperties = new H2ConsoleProperties();
        h2ConsoleProperties.setEnabled(true);
        h2ConsoleProperties.setPath("/console");
        h2ConsoleProperties.getSettings().setTrace(true);
        h2ConsoleProperties.getSettings().setWebAllowOthers(false);
        return h2ConsoleProperties;
    }

    @Bean
    public DataSource h2EmbedDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:h2:~/test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("org.h2.Driver");
        return dataSource;
    }
}
