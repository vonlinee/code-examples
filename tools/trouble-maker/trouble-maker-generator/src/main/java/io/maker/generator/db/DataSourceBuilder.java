package io.maker.generator.db;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.BeanUtils;

import com.alibaba.druid.pool.DruidDataSource;

import io.maker.base.utils.ClassUtils;

/**
 * Convenience class for building a {@link DataSource} with common
 * implementations and properties. If HikariCP, Tomcat or Commons DBCP are on
 * the classpath one of them will be selected (in that order with Hikari first).
 * In the interest of a uniform interface, and so that there can be a fallback
 * to an embedded database if one can be detected on the classpath, only a small
 * set of common configuration properties are supported. To inject additional
 * properties into the result you can downcast it, or use
 * {@code @ConfigurationProperties}.
 *
 * @param <T> type of DataSource produced by the builder
 * @author Dave Syer
 * @author Madhura Bhave
 * @since 2.0.0
 */
public final class DataSourceBuilder<T extends DataSource> {

	/**
	 * supported datasource type
	 */
	private static final String[] DATA_SOURCE_TYPE_NAMES = new String[] { "com.zaxxer.hikari.HikariDataSource",
			"org.apache.tomcat.jdbc.pool.DataSource", "org.apache.commons.dbcp2.BasicDataSource" };

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