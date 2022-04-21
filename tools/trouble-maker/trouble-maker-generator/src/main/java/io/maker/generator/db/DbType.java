package io.maker.generator.db;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import org.springframework.util.StringUtils;

/**
 * Enumeration of common database type.
 */
public enum DbType {

	/**
	 * Unknown type.
	 */
	UNKNOWN(null, null, null, null),

	/**
	 * Apache Derby.
	 */
	DERBY("Apache Derby", "org.apache.derby.jdbc.EmbeddedDriver", "org.apache.derby.jdbc.EmbeddedXADataSource",
			"SELECT 1 FROM SYSIBM.SYSDUMMY1"),

	/**
	 * H2.
	 */
	H2("H2", "org.h2.Driver", "org.h2.jdbcx.JdbcDataSource", "SELECT 1"),

	/**
	 * HyperSQL DataBase.
	 */
	HSQLDB("HSQL Database Engine", "org.hsqldb.jdbc.JDBCDriver", "org.hsqldb.jdbc.pool.JDBCXADataSource",
			"SELECT COUNT(*) FROM INFORMATION_SCHEMA.SYSTEM_USERS"),

	/**
	 * SQL Lite.
	 */
	SQLITE("SQLite", "org.sqlite.JDBC", "", ""),

	/**
	 * MySQL.
	 */
	MYSQL8("MySQL8", "com.mysql.cj.jdbc.Driver", "com.mysql.cj.jdbc.MysqlXADataSource", "/* ping */ SELECT 1"),

	MySQL5("MySQL5", "com.mysql.cj.jdbc.Driver", "com.mysql.jdbc.MysqlXADataSource", "/* ping */ SELECT 1"),

	/**
	 * Maria DB.
	 */
	MARIADB("MySQL", "org.mariadb.jdbc.Driver", "org.mariadb.jdbc.MariaDbDataSource", "SELECT 1") {

		@Override
		public String getId() {
			return "mysql";
		}
	},

	/**
	 * Google App Engine.
	 */
	GAE(null, "com.google.appengine.api.rdbms.AppEngineDriver", "", ""),

	/**
	 * Oracle.
	 */
	ORACLE("Oracle", "oracle.jdbc.OracleDriver", "oracle.jdbc.xa.client.OracleXADataSource", "SELECT 'Hello' from DUAL",
			"Oracle11及以下数据库"),

	/**
	 * Postgres.
	 */
	POSTGRESQL("PostgreSQL", "org.postgresql.Driver", "org.postgresql.xa.PGXADataSource", "SELECT 1"),

	/**
	 * Amazon Redshift.
	 * 
	 * @since 2.2.0
	 */
	REDSHIFT("Amazon Redshift", "com.amazon.redshift.jdbc.Driver", null, "SELECT 1"),

	/**
	 * HANA - SAP HANA Database - HDB.
	 * 
	 * @since 2.1.0
	 */
	HANA("HDB", "com.sap.db.jdbc.Driver", "com.sap.db.jdbcext.XADataSourceSAP", "SELECT 1 FROM SYS.DUMMY") {
		@Override
		protected Collection<String> getUrlPrefixes() {
			return Collections.singleton("sap");
		}
	},

	/**
	 * jTDS. As it can be used for several databases, there isn't a single product
	 * name we could rely on.
	 */
	JTDS(null, "net.sourceforge.jtds.jdbc.Driver", "", ""),

	/**
	 * SQL Server.
	 */
	SQLSERVER("Microsoft SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver",
			"com.microsoft.sqlserver.jdbc.SQLServerXADataSource", "SELECT 1") {

		@Override
		protected boolean matchProductName(String productName) {
			return super.matchProductName(productName) || "SQL SERVER".equalsIgnoreCase(productName);
		}

	},

	/**
	 * Firebird.
	 */
	FIREBIRD("Firebird", "org.firebirdsql.jdbc.FBDriver", "org.firebirdsql.ds.FBXADataSource",
			"SELECT 1 FROM RDB$DATABASE") {

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
	DB2("DB2", "com.ibm.db2.jcc.DB2Driver", "com.ibm.db2.jcc.DB2XADataSource", "SELECT 1 FROM SYSIBM.SYSDUMMY1") {

		@Override
		protected boolean matchProductName(String productName) {
			return super.matchProductName(productName) || productName.toLowerCase(Locale.ENGLISH).startsWith("db2/");
		}
	},

	/**
	 * DB2 AS400 Server.
	 */
	DB2_AS400("DB2 UDB for AS/400", "com.ibm.as400.access.AS400JDBCDriver",
			"com.ibm.as400.access.AS400JDBCXADataSource", "SELECT 1 FROM SYSIBM.SYSDUMMY1") {

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
	TERADATA("Teradata", "com.teradata.jdbc.TeraDriver", "", ""),

	/**
	 * Informix.
	 */
	INFORMIX("Informix Dynamic Server", "com.informix.jdbc.IfxDriver", null, "select count(*) from systables") {

		@Override
		protected Collection<String> getUrlPrefixes() {
			return Arrays.asList("informix-sqli", "informix-direct");
		}

	};

	private final String productName;
	private final String driverClassName;
	private final String xaDataSourceClassName;
	private final String validationQuery;
	private final String remark;

	DbType(String productName, String driverClassName, String xaDataSourceClassName, String remark) {
		this(productName, driverClassName, xaDataSourceClassName, null, remark);
	}

	DbType(String productName, String driverClassName, String xaDataSourceClassName, String validationQuery,
			String remark) {
		this.productName = productName;
		this.driverClassName = driverClassName;
		this.xaDataSourceClassName = xaDataSourceClassName;
		this.validationQuery = validationQuery;
		this.remark = remark;
	}

	/**
	 * Return the identifier of this driver.
	 * 
	 * @return the identifier
	 */
	public String getId() {
		return name().toLowerCase(Locale.ENGLISH);
	}

	protected boolean matchProductName(String productName) {
		return this.productName != null && this.productName.equalsIgnoreCase(productName);
	}

	protected Collection<String> getUrlPrefixes() {
		return Collections.singleton(this.name().toLowerCase(Locale.ENGLISH));
	}

	/**
	 * Return the driver class name.
	 * 
	 * @return the class name or {@code null}
	 */
	public String getDriverClassName() {
		return this.driverClassName;
	}

	/**
	 * Return the XA driver source class name.
	 * 
	 * @return the class name or {@code null}
	 */
	public String getXaDataSourceClassName() {
		return this.xaDataSourceClassName;
	}

	/**
	 * Return the validation query.
	 * 
	 * @return the validation query or {@code null}
	 */
	public String getValidationQuery() {
		return this.validationQuery;
	}

	/**
	 * Find a {@link DbType} for the given URL.
	 * 
	 * @param url the JDBC URL
	 * @return the database driver or {@link #UNKNOWN} if not found
	 */
	public static DbType fromJdbcUrl(String url) {
		if (StringUtils.hasLength(url)) {
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
		}
		return UNKNOWN;
	}

	/**
	 * Find a {@link DbType} for the given product name.
	 * 
	 * @param productName product name
	 * @return the database driver or {@link #UNKNOWN} if not found
	 */
	public static DbType fromProductName(String productName) {
		if (StringUtils.hasLength(productName)) {
			for (DbType candidate : values()) {
				if (candidate.matchProductName(productName)) {
					return candidate;
				}
			}
		}
		return UNKNOWN;
	}

}
