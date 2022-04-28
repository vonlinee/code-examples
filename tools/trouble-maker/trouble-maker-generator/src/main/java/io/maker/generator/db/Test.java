package io.maker.generator.db;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.alibaba.druid.pool.DruidDataSource;

import io.maker.base.io.FileUtils;
import io.maker.base.utils.Lists;
import io.maker.base.utils.PropertiesUtils;
import io.maker.base.utils.Validator;
import io.maker.extension.poi.ExcelUtils;
import io.maker.generator.db.pool.DruidPool;
import io.maker.generator.db.result.MapListHandler;
import io.maker.generator.db.result.ResultSetHandler;

public class Test {
    public static void main(String[] args) throws Exception {
//        Properties properties = JdbcUtils.getLocalProperties();
//        DruidDataSource dataSource = DruidPool.druidDataSource(properties);
//        Connection conn = dataSource.getConnection();
//        List<Map<String, Object>> result = JdbcUtils.query(conn, "select * from jsh_account", new MapListHandler());
//        if (!Validator.isNullOrEmpty(result)) {
//            ExcelUtils.writeExcel(result, new File("D:/Temp/1.xlsx").getAbsolutePath(), "Hello");
//        }
//        FileUtils.showFile(new File("D:/Temp/1.xlsx"));
    	
    	Connection connection = JdbcUtils.getLocalMySQLConnection("information_schema");
    	
    	String sql = "		SELECT\r\n"
    			+ "			*\r\n"
    			+ "		FROM `information_schema`.`TABLES` AS T\r\n"
    			+ "			JOIN `information_schema`.`COLUMNS` AS C ON T.TABLE_NAME = C.TABLE_NAME\r\n"
    			+ "		WHERE T.TABLE_NAME = 'orders'";
    	List<Map<String, Object>> list = JdbcUtils.query(connection, sql, ResultSetHandler.MAP_LIST);
    	
    	list.forEach(System.out::println);
    }
}
