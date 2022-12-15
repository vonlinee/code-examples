package io.devpl.toolkit.fxui.config;

/**
 * 数据库驱动类型
 */
public enum DBDriver {

    MySQL5("com.mysql.jdbc.Driver", "jdbc:mysql://%s:%s/%s?useUnicode=true&useSSL=false&characterEncoding=%s", "mysql-connector-java-5.1.38.jar"),
    MySQL8("com.mysql.cj.jdbc.Driver", "jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useUnicode=true&useSSL=false&characterEncoding=%s", "mysql-connector-java-8.0.11.jar"),
    ORACLE("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@//%s:%s/%s", "ojdbc6.jar"),
    POSTGRE_SQL("org.postgresql.Driver", "jdbc:postgresql://%s:%s/%s", "postgresql-9.4.1209.jar"),
    SQL_SERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://%s:%s;databaseName=%s", "sqljdbc4-4.0.jar"),
    SQLITE("org.sqlite.JDBC", "jdbc:sqlite:%s", "sqlite-jdbc-3.19.3.jar");

    // 驱动类全类名
    private String driverClass;
    // 连接URL模式串
    private String connectionUrlPattern;
    // 驱动Jar文件
    private String connectorJarFile;
    private String description;

    DBDriver(String driverClass, String connectionUrlPattern, String connectorJarFile) {
        this.driverClass = driverClass;
        this.connectionUrlPattern = connectionUrlPattern;
        this.connectorJarFile = connectorJarFile;
    }

    public static final DBDriver DEFAULT_DRIVER = DBDriver.MySQL5;

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setConnectionUrlPattern(String connectionUrlPattern) {
        this.connectionUrlPattern = connectionUrlPattern;
    }

    public void setConnectorJarFile(String connectorJarFile) {
        this.connectorJarFile = connectorJarFile;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getConnectionUrlPattern() {
        return connectionUrlPattern;
    }

    public String getConnectorJarFile() {
        return connectorJarFile;
    }

    public static String[] supportedDbNames() {
        final String[] names = new String[values().length];
        final DBDriver[] drivers = values();
        for (int i = 0; i < drivers.length; i++) {
            names[i] = drivers[i].name();
        }
        return names;
    }

    public boolean nameEquals(String name) {
        return this.name().equals(name);
    }
}