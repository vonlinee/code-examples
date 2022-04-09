package io.maker.generator.db;

import io.maker.generator.db.meta.resultset.ResultSetColumnMetadata;
import io.maker.generator.db.meta.resultset.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.ha.PropertiesUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.*;

import static java.sql.DriverManager.registerDriver;

/**
 * https://cloud.tencent.com/developer/article/1581184
 * <p>
 * https://www.zhihu.com/question/20355738
 * <p>
 * 这么说吧，在关系型数据库中，分三级：database.schema.table。即一个数据库下面可以包含多个schema，
 * 一个schema下可以包含多个数据库对象，比如表、存储过程、触发器等。但并非所有数据库都实现了schema这一层，
 * 比如mysql直接把schema和database等效了，PostgreSQL、Oracle、SQL server等的schema也含义不太相同。
 * 所以说，关系型数据库中没有catalog的概念。但在一些其它地方（特别是大数据领域的一些组件）有catalog的概念，
 * 也是用来做层级划分的，一般是这样的层级关系：catalog.database.table。 A collection of JDBC helper
 * methods. This class is thread safe.
 */
public final class JdbcUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcUtils.class);
    
    private static final String JDBC_PROPERTIES = "jdbc.properties";

    /**
     * 加载连接信息
     */
    private static final String DEFAULT_PROPERTIES_LOCATION = System.getProperty("user.home") + File.separator + JDBC_PROPERTIES;

    public static final String MYSQL5_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    public static final String MYSQL8_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String LOCAL_MYSQL_URL = "jdbc:mysql://localhost:3306/db_mysql?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
    public static final String LOCAL_USERNAME = "root";
    public static final String LOCAL_PASSWORD = "123456";

    public static final String ORACLE_DRIVER_CLASS_NAME = "oracle.jdbc.driver.OracleDriver";
    public static final String ORACLE_URL = "jdbc:oracle:thin:@192.168.12.44:1521:orcl";

    //从本地默认加载的连接信息
    private static final Properties props;

    static {
        props = getProperties(DEFAULT_PROPERTIES_LOCATION);
    }

    public static void setProperties(Map<String, String> map) {
        for (String key : map.keySet()) {
            props.setProperty(key, map.get(key));
        }
    }

    /**
     * 获取默认连接配置文件  DEFAULT_PROPERTIES_LOCATION
     * System.getProperty("user.home") + File.separator + JDBC_PROPERTIES
     * ${user.home}/jdbc.properties
     * @return Properties
     */
    public static Properties getLocalProperties() {
        return props;
    }

    /**
     * 从本地文件加载
     * @return Properties
     */
    public static Properties getProperties(String filePath) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Get schema. 不支持Oracle
     * @param connection   connection
     * @param databaseType database type
     * @return schema
     */
    public static String getSchema(final Connection connection, final String databaseType) {
        String result = null;
        try {
            if ("Oracle".equalsIgnoreCase(databaseType)) {
                return null;
            }
            result = connection.getSchema();
        } catch (final SQLException ignore) {
            LOG.error("failed to get schema of databaseType {}", databaseType);
        }
        return result;
    }

    /**
     * TODO query
     * @param conn
     * @param sql
     * @param <T>
     * @return
     */
    public static <T> T query(Connection conn, String sql) {
        try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            return null;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static <T> T query(Connection conn, String sql, ResultSetHandler<T> handler) {
        try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            LOG.debug("\nQuery : " + sql);
            return handler.handle(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * isTableExist
     * @param connection   Connection
     * @param catalog      catalog
     * @param table        tableName
     * @param databaseType databaseType
     * @return boolean
     * @throws SQLException
     */
    public static boolean isTableExist(final Connection connection, final String catalog, final String table, final String databaseType) throws SQLException {
        try (ResultSet resultSet = connection.getMetaData().getTables(catalog, getSchema(connection, databaseType), table, null)) {
            return resultSet.next();
        }
    }

    /**
     * 查询，封装结果
     * @param conn
     * @param sql
     * @param args
     * @return
     */
    public static List<Map<String, Object>> queryMapping(Connection conn, String sql, Object... args) {
        List<Map<String, Object>> table = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                LOG.debug("execute sql : \n" + sql);
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnCount = rsmd.getColumnCount();
                int i = 1;
                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>(columnCount);
                    for (int j = 0; j < columnCount; j++) {
                        if (resultSet.wasNull()) {

                        }
                    }
                    table.add(row);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return table;
    }

    /**
     * 使用PreparedStatement进行查询
     * @param conn
     * @param sql
     * @param handler
     * @param args
     * @param <T>
     * @return
     */
    public static <T> T prepareQuery(Connection conn, String sql, ResultSetHandler<T> handler, Object... args) {
        List<Map<String, Object>> table = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ParameterMetaData pmd = pstmt.getParameterMetaData();
            int paramCount = pmd.getParameterCount();
            if (args.length != paramCount) {
                throw new RuntimeException("prepared sql (" + sql + ") needs param size is " + "" + paramCount + ", but provided " + args.length);
            }
            for (int i = 0; i < paramCount; i++) {
                int parameterType = pmd.getParameterType(i);
                if (parameterType == Types.VARCHAR) {
                    pstmt.setString(i, (String) args[i]);
                }
            }
            return handler.handle(pstmt.executeQuery(sql));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * 获取DatabaseMetaData
     * @param dataSource
     * @return
     * @throws SQLException
     */
    public static DatabaseMetaData getDatabaseMeta(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return connection.getMetaData();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * 获取DatabaseMetaData
     * @param conn
     * @return
     * @throws SQLException
     */
    public static DatabaseMetaData getDatabaseMeta(Connection conn) throws SQLException {
        return conn.getMetaData();
    }

    /**
     * 获取数据库元数据
     * @param dbmd
     * @return
     * @throws SQLException
     */
    public static Map<String, Object> parseDatabaseMetaData(DatabaseMetaData dbmd) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        map.put("productName", dbmd.getDatabaseProductName());// 获取数据库产品名称
        map.put("productVersion", dbmd.getDatabaseProductVersion());// 获取数据库版本号
        map.put("userName", dbmd.getUserName());// 获取数据库用户名
        map.put("userUrl", dbmd.getURL());// 获取数据库连接URL
        map.put("driverName", dbmd.getDriverName());// 获取数据库驱动
        map.put("driverVersion", dbmd.getDriverVersion());// 获取数据库驱动版本号
        map.put("isReadOnly", dbmd.isReadOnly());// 查看数据库是否允许读操作
        map.put("supportsTransactions", dbmd.supportsTransactions());// 查看数据库是否支持事务操作
        map.put("catalogTerm", dbmd.getCatalogTerm());
        map.put("allProceduresAreCallable", dbmd.allProceduresAreCallable());
        map.put("allTablesAreSelectable", dbmd.allTablesAreSelectable());
        map.put("generatedKeyAlwaysReturned", dbmd.generatedKeyAlwaysReturned());
        return map;
    }

    /**
     * 获取ResultSet结果表的字段信息，ResultSet包含结果集合
     * @param rsmd ResultSetMetaData
     * @return Map<String, Object> key根据ResultSetMetaData的属性确定
     */
    public static List<ResultSetColumnMetadata> extractResultSetMetaInformation(ResultSetMetaData rsmd) {
        List<ResultSetColumnMetadata> resultSetDataList = new ArrayList<>();
        try {
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                resultSetDataList.add(new ResultSetColumnMetadata(rsmd, i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSetDataList;
    }

    /**
     * 提取ResultSet数据
     * @param resultSet
     * @return
     */
    public static List<Map<String, Object>> extractResultSetData(ResultSet resultSet) {
        List<Map<String, Object>> resultSetRows = new ArrayList<>();
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            Map<String, Object> row = new HashMap<>(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                row.put(rsmd.getColumnName(i), resultSet.getObject(i));
                resultSetRows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSetRows;
    }

    /**
     * 获取参数元数据
     * @param pstmt
     * @throws Exception
     */
    public static void parameterMetaData(PreparedStatement pstmt) throws Exception {
        ParameterMetaData paramMetaData = pstmt.getParameterMetaData();// 获取ParameterMetaData对象
        int paramCount = paramMetaData.getParameterCount();// 获取参数个数
        pstmt.close();
        System.out.println(paramCount);
    }

    /**
     * TODO 注意，此方法内部不关闭Connection
     * @param conn
     * @return
     */
    public static Map<String, Object> getTableCatalogs(Connection conn) {
        try (ResultSet resultSet = conn.getMetaData().getCatalogs()) {
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HashMap<>(0);
    }

    /**
     * 获取数据库连接
     * @return Connection
     */
    public static Optional<Connection> getConnection(String dbName, Properties prop) {
        boolean result = loadDriver(prop.getProperty("jdbc.driver"));
        Connection connection = null;
        if (result) {
            try {
                connection = DriverManager.getConnection(prop.getProperty("jdbc.url"), prop.getProperty("jdbc.username"), prop.getProperty("jdbc.password"));
            } catch (SQLException e) {
                LOG.error("failed to get connection", e);
            }
        } else {
            LOG.error("failed to load driver");
        }
        return Optional.ofNullable(connection);
    }

    /**
     * 获取数据库连接
     * @return Connection
     */
    public static Optional<Connection> getConnection(String driver, String url, String userName, String password) {
        Connection connection = null;
        boolean result = loadDriver(driver);
        if (result) {
            try {
                connection = DriverManager.getConnection(url, userName, password);
            } catch (SQLException e) {
                LOG.error("failed to get connection", e);
            }
        } else {
            LOG.error("failed to load driver");
        }
        return Optional.ofNullable(connection);
    }

    /**
     * 获取数据库
     * @param properties
     * @return
     */
    public static Optional<Connection> getConnection(Properties properties) {
        Connection connection = null;
        boolean result = loadDriver(properties.getProperty("driverClassName"));
        if (result) {
            try {
                connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
            } catch (SQLException e) {
                LOG.error("failed to get connection", e);
            }
        } else {
            LOG.error("failed to load driver");
        }
        return Optional.ofNullable(connection);
    }
    
    public static Connection getLocalMySQLConnection(String dbName, Map<String, String> params) throws SQLException {
    	return getConnection("mysql", "localhost", 3306, dbName, params);
    }
    
    /**
     * 获取本地MySQL数据库连接
     * @param dbName
     * @return
     * @throws SQLException
     * Connection
     */
    public static Connection getLocalMySQLConnection(String dbName) throws SQLException {
    	return getConnection("mysql", "localhost", 3306, dbName, defaultConnectionParams());
    }
    
    /**
     * 默认连接参数设置
     * createDatabaseIfNotExists=true&useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
     * @return
     * Map<String,String>
     */
    public static Map<String, String> defaultConnectionParams() {
    	Map<String, String> map = new HashMap<>();
    	map.put("useSSL", "false");
    	map.put("createDatabaseIfNotExists", "true"); //是否创建数据库
    	map.put("useUnicode", "true"); 
    	map.put("characterEncoding", "utf8");
    	map.put("serverTimezone", "GMT%2B8");
    	return map;
    }
    
    public static Connection getConnection(String dbType, String ip, int port, String dbName, Map<String, String> params) throws SQLException {
    	StringJoiner paramString = new StringJoiner("&");
    	for(Map.Entry<String, String> entry : params.entrySet()) {
    		paramString.add(entry.getKey() + "=" + entry.getValue());
    	}
    	String url = "jdbc:" + dbType + "://" + ip + ":" + port + "/" + dbName + "?" + paramString.toString();
    	try {
			return DriverManager.getConnection(url, "root", "123456");
		} catch (SQLException e) {
			throw e;
		}
    }

    /**
     * 获取数据库表信息
     * 在MySQL中，物理上schema和database是等价的。 在MySQL
     * SQL语法中你可以用SCHEMA这个关键字代替DATABASE关键字，比如用CREATE SCHEMA代替CREATE DATABASE。
     * @param dbName
     * @param conn
     * @throws Exception
     */
    public static Map<String, Object> getTableMetaData(String dbName, Connection conn) throws Exception {
        final Map<String, Object> map = new HashMap<>();
        DatabaseMetaData dbMetaData = conn.getMetaData();// 获取数据库元数据
        // 获取所有的数据库表信息
        ResultSet tableTypesResultSet = dbMetaData.getTableTypes();
        List<Map<String, Object>> list = resultSetToList(tableTypesResultSet);
        String[] tableTypes = {"LOCAL TEMPORARY", "SYSTEM TABLE", "SYSTEM VIEW", "TABLE", "VIEW"};
        // catalog, schemaPattern, tableNamePattern, String types[]
        ResultSet tablers = dbMetaData.getTables(null, null, null, tableTypes);
        while (tablers.next()) { //结果只有一行
            System.out.println(tablers.getString(1)); // 所属数据库
            System.out.println(tablers.getString(2)); // 所属schema
            System.out.println(tablers.getString(3)); // 表名
            System.out.println(tablers.getString(4)); // 数据库表类型
            System.out.println(tablers.getString(5)); // 数据库表备注
        }
        return map;
    }

    /**
     * ResultSet转换为List
     * @param rs
     * @return
     * @throws SQLException
     */
    private static List<Map<String, Object>> resultSetToList(final ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();// 获取键名
        int columnCount = md.getColumnCount();// 获取行的数量
        while (rs.next()) {
            Map<String, Object> rowData = new HashMap<>();// 声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));// 获取键名及值
            }
            list.add(rowData);
        }
        return list;
    }

    /**
     * Close a <code>Connection</code>, avoid closing if null.
     * @param conn Connection to close.
     * @throws SQLException if a database access error occurs
     */
    public static void close(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * Close a <code>ResultSet</code>, avoid closing if null.
     * @param rs ResultSet to close.
     * @throws SQLException if a database access error occurs
     */
    public static void close(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    /**
     * Close a <code>Statement</code>, avoid closing if null.
     * @param stmt Statement to close.
     * @throws SQLException if a database access error occurs
     */
    public static void close(Statement stmt) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
    }

    /**
     * Close a <code>Connection</code>, avoid closing if null and hide any
     * SQLExceptions that occur.
     * @param conn Connection to close.
     */
    public static void closeQuietly(Connection conn) {
        try {
            close(conn);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }

    /**
     * Close a <code>Connection</code>, <code>Statement</code> and
     * <code>ResultSet</code>. Avoid closing if null and hide any SQLExceptions that
     * occur.
     * @param conn Connection to close.
     * @param stmt Statement to close.
     * @param rs   ResultSet to close.
     */
    public static void closeQuietly(Connection conn, Statement stmt, ResultSet rs) {
        try {
            closeQuietly(rs);
        } finally {
            try {
                closeQuietly(stmt);
            } finally {
                closeQuietly(conn);
            }
        }
    }

    /**
     * Close a <code>ResultSet</code>, avoid closing if null and hide any
     * SQLExceptions that occur.
     * @param rs ResultSet to close.
     */
    public static void closeQuietly(ResultSet rs) {
        try {
            close(rs);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }

    /**
     * Close a <code>Statement</code>, avoid closing if null and hide any
     * SQLExceptions that occur.
     * @param stmt Statement to close.
     */
    public static void closeQuietly(Statement stmt) {
        try {
            close(stmt);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }

    /**
     * Commits a <code>Connection</code> then closes it, avoid closing if null.
     * @param conn Connection to close.
     * @throws SQLException if a database access error occurs
     */
    public static void commitAndClose(Connection conn) throws SQLException {
        if (conn != null) {
            try {
                conn.commit();
            } finally {
                conn.close();
            }
        }
    }

    /**
     * Commits a <code>Connection</code> then closes it, avoid closing if null and
     * hide any SQLExceptions that occur.
     * @param conn Connection to close.
     */
    public static void commitAndCloseQuietly(Connection conn) {
        try {
            commitAndClose(conn);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }

    /**
     * Loads and registers a database driver class. If this succeeds, it returns
     * true, else it returns false.
     * @param driverClassName of driver to load
     * @return boolean <code>true</code> if the driver was found, otherwise
     * <code>false</code>
     */
    public static boolean loadDriver(String driverClassName) {
        return loadDriver(JdbcUtils.class.getClassLoader(), driverClassName);
    }

    /**
     * Loads and registers a database driver class. If this succeeds, it returns
     * true, else it returns false.
     * @param classLoader     the class loader used to load the driver class
     * @param driverClassName of driver to load
     * @return boolean <code>true</code> if the driver was found, otherwise
     * <code>false</code>
     * @since 1.4
     */
    public static boolean loadDriver(ClassLoader classLoader, String driverClassName) {
        try {
            Class<?> loadedClass = classLoader.loadClass(driverClassName);
            if (!Driver.class.isAssignableFrom(loadedClass)) {
                return false;
            }
            @SuppressWarnings("unchecked") // guarded by previous check
            Class<Driver> driverClass = (Class<Driver>) loadedClass;
            Constructor<Driver> driverConstructor = driverClass.getConstructor();
            // make Constructor accessible if it is private
            boolean isConstructorAccessible = driverConstructor.isAccessible();
            if (!isConstructorAccessible) {
                driverConstructor.setAccessible(true);
            }
            try {
                Driver driver = driverConstructor.newInstance();
                registerDriver(new DriverProxy(driver));
            } finally {
                driverConstructor.setAccessible(isConstructorAccessible);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Print the stack trace for a SQLException to STDERR.
     * @param e SQLException to print stack trace of
     */
    public static void printStackTrace(SQLException e) {
        printStackTrace(e, new PrintWriter(System.err));
    }

    /**
     * Print the stack trace for a SQLException to a specified PrintWriter.
     * @param e  SQLException to print stack trace of
     * @param pw PrintWriter to print to
     */
    public static void printStackTrace(SQLException e, PrintWriter pw) {
        SQLException next = e;
        while (next != null) {
            next.printStackTrace(pw);
            next = next.getNextException();
            if (next != null) {
                pw.println("Next SQLException:");
            }
        }
    }

    /**
     * Print warnings on a Connection to STDERR.
     * @param conn Connection to print warnings from
     */
    public static void printWarnings(Connection conn) {
        printWarnings(conn, new PrintWriter(System.err));
    }

    /**
     * Print warnings on a Connection to a specified PrintWriter.
     * @param conn Connection to print warnings from
     * @param pw   PrintWriter to print to
     */
    public static void printWarnings(Connection conn, PrintWriter pw) {
        if (conn != null) {
            try {
                printStackTrace(conn.getWarnings(), pw);
            } catch (SQLException e) {
                printStackTrace(e, pw);
            }
        }
    }

    /**
     * Rollback any changes made on the given connection.
     * @param conn Connection to rollback. A null value is legal.
     * @throws SQLException if a database access error occurs
     */
    public static void rollback(Connection conn) throws SQLException {
        if (conn != null) {
            conn.rollback();
        }
    }

    /**
     * Performs a rollback on the <code>Connection</code> then closes it, avoid
     * closing if null.
     * @param conn Connection to rollback. A null value is legal.
     * @throws SQLException if a database access error occurs
     * @since DbUtils 1.1
     */
    public static void rollbackAndClose(Connection conn) throws SQLException {
        if (conn != null) {
            try {
                conn.rollback();
            } finally {
                conn.close();
            }
        }
    }

    /**
     * Performs a rollback on the <code>Connection</code> then closes it, avoid
     * closing if null and hide any SQLExceptions that occur.
     * @param conn Connection to rollback. A null value is legal.
     * @since DbUtils 1.1
     */
    public static void rollbackAndCloseQuietly(Connection conn) {
        try {
            rollbackAndClose(conn);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }

    /**
     * Simple {@link Driver} proxy class that proxies a JDBC Driver loaded
     * dynamically.
     * @since 1.6
     */
    private static final class DriverProxy implements Driver {

        private boolean parentLoggerSupported = true;

        /**
         * The adapted JDBC Driver loaded dynamically.
         */
        private final Driver adapted;

        /**
         * Creates a new JDBC Driver that adapts a JDBC Driver loaded dynamically.
         * @param adapted the adapted JDBC Driver loaded dynamically.
         */
        public DriverProxy(Driver adapted) {
            this.adapted = adapted;
        }

        @Override
        public boolean acceptsURL(String url) throws SQLException {
            return adapted.acceptsURL(url);
        }

        @Override
        public Connection connect(String url, Properties info) throws SQLException {
            return adapted.connect(url, info);
        }

        @Override
        public int getMajorVersion() {
            return adapted.getMajorVersion();
        }

        @Override
        public int getMinorVersion() {
            return adapted.getMinorVersion();
        }

        @Override
        public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
            return adapted.getPropertyInfo(url, info);
        }

        @Override
        public boolean jdbcCompliant() {
            return adapted.jdbcCompliant();
        }

        @Override
        public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return null;
        }
    }
}
