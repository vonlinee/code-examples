package io.devpl.toolkit.fxui.utils;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * 将ResultSet转换为Map,列名如果是下划线,将会被转换为驼峰
 * @see org.springframework.jdbc.core.ColumnMapRowMapper
 */
public class MapEntityRowMapper implements RowMapper<Map<String, Object>> {

    @Override
    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Map<String, Object> mapOfColumnValues = createColumnMap(columnCount);
        for (int i = 1; i <= columnCount; i++) {
            String column = JdbcUtils.lookupColumnName(rsmd, i);
            final String columnKey = getColumnKey(column);
            // 下划线转驼峰
            mapOfColumnValues.putIfAbsent(StringUtils.underlineToCamel(columnKey), getColumnValue(rs, i));
        }
        return mapOfColumnValues;
    }

    /**
     * Create a Map instance to be used as column map.
     * <p>By default, a linked case-insensitive Map will be created.
     * @param columnCount the column count, to be used as initial
     *                    capacity for the Map
     * @return the new Map instance
     * @see org.springframework.util.LinkedCaseInsensitiveMap
     */
    protected Map<String, Object> createColumnMap(int columnCount) {
        return new LinkedCaseInsensitiveMap<>(columnCount);
    }

    /**
     * Determine the key to use for the given column in the column Map.
     * <p>By default, the supplied column name will be returned unmodified.
     * @param columnName the column name as returned by the ResultSet
     * @return the column key to use
     * @see java.sql.ResultSetMetaData#getColumnName
     */
    protected String getColumnKey(String columnName) {
        return columnName;
    }

    /**
     * Retrieve a JDBC object value for the specified column.
     * <p>The default implementation uses the {@code getObject} method.
     * Additionally, this implementation includes a "hack" to get around Oracle
     * returning a non standard object for their TIMESTAMP data type.
     * @param rs    the ResultSet holding the data
     * @param index the column index
     * @return the Object returned
     * @see org.springframework.jdbc.support.JdbcUtils#getResultSetValue
     */
    @Nullable
    protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
        return JdbcUtils.getResultSetValue(rs, index);
    }
}
