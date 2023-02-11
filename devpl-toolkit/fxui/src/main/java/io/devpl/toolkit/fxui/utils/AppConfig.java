package io.devpl.toolkit.fxui.utils;

import io.devpl.toolkit.fxui.model.props.ConnectionConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 本应用所有的数据库操作都在此
 */
public class AppConfig {

    // static final String URL = "jdbc:h2:~/devpl";
    // static final String USERNAME = "sa";

    static final String URL = "jdbc:mysql://localhost:3306/devpl?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";

    static final String USERNAME = "root";
    static final String PASSWORD = "123456";

    public static Connection getConnection() throws Exception {
        return DBUtils.getConnection(URL, USERNAME, PASSWORD);
    }

    public static List<ConnectionConfig> listConnectionInfo() {
        try (Connection conn = getConnection();) {
            String sql = "select * from connection_info";
            ResultSet rs = DBUtils.executeQuery(conn, sql);
            List<ConnectionConfig> results = new ArrayList<>();
            while (rs.next()) {
                ConnectionConfig item = new ConnectionConfig();
                item.setId(rs.getString("id"));
                item.setPort(rs.getString("port"));
                item.setDbName(rs.getString("db_name"));
                item.setConnectionName(rs.getString("name"));
                item.setHost(rs.getString("host"));
                item.setDbType(rs.getString("db_type"));
                item.setUsername(rs.getString("username"));
                item.setPassword(rs.getString("password"));
                item.setEncoding("utf-8");
                results.add(item);
            }
            return results;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveConnectionConfig(ConnectionConfig config) {
        String sql = "INSERT INTO connection_info\n" + "(id, name, host, port, db_type, db_name, username, password)\n" + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        if (StringUtils.hasNotText(config.getId())) {
            config.setId(UUID.randomUUID().toString());
        }
        if (StringUtils.hasNotText(config.getConnectionName())) {
            String connectionName = config.getHost() + "_" + config.getPort();
            config.setConnectionName(connectionName);
        }
        try (Connection conn = getConnection()) {
            DBUtils.insert(conn, sql, config.getId(), config.getConnectionName(), config.getHost(), config.getPort(), config.getDbType(), config.getDbName(), config.getUsername(), config.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
