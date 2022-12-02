package auto.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConfig {

    private static DataSource druidMysqlSource;
    private static Connection con;
    private static Statement sta;
    private static ResultSet rs;

    public static DataSource setDruidMysqlSource(Map<String, String> configMap) throws Exception {
        if (druidMysqlSource == null) {
            druidMysqlSource = DruidDataSourceFactory.createDataSource(configMap);
        }
        return druidMysqlSource;
    }

    public static ResultSet getConnection(String sql) throws Exception {
        con = druidMysqlSource.getConnection();
        sta = con.createStatement();
        rs = sta.executeQuery(sql);
        return rs;
    }

    public static void close() throws SQLException {
        rs.close();
        sta.close();
        con.close();
    }

}
