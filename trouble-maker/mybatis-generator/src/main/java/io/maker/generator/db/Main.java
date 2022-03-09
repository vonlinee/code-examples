package io.maker.generator.db;

import com.alibaba.druid.pool.DruidDataSource;
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
//        Properties properties = JdbcUtils.getLocalProperties();
//        DruidDataSource dataSource = DruidPool.druidDataSource(properties);
//        Connection conn = dataSource.getConnection();
//
//        List<Map<String, Object>> result = JdbcUtils.query(conn, "select * from jsh_account", new MapListHandler());
//
//        assert result != null;
//        //ExcelUtils.writeExcelAndShow(result, "course");
//
//        List<Map<String, Object>> list = TableMetaDataLoader.loadInfomationSchema(dataSource, "db_mysql", "jsh_account", new MapListHandler());
//        ExcelUtils.writeExcelAndShow(list, "course");
//
//        DataSourceProperties.builder();




    }
}
