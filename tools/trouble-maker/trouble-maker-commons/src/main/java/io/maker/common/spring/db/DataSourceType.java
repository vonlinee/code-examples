package io.maker.common.spring.db;

/**
 * 如配置文件中未指定数据源类型，使用该默认值
 */
public enum DataSourceType {
	TomcatJdbcPool("org.apache.tomcat.jdbc.pool.DataSource"), 
	HikariDataSource("com.zaxxer.hikari.HikariDataSource"),
	SpringDataSource("org.springframework.jdbc.datasource.DriverManagerDataSource"),
	DruidPool("com.alibaba.druid.pool.DruidDataSource");

	private String className;

	DataSourceType(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}