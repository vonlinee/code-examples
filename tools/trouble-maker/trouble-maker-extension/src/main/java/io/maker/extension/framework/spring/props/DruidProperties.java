package io.maker.extension.framework.spring.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "druid", ignoreUnknownFields = true)
public class DruidProperties {
	private int initialSize = 3;
	private int minIdle = 3;
	private int maxActive = 300;
	private int maxWait = 60000;
	private int timeBetweenEvictionRunsMillis = 60000;
	private int minEvictableIdleTimeMillis = 300000;
	private String validationQuery = "select 1 from dual";
	private boolean testWhileIdle = true;
	private boolean testOnBorrow = false;
	private boolean testOnReturn = false;
	private boolean poolPreparedStatements = false;
	private int maxPoolPreparedStatementPerConnectionSize = 20;
	private long phyTimeoutMillis = -1L;
	private boolean keepAlive = false;
	private String connectionProperties;
	private String filters = "stat";
	private boolean logAbandoned = false;
	private boolean removeAbandoned = false;
	private int removeAbandonedTimeout = 300;

	public int getInitialSize() {
		return this.initialSize;
	}

	public int getMinIdle() {
		return this.minIdle;
	}

	public int getMaxActive() {
		return this.maxActive;
	}

	public int getMaxWait() {
		return this.maxWait;
	}

	public int getTimeBetweenEvictionRunsMillis() {
		return this.timeBetweenEvictionRunsMillis;
	}

	public int getMinEvictableIdleTimeMillis() {
		return this.minEvictableIdleTimeMillis;
	}

	public String getValidationQuery() {
		return this.validationQuery;
	}

	public boolean isTestWhileIdle() {
		return this.testWhileIdle;
	}

	public boolean isTestOnBorrow() {
		return this.testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return this.testOnReturn;
	}

	public boolean isPoolPreparedStatements() {
		return this.poolPreparedStatements;
	}

	public int getMaxPoolPreparedStatementPerConnectionSize() {
		return this.maxPoolPreparedStatementPerConnectionSize;
	}

	public String getFilters() {
		return this.filters;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public long getPhyTimeoutMillis() {
		return this.phyTimeoutMillis;
	}

	public void setPhyTimeoutMillis(long phyTimeoutMillis) {
		this.phyTimeoutMillis = phyTimeoutMillis;
	}

	public boolean isKeepAlive() {
		return this.keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public String getConnectionProperties() {
		return this.connectionProperties;
	}

	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public boolean isRemoveAbandoned() {
		return this.removeAbandoned;
	}

	public void setRemoveAbandoned(boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}

	public boolean isLogAbandoned() {
		return this.logAbandoned;
	}

	public void setLogAbandoned(boolean logAbandoned) {
		this.logAbandoned = logAbandoned;
	}

	public int getRemoveAbandonedTimeout() {
		return this.removeAbandonedTimeout;
	}

	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}
}