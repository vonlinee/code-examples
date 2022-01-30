package io.doraemon.pocket.generator.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.ha.PropertiesUtils;

public class DruidUtils {
	
	public static void main(String[] args) throws Exception {
		Properties properties = PropertiesUtils.loadProperties("/druid.properties");
		DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);

		TableMetaData tableMetaData = TableMetaDataLoader.load(dataSource, "orders", "table");
		Map<String, ColumnMetaData> columns = tableMetaData.getColumns();
		for(String key : columns.keySet()) {
			ColumnMetaData data = columns.get(key);
			String name = data.getName();
			String dataTypeName = data.getDataTypeName();
			int dataType = data.getDataType();
			System.out.println(name + " " + dataType + " " + dataTypeName);
		}
	}

}
