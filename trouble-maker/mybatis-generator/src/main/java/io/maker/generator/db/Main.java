package io.maker.generator.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import io.maker.generator.db.meta.column.ColumnMetaDataLoader;
import com.alibaba.druid.pool.DruidDataSource;

import io.maker.generator.db.meta.resultset.MapListHandler;
import io.maker.generator.db.pool.DruidPool;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties properties = JdbcUtils.getProperties();
        DruidDataSource dataSource = DruidPool.druidDataSource(properties);
        Connection conn = dataSource.getConnection();

        List<Map<String, Object>> result = JdbcUtils.query(conn, "select * from course", new MapListHandler());

        System.out.println(result);

    }
}
