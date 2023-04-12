package io.devpl.tookit.utils;

import com.alibaba.druid.pool.DruidDataSource;
import io.devpl.tookit.fxui.model.ConnectionConfig;
import io.devpl.tookit.fxui.model.ProjectConfiguration;
import io.devpl.tookit.utils.json.JSONUtils;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

/**
 * 本应用所有的数据库操作都在此
 */
public class AppConfig {

    static final String URL = "jdbc:mysql://localhost:3306/devpl?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";

    static final String USERNAME = "root";
    static final String PASSWORD = "123456";

    public static JdbcTemplate initJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DruidDataSource dds = new DruidDataSource();
        dds.setUrl("jdbc:sqlite::resource:db/sqlite.db");
        dds.setDriverClassName("org.sqlite.JDBC");
        jdbcTemplate.setDataSource(dds);
        return jdbcTemplate;
    }

    public static void main(String[] args) {
        final JdbcTemplate jdbcTemplate = initJdbcTemplate();

        final List<Map<String, Object>> map = jdbcTemplate.query("select 1", new ColumnMapRowMapper());

        System.out.println(map);
    }

    public static Connection getConnection() throws Exception {
        return DBUtils.getConnection(URL, USERNAME, PASSWORD);
    }

    public static List<ConnectionConfig> listConnectionInfo() {
        try (Connection conn = getConnection();) {
            String sql = "select * from connection_config";
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

    /**
     * 根据ID删除连接信息
     *
     * @param connectionInfos 连接信息列表
     * @return 删除条数
     */
    public static int deleteConnectionById(List<ConnectionConfig> connectionInfos) {
        String sql = "DELETE FROM connection_config WHERE id IN ";
        StringJoiner stringJoiner = new StringJoiner(",", "(", ")");
        for (ConnectionConfig connectionInfo : connectionInfos) {
            stringJoiner.add("'" + connectionInfo.getId() + "'");
        }
        sql += stringJoiner;
        try (Connection conn = getConnection()) {
            return DBUtils.delete(conn, sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveConnectionConfig(ConnectionConfig config) {
        String sql = "INSERT INTO connection_config " + "(id, name, host, port, db_type, db_name, username, password)\n" + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
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

    public static List<ProjectConfiguration> listProjectConfigurations() {
        try (Connection conn = getConnection()) {
            String sql = "select * from generator_config";
            ResultSet rs = DBUtils.executeQuery(conn, sql);
            List<ProjectConfiguration> results = new ArrayList<>();
            while (rs.next()) {
                ProjectConfiguration item = JSONUtils.toObject(rs.getString("value"), ProjectConfiguration.class);
                results.add(item);
            }
            return results;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveProjectConfiguration(ProjectConfiguration projectConfiguration) {
        try (Connection conn = getConnection()) {
            String sql = "insert into generator_config values(?, ?)";
            DBUtils.insert(conn, sql, projectConfiguration.getName(), JSONUtils.toJSONString(projectConfiguration));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
