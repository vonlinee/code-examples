package code.magicode.generator.db;

import java.sql.Connection;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;

import code.magicode.generator.db.pool.DruidPool;
import io.maker.base.ResourceLoader;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties prop = ResourceLoader.loadProperties("jdbc.properties");
        Connection conn = DruidPool.druidConnection(prop);
        DruidDataSource dataSource = DruidPool.druidDataSource(prop);
        JdbcUtils.getTableMetaData("mybatis_learn", conn);
    }
}
