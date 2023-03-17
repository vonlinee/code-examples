package io.devpl.toolkit.utils;

import io.devpl.toolkit.codegen.JDBCDriver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.sql.*;
import java.util.*;

/**
 * 封装apache common-dbutils进行数据库操作
 */
public class DBUtils {

    private static final Log log = LogFactory.getLog(DBUtils.class);

    /**
     * 数据库连接超时时长
     */
    private static final int DB_CONNECTION_TIMEOUTS_SECONDS = 1;
    private static final Map<JDBCDriver, Driver> drivers = new HashMap<>();

    /**
     * 数据库驱动通过SPI自动加载，因此只需要提供url即可区分不同的数据库
     *
     * @param url      连接URL
     * @param username 用户名
     * @param password 密码
     * @return 数据库连接
     * @throws SQLException 连接失败
     */
    public static Connection getConnection(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 获取连接
     *
     * @param url        全部连接URL去掉参数的部分
     * @param properties 需要包含user和password两个key，其他JDBC连接属性可选
     * @return Connection
     * @throws SQLException 连接异常
     */
    public static Connection getConnection(String url, Properties properties) throws SQLException {
        return DriverManager.getConnection(url, properties);
    }

    public static void closeQuitely(Connection connection) {
        try {
            if (connection == null) {
                return;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接的所有数据库名
     *
     * @param connection 数据库连接对象
     * @return
     */
    public static List<String> getDatabaseNames(Connection connection) {
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            return extractOneColumn(String.class, dbmd.getCatalogs());
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    /**
     * 默认取第1列
     *
     * @param <T>       数据类型
     * @param type      数据类型
     * @param resultSet ResultSet
     * @return 一列作为List
     * @throws SQLException SQLException
     */
    public static <T> List<T> extractOneColumn(Class<T> type, ResultSet resultSet) throws SQLException {
        SingleColumnRowMapper<T> rowMapper = new SingleColumnRowMapper<>();
        List<T> result = new ArrayList<>();
        int rowIndex = 0;
        while (resultSet.next()) {
            result.add(rowMapper.mapRow(resultSet, rowIndex++));
        }
        return result;
    }
}
