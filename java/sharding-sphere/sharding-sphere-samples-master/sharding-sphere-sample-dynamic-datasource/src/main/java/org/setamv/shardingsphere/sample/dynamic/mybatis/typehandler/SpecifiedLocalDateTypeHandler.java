package org.setamv.shardingsphere.sample.dynamic.mybatis.typehandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.shardingsphere.shardingjdbc.jdbc.unsupported.AbstractUnsupportedOperationResultSet;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Mybatis针对{@link LocalDate}类型值默认的TypeHandler是{@link org.apache.ibatis.type.LocalDateTypeHandler}，
 * 该TypeHandler都是通过rs.getObject的方式从结果集中取值，但是Sharding jdbc不支持这种方式，
 * 参考{@link AbstractUnsupportedOperationResultSet#getObject(String, Class)}
 * 所以自定义{@link LocalDate}类型值的TypeHandler。
 * @author setamv
 * @date 2021-04-17
 */
@Component
@MappedTypes(LocalDate.class)
@MappedJdbcTypes(value = {JdbcType.DATE}, includeNullJdbcType = true)
public class SpecifiedLocalDateTypeHandler extends BaseTypeHandler<LocalDate> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String dateTime = rs.getString(columnName);
        return parse(dateTime);
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String dateTime = rs.getString(columnIndex);
        return parse(dateTime);
    }

    @Override
    public LocalDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String dateTime = cs.getString(columnIndex);
        return parse(dateTime);
    }

    private LocalDate parse(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        return LocalDate.parse(date, DATE_FORMATTER);
    }
}
