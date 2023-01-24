package io.devpl.toolkit.fxui.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

/**
 * 数据库连接管理器
 */
public class ConnectionManager {

    public static HikariDataSource dataSource;

    // static final String URL = "jdbc:h2:~/devpl";
    static final String URL = "jdbc:mysql://localhost:3306/devpl?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
    // static final String USERNAME = "sa";
    static final String USERNAME = "root";
    static final String PASSWORD = "123456";

    // static Server h2Server = null;

    static {
        // try {
        //     h2Server = new Server(new WebServer());
        //     h2Server.start();
        // } catch (SQLException e) {
        //     log.error("failed to start h2 database server, cause:", e);
        //     System.exit(0);
        // }
        // if (h2Server.isRunning(false)) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        dataSource = new HikariDataSource(config);
        // }
    }

    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    // public static void close() {
    //     dataSource.close();
    //     h2Server.shutdown();
    // }
}
