package io.maker.gen.db;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import io.maker.gen.db.meta.column.ColumnMetaDataLoader;
import com.alibaba.druid.pool.DruidDataSource;

import io.maker.gen.db.pool.DruidPool;
import io.maker.base.ResourceLoader;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties prop = ResourceLoader.loadProperties("jdbc.properties");
        Connection conn = DruidPool.druidConnection(prop);
        DruidDataSource dataSource = DruidPool.druidDataSource(prop);
        JdbcUtils.getTableMetaData("mybatis_learn", conn);
        List<Map<String, Object>> maps = JdbcUtils.executeQuery(conn, "", null);
        ColumnMetaDataLoader.loadInfomationSchema(dataSource, "db_mysql", "course");
    }
}
