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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Mybatis针对{@link LocalDateTime}类型值默认的TypeHandler是{@link org.apache.ibatis.type.LocalDateTimeTypeHandler}，
 * 该TypeHandler都是通过rs.getObject的方式从结果集中取值，但是Sharding jdbc不支持这种方式，
 * 参考{@link AbstractUnsupportedOperationResultSet#getObject(java.lang.String, java.lang.Class)}
 * 所以自定义{@link LocalDateTime}类型值的TypeHandler。
 * @author setamv
 * @date 2021-04-17
 */
@Component
@MappedTypes(LocalDateTime.class)
@MappedJdbcTypes(value = {JdbcType.TIMESTAMP, JdbcType.TIME}, includeNullJdbcType = true)
public class SpecifiedLocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

    private static final String DATE_TIME_0_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_TIME_1_PATTERN = "yyyy-MM-dd HH:mm:ss.S";
    private static final String DATE_TIME_2_PATTERN = "yyyy-MM-dd HH:mm:ss.SS";
    private static final String DATE_TIME_3_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    private static final int DATE_TIME_0_LEN = DATE_TIME_0_PATTERN.length();
    private static final int DATE_TIME_1_LEN = DATE_TIME_1_PATTERN.length();
    private static final int DATE_TIME_2_LEN = DATE_TIME_2_PATTERN.length();
    private static final int DATE_TIME_3_LEN = DATE_TIME_3_PATTERN.length();

    private static final DateTimeFormatter DATE_TIME_0_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_0_PATTERN);
    private static final DateTimeFormatter DATE_TIME_1_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_1_PATTERN);
    private static final DateTimeFormatter DATE_TIME_2_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_2_PATTERN);
    private static final DateTimeFormatter DATE_TIME_3_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_3_PATTERN);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String dateTime = rs.getString(columnName);
        return parse(dateTime);
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String dateTime = rs.getString(columnIndex);
        return parse(dateTime);
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String dateTime = cs.getString(columnIndex);
        return parse(dateTime);
    }

    private LocalDateTime parse(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            return null;
        }
        int len = dateTime.length();
        if (len == DATE_TIME_0_LEN) {
            return LocalDateTime.parse(dateTime, DATE_TIME_0_FORMATTER);
        } else if (len == DATE_TIME_1_LEN) {
            return LocalDateTime.parse(dateTime, DATE_TIME_1_FORMATTER);
        } else if (len == DATE_TIME_2_LEN) {
            return LocalDateTime.parse(dateTime, DATE_TIME_2_FORMATTER);
        } else if (len == DATE_TIME_3_LEN) {
            return LocalDateTime.parse(dateTime, DATE_TIME_3_FORMATTER);
        }
        throw new IllegalArgumentException("数据库的时间值[" + dateTime + "]无法解析成java.time.LocalDateTime");
    }
}
