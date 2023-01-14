package io.devpl.toolkit.fxui.model.props;

import io.devpl.toolkit.fxui.common.Constants;
import io.devpl.toolkit.fxui.common.DBDriver;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 连接信息
 */
public class ConnectionInfo {

    // 连接名称
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty dbType = new SimpleStringProperty(DBDriver.MySQL5.name());
    private final StringProperty host = new SimpleStringProperty("127.0.0.1");
    private final IntegerProperty port = new SimpleIntegerProperty(3306);
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

    public int getPort() {
        return port.get();
    }

    public IntegerProperty portProperty() {
        return port;
    }

    public void setPort(int port) {
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
}
