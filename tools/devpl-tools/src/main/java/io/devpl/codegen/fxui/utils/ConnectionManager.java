package io.devpl.codegen.fxui.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
    private static final Logger _LOG = LoggerFactory.getLogger(ConnectionManager.class);
    private static final String DB_URL = "jdbc:sqlite:./config/sqlite3.db";

    public static Connection getConnection() throws Exception {
        Class.forName("org.sqlite.JDBC");
        File file = new File(DB_URL.substring("jdbc:sqlite:".length())).getAbsoluteFile();
        _LOG.info("database FilePath :{}", file.getAbsolutePath());
        return DriverManager.getConnection(DB_URL);
    }
}
