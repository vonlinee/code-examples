package io.maker.generator.db;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

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
