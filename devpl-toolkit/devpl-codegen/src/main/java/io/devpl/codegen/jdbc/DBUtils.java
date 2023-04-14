package io.devpl.codegen.jdbc;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    public static <T> List<T> extract(ResultSet resultSet, Class<T> type) throws SQLException {
        BeanPropertyRowMapper<T> beanRowMapper = new BeanPropertyRowMapper<>(type);
        int rowNum = 0;
        List<T> rowList = new ArrayList<>();
        while (resultSet.next()) {
            rowList.add(beanRowMapper.mapRow(resultSet, rowNum++));
        }
        return rowList;
    }
}
