package io.doraemon.pocket.generator.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ColumnMetaDataLoader {
    
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String TYPE_NAME = "TYPE_NAME";
    
    /**
     * Load column meta data list.
     * @param connection connection
     * @param table table name
     * @param databaseType database type
     * @return column meta data list
     * @throws SQLException SQL exception
     */
    public static Collection<ColumnMetaData> load(final Connection connection, final String table, final String databaseType) throws SQLException {
        if (!isTableExist(connection, connection.getCatalog(), table, databaseType)) {
            return Collections.emptyList();
        }
        Collection<ColumnMetaData> result = new LinkedList<>();
        Collection<String> primaryKeys = loadPrimaryKeys(connection, table, databaseType);
        List<String> columnNames = new ArrayList<>();
        List<Integer> columnTypes = new ArrayList<>();
        List<String> columnTypeNames = new ArrayList<>();
        List<Boolean> isPrimaryKeys = new ArrayList<>();
        List<Boolean> isCaseSensitives = new ArrayList<>();
        try (ResultSet resultSet = connection.getMetaData().getColumns(connection.getCatalog(), JdbcUtil.getSchema(connection, databaseType), table, "%")) {
            while (resultSet.next()) {
                String columnName = resultSet.getString(COLUMN_NAME);
                columnTypes.add(resultSet.getInt(DATA_TYPE));
                columnTypeNames.add(resultSet.getString(TYPE_NAME));
                isPrimaryKeys.add(primaryKeys.contains(columnName));
                columnNames.add(columnName);
            }
        }
        try (ResultSet resultSet = connection.createStatement().executeQuery(generateEmptyResultSQL(table, databaseType))) {
            for (String each : columnNames) {
                isCaseSensitives.add(resultSet.getMetaData().isCaseSensitive(resultSet.findColumn(each)));
            }
        }
        for (int i = 0; i < columnNames.size(); i++) {
            // TODO load auto generated from database meta data
            result.add(new ColumnMetaData(columnNames.get(i), columnTypes.get(i), columnTypeNames.get(i), isPrimaryKeys.get(i), false, isCaseSensitives.get(i)));
        }
        return result;
    }
    
    private static String generateEmptyResultSQL(final String table, final String databaseType) {
        // TODO consider add a getDialectDelimeter() interface in parse module
        String delimiterLeft;
        String delimiterRight;
        if ("MySQL".equals(databaseType) || "MariaDB".equals(databaseType)) {
            delimiterLeft = "`";
            delimiterRight = "`";
        } else if ("Oracle".equals(databaseType) || "PostgreSQL".equals(databaseType) || "H2".equals(databaseType) || "SQL92".equals(databaseType)) {
            delimiterLeft = "\"";
            delimiterRight = "\"";
        } else if ("SQLServer".equals(databaseType)) {
            delimiterLeft = "[";
            delimiterRight = "]";
        } else {
            delimiterLeft = "";
            delimiterRight = "";
        }
        return "SELECT * FROM " + delimiterLeft + table + delimiterRight + " WHERE 1 != 1";
    }
    
    private static boolean isTableExist(final Connection connection, final String catalog, final String table, final String databaseType) throws SQLException {
        try (ResultSet resultSet = connection.getMetaData().getTables(catalog, JdbcUtil.getSchema(connection, databaseType), table, null)) {
            return resultSet.next();
        }
    }
    
    private static Collection<String> loadPrimaryKeys(final Connection connection, final String table, final String databaseType) throws SQLException {
        Collection<String> result = new HashSet<>();
        try (ResultSet resultSet = connection.getMetaData().getPrimaryKeys(connection.getCatalog(), JdbcUtil.getSchema(connection, databaseType), table)) {
            while (resultSet.next()) {
                result.add(resultSet.getString(COLUMN_NAME));
            }
        }
        return result;
    }
}
