package code.fxutils.core.db;

import code.fxutils.core.util.ResourceLoader;
import com.alibaba.druid.pool.DruidDataSource;

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
