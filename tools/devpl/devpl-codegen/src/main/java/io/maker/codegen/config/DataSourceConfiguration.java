package io.maker.codegen.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    /**
     * 内嵌的配置数据库
     *
     * @return
     */
    @Bean
    public DataSource h2DataSource() {
        DruidDataSource source = new DruidDataSource();
        source.setDriverClassName("org.h2.Driver");
        source.setUsername("root");
        source.setPassword("123456");
        source.setUrl("jdbc:h2:h2_db:config;DB_CLOSE_ON_EXIT=FALSE");
        return source;
    }

    public DataSource springDataSource() {
        return null;
    }
}
