package io.devpl.toolkit.fxui.dao;

import io.devpl.toolkit.fxui.model.props.ConnectionConfig;
import io.devpl.toolkit.fxui.utils.DBUtils;
import io.devpl.toolkit.fxui.utils.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 连接配置数据库操作
 */
public class ConnectionConfigurationDao extends Repository {

    public List<ConnectionConfig> selectList() {
        Connection conn = getConnection();
        try {
            String sql = "select * from connection_info";
            ResultSet rs = DBUtils.executeQuery(conn, sql);
            List<ConnectionConfig> results = new ArrayList<>();
            while (rs.next()) {
                ConnectionConfig item = new ConnectionConfig();
                item.setId(rs.getString("id"));
                item.setPort(rs.getString("port"));
                item.setDbName(rs.getString("db_name"));
                item.setName(rs.getString("name"));
                item.setHost(rs.getString("host"));
                item.setDbType(rs.getString("db_type"));
                item.setUsername(rs.getString("username"));
                item.setPassword(rs.getString("password"));
                item.setEncoding("utf-8");
                results.add(item);
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(ConnectionConfig config) {
        String sql = "INSERT INTO connection_info\n" +
                "(id, name, host, port, db_type, db_name, username, password)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        if (StringUtils.hasNotText(config.getId())) {
            config.setId(UUID.randomUUID().toString());
        }
        if (StringUtils.hasNotText(config.getName())) {
            String connectionName = config.getHost() + "_" + config.getPort();
            config.setName(connectionName);
        }
        Connection conn = getConnection();
        try {
            DBUtils.insert(conn, sql, config.getId(), config.getName(), config.getHost(), config.getPort(), config.getDbType(), config.getDbName(), config.getUsername(), config.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
