package io.maker.extension.mybatis.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;

public class MyBatisConfiguration {

    private static final SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

    private Map<String, Environment> environments;
    private final Configuration configuration;

    public MyBatisConfiguration() {
        this.environments = new HashMap<>();
        this.configuration = new Configuration();
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
}
