package io.doraemon.pocket.generator.sql;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.shardingsphere.sql.parser.binder.metadata.table.TableMetaData;
import org.apache.shardingsphere.sql.parser.binder.metadata.table.TableMetaDataLoader;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.ha.PropertiesUtils;

public class DruidUtils {
	
	public static void main(String[] args) throws Exception {
		Properties properties = PropertiesUtils.loadProperties("/druid.properties");
		DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
//		if (dataSource instanceof DruidDataSource) {
//			DruidDataSource druidDataSource = (DruidDataSource) dataSource;
//		}
		TableMetaData tableMetaData = TableMetaDataLoader.load(dataSource, "customers", "table");
	}
}
