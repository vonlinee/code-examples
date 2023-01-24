package io.devpl.toolkit.fxui.model.props;

import io.devpl.fxtras.mvc.ViewModel;
import io.devpl.toolkit.fxui.common.JDBCDriver;
import io.devpl.toolkit.fxui.utils.DBUtils;
import io.devpl.toolkit.fxui.utils.StringUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接信息
 */
public class ConnectionInfo implements ViewModel {

    private final StringProperty id = new SimpleStringProperty();
    /**
     * 连接名称
     */
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty dbType = new SimpleStringProperty();
    private final StringProperty host = new SimpleStringProperty();
    private final StringProperty port = new SimpleStringProperty();
    /**
     * 数据库名称
     */
    private final StringProperty schema = new SimpleStringProperty();
    private final StringProperty dbName = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty encoding = new SimpleStringProperty();

    public String getName() {
        final String connectionName = name.get();
        return StringUtils.hasText(connectionName) ? connectionName : host.get() + ":" + port.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDbType() {
        return dbType.get();
    }

    public StringProperty dbTypeProperty() {
        return dbType;
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setDbType(String dbType) {
        this.dbType.set(dbType);
    }

    public String getHost() {
        return host.get();
    }

    public StringProperty hostProperty() {
        return host;
    }

    public void setHost(String host) {
        this.host.set(host);
    }

    public String getPort() {
        return port.get();
    }

    public StringProperty portProperty() {
        return port;
    }

    public void setPort(String port) {
        this.port.set(port);
    }

    public String getSchema() {
        return schema.get();
    }

    public StringProperty schemaProperty() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema.set(schema);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getEncoding() {
        return encoding.get();
    }

    public StringProperty encodingProperty() {
        return encoding;
    }

    public String getDbName() {
        return dbName.get();
    }

    public StringProperty dbNameProperty() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName.set(dbName);
    }

    public void setEncoding(String encoding) {
        this.encoding.set(encoding);
    }

    @Override
    public String toString() {
        return "ConnectionInfo{" + "id=" + id.get() + ", dbName =" + dbName.get() + ", name=" + name.get() + ", dbType=" + dbType.get() + ", host=" + host.get() + ", port=" + port.get() + ", schema=" + schema.get() + ", username=" + username.get() + ", password=" + password.get() + ", encoding=" + encoding.get() + '}';
    }

    public String getConnectionUrl() {
        JDBCDriver driver = JDBCDriver.valueOf(dbType.get());
        String databaseName = schema.get();
        if (databaseName == null || databaseName.length() == 0) {
            databaseName = "";
        }
        return driver.getConnectionUrl(host.get(), port.get(), databaseName, null);
    }

    public String getConnectionUrl(String databaseName, Properties properties) {
        JDBCDriver driver = JDBCDriver.valueOf(dbType.get());
        return driver.getConnectionUrl(host.get(), port.get(), databaseName, properties);
    }

    public Connection getConnection(String databaseName, Properties properties) throws SQLException {
        String connectionUrl = getConnectionUrl(databaseName, properties);
        if (properties == null) {
            properties = new Properties();
            properties.put("user", username.get());
            properties.put("password", password.get());
            properties.put("serverTimezone", "UTC");
            properties.put("useUnicode", "true");
            properties.put("useSSL", "false");
            properties.put("characterEncoding", encoding.get());
        }
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
        properties.put("user", username.get());
        properties.put("password", password.get());
        properties.put("serverTimezone", "UTC");
        properties.put("useUnicode", "true");
        properties.put("useSSL", "false");
        properties.put("characterEncoding", encoding.get());
        return DBUtils.getConnection(connectionUrl, properties);
    }
}
