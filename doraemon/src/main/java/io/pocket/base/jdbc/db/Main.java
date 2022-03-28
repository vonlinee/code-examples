package io.pocket.base.jdbc.db;

import com.alibaba.druid.pool.DruidDataSource;
import io.pocket.base.io.FileUtils;
import io.pocket.base.lang.Validator;
import io.pocket.extension.poi.ExcelUtils;
import io.pocket.base.jdbc.db.meta.resultset.MapListHandler;
import io.pocket.base.jdbc.db.pool.DruidPool;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties properties = JdbcUtils.getLocalProperties();
        DruidDataSource dataSource = DruidPool.druidDataSource(properties);
        Connection conn = dataSource.getConnection();

        List<Map<String, Object>> result = JdbcUtils.query(conn, "select * from jsh_account", new MapListHandler());
        if (!Validator.isNullOrEmpty(result)) {
            ExcelUtils.writeExcel(result, new File("D:/Temp/1.xlsx").getAbsolutePath(), "Hello");
        }
        FileUtils.showFile(new File("D:/Temp/1.xlsx"));
    }
}
