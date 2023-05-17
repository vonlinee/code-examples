package code.example.jdbc.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.ha.PropertiesUtils;

import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionPoolTest {
    public static void main(String[] args) throws SQLException {
        Properties properties = PropertiesUtils.loadProperties("/druid.properties");
        DruidDataSource druidDataSource = new DruidDataSource();
        DruidDataSourceFactory.config(druidDataSource, properties);
        System.out.println(druidDataSource);
    }
}
