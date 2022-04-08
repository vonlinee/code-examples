package sample.dynamic.datasource.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.business")
    public DataSource businessDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
    
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.ordercenter")
    public DataSource ordercenterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    // @Bean
    // @ConfigurationProperties(prefix = "spring.datasource.datasource2")
    // public DataSource dataSource2() {
    //     // 底层会自动拿到spring.datasource中的配置， 创建一个DruidDataSource
    //     return DruidDataSourceBuilder.create().build();
    // }

    // @Bean
    // public Interceptor dynamicDataSourcePlugin() {
    //     return new DynamicDataSourcePlugin();
    // }

    @Bean
    public DataSourceTransactionManager transactionManager1(DynamicDataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    // @Bean
    // public DataSourceTransactionManager transactionManager2(DynamicDataSource dataSource){
    //     DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
    //     dataSourceTransactionManager.setDataSource(dataSource);
    //     return dataSourceTransactionManager;
    // }
}
