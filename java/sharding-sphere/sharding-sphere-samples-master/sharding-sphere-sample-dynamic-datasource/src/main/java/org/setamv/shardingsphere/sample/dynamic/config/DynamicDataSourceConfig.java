package org.setamv.shardingsphere.sample.dynamic.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态数据源配置。总共配置五个数据源，参考{@link DataSourceNames}
 * @author setamv
 * @date 2021-04-16
 */
@Configuration
@PropertySource(value = {"classpath:sharding.yml"}, factory = YamlPropertySourceFactory.class)
@MapperScan(basePackages = "org.setamv.shardingsphere.sample.dynamic.mapper", sqlSessionFactoryRef = "dynamicSqlSessionFactory")
public class DynamicDataSourceConfig {

    @Bean(name = DataSourceNames.SCM_DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.scm-ds")
    public DataSource dataSourceDs() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置动态路由的数据源。该数据源根据当前线程设置的数据源名称路由到指定的数据源：<ul>
     *     <li>{@link DataSourceNames#SHARDING_DATA_SOURCE}：Sharding数据源，默认的数据源</li>
     *     <li>{@link DataSourceNames#SCM_DATA_SOURCE}：SCM主库数据源</li>
     * </ul>
     * <p>通过{@link DynamicRouteDataSourceContext#setDataSource(String)}设置当前线程使用的数据源
     * <p>DependsOn注解解决数据库循环依赖问题
     * @param shardingDataSource Sharding JDBC数据源
     * @param scmDataSource SCM主库数据源
     * @return 动态数据源
     */
    @Primary
    @DependsOn({DataSourceNames.SHARDING_DATA_SOURCE, DataSourceNames.SCM_DATA_SOURCE})
    @Bean(name = DataSourceNames.DYNAMIC_DATA_SOURCE)
    public DataSource dynamicRouteDataSource(
            @Qualifier(DataSourceNames.SHARDING_DATA_SOURCE) DataSource shardingDataSource,
            @Qualifier(DataSourceNames.SCM_DATA_SOURCE) DataSource scmDataSource) {
        DynamicRouteDataSource multiDataSource = new DynamicRouteDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>(2, 1.0F);
        targetDataSources.put(DataSourceNames.SCM_DATA_SOURCE, scmDataSource);
        targetDataSources.put(DataSourceNames.SHARDING_DATA_SOURCE, shardingDataSource);
        multiDataSource.setTargetDataSources(targetDataSources);
        // 设置shardingDataSource为默认的数据源，即默认使用分片数据源。如果要使用scmDataSource，需要开始数据库操作之前，
        // 使用 org.setamv.shardingsphere.sample.dynamic.config.DynamicRouteDataSourceContext.setDataSource 指定使用的数据源
        multiDataSource.setDefaultTargetDataSource(shardingDataSource);
        return multiDataSource;
    }

    /**
     * 分页插件。Mybatis会自动加载Interceptor
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
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
     * @param typeHandlersProvider 参考{@link MybatisAutoConfiguration}构造函数
     * @param languageDriversProvider 参考{@link MybatisAutoConfiguration}构造函数
     * @param resourceLoader 参考{@link MybatisAutoConfiguration}构造函数
     * @param databaseIdProvider 参考{@link MybatisAutoConfiguration}构造函数
     * @param configurationCustomizersProvider 参考{@link MybatisAutoConfiguration}构造函数
     * @return 包含动态数据源的SqlSessionFactory
     * @throws Exception
     */
    @Bean(name = "dynamicSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier(DataSourceNames.DYNAMIC_DATA_SOURCE) DataSource dataSource,
            MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider,
            ObjectProvider<TypeHandler[]> typeHandlersProvider, ObjectProvider<LanguageDriver[]> languageDriversProvider,
            ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider,
            ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) throws Exception {
        MybatisAutoConfiguration mybatisAutoConfiguration = new MybatisAutoConfiguration(properties, interceptorsProvider, typeHandlersProvider, languageDriversProvider, resourceLoader , databaseIdProvider, configurationCustomizersProvider);
        return mybatisAutoConfiguration.sqlSessionFactory(dataSource);
    }


    @Bean(name = "dynamicShardingSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("dynamicSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return  new SqlSessionTemplate(sessionFactory);
    }

    @Bean(name = "dynamicShardingTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier(DataSourceNames.DYNAMIC_DATA_SOURCE) DataSource ds){
        return new DataSourceTransactionManager(ds);
    }
}
