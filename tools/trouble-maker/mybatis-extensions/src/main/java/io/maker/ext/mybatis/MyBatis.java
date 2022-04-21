package io.maker.ext.mybatis;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MyBatis {

    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/db_mybatis?useSSL=false&serverTimezone=UTC";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
//    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://localhost:3306/mp?useSSL=false&serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "123456";

    //利用static属于类不属于对象，全局唯一
    private static final SqlSessionFactory sqlSessionFactory;

    //利用静态块在出书画时实例化sqlSessionFactory
    static {
        Reader reader;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private final Configuration configuration;

    public MyBatis() {
        this.configuration = new Configuration();
    }

    public void loadMapperStatements(String mapperXmlFile) {
        try (InputStream inputStream = new FileInputStream(mapperXmlFile)) {
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(inputStream, configuration, mapperXmlFile, configuration.getSqlFragments());
            xmlMapperBuilder.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void executeMapperStatement(Connection connection, String mapperStatementId, Map<String, Object> paramMap) {
        MappedStatement mappedStatement = configuration.getMappedStatement(mapperStatementId);
        BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(paramMap);
        System.out.println(boundSql.getSql());
        //横切结束
        try (PreparedStatement pstmt = connection.prepareStatement(boundSql.getSql())) {
            ParameterHandler parameterHandler = newParameterHandler(mappedStatement, paramMap);
            parameterHandler.setParameters(pstmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ParameterHandler newParameterHandler(MappedStatement ms, Map<String, Object> paramMap) {
        BoundSql boundSql = ms.getSqlSource().getBoundSql(paramMap);
        return configuration.newParameterHandler(ms, boundSql.getParameterObject(), boundSql);
    }

    /**
     * 执行SELECT查询SQL
     * @param func 要执行查询语句的代码块
     * @return 返回查询结果
     */
    public static Object executeQuery(Function<SqlSession, Object> func) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return func.apply(sqlSession);
        }
    }

    /**
     * 执行INSERT/UPDATE/DELETE写操作SQL
     * @param func 要执行的写操作代码块
     * @return 返回的结果对象
     * flase = autocommit 关闭
     */
    public static Object executeUpdate(Function<SqlSession, Object> func) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            Object obj = func.apply(sqlSession);
            sqlSession.commit();
            return obj;
        } catch (RuntimeException e) {
            sqlSession.rollback();
            throw (e);
        } finally {
            sqlSession.close();
        }
    }
}
