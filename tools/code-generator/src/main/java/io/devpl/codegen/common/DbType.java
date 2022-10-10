package io.devpl.codegen.common;

import java.util.*;

/**
 * Enumeration of common database type.
 * 以productName字段作为唯一标识，而不是枚举类的name()方法
 */
public enum DbType {

    /**
     * Unknown type.
     */
    UNKNOWN(null, null, null, null, null, "", ""),

    /**
     * Apache Derby.
     */
    DERBY("Apache Derby", "org.apache.derby.jdbc.EmbeddedDriver", "org.apache.derby.jdbc.EmbeddedXADataSource",
            "SELECT 1 FROM SYSIBM.SYSDUMMY1", "jdbc", "", ""),

    /**
     * H2.
     */
    H2("H2", "org.h2.Driver", "org.h2.jdbcx.JdbcDataSource", "SELECT 1", "jdbc:microsoft:sqlserver://", "", ""),

    /**
     * HyperSQL DataBase.
     */
    HSQLDB("HSQL Database Engine", "org.hsqldb.jdbc.JDBCDriver", "org.hsqldb.jdbc.pool.JDBCXADataSource",
            "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SYSTEM_USERS", "jdbc:microsoft:sqlserver://", "", ""),

    /**
     * SQL Lite.
     */
    SQLITE("SQLite", "org.sqlite.JDBC", "", "", "jdbc:microsoft:sqlserver://", "jdbc:sqlite:%s", "sqlite-jdbc-3.19.3.jar"),

    /**
     * MySQL8.
     */
    MYSQL8("MySQL8", "com.mysql.cj.jdbc.Driver", "com.mysql.cj.jdbc.MysqlXADataSource", "/* ping */ SELECT 1",
            "jdbc:mysql://".intern(), "jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useUnicode=true&useSSL=false&characterEncoding=%s", "mysql-connector-java-8.0.11.jar"),

    /**
     * MySQL5.
     * 时区错误问题：https://blog.csdn.net/weixin_42556307/article/details/107175246
     */
    MYSQL5("MySQL5", "com.mysql.cj.jdbc.Driver", "com.mysql.jdbc.MysqlXADataSource", "/* ping */ SELECT 1",
            "jdbc:mysql://".intern(), "jdbc:mysql://%s:%s/%s?useUnicode=true&useSSL=false&characterEncoding=%s&serverTimezone=UTC", "mysql-connector-java-5.1.38.jar"),

    /**
     * Maria DB.
     */
    MARIADB("MySQL", "org.mariadb.jdbc.Driver", "org.mariadb.jdbc.MariaDbDataSource", "SELECT 1", "jdbc:mysql://", "", "") {
        @Override
        public String getId() {
            return "mysql";
        }
    },

    /**
     * Google App Engine.
     */
    GAE("Google App Engine", "com.google.appengine.api.rdbms.AppEngineDriver", "", "", "jdbc:microsoft:sqlserver://", "", ""),

    /**
     * Oracle.
     */
    ORACLE("Oracle", "oracle.jdbc.OracleDriver", "oracle.jdbc.xa.client.OracleXADataSource", "SELECT 'Hello' from DUAL",
            "jdbc:oracle:thin:@//", "Oracle11及以下数据库", "jdbc:oracle:thin:@//%s:%s/%s", "ojdbc6.jar"),

    /**
     * Postgres.
     */
    POSTGRE_SQL("PostgreSQL", "org.postgresql.Driver", "org.postgresql.xa.PGXADataSource", "SELECT 1", "", "jdbc:postgresql://%s:%s/%s", "postgresql-9.4.1209.jar"),

    /**
     * Amazon Redshift.
     * @since 2.2.0
     */
    REDSHIFT("Amazon Redshift", "com.amazon.redshift.jdbc.Driver", null, "SELECT 1", "", "", ""),

    /**
     * HANA - SAP HANA Database - HDB.
     * @since 2.1.0
     */
    HANA("HDB", "com.sap.db.jdbc.Driver", "com.sap.db.jdbcext.XADataSourceSAP", "SELECT 1 FROM SYS.DUMMY", "", "", "") {
        @Override
        protected Collection<String> getUrlPrefixes() {
            return Collections.singleton("sap");
        }
    },

    /**
     * jTDS. As it can be used for several databases, there isn't a single product
     * name we could rely on.
     */
    JTDS("JTDS", "net.sourceforge.jtds.jdbc.Driver", "", "", "", "", ""),

    /**
     * SQL Server.
     */
    MICROSOFT_SQLSERVER("Microsoft SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver",
            "com.microsoft.sqlserver.jdbc.SQLServerXADataSource", "SELECT 1", "jdbc:microsoft:sqlserver://", "jdbc:sqlserver://%s:%s;databaseName=%s", "sqljdbc4-4.0.jar") {
        @Override
        protected boolean matchProductName(String productName) {
            return super.matchProductName(productName) || "SQL SERVER".equalsIgnoreCase(productName);
        }
    },

    /**
     * Firebird.
     */
    FIREBIRD("Firebird", "org.firebirdsql.jdbc.FBDriver", "org.firebirdsql.ds.FBXADataSource",
            "SELECT 1 FROM RDB$DATABASE", "", "", "") {
        @Override
        protected Collection<String> getUrlPrefixes() {
            return Arrays.asList("firebirdsql", "firebird");
        }

        @Override
        protected boolean matchProductName(String productName) {
            return super.matchProductName(productName)
                    || productName.toLowerCase(Locale.ENGLISH).startsWith("firebird");
        }
    },

    /**
     * DB2 Server.
     */
    DB2("DB2", "com.ibm.db2.jcc.DB2Driver", "com.ibm.db2.jcc.DB2XADataSource", "SELECT 1 FROM SYSIBM.SYSDUMMY1",
            "jdbc:db2://", "", "") {
        @Override
        protected boolean matchProductName(String productName) {
            return super.matchProductName(productName) || productName.toLowerCase(Locale.ENGLISH).startsWith("db2/");
        }
    },

    /**
     * DB2 AS400 Server.
     */
    DB2_AS400("DB2 UDB for AS/400", "com.ibm.as400.access.AS400JDBCDriver",
            "com.ibm.as400.access.AS400JDBCXADataSource", "SELECT 1 FROM SYSIBM.SYSDUMMY1", "", "", "") {
        @Override
        public String getId() {
            return "db2";
        }

        @Override
        protected Collection<String> getUrlPrefixes() {
            return Collections.singleton("as400");
        }

        @Override
        protected boolean matchProductName(String productName) {
            return super.matchProductName(productName) || productName.toLowerCase(Locale.ENGLISH).contains("as/400");
        }
    },

    /**
     * Teradata.
     */
    TERADATA("Teradata", "com.teradata.jdbc.TeraDriver", "", "", "", "", ""),

    /**
     * Informix.
     */
    INFORMIX("Informix Dynamic Server", "com.informix.jdbc.IfxDriver", null, "", "select count(*) from systables", "", "") {
        @Override
        protected Collection<String> getUrlPrefixes() {
            return Arrays.asList("informix-sqli", "informix-direct");
        }

    };

    private final String productName;
    private final String driverClassName;
    private final String xaDataSourceClassName;
    private final String validationQuery;
    private final String jdbcProtocol;
    private final String connectionUrlPattern;
    private final String driverJarFileName;
    private final String remark;

    DbType(String productName, String driverClassName, String xaDataSourceClassName, String jdbcProtocol,
           String remark, String connectionUrlPattern, String driverJarFileName) {
        this(productName, driverClassName, xaDataSourceClassName, null, jdbcProtocol, remark, connectionUrlPattern, driverJarFileName);
    }

    DbType(String productName, String driverClassName, String xaDataSourceClassName, String validationQuery,
           String jdbcProtocol, String remark, String connectionUrlPattern, String driverJarFileName) {
        this.productName = productName;
        this.driverClassName = driverClassName;
        this.xaDataSourceClassName = xaDataSourceClassName;
        this.validationQuery = validationQuery;
        this.jdbcProtocol = jdbcProtocol;
        this.remark = remark;
        this.connectionUrlPattern = connectionUrlPattern;
        this.driverJarFileName = driverJarFileName;
    }

    /**
     * Return the identifier of this driver.
     * @return the identifier
     */
    public String getId() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    /**
     * 提供一种非严格匹配的策略
     * @param productName 数据库产品名称
     * @return
     */
    protected boolean matchProductName(String productName) {
        return this.productName != null && this.productName.equalsIgnoreCase(productName);
    }

    protected Collection<String> getUrlPrefixes() {
        return Collections.singleton(this.name().toLowerCase(Locale.ENGLISH));
    }

    /**
     * Return the driver class name.
     * @return the class name or {@code null}
     */
    public String getDriverClassName() {
        return this.driverClassName;
    }

    /**
     * Return the XA driver source class name.
     * @return the class name or {@code null}
     */
    public String getXaDataSourceClassName() {
        return this.xaDataSourceClassName;
    }

    /**
     * Return the validation query.
     * @return the validation query or {@code null}
     */
    public String getValidationQuery() {
        return this.validationQuery;
    }

    public String getProductName() {
        return productName;
    }

    public String getJdbcProtocol() {
        return jdbcProtocol;
    }

    public String getRemark() {
        return remark;
    }

    /**
     * Find a {@link DbType} for the given URL.
     * @param url the JDBC URL
     * @return the database driver or {@link #UNKNOWN} if not found
     */
    public static DbType fromJdbcUrl(String url) {
        if (!url.startsWith("jdbc")) {
            throw new IllegalArgumentException("URL must start with 'jdbc'");
        }
        String urlWithoutPrefix = url.substring("jdbc".length()).toLowerCase(Locale.ENGLISH);
        for (DbType driver : values()) {
            for (String urlPrefix : driver.getUrlPrefixes()) {
                String prefix = ":" + urlPrefix + ":";
                if (driver != UNKNOWN && urlWithoutPrefix.startsWith(prefix)) {
                    return driver;
                }
            }
        }
        return UNKNOWN;
    }

    /**
     * Find a {@link DbType} for the given product name.
     * @param productName product name
     * @return the database driver or {@link #UNKNOWN} if not found
     */
    public static DbType fromProductName(String productName) {
        if (productName == null || productName.isEmpty()) return UNKNOWN;
        for (DbType candidate : values()) {
            if (candidate.matchProductName(productName)) {
                return candidate;
            }
        }
        return UNKNOWN;
    }

    public String getConnectionUrlPattern() {
        return connectionUrlPattern;
    }

    public String getDriverJarFileName() {
        return driverJarFileName;
    }

    public static List<String> supportedDatabaseProductNames() {
        List<String> names = new ArrayList<>();
        for (DbType value : values()) {
            if (value == UNKNOWN) continue;
            names.add(value.getProductName());
        }
        names.sort(Comparator.naturalOrder());
        return names;
    }
}
