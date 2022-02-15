package com.baomidou.mybatisplus.generator.jdbc.model.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler<T> {
    T handle(ResultSet rs) throws SQLException;
}
