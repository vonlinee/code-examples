package io.maker.common.spring.db;

/**
 * 如配置文件中未指定数据源类型，使用该默认值
 */
import javax.sql.DataSource;

public enum DataSourceType {
    Druid(""),
    HikariCP(""),
    TomcatJDBC("org.apache.tomcat.jdbc.pool.DataSource"),
    Dbcp("org.apache.commons.dbcp2.BasicDataSource"),
    Spring("org.springframework.jdbc.datasource.DriverManagerDataSource"),
    C3p0("com.mchange.v2.c3p0.ComboPooledDataSource");

    String name;

    DataSourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}