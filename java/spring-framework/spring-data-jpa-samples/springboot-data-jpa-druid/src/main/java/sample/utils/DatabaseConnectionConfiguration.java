package sample.utils;

import java.util.Map;

/**
 * jdbc:mysql://localhost:3306/jpa_learn?createDatabaseIfNotExist=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false
 */
public class DatabaseConnectionConfiguration {

	// 基本连接信息
	private String dbType = "mysql";
	private String host = "localhost";
	private int port = 3306;
	private String databaseName;
	// 参数配置
	private boolean createDatabaseIfNotExist;
	private boolean useUnicode = true;
	private boolean useSSL = false;
	private String serverTimezone = "UTC";
	private String characterEncoding = "utf8";

	private DatabaseConnectionConfiguration(Builder builder) {
		this.dbType = builder.dbType;
		this.host = builder.host;
		this.port = builder.port;
		this.databaseName = builder.databaseName;
		this.createDatabaseIfNotExist = builder.createDatabaseIfNotExist;
		this.useUnicode = builder.useUnicode;
		this.useSSL = builder.useSSL;
		this.serverTimezone = builder.serverTimezone;
		this.characterEncoding = builder.characterEncoding;
	}

	public String getUrl() {
		return determineDbProtoctl() + "://" + host + ":" + port + "/" + databaseName;
	}

	private String determineDbProtoctl() {
		String prefix = "jdbc:";
		return prefix + this.dbType;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {

		private Map<String, Object> options;

		private String dbType = "mysql";
		private String host = "localhost";
		private int port = 3306;
		private String databaseName;
		private boolean createDatabaseIfNotExist;
		private boolean useUnicode = true;
		private boolean useSSL = false;
		private String serverTimezone = "UTC";
		private String characterEncoding = "utf8";

		private Builder() {
		}

		public Builder DbType(String dbType) {
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

		public Builder CreateDatabaseIfNotExist(boolean createDatabaseIfNotExist) {
			options.put("createDatabaseIfNotExist", this.createDatabaseIfNotExist = createDatabaseIfNotExist);
			return this;
		}

		public Builder UseUnicode(boolean useUnicode) {
			options.put("createDatabaseIfNotExist", this.useUnicode = useUnicode);
			return this;
		}

		public Builder UseSSL(boolean useSSL) {
			options.put("createDatabaseIfNotExist", this.useSSL = useSSL);
			return this;
		}

		public Builder ServerTimezone(String serverTimezone) {
			options.put("createDatabaseIfNotExist", this.serverTimezone = serverTimezone);
			return this;
		}

		public Builder CharacterEncoding(String characterEncoding) {
			options.put("createDatabaseIfNotExist", this.characterEncoding = characterEncoding);
			return this;
		}

		public DatabaseConnectionConfiguration build() {
			return new DatabaseConnectionConfiguration(this);
		}
	}
}
