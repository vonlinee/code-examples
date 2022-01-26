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
		if (dataSource instanceof DruidDataSource) {
			DruidDataSource druidDataSource = (DruidDataSource) dataSource;
			druidDataSource.createMetaDataId();
			if (druidDataSource.isEnable()) {
				
			}
			druidDataSource.close();
		}
		TableMetaData tableMetaData = TableMetaDataLoader.load(dataSource, "t_sac_basis_log", "table");
		Map<String, ColumnMetaData> columns = tableMetaData.getColumns();
		for(String key : columns.keySet()) {
			ColumnMetaData data = columns.get(key);
			String name = data.getName();
			String dataTypeName = data.getDataTypeName();
			int dataType = data.getDataType();
			System.out.println(name + " " + dataType + " " + dataTypeName);
		}
	}
	
	public static void test() throws IOException {
		File file = new File("D:\\Work\\WISDOM_MOTOR\\工作日志\\2022-01-25.txt");
		
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int i = 0;
		while ((i = reader.read()) > 0) {
			String line = reader.readLine();
			if (line.trim().isEmpty()) {
				continue;
			}
			if (line.contains("PRIMARY") || line.contains("KEY") || line.contains("ENGINE")) {
				continue;
			}
			if (line.contains("TABLE")) {
				int indexOf = line.indexOf("`");
				String tableName = line.substring(indexOf + 1).split("`")[0];
				System.out.println();
				System.out.println();
				System.out.println(tableName);
			} else {
				if (line.contains("json")) {
					System.out.println("EXTEND_JSON");
				} else if (line.contains("datetime")) {
					System.out.println("datetime");
				} else if (line.contains("text")) {
					System.out.println("text");
				} else {
					int k = line.indexOf(")");
					int j = line.indexOf("'");
					int len = line.length();
					String s1 = line.substring(0, k + 1);
					String[] split = s1.split(" ");
					String s2 = line.substring(j, len);
					if (s2.contains(",")) {
						s2 = s2.substring(1, s2.length() - 2);
					} else {
						s2 = s2.substring(1, s2.length());
					}
					System.out.println(split[0] + " " + split[1] +  "\t\t\t"  + s2);
				}
			}
		}
	}
}
