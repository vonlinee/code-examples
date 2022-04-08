package org.setamv.shardingsphere.starter.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.yaml.swapper.ShardingRuleConfigurationYamlSwapper;
import org.apache.shardingsphere.shardingjdbc.spring.boot.sharding.SpringBootShardingRuleConfigurationProperties;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Sharding JDBC数据源配置
 *
 * @author setamv
 */
@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
@MapperScan(basePackages = "org.setamv.**.dao", sqlSessionFactoryRef = "sqlSessionFactory")
@PropertySource(value = {"classpath:sharding.yml"}, factory = YamlPropertySourceFactory.class)
public class DataSourceConfig {

    public static final String DATA_SOURCE_0 = "ds0";
    public static final String DATA_SOURCE_1 = "ds1";
    public static final String DATA_SOURCE = "dataSource";

    @Bean(name = DATA_SOURCE_0)
    @ConfigurationProperties(prefix = "shardingsphere.datasource.ds0")
    public DataSource dataSource0() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = DATA_SOURCE_1)
    @ConfigurationProperties(prefix = "shardingsphere.datasource.ds1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @DependsOn({DATA_SOURCE_0, DATA_SOURCE_1})
    @Bean(name = DATA_SOURCE)
    public DataSource dataSource(
            @Qualifier(DATA_SOURCE_0) DataSource ds0,
            @Qualifier(DATA_SOURCE_1) DataSource ds1,
            @Qualifier("shardingConfig") SpringBootShardingRuleConfigurationProperties shardingRule,
            @Qualifier("shardingProperties") Properties shardingProperties) {
        // 如果归档库需要分库，这里需要将分库的多个库都注入进去
        Map<String, DataSource> dataSourceMap = new HashMap<>(2, 1.0F);
        dataSourceMap.put(DATA_SOURCE_0, ds0);
        dataSourceMap.put(DATA_SOURCE_1, ds1);

        ShardingRuleConfiguration ruleConfiguration = new ShardingRuleConfigurationYamlSwapper().swap(shardingRule);
        try {
            return new CustomizedShardingDataSource(dataSourceMap, ruleConfiguration, shardingProperties);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 手动创建SqlSessionFactory，手动注入动态数据源。
     * <p>该方法是模仿mybatis-spring-boot-starter中的{@link MybatisAutoConfiguration}创建数据源，只是将mybatis-spring-boot-starter中对应的数据源替换成动态数据源，
     * 其他的参数保持不变。这样的好处是，SqlSessionFactory构造的细节与mybatis-spring-boot-starter仍然保持一致（除了数据源注入的是动态数据源），
     * 不需要手动往SqlSessionFactory中设置mybatis的相关配置对象（如 TypeHandler、Plugins等）
     * @param dataSource 动态数据源注入
     * @param properties Mybatis配置信息
     *      这里注入mybatisProperties是因为配置多数据源时，SqlSessionFactory为手动创建的，
     *      org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration就不会再初始化SqlSessionFactory了
     *      所以，必须手动将mybatis的配置注入到手动创建的SqlSessionFactory中
     * @param interceptorsProvider 参考{@link MybatisAutoConfiguration}构造函数
     * @param resourceLoader 参考{@link MybatisAutoConfiguration}构造函数
     * @param databaseIdProvider 参考{@link MybatisAutoConfiguration}构造函数
     * @param configurationCustomizersProvider 参考{@link MybatisAutoConfiguration}构造函数
     * @return 包含动态数据源的SqlSessionFactory
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier(DATA_SOURCE) DataSource dataSource,
            MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider,
            ObjectProvider<TypeHandler[]> typeHandlersProvider, ObjectProvider<LanguageDriver[]> languageDriversProvider,
            ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider,
            ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) throws Exception {
        MybatisAutoConfiguration mybatisAutoConfiguration = new MybatisAutoConfiguration(
                properties, interceptorsProvider, typeHandlersProvider , languageDriversProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
        return mybatisAutoConfiguration.sqlSessionFactory(dataSource);
    }


    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sessionFactory) {
        return  new SqlSessionTemplate(sessionFactory);
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier(DATA_SOURCE) DataSource ds){
        return new DataSourceTransactionManager(ds);
    }

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



}
