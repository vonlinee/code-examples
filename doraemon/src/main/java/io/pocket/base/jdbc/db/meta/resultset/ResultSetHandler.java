package io.pocket.base.jdbc.db.meta.resultset;

import io.pocket.base.jdbc.db.JdbcUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetHandler<T> {
    T handle(ResultSet rs) throws SQLException;

    default T handle(ResultSet rs, boolean close) throws SQLException {
        T result = handle(rs);
        if (close) {
            JdbcUtils.closeQuietly(rs);
        }
        return result;
    }
}
