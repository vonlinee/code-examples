package io.maker.extension.mybatis.core;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;

/**
 * MyBatis配置类
 */
public class MyBatisConfiguration {

    private static final SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

    private Map<String, Environment> environments;
    private final Configuration configuration;
    private MapperRegistry mapperRegistry;
    private String name;
    private Cache cache;
    private MappedStatement mappedStatement;

    public MyBatisConfiguration() {
        this.environments = new HashMap<>();
        this.configuration = new Configuration();
        this.mapperRegistry = configuration.getMapperRegistry();
    }

    public void init(DataSource dataSource) {
        Environment environment = new Environment("dev", transactionFactory(), dataSource);
        configuration.setEnvironment(environment);
        configuration.setUseColumnLabel(true);
        configuration.setNullableOnForEach(true);
        configuration.setUseGeneratedKeys(true);
    }

    private TransactionFactory transactionFactory() {
        return new ManagedTransactionFactory();
    }

    public void addMapper(Class<?> mapperClass) {
        configuration.addMapper(mapperClass);
    }
}
