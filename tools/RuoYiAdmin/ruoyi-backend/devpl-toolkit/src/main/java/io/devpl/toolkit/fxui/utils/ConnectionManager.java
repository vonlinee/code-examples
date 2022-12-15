package io.devpl.toolkit.fxui.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库连接管理
 */
public class ConnectionManager {
    private static final Logger log = LoggerFactory.getLogger(ConnectionManager.class);
    private static final String DB_URL = "jdbc:sqlite:./config/sqlite3.db";

    static final HikariDataSource dataSource;

    private static HikariConfig initHikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/devpl");
        config.setUsername("root");
        config.setPassword("123456");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return config;
    }

    private static boolean embedConnection;

    static {
        final HikariConfig config = initHikariConfig();
        dataSource = new HikariDataSource(config);
        try {
            Class.forName("org.sqlite.JDBC");
            embedConnection = true;
        } catch (ClassNotFoundException e) {
            log.error("cannot load driver [org.sqlite.JDBC]");
        }
    }

    public static Connection getConnection() throws Exception {
        // Class.forName("org.sqlite.JDBC");
        // File file = new File(DB_URL.substring("jdbc:sqlite:".length())).getAbsoluteFile();
        // _LOG.info("database FilePath :{}", file.getAbsolutePath());
        // return DriverManager.getConnection(DB_URL);
        return dataSource.getConnection();
    }

    public static Connection getEmbedConnection() throws Exception {
        if (embedConnection) {
            throw new RuntimeException("failed to get embed db connection, cause the driver does not exist");
        }
        File file = new File(DB_URL.substring("jdbc:sqlite:".length())).getAbsoluteFile();
        log.info("database FilePath :{}", file.getAbsolutePath());
        return DriverManager.getConnection(DB_URL);
    }
}
