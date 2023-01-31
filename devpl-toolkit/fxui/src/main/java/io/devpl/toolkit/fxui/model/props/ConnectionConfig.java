package io.devpl.toolkit.fxui.model.props;

import io.devpl.toolkit.fxui.common.JDBCDriver;
import io.devpl.toolkit.fxui.utils.DBUtils;
import lombok.Data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接配置
 */
@Data
public class ConnectionConfig {

    private String id;
    /**
     * 连接名称
     */
    private String name;
    private String dbType;
    private JDBCDriver driverInfo;
    private String host;
    private String port;
    /**
     * 数据库名称
     */
    private String schema;
    private String dbName;
    private String username;
    private String password;
    private String encoding;

    private Properties properties;

    public String getConnectionUrl() {
        JDBCDriver driver = JDBCDriver.valueOfDriverName(dbType);
        String databaseName = schema;
        if (databaseName == null) {
            databaseName = "";
        }
        assert driver != null;
        return driver.getConnectionUrl(host, port, databaseName, null);
    }

    public String getConnectionUrl(String databaseName) {
        JDBCDriver driver = JDBCDriver.valueOf(dbType);
        return driver.getConnectionUrl(host, port, databaseName, properties);
    }

    public String getConnectionUrl(String databaseName, Properties properties) {
        JDBCDriver driver = JDBCDriver.valueOf(dbType);
        return driver.getConnectionUrl(host, port, databaseName, properties);
    }

    public Connection getConnection(String databaseName, Properties properties) throws SQLException {
        String connectionUrl = getConnectionUrl(databaseName, properties);
        if (properties == null) {
            properties = new Properties();
            properties.put("user", username);
            properties.put("password", password);
            properties.put("serverTimezone", "UTC");
            properties.put("useUnicode", "true");
            properties.put("useSSL", "false");
            properties.put("characterEncoding", encoding);
        }
        return DBUtils.getConnection(connectionUrl, properties);
    }

    public Connection getConnection(String databaseName) throws SQLException {
        String connectionUrl = getConnectionUrl(databaseName);
        Properties properties = new Properties();
        properties.put("user", username);
        properties.put("password", password);
        properties.put("serverTimezone", "UTC");
        properties.put("useUnicode", "true");
        properties.put("useSSL", "false");
        properties.put("characterEncoding", encoding);
        return DBUtils.getConnection(connectionUrl, properties);
    }

    /**
     * 获取数据库连接
     * @return 数据库连接实例
     * @throws SQLException 获取连接失败
     */
    public Connection getConnection() throws SQLException {
        String connectionUrl = getConnectionUrl();
        Properties properties = new Properties();
        properties.put("user", username);
        properties.put("password", password);
        properties.put("serverTimezone", "UTC");
        properties.put("useUnicode", "true");
        properties.put("useSSL", "false");
        properties.put("characterEncoding", encoding);
        return DBUtils.getConnection(connectionUrl, properties);
    }

    public void fillConnectionNameIfEmpty() {
        String connectionName = name;
        if (connectionName == null || connectionName.isEmpty()) {
            connectionName = host + "_" + port;
            name = connectionName;
        }
    }
}
