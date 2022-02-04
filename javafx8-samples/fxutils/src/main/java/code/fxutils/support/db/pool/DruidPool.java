package code.fxutils.support.db.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import code.fxutils.support.db.pool.DatabaseConnectionPool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

public class DruidPool extends DatabaseConnectionPool {

    public static DruidDataSource druidDataSource(Properties prop) throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        DruidDataSourceFactory.config(dataSource, prop);
        return dataSource;
    }

    public static Connection druidConnection(Properties prop) throws Exception {
        return druidDataSource(prop).getConnection();
    }

    @Override
    public String vendor() {
        return "Alibaba Druid Connection Pool";
    }

    @Override
    public String website() {
        return "";
    }

    @Override
    public DataSource pickDataSource() {
        return null;
    }
}
