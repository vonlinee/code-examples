package io.maker.generator.utils;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * 使用mybatis执行SQL
 * https://mybatis.org/mybatis-3/zh/java-api.html
 */
public class MyBatis {

    private final Configuration config;
    private SqlSessionFactory factory = null;

    private static final String MYBATIS_CONFIG_LOCATION = "mybatis/mybatis-config.xml";

    private String environmentId = "development";

    static {
        /**
         * SqlSessionFactory build(InputStream inputStream)
         * SqlSessionFactory build(InputStream inputStream, String environment)
         * SqlSessionFactory build(InputStream inputStream, Properties properties)
         * SqlSessionFactory build(InputStream inputStream, String env, Properties props)
         * SqlSessionFactory build(Configuration config)
         */
        //factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(MYBATIS_CONFIG_LOCATION));
    }

    public MyBatis(DataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment(environmentId, transactionFactory, dataSource);
        this.config = new Configuration();
        config.setLazyLoadingEnabled(true);
        config.setEnvironment(environment);
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        this.factory = builder.build(config);
    }

    public void registerAlias(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            config.getTypeAliasRegistry().registerAlias(clazz);
        }
    }

    public void addMapper(Class<?>... mappers) {
        for (Class<?> mapper : mappers) {
            config.addMapper(mapper);
        }
    }

    private SqlSessionFactory createSessionFactory() {
        return factory;
    }

    //SqlSession openSession()
    //SqlSession openSession(boolean autoCommit)
    //SqlSession openSession(Connection connection)
    //SqlSession openSession(TransactionIsolationLevel level)
    //SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level)
    //SqlSession openSession(ExecutorType execType)
    //SqlSession openSession(ExecutorType execType, boolean autoCommit)
    //SqlSession openSession(ExecutorType execType, Connection connection)
    //Configuration getConfiguration();
    private SqlSession openSession() {
        return createSessionFactory().openSession();
    }

    public <T> T selectOne(String id, Map<String, Object> param, Class<T> type) {
        return openSession().selectOne(id, param);
    }

    public <E> List<E> selectList(String id, Map<String, Object> param, Class<E> type) {
        return openSession().selectList(id, param);
    }
}

//COLLATE(COLLATION)
//https://blog.csdn.net/weixin_44167712/article/details/89883888