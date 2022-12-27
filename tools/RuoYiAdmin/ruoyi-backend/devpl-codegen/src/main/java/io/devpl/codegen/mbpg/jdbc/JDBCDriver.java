package io.devpl.codegen.mbpg.jdbc;

/**
 * JDBC驱动枚举类
 */
public enum JDBCDriver {

    MYSQL5("MySQL5", "msyql", "com.mysql.jdbc.Driver"),
    MYSQL8("MySQL8", "msyql", "com.mysql.cj.jdbc.Driver"),
    ORACLE("Oracle", "oracle:thin", "com.mysql.cj.jdbc.Driver"),
    SQL_SERVER("Microsoft SQL Server", "microsoft:sqlserver", "com.microsoft.jdbc.sqlserver.SQLServerDriver");

    public static final String PROTOCOL = "jdbc"; // JDBC协议名称

    private final String provider; // 厂商
    private final String subProtocol; // 子协议名：数据库类型协议
    private final String driverClassName; // 驱动类名
    private String description; // 描述信息

    JDBCDriver(String provider, String subProtocol, String driverClassName) {
        this.provider = provider;
        this.subProtocol = subProtocol;
        this.driverClassName = driverClassName;
    }

    public String getProvider() {
        return provider;
    }

    public String getSubProtocol() {
        return subProtocol;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getDescription() {
        return description;
    }

    public String getJdbcConnectionBaseUrl(String ip, int port, String nameOfDataBase) {
        return PROTOCOL + ":" + subProtocol + "://" + ip + ":" + port + "/" + nameOfDataBase;
    }
}
