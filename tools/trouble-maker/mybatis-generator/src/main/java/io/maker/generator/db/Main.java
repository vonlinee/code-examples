package io.maker.generator.db;

import com.alibaba.druid.pool.DruidDataSource;
import io.maker.base.io.FileUtils;
import io.maker.base.utils.Validator;
import io.maker.extension.poi.ExcelUtils;
import io.maker.generator.db.meta.resultset.MapListHandler;
import io.maker.generator.db.meta.table.TableMetaDataLoader;
import io.maker.generator.db.pool.DruidPool;

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
