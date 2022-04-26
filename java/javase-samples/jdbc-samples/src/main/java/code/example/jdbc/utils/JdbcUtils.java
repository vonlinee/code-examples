package code.example.jdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

public final class JdbcUtils {

    public static final String MYSQL_URL_PREFIX = "jdbc:mysql://localhost:3306/";
    public static final String MYSQL_URL_SUFFIX = "?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
    public static final String USERNAME = "root";
    public static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    public static final String PASSWORD = "123456";

    private static boolean flag = false;

    static {
        flag = DbUtils.loadDriver(DRIVER_CLASS_NAME);
    }

    /**
     * java.sql.DataSource
     * 如果是连接池技术，都需要实现javax.sql.DataSource接口
     * @return Connection
     */
    public static Connection getConnection(String dataBaseName) {
        if (flag) {

        }
        return null;
    }

    public static Connection getConnection(String dbName, String ip, int port, String userName, String password) {
        try {
            return DriverManager.getConnection(MYSQL_URL_PREFIX + dbName + MYSQL_URL_SUFFIX, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection() {
        if (flag) {
            try {
                return DriverManager.getConnection(MYSQL_URL_PREFIX + "db_mysql" + MYSQL_URL_SUFFIX, USERNAME, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
