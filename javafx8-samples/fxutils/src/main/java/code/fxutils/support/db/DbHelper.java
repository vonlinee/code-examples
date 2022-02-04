package code.fxutils.support.db;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import code.fxutils.support.util.ResourceLoader;

import java.sql.*;
import java.util.*;

/**
 * https://cloud.tencent.com/developer/article/1581184
 * <p>
 * <p>
 * https://www.zhihu.com/question/20355738
 * 这么说吧，在关系型数据库中，分三级：database.schema.table。即一个数据库下面可以包含多个schema，
 * 一个schema下可以包含多个数据库对象，比如表、存储过程、触发器等。但并非所有数据库都实现了schema这一层，
 * 比如mysql直接把schema和database等效了，PostgreSQL、Oracle、SQL server等的schema也含义不太相同。
 * 所以说，关系型数据库中没有catalog的概念。但在一些其它地方（特别是大数据领域的一些组件）有catalog的概念，
 * 也是用来做层级划分的，一般是这样的层级关系：catalog.database.table。
 */
public final class DbHelper {

    private static final Logger LOG = LoggerFactory.getLogger(DbHelper.class);

    private static final String JDBC_PROPERTIES = "jdbc.properties";

    /**
     * 获取数据库连接
     * @return Connection
     */
    public static Optional<Connection> getOptionalConnection() {
        Properties prop = ResourceLoader.loadProperties(JDBC_PROPERTIES);
        boolean result = DbUtils.loadDriver(prop.getProperty("mysql.jdbc.driver"));
        Connection connection = null;
        if (result) {
            try {
                connection = DriverManager.getConnection(
                        prop.getProperty("mysql.jdbc.url"),
                        prop.getProperty("mysql.jdbc.username"),
                        prop.getProperty("mysql.jdbc.password"));
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
    public static Connection getConnection() {
        Properties prop = ResourceLoader.loadProperties(JDBC_PROPERTIES);
        boolean result = DbUtils.loadDriver(prop.getProperty("mysql.jdbc.driver"));
        Connection connection = null;
        if (result) {
            try {
                connection = DriverManager.getConnection(
                        prop.getProperty("mysql.jdbc.url"),
                        prop.getProperty("mysql.jdbc.username"),
                        prop.getProperty("mysql.jdbc.password"));
            } catch (SQLException e) {
                LOG.error("failed to get connection", e);
            }
        } else {
            LOG.error("failed to load driver");
        }
        return connection;
    }

    public static Map<String, String> getDbInfo() throws SQLException {
        HashMap<String, String> map = new HashMap<>();
        final Connection conn = getConnection();
        DatabaseMetaData dbMetaData = conn.getMetaData(); //获取数据库元数据
        map.put("productName", dbMetaData.getDatabaseProductName());//获取数据库产品名称
        map.put("productVersion", dbMetaData.getDatabaseProductVersion());//获取数据库版本号
        map.put("userName", dbMetaData.getUserName());//获取数据库用户名
        map.put("userUrl", dbMetaData.getURL());//获取数据库连接URL
        map.put("driverName", dbMetaData.getDriverName());//获取数据库驱动
        map.put("driverVersion", dbMetaData.getDriverVersion());//获取数据库驱动版本号
        map.put("isReadOnly", Boolean.toString(dbMetaData.isReadOnly()));//查看数据库是否允许读操作
        map.put("supportsTransactions", Boolean.toString(dbMetaData.supportsTransactions()));//查看数据库是否支持事务操作
        return map;
    }

    public static void getTableCatalogs(Connection conn) throws SQLException {
        //获取元数据
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getCatalogs(); //获取数据库列表
        //遍历获取所有数据库表
        while (rs.next()) {
            System.out.println(rs.getString(1)); //打印数据库名称
        }
        rs.close(); //释放资源
        conn.close();
    }

    /**
     * 获取数据库表信息
     * <p>
     * 在MySQL中，物理上schema和database是等价的。
     * 在MySQL SQL语法中你可以用SCHEMA这个关键字代替DATABASE关键字，比如用CREATE SCHEMA代替CREATE DATABASE。
     * @param dbName
     * @param conn
     * @throws Exception
     */
    public static void getTableMetaData(String dbName, Connection conn) throws Exception {
        DatabaseMetaData dbMetaData = conn.getMetaData();//获取数据库元数据
        //获取所有的数据库表信息
        ResultSet tableTypesResultSet = dbMetaData.getTableTypes();
        List<Map<String, Object>> list = resultSetToList(tableTypesResultSet);
        String[] tableTypes = {
                "LOCAL TEMPORARY",
                "SYSTEM TABLE",
                "SYSTEM VIEW",
                "TABLE",
                "VIEW"
        };
        //catalog, schemaPattern, tableNamePattern, String types[]
        ResultSet tablers = dbMetaData.getTables(null, null, null, tableTypes);
        while (tablers.next()) {
            System.out.println(tablers.getString(1)); //所属数据库
            System.out.println(tablers.getString(2)); //所属schema
            System.out.println(tablers.getString(3)); //表名
            System.out.println(tablers.getString(4)); //数据库表类型
            System.out.println(tablers.getString(5)); //数据库表备注
        }
    }

    private static List<Map<String, Object>> resultSetToList(final ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        while (rs.next()) {
            Map<String, Object> rowData = new HashMap<>();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
            }
            list.add(rowData);
        }
        return list;
    }

    public static void parameterMetaData(PreparedStatement pstmt) throws Exception {
        ParameterMetaData paramMetaData = pstmt.getParameterMetaData();//获取ParameterMetaData对象
        int paramCount = paramMetaData.getParameterCount();//获取参数个数
        System.out.println(paramCount);
    }

    public static void main(String[] args) throws Exception {
        Connection connection = getConnection();
        getTableMetaData("mybatis_learn", connection);
    }
}
