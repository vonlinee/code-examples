package io.devpl.toolkit.fxui.model.props;

import io.devpl.toolkit.framework.mvc.ViewModel;
import io.devpl.toolkit.fxui.common.Constants;
import io.devpl.toolkit.fxui.common.JdbcDriver;
import io.devpl.toolkit.fxui.utils.DBUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接信息
 */
public class ConnectionInfo implements ViewModel {

    // 连接名称
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty dbType = new SimpleStringProperty(JdbcDriver.MySQL5.name());
    private final StringProperty host = new SimpleStringProperty("127.0.0.1");
    private final StringProperty port = new SimpleStringProperty("3306");
    private final StringProperty schema = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty("root");
    private final StringProperty password = new SimpleStringProperty("123456");
    private final StringProperty encoding = new SimpleStringProperty(Constants.DEFAULT_ENCODING);

    public String getName() {
        return name.get();
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

    public void setEncoding(String encoding) {
        this.encoding.set(encoding);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConnectionInfo{");
        sb.append("name=").append(name.get());
        sb.append(", dbType=").append(dbType.get());
        sb.append(", host=").append(host.get());
        sb.append(", port=").append(port.get());
        sb.append(", schema=").append(schema.get());
        sb.append(", username=").append(username.get());
        sb.append(", password=").append(password.get());
        sb.append(", encoding=").append(encoding.get());
        sb.append('}');
        return sb.toString();
    }

    public String getConnectionUrl() {
        JdbcDriver driver = JdbcDriver.valueOf(dbType.get());
        String databaseName = schema.get();
        if (databaseName == null || databaseName.length() == 0) {
            databaseName = "";
        }
        return String.format(driver.getConnectionUrlPattern(), host.get(), port.get(), databaseName);
    }

    public Connection getConnection() throws SQLException {
        String connectionUrl = getConnectionUrl();
        final Properties properties = new Properties();
        properties.put("user", username.get());
        properties.put("password", password.get());
        properties.put("serverTimezone", "UTC");
        properties.put("useUnicode", "true");
        properties.put("useSSL", "false");
        properties.put("characterEncoding", encoding.get());
        return DBUtils.getConnection(connectionUrl, properties);
    }
}
