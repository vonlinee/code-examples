package code.fxutils.support.db;

import com.alibaba.druid.pool.DruidDataSource;

import code.fxutils.support.db.pool.DruidPool;
import code.fxutils.support.util.ResourceLoader;

import java.sql.Connection;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        Properties prop = ResourceLoader.loadProperties("jdbc.properties");
        Connection conn = DruidPool.druidConnection(prop);
        DruidDataSource dataSource = DruidPool.druidDataSource(prop);
        DbHelper.getTableMetaData("mybatis_learn", conn);
    }
}
