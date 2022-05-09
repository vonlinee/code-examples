package io.maker.codegen.core.db;

import java.sql.Connection;

import io.maker.codegen.core.db.meta.schema.SchemaMetaDataLoader;

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
    	
    	
    	SchemaMetaDataLoader.load(null, 0, null);
    	
    	

    }
}
