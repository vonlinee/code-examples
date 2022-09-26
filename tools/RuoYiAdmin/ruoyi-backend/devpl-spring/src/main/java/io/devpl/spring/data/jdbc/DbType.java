package io.devpl.spring.data.jdbc;

/**
 * 常见的数据库类型枚举
 */
public enum DbType {

    /**
     * Unknown type.
     */
    UNKNOWN("null", "null", "null", "null", "null", "null"),

    /**
     * Apache Derby.
     */
    DERBY("Apache Derby", "org.apache.derby.jdbc.EmbeddedDriver", "org.apache.derby.jdbc.EmbeddedXADataSource",
            "SELECT 1 FROM SYSIBM.SYSDUMMY1", "jdbc:derby", ""),

    /**
     * H2.
     */
    H2("H2", "org.h2.Driver", "org.h2.jdbcx.JdbcDataSource", "SELECT 1", "jdbc:microsoft:sqlserver://", ""),

    /**
     * HyperSQL DataBase.
     */
    HSQLDB("HSQL Database Engine", "org.hsqldb.jdbc.JDBCDriver", "org.hsqldb.jdbc.pool.JDBCXADataSource",
            "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SYSTEM_USERS", "jdbc:microsoft:sqlserver://", ""),

    /**
     * SQL Lite.
     */
    SQLITE("SQLite", "org.sqlite.JDBC", "", "", "jdbc:microsoft:sqlserver://", ""),

    /**
     * MySQL8.
     */
    MYSQL8("MySQL8", "com.mysql.cj.jdbc.Driver", "com.mysql.cj.jdbc.MysqlXADataSource", "/* ping */ SELECT 1",
            "jdbc:mysql://", ""),

    /**
     * MySQL5.
     */
    MySQL5("MySQL5", "com.mysql.cj.jdbc.Driver", "com.mysql.jdbc.MysqlXADataSource", "/* ping */ SELECT 1",
            "jdbc:mysql://", ""),

    /**
     * Maria DB.
     */
    MARIADB("MySQL", "org.mariadb.jdbc.Driver", "org.mariadb.jdbc.MariaDbDataSource", "SELECT 1", "jdbc:mysql://", ""),

    /**
     * Google App Engine.
     */
    GAE(null, "com.google.appengine.api.rdbms.AppEngineDriver", "", "", "jdbc:microsoft:sqlserver://", ""),

    /**
     * Oracle.
     */
    ORACLE("Oracle", "oracle.jdbc.OracleDriver", "oracle.jdbc.xa.client.OracleXADataSource", "SELECT 'Hello' from DUAL",
            "jdbc:oracle:thin:@//", "Oracle11及以下数据库"),

    /**
     * Postgres.
     */
    POSTGRESQL("PostgreSQL", "org.postgresql.Driver", "org.postgresql.xa.PGXADataSource", "SELECT 1", "", ""),

    /**
     * Amazon Redshift.
     * @since 2.2.0
     */
    REDSHIFT("Amazon Redshift", "com.amazon.redshift.jdbc.Driver", null, "SELECT 1", "", ""),

    /**
     * HANA - SAP HANA Database - HDB.
     * @since 2.1.0
     */
    HANA("HDB", "com.sap.db.jdbc.Driver", "com.sap.db.jdbcext.XADataSourceSAP", "SELECT 1 FROM SYS.DUMMY", "", ""),

    /**
     * jTDS. As it can be used for several databases, there isn't a single product
     * name we could rely on.
     */
    JTDS(null, "net.sourceforge.jtds.jdbc.Driver", "", "", "", ""),

    /**
     * SQL Server.
     */
    SQLSERVER("Microsoft SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver",
            "com.microsoft.sqlserver.jdbc.SQLServerXADataSource", "SELECT 1", "jdbc:microsoft:sqlserver://", ""),

    /**
     * Firebird.
     */
    FIREBIRD("Firebird", "org.firebirdsql.jdbc.FBDriver", "org.firebirdsql.ds.FBXADataSource",
            "SELECT 1 FROM RDB$DATABASE", "", ""),

    /**
     * DB2 Server.
     */
    DB2("DB2", "com.ibm.db2.jcc.DB2Driver", "com.ibm.db2.jcc.DB2XADataSource", "SELECT 1 FROM SYSIBM.SYSDUMMY1",
            "jdbc:db2://", ""),

    /**
     * DB2 AS400 Server.
     */
    DB2_AS400("DB2 UDB for AS/400", "com.ibm.as400.access.AS400JDBCDriver",
            "com.ibm.as400.access.AS400JDBCXADataSource", "SELECT 1 FROM SYSIBM.SYSDUMMY1", "", ""),

    /**
     * Teradata.
     */
    TERADATA("Teradata", "com.teradata.jdbc.TeraDriver", "", "", "", ""),

    /**
     * Informix.
     */
    INFORMIX("Informix Dynamic Server", "com.informix.jdbc.IfxDriver", null, "", "select count(*) from systables", "");

    private final String name;
    private final String driverClassName;
    private final String xaDataSourceClassName;
    private final String validationQuery;

    /**
     * URL=jdbc:mysql://[host][:port]/[database]
     * 一般规则 JDBC的URL ＝协议名＋子协议名＋数据源名。
     */
    private final String protocol;

    /**
     * 注释信息
     */
    private final String remark;

    DbType(String name, String driverClassName, String xaDataSourceClassName, String validationQuery, String protocol, String remark) {
        this.name = name;
        this.driverClassName = driverClassName;
        this.xaDataSourceClassName = xaDataSourceClassName;
        this.validationQuery = validationQuery;
        this.protocol = protocol;
        this.remark = remark;
    }

    public static DbType valueOfName(String name) {
        for (DbType type : DbType.values()) {
            if (type.getName().equalsIgnoreCase(String.valueOf(name))) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public String getName() {
        return name;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getXaDataSourceClassName() {
        return xaDataSourceClassName;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getRemark() {
        return remark;
    }
}
