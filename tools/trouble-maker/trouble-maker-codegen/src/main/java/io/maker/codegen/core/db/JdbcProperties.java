package io.maker.codegen.core.db;

/**
 * JDBC连接信息
 * 参考：https://blog.csdn.net/liu_sisi/article/details/88728332
 */
public class JdbcProperties {

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 3306;

	private String driverClassName;
	private String connectionUrl;
	
	/**
	 * 数据库类型
	 */
	private DbType dbType;

	/**
	 * 主库地址，包括 IP 地址、localhost 或者配置文件中主库地址列表对应的变量名，如 dm_svc.conf 中的‟o2000‟
	 */
	private String host = DEFAULT_HOST;

	/**
	 * 端口号，服务器登录端口号
	 */
	private int port = DEFAULT_PORT;

	/**
	 * 连接的数据库名
	 */
	private String databaseName;

	/**
	 * 登录用户
	 */
	private String username;

	/**
	 * 登录密码
	 */
	private String password;

	/**
	 * 与服务器进行通信时使用SSL（true／false），默认值为false
	 */
	private boolean useSSL;

	/**
	 * 处理字符串时，驱动程序是否应使用Unicode字符编码？ 仅应在驱动程序无法确定字符集映射，
	 * 或你正在强制驱动程序使用MySQL不是固有支持的字符集时（如UTF-8）才应使用。真／假，默认为“真”
	 */
	private boolean useUnicode;

	/**
	 * 覆盖时区的检测/映射。当服务器的时区为映射到Java时区时使用
	 * 
	 * @since 3.0.2
	 */
	private String serverTimezone;

	/**
	 * 是否在客户端和服务器时区间转换时间／日期类型，默认为false
	 * 
	 * @since 3.0.2
	 */
	private boolean useTimezone;

	/**
	 * 驱动程序用于创建与服务器套接字连接的类的名称。该类必须实现了接口com.mysql.jdbc.SocketFactory，并有公共无参量构造函数
	 * 默认值com.mysql.jdbc.StandardSocketFactory
	 * 
	 * @since 3.0.3
	 */
	private String socketFactory;

	/**
	 * 连接数据库超时时间；单位 ms，有效值范围 0~2147483647， 0 表示无限制；默认 0； 否
	 * 套接字连接的超时（单位为毫秒），0表示无超时。仅对JDK-1.4或更新版本有效。默认值为“0”。
	 */
	private int connectTimeout;

	/**
	 * 如果不存在，创建URL中给定的数据库。假定用户具有创建数据库的权限。
	 */
	private boolean createDatabaseIfNotExist;

	/**
	 * 驱动程序是否应允许从空字符串字段到数值“0”的转换，默认为true
	 * 
	 * @since 3.1.8
	 */
	private boolean emptyStringsConvertToZero;

	/**
	 * 如果“useUnicode”被设置为“真”，处理字符串时，驱动程序应使用什么字符编码，默认为默认为autodetect
	 */
	private String characterEncoding;

	// ===============================================================================
	
	/**
	 * Builder
	 * @param builder
	 */
	private JdbcProperties(Builder builder) {
		this.dbType = builder.dbType;
		this.host = builder.host;
		this.port = builder.port;
		this.databaseName = builder.databaseName;
		this.username = builder.username;
		this.password = builder.password;
		this.useSSL = builder.useSSL;
		this.useUnicode = builder.useUnicode;
		this.serverTimezone = builder.serverTimezone;
		this.useTimezone = builder.useTimezone;
		this.socketFactory = builder.socketFactory;
		this.connectTimeout = builder.connectTimeout;
		this.createDatabaseIfNotExist = builder.createDatabaseIfNotExist;
		this.emptyStringsConvertToZero = builder.emptyStringsConvertToZero;
		this.characterEncoding = builder.characterEncoding;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private DbType dbType;
		private String host = DEFAULT_HOST;
		private int port = DEFAULT_PORT;
		private String databaseName;
		private String username;
		private String password;
		private boolean useSSL;
		private boolean useUnicode;
		private String serverTimezone;
		private boolean useTimezone;
		private String socketFactory;
		private int connectTimeout;
		private boolean createDatabaseIfNotExist;
		private boolean emptyStringsConvertToZero;
		private String characterEncoding;

		private Builder() {
		}

		public Builder DbType(DbType dbType) {
			this.dbType = dbType;
			return this;
		}

		public Builder Host(String host) {
			this.host = host;
			return this;
		}

		public Builder Port(int port) {
			this.port = port;
			return this;
		}

		public Builder DatabaseName(String databaseName) {
			this.databaseName = databaseName;
			return this;
		}

		public Builder Username(String username) {
			this.username = username;
			return this;
		}

		public Builder Password(String password) {
			this.password = password;
			return this;
		}

		public Builder UseSSL(boolean useSSL) {
			this.useSSL = useSSL;
			return this;
		}

		public Builder UseUnicode(boolean useUnicode) {
			this.useUnicode = useUnicode;
			return this;
		}

		public Builder ServerTimezone(String serverTimezone) {
			this.serverTimezone = serverTimezone;
			return this;
		}

		public Builder UseTimezone(boolean useTimezone) {
			this.useTimezone = useTimezone;
			return this;
		}

		public Builder SocketFactory(String socketFactory) {
			this.socketFactory = socketFactory;
			return this;
		}

		public Builder ConnectTimeout(int connectTimeout) {
			this.connectTimeout = connectTimeout;
			return this;
		}

		public Builder CreateDatabaseIfNotExist(boolean createDatabaseIfNotExist) {
			this.createDatabaseIfNotExist = createDatabaseIfNotExist;
			return this;
		}

		public Builder EmptyStringsConvertToZero(boolean emptyStringsConvertToZero) {
			this.emptyStringsConvertToZero = emptyStringsConvertToZero;
			return this;
		}

		public Builder CharacterEncoding(String characterEncoding) {
			this.characterEncoding = characterEncoding;
			return this;
		}

		public JdbcProperties build() {
			return new JdbcProperties(this);
		}
	}
}
