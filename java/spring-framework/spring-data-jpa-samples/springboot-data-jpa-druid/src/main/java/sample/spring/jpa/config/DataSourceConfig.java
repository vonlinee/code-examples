package sample.spring.jpa.config;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSource1")
    @Qualifier("dataSource1")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource1(){
        return DataSourceBuilder.create().build();
    }
}
