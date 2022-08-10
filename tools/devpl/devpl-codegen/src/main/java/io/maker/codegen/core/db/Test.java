package io.maker.codegen.core.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.maker.base.utils.Maps;
import io.maker.codegen.core.db.meta.schema.SchemaMetaDataLoader;
import io.maker.codegen.core.db.meta.table.TableMetaData;

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
        List<String> tableNames = new ArrayList<>();
        tableNames.add("COLUMNS");
        tableNames.add("TALBES");
        Map<String, TableMetaData> meta = SchemaMetaDataLoader.load(connection, tableNames, "MySQL");
        Maps.println(meta);
    }
}
