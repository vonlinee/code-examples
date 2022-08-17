package io.devpl.commons.db.jdbc;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源配置
 */
@DependsOn("dataSourceRegistry")
@Configuration
@ConditionalOnMissingBean(value = {DataSourceAutoConfiguration.class})
public class DataSourceConfiguration {

    @Bean
    DataSource dynamicDataSource(DataSourceRegistry registry) {
        DynamicDataSource dataSource = new DynamicDataSource();
        Map<String, DataSource> dataSources = registry.getDataSources();
        dataSource.setOptionalDataSources(dataSources);
        return dataSource;
    }
}
