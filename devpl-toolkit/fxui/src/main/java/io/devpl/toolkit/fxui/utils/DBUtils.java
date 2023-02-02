package io.devpl.toolkit.fxui.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import io.devpl.codegen.mbpg.jdbc.meta.ColumnMetadata;
import io.devpl.codegen.mbpg.jdbc.meta.TableMetadata;
import io.devpl.sdk.util.ResourceUtils;
import io.devpl.toolkit.fxui.common.JDBCDriver;
import io.devpl.toolkit.fxui.model.DatabaseInfo;
import io.devpl.toolkit.fxui.utils.ssh.JSchUtils;

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
    private static final QueryRunner runner = new QueryRunner();

    /**
     * 数据库驱动通过SPI自动加载，因此只需要提供url即可区分不同的数据库
     * @param url      连接URL
     * @param username 用户名
     * @param password 密码
     * @return 数据库连接
     * @throws SQLException 连接失败
     */
    public static Connection getConnection(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection getConnection(DatabaseInfo config) throws SQLException {
        JDBCDriver dbType = JDBCDriver.valueOf(config.getDbType());
        loadDbDriver(dbType);
        String url = JSchUtils.getConnectionUrlWithSchema(config);
        Properties props = new Properties();
        props.setProperty("user", config.getUsername()); //$NON-NLS-1$
        props.setProperty("password", config.getPassword()); //$NON-NLS-1$
        DriverManager.setLoginTimeout(DB_CONNECTION_TIMEOUTS_SECONDS);
        Connection connection = getConnection(url, props);
        if (connection == null) {
            throw new RuntimeException("获取连接失败");
        }
        return connection;
    }

    /**
     * 获取连接
     * @param url        全部连接URL去掉参数的部分
     * @param properties 需要包含user和password两个key，其他JDBC连接属性可选
     * @return Connection
     * @throws SQLException 连接异常
     */
    public static Connection getConnection(String url, Properties properties) throws SQLException {
        return DriverManager.getConnection(url, properties);
    }

    public static List<String> getAllJDBCDriverJarPaths() {
        List<String> jarFilePathList = new ArrayList<>();
        try {
            File file = ResourceUtils.getProjectFile("/lib");
            if (file == null) {
                return Collections.emptyList();
            }
            final File[] jarFiles = FileUtils.listAllFiles(file);
            for (File jarFile : jarFiles) {
                if (jarFile.isFile() && jarFile.getAbsolutePath().endsWith(".jar")) {
                    jarFilePathList.add(jarFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("找不到驱动文件，请联系开发者");
        }
        return jarFilePathList;
    }

    /**
     * 加载数据库驱动
     * @param dbType 数据库类型
     * @see JDBCDriver
     */
    private static void loadDbDriver(JDBCDriver dbType) {
        if (drivers.containsKey(dbType)) {
            return;
        }
        ClassLoader classloader = ResourceUtils.getClassLoader();
        try {
            Class<?> clazz = Class.forName(dbType.getDriverClassName(), true, classloader);
            Driver driver = (Driver) clazz.getConstructor().newInstance();
            drivers.putIfAbsent(dbType, driver);
        } catch (Exception e) {
            log.error("load driver error", e);
            throw new RuntimeException("找不到驱动");
        }
    }

    /**
     * 获取元数据
     * @param conn 连接
     * @return 元数据
     */
    public static List<TableMetadata> getTablesMetadata(Connection conn) {
        return getTablesMetadata(conn, null, null);
    }

    public static List<TableMetadata> getTablesMetadata(Connection conn, String[] types) {
        return getTablesMetadata(conn, null, types);
    }

    /**
     * 获取元数据
     * @param conn
     * @param tableNamePattern
     * @return
     */
    public static List<ColumnMetadata> getColumnsMetadata(Connection conn, String tableNamePattern) {
        try {
            final DatabaseMetaData dbmd = conn.getMetaData();
            final String catalog = conn.getCatalog();
            final String schema = conn.getSchema();
            try (ResultSet rs = dbmd.getColumns(catalog, schema, tableNamePattern, null)) {
                return BEAN_PROPERTY_ROW_PROCESSOR.toBeanList(rs, ColumnMetadata.class);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    /**
     * 连接时如果指定了数据库进行连接，则有数据，如果没有指定数据库进行连接，则无数据
     * @param conn             数据库连接
     * @param tableNamePattern 表名
     * @param types            表类型
     * @return
     */
    public static List<TableMetadata> getTablesMetadata(Connection conn, String tableNamePattern, String[] types) {
        List<TableMetadata> tmdList;
        try {
            final DatabaseMetaData dmd = conn.getMetaData();
            final String catalog = conn.getCatalog();
            final String schema = conn.getSchema();
            tmdList = getTablesMetadata(dmd, catalog, schema, tableNamePattern, types);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tmdList;
    }

    public static ResultSet executeQuery(Connection connection, String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public static int insert(Connection conn, String sql, Object... params) throws SQLException {
        Map<String, Object> map = runner.insert(conn, sql, new MapHandler(), params);
        if (map != null) {
            return 1;
        }
        return 0;
    }

    public static <T> T query(Connection connection, String sql, ResultSetHandler<T> handler) throws SQLException {
        return runner.query(connection, sql, handler);
    }

    public static List<Map<String, Object>> queryMapList(Connection connection, String sql) throws SQLException {
        return runner.query(connection, sql, new MapListHandler());
    }

    public static Object[] queryArray(Connection connection, String sql) throws SQLException {
        return runner.query(connection, sql, new ArrayHandler());
    }

    private static final BasicRowProcessor BEAN_PROPERTY_ROW_PROCESSOR = new BasicRowProcessor(new GenerousBeanProcessor());

    /**
     * 查询JavaBean组成的List
     * @param connection   数据库连接
     * @param sql          SQL
     * @param requiredType JavaBean类型
     * @param <T>          JavaBean类型
     * @return List<T>
     * @throws SQLException if a database access error occurs
     */
    public static <T> List<T> queryBeanList(Connection connection, String sql, Class<T> requiredType) throws SQLException {
        return runner.query(connection, sql, new BeanListHandler<>(requiredType, BEAN_PROPERTY_ROW_PROCESSOR));
    }

    public static List<Object[]> queryList(Connection connection, String sql) throws SQLException {
        return runner.query(connection, sql, new ArrayListHandler());
    }

    public static List<Map<String, Object>> toMapList(ResultSet resultSet) throws SQLException {
        return new MapListHandler().handle(resultSet);
    }

    public static <T> List<T> extractOneColumn(String columnName, Class<T> type, ResultSet resultSet) throws SQLException {
        ColumnListHandler<T> handler = new ColumnListHandler<>(columnName);
        return handler.handle(resultSet);
    }

    public static <T> List<T> extractOneColumn(int columnIndex, Class<T> type, ResultSet resultSet) throws SQLException {
        ColumnListHandler<T> handler = new ColumnListHandler<>(columnIndex);
        return handler.handle(resultSet);
    }

    /**
     * 默认取第1列
     * @param <T>
     * @param type
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public static <T> List<T> extractOneColumn(Class<T> type, ResultSet resultSet) throws SQLException {
        ColumnListHandler<T> handler = new ColumnListHandler<>(1);
        return handler.handle(resultSet);
    }

    public static List<TableMetadata> getTablesMetadata(DatabaseMetaData dbmd, String catalog, String schemaPattern, String tableNamePattern, String[] types) {
        try (ResultSet rs = dbmd.getTables(catalog, schemaPattern, tableNamePattern, types)) {
            return BEAN_PROPERTY_ROW_PROCESSOR.toBeanList(rs, TableMetadata.class);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
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

    public static List<String> getDatabaseNames(Connection connection) {
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            return extractOneColumn(String.class, dbmd.getCatalogs());
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
}
