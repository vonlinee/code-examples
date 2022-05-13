package io.maker.common.spring.db;

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