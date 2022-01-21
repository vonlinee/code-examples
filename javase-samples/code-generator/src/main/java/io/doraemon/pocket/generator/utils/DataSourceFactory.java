package io.doraemon.pocket.generator.utils;

import java.util.Properties;

import javax.sql.DataSource;

public interface DataSourceFactory {
	void setProperties(Properties props);
	DataSource getDataSource();
}
