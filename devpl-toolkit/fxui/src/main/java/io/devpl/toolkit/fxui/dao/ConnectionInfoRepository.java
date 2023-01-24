package io.devpl.toolkit.fxui.dao;

import io.devpl.toolkit.fxui.model.props.ConnectionInfo;
import io.devpl.toolkit.fxui.utils.ConnectionManager;
import io.devpl.toolkit.fxui.utils.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionInfoRepository {

    public List<ConnectionInfo> selectList() {
        Connection conn;
        try {
            conn = ConnectionManager.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            String sql = "select * from connection_info";
            ResultSet rs = DBUtils.executeQuery(conn, sql);
            List<ConnectionInfo> results = new ArrayList<>();
            while (rs.next()) {
                ConnectionInfo item = new ConnectionInfo();
                item.setId(rs.getString("id"));
                item.setPort(rs.getString("port"));
                item.setDbName(rs.getString("db_name"));
                item.setName(rs.getString("name"));
                item.setHost(rs.getString("host"));
                item.setDbType(rs.getString("db_type"));
                results.add(item);
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(ConnectionInfo connectionInfo) {
        String sql = "INSERT INTO connection_info\n" +
                "(id, name, host, port, db_type, db_name)\n" +
                "VALUES(?, ?, ?, ?, ?, ?)";

    }
}
