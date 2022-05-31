package io.maker.codegen.core.db;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.BeanUtils;
import io.maker.base.utils.ClassUtils;

/**
 * 如果类路径下存在HikariCP, Tomcat or Commons DBCP其中之一，那么将会选择其中之一，如果存在多个，则Hikari优先
 * 为了实现统一的接口，并且如果可以在类路径上检测到嵌入式数据库，那么可以回退到嵌入式数据库，只支持一小部分公共配置属性
 * 如果要注入其他属性，可以向下转型（downcast）
 * 
 * @param <T> type of DataSource produced by the builder
 */
public final class DataSourceBuilder<T extends DataSource> {

	/**
	 * 支持的数据类型
	 */
	private static final String[] DATA_SOURCE_TYPE_NAMES = new String[] {
			"com.zaxxer.hikari.HikariDataSource",
			"org.apache.tomcat.jdbc.pool.DataSource",
			"org.apache.commons.dbcp2.BasicDataSource"
	};

	private Class<? extends DataSource> type;

	private ClassLoader classLoader;

	private final Map<String, String> properties = new HashMap<>();

	public static DataSourceBuilder<?> create() {
		return new DataSourceBuilder<>(null);
	}

	public static DataSourceBuilder<?> create(ClassLoader classLoader) {
		return new DataSourceBuilder<>(classLoader);
	}

	private DataSourceBuilder(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@SuppressWarnings("unchecked")
	public T build() {
		Class<? extends DataSource> type = getType();
		DataSource result = BeanUtils.instantiateClass(type);
		maybeGetDriverClassName();
		bind(result); // properties => DataSource
		return (T) result;
	}

	/**
	 * 绑定数据源属性
	 * 
	 * @param result
	 */
	private void bind(DataSource result) {

	}

	private void maybeGetDriverClassName() {
		if (!this.properties.containsKey("driverClassName") && this.properties.containsKey("url")) {
			String url = this.properties.get("url");
			String driverClass = DbType.fromJdbcUrl(url).getDriverClassName();
			this.properties.put("driverClassName", driverClass);
		}
	}

	@SuppressWarnings("unchecked")
	public <D extends DataSource> DataSourceBuilder<D> type(Class<D> type) {
		this.type = type;
		return (DataSourceBuilder<D>) this;
	}

	public DataSourceBuilder<T> url(String url) {
		this.properties.put("url", url);
		return this;
	}

	public DataSourceBuilder<T> driverClassName(String driverClassName) {
		this.properties.put("driverClassName", driverClassName);
		return this;
	}

	public DataSourceBuilder<T> username(String username) {
		this.properties.put("username", username);
		return this;
	}

	public DataSourceBuilder<T> password(String password) {
		this.properties.put("password", password);
		return this;
	}

	@SuppressWarnings("unchecked")
	public static Class<? extends DataSource> findType(ClassLoader classLoader) {
		for (String name : DATA_SOURCE_TYPE_NAMES) {
			try {
				return (Class<? extends DataSource>) ClassUtils.forName(name, classLoader);
			} catch (Exception ex) {
				// Swallow and continue
			}
		}
		return null;
	}

	private Class<? extends DataSource> getType() {
		Class<? extends DataSource> type = (this.type != null) ? this.type : findType(this.classLoader);
		if (type != null) {
			return type;
		}
		throw new IllegalStateException("No supported DataSource type found");
	}
}
