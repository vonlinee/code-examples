package io.pocket.base.jdbc.db;

import lombok.Data;

import java.util.Properties;

/**
 * Builder模式控制顺序
 */
@Data
public class DataSourceProperties {

    private DbType dbType;
    private String dbVersion;
    private String dbPoolVendor; //数据库连接池厂商
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String dbName;
    private String ip;
    private String port;

    public static DataSourceProperties builder() {
        return new DataSourceProperties();
    }

    public DataSourceProperties dbType(DbType dbVendor) {
        return this;
    }

    public DataSourceProperties dbPoolVendor(String dbPoolVendor) {
        this.dbPoolVendor = dbPoolVendor;
        return this;
    }

    public DataSourceProperties dbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
        return this;
    }

    public DataSourceProperties ip(String ip) {
        this.ip = ip;
        return this;
    }

    public DataSourceProperties port(int port) {
        this.port = String.valueOf(port);
        return this;
    }

    public DataSourceProperties username(String username) {
        this.username = username;
        return this;
    }

    public DataSourceProperties password(String password) {
        this.password = password;
        return this;
    }

    public DataSourceProperties dbName(String databaseName) {
        this.dbName = databaseName;
        return this;
    }

    public Properties build() {
        Properties prop = new Properties();
        prop.put("driverClassName", driverClassName);
        prop.put("url", url);
        prop.put("username", username);
        prop.put("password", password);
        return prop;
    }

    private String jdbcUrlPrefix(DbType dbType) {
        String urlPrefix = "";
        switch (dbType) {
            case MYSQL:
                urlPrefix = "jdbc:mysql://";
                break;
            case ORACLE:
                urlPrefix = "jdbc:oracle://";
            default:
                break;
        }
        return urlPrefix;
    }

    private void assembleUrl() {
        this.url = jdbcUrlPrefix(dbType) + ip + ":" + port + "/" + dbName + "?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
    }

    private void chooseDriverClassName() {
        String driverClassName = "";
        if (dbType == DbType.MYSQL) {
            if (dbVersion.startsWith("5")) {
                driverClassName = "com.mysql.jdbc.Driver";
            } else if (dbVersion.startsWith("8")) {
                driverClassName = "com.mysql.cj.jdbc.Driver";
            }
        } else if (dbType == DbType.ORACLE) {
            driverClassName = "";
        }
        this.driverClassName = driverClassName;
    }
}
