package org.setamv.shardingsphere.sample.dynamic.config;

import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.core.yaml.swapper.ShardingRuleConfigurationYamlSwapper;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.spring.boot.sharding.SpringBootShardingRuleConfigurationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Sharding JDBC数据源配置。因为需要融合Sharding JDBC和非Sharding JDBC数据源并做动态数据源路由，所以手动配置Sharding JDBC数据源。
 *
 * @author setamv
 * @date 2021-04-16
 */
@Configuration
@PropertySource(value = {"classpath:sharding.yml"}, factory = YamlPropertySourceFactory.class)
public class ShardingDataSourceConfig {

    /**
     * 从 sharding.yml 中加载配置。参考 {@link SpringBootShardingRuleConfigurationProperties}，该类是 sharding-jdbc-spring-boot-starter 的sharding 规则加载类
     *
     * @return sharding规则
     */
    @Bean(name = "shardingConfig")
    @ConfigurationProperties(prefix = "shardingsphere.sharding")
    public SpringBootShardingRuleConfigurationProperties shardingConfig() {
        return new SpringBootShardingRuleConfigurationProperties();
    }

    @Bean(name = "shardingProperties")
    @ConfigurationProperties(prefix = "shardingsphere.props")
    public Properties shardingProperties() {
        return new Properties();
    }


    @Bean(name = DataSourceNames.SCM0_DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.scm-ds0")
    public DataSource dataSourceDs0() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = DataSourceNames.SCM1_DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.scm-ds1")
    public DataSource dataSourceDs1() {
        return DataSourceBuilder.create().build();
    }

    @DependsOn({DataSourceNames.SCM0_DATA_SOURCE, DataSourceNames.SCM1_DATA_SOURCE})
    @Bean(name = DataSourceNames.SHARDING_DATA_SOURCE)
    public DataSource dataSource(
            @Qualifier(DataSourceNames.SCM0_DATA_SOURCE) DataSource scm1DataSource,
            @Qualifier(DataSourceNames.SCM1_DATA_SOURCE) DataSource scm2DataSource,
            @Qualifier("shardingConfig") SpringBootShardingRuleConfigurationProperties shardingRule,
            @Qualifier("shardingProperties") Properties shardingProperties) {
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(DataSourceNames.SCM0_DATA_SOURCE, scm1DataSource);
        dataSourceMap.put(DataSourceNames.SCM1_DATA_SOURCE, scm2DataSource);

        ShardingRuleConfiguration ruleConfiguration = new ShardingRuleConfigurationYamlSwapper().swap(shardingRule);

        try {
            return ShardingDataSourceFactory.createDataSource(dataSourceMap, ruleConfiguration, shardingProperties);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
