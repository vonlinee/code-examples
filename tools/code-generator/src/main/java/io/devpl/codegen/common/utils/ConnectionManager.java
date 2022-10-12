package io.devpl.codegen.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionManager.class);
    private static final String DB_URL = "jdbc:sqlite:./config/sqlite3.db";

    private static final String DRIVER_CLASS_NAME = "org.sqlite.JDBC";

    private static final File file = new File(DB_URL.substring("jdbc:sqlite:".length())).getAbsoluteFile();

    static {
        loadDriver(DRIVER_CLASS_NAME);
    }

    public static void loadDriver(String driverClassName) {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException exception) {
            LOG.error("failed to load driver {}", driverClassName);
            System.exit(0);
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        try {
            properties.load(Resources.fromClasspath("jdbc.properties").openStream());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, password);
    }
}
