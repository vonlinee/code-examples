package io.maker.generator.db.meta.resultset;

import io.maker.generator.db.JdbcUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetHandler<T> {
    T handle(ResultSet rs) throws SQLException;

    default T handleAndCloseResultSet(ResultSet rs, boolean close) throws SQLException {
        T result = handle(rs);
        if (close) {
            JdbcUtils.closeQuietly(rs);
        }
        return result;
    }
}
