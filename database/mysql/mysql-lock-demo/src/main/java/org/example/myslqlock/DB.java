package org.example.myslqlock;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.function.BiConsumer;

public class DB {
    private final BasicDataSource dataSource;

    public DB() {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/mysql_learn");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }

    // 获取数据库连接
    public final Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // 查询单个对象
    public <T> T queryForObject(String sql, Class<T> clazz, Object... params) {
        try (Connection conn = getConnection()) {
            QueryRunner runner = new QueryRunner(dataSource);
            return runner.query(conn, sql, new BeanHandler<>(clazz), params);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 查询多个对象
    public <T> List<T> queryForList(String sql, Class<T> clazz, Object... params) {
        try (Connection conn = getConnection()) {
            QueryRunner runner = new QueryRunner(dataSource);
            return runner.query(conn, sql, new BeanListHandler<>(clazz), params);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 更新操作（插入、更新、删除）
    public int update(String sql, Object... params) {
        try (Connection conn = getConnection()) {
            QueryRunner runner = new QueryRunner(dataSource);
            return runner.update(conn, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void transactional(BiConsumer<Connection, QueryRunner> action) throws RuntimeException {
        QueryRunner runner = new QueryRunner(dataSource);
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try {
                action.accept(conn, runner);
                conn.commit();
            } catch (Throwable throwable) {
                conn.rollback();
                throw throwable;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}