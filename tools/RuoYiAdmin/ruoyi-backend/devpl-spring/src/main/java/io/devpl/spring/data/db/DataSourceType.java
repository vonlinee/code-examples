package io.devpl.spring.data.db;

/**
 * 如配置文件中未指定数据源类型，使用该默认值
 */
public enum DataSourceType {
	DRUID("com.alibaba.druid.pool.DruidDataSource"),
	HIKARICP("com.zaxxer.hikari.HikariDataSource"),
	TOMCAT_JDBC("org.apache.tomcat.jdbc.pool.DataSource"),
	DBCP2("org.apache.commons.dbcp2.BasicDataSource"),
	SPRING("org.springframework.jdbc.datasource.DriverManagerDataSource"),
	C3P0("com.mchange.v2.c3p0.ComboPooledDataSource");

	String className;

	DataSourceType(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}
}