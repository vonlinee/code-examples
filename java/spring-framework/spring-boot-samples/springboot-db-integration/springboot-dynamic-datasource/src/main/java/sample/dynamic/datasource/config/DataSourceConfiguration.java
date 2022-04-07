package sample.dynamic.datasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import sample.dynamic.datasource.common.DynamicDataSource;

import javax.sql.DataSource;

/**
 * 数据源配置
 */
@Configuration
@MapperScan(sqlSessionFactoryRef = "sqlSessionFactoryBean")
public class DataSourceConfiguration {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations();
        return bean;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.productcenter")
    public DataSource prcDataSource() {
        // 底层会自动拿到spring.datasource中的配置， 创建一个DruidDataSource
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.ordercenter")
    public DataSource orcDataSource() {
        // 底层会自动拿到spring.datasource中的配置， 创建一个DruidDataSource
        return DruidDataSourceBuilder.create().build();
    }

    // @Bean
    // public Interceptor dynamicDataSourcePlugin() {
    //     return null;
    // }

    @Bean(name = "prcTransactionManager")
    public DataSourceTransactionManager prcTransactionManager(DynamicDataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    @Bean(name = "orcTransactionManager")
    public DataSourceTransactionManager orcTransactionManager(DynamicDataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}

