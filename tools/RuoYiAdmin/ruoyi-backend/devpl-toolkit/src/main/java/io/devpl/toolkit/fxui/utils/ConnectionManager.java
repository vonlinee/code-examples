package io.devpl.toolkit.fxui.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * 数据库连接管理
 */
public class ConnectionManager {
    private static final Logger _LOG = LoggerFactory.getLogger(ConnectionManager.class);
    private static final String DB_URL = "jdbc:sqlite:./config/sqlite3.db";

    static final HikariDataSource dataSource;

    private static HikariConfig config() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/devpl");
        config.setUsername("root");
        config.setPassword("123456");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return config;
    }

    static {
        final HikariConfig config = config();
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws Exception {
        // Class.forName("org.sqlite.JDBC");
        // File file = new File(DB_URL.substring("jdbc:sqlite:".length())).getAbsoluteFile();
        // _LOG.info("database FilePath :{}", file.getAbsolutePath());
        // return DriverManager.getConnection(DB_URL);
        return dataSource.getConnection();
    }
}
