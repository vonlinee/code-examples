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
import io.maker.base.utils.Validator;
import io.maker.extension.poi.ExcelUtils;
import io.maker.generator.db.pool.DruidPool;
import io.maker.generator.db.resultset.MapListHandler;

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
    	
    	DataSource dataSource = DataSourceBuilder.create()
    		.type(DruidDataSource.class)
    		.url("jdbc:mysql://localhost:3306/db_mysql?useUnicode=true&characterEncoding=utf8")
    		.username("root")
    		.password("123456")
    		.driverClassName("com.mysql.jdbc.Driver")
    		.build();
    	
    	System.out.println(dataSource);
    	
    	Connection connection = dataSource.getConnection();
    	
    	System.out.println(connection);
    }
}
