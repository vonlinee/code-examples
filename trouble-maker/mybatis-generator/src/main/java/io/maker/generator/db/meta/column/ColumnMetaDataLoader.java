/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.maker.generator.db.meta.column;

import io.maker.generator.db.JdbcUtils;
import io.maker.generator.db.meta.resultset.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * ResultSetColumnMetadata meta data loader.
 */
public final class ColumnMetaDataLoader {

    private ColumnMetaDataLoader() {
    }

    private static final Logger LOG = LoggerFactory.getLogger(ColumnMetaDataLoader.class);

    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String TYPE_NAME = "TYPE_NAME";

    /**
     * Load column meta data list.
     * @param connection   connection
     * @param table        table name
     * @param databaseType database type
     * @return column meta data list
     * @throws SQLException SQL exception
     */
    public static Collection<ColumnMetaData> load(final Connection connection, final String table,
                                                  final String databaseType) throws SQLException {
        if (!isTableExist(connection, connection.getCatalog(), table, databaseType)) {
            LOG.error(table + " does not exist!");
            return Collections.emptyList();
        }
        Collection<ColumnMetaData> result = new LinkedList<>();
        Collection<String> primaryKeys = loadPrimaryKeys(connection, table, databaseType);
        List<String> columnNames = new ArrayList<>();
        List<Integer> columnTypes = new ArrayList<>();
        List<String> columnTypeNames = new ArrayList<>();
        List<Boolean> isPrimaryKeys = new ArrayList<>();
        List<Boolean> isCaseSensitives = new ArrayList<>();
        try (ResultSet resultSet = connection.getMetaData().getColumns(connection.getCatalog(),
                JdbcUtils.getSchema(connection, databaseType), table, "%")) {
            while (resultSet.next()) {
                String columnName = resultSet.getString(COLUMN_NAME);
                columnTypes.add(resultSet.getInt(DATA_TYPE));
                columnTypeNames.add(resultSet.getString(TYPE_NAME));
                isPrimaryKeys.add(primaryKeys.contains(columnName));
                columnNames.add(columnName);
            }
        }
        String emptySql = generateEmptyResultSQL(table, databaseType);
        LOG.info("execute sql :\n" + emptySql);
        try (ResultSet resultSet = connection.createStatement().executeQuery(emptySql)) {
            for (String each : columnNames) {
                isCaseSensitives.add(resultSet.getMetaData().isCaseSensitive(resultSet.findColumn(each)));
            }
        }
        for (int i = 0; i < columnNames.size(); i++) {
            // TODO load auto generated from database meta data
            ColumnMetaData columnMetaData = new ColumnMetaData();
            columnMetaData.setName(columnNames.get(i));
            columnMetaData.setColumnName(columnNames.get(i));
            columnMetaData.setColumnType(columnTypes.get(i));
            columnMetaData.setColumnTypeName(columnTypeNames.get(i));
            columnMetaData.setPrimaryKey(isPrimaryKeys.get(i));
            columnMetaData.setGenerated(false);
            columnMetaData.setCaseSensitive(isCaseSensitives.get(i));
            result.add(columnMetaData);
        }
        return result;
    }

    /**
     * Load column meta data list.
     * @param connection   connection
     * @param table        table name
     * @param databaseType database type
     * @return column meta data list
     * @throws SQLException SQL exception
     */
    public static Collection<ColumnMetaData> loadMetaInfo(final Connection connection, final String table,
                                                          final String databaseType) throws SQLException {
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
        try (ResultSet resultSet = connection.getMetaData().getColumns(connection.getCatalog(),
                JdbcUtils.getSchema(connection, databaseType), table, "%")) {
            while (resultSet.next()) {
                String columnName = resultSet.getString(COLUMN_NAME);
                columnTypes.add(resultSet.getInt(DATA_TYPE));
                columnTypeNames.add(resultSet.getString(TYPE_NAME));
                isPrimaryKeys.add(primaryKeys.contains(columnName));
                columnNames.add(columnName);
            }
        }
        String emptySql = generateEmptyResultSQL(table, databaseType);
        LOG.info("execute sql :\n" + emptySql);
        try (ResultSet resultSet = connection.createStatement().executeQuery(emptySql)) {
            for (String each : columnNames) {
                isCaseSensitives.add(resultSet.getMetaData().isCaseSensitive(resultSet.findColumn(each)));
            }
        }
        for (int i = 0; i < columnNames.size(); i++) {
            // TODO load auto generated from database meta data
            ColumnMetaData columnMetaData = new ColumnMetaData();
            columnMetaData.setName(columnNames.get(i));
            columnMetaData.setColumnName(columnNames.get(i));
            columnMetaData.setColumnType(columnTypes.get(i));
            columnMetaData.setColumnTypeName(columnTypeNames.get(i));
            columnMetaData.setPrimaryKey(isPrimaryKeys.get(i));
            columnMetaData.setGenerated(false);
            columnMetaData.setCaseSensitive(isCaseSensitives.get(i));
            result.add(columnMetaData);
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
        } else if ("Oracle".equals(databaseType) || "PostgreSQL".equals(databaseType) || "H2".equals(databaseType)
                || "SQL92".equals(databaseType)) {
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

    /**
     * 判断表是否存在
     * @param connection
     * @param catalog
     * @param table
     * @param databaseType
     * @return
     * @throws SQLException boolean
     */
    private static boolean isTableExist(final Connection connection, final String catalog, final String table,
                                        final String databaseType) throws SQLException {
        try (ResultSet resultSet = connection.getMetaData().getTables(catalog,
                JdbcUtils.getSchema(connection, databaseType), table, null)) {
            return resultSet.next();
        }
    }

    private static Collection<String> loadPrimaryKeys(final Connection connection, final String table,
                                                      final String databaseType) throws SQLException {
        Collection<String> result = new HashSet<>();
        try (ResultSet resultSet = connection.getMetaData().getPrimaryKeys(connection.getCatalog(),
                JdbcUtils.getSchema(connection, databaseType), table)) {
            while (resultSet.next()) {
                result.add(resultSet.getString(COLUMN_NAME));
            }
        }
        return result;
    }

    private static final String INFOMATION_SCHEMA_SQL_FORMAT = "SELECT * FROM `information_schema`.`COLUMNS` WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s'";

    public static ColumnInfoSchema loadInfomationSchema(final DataSource dataSource, String dbName, String tableName) {
        String sql = String.format(INFOMATION_SCHEMA_SQL_FORMAT, dbName, tableName);
        ColumnInfoSchema schema = new ColumnInfoSchema();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    schema.setTableCatalog(resultSet.getString("TABLE_CATALOG"));
                    schema.setTableSchema(resultSet.getString("TABLE_SCHEMA"));
                    schema.setTableName(resultSet.getString("TABLE_NAME"));
                    schema.setColumnName(resultSet.getString("COLUMN_NAME")); //varchar(64)
                    schema.setOrdinalPosition(resultSet.getString("ORDINAL_POSITION")); //bigint(21) unsigned
                    schema.setColumnDefault(resultSet.getString("COLUMN_DEFAULT")); // longtext
                    schema.setIsNullable(resultSet.getString("IS_NULLABLE")); //varchar(3)
                    schema.setTableCatalog(resultSet.getString("DATA_TYPE")); //varchar(64)
                    schema.setTableCatalog(resultSet.getString("CHARACTER_MAXIMUM_LENGTH"));//bigint(21) unsigned
                    schema.setTableCatalog(resultSet.getString("CHARACTER_OCTET_LENGTH"));//bigint(21) unsigned
                    schema.setTableCatalog(resultSet.getString("NUMERIC_PRECISION"));//bigint(21) unsigned
                    schema.setTableCatalog(resultSet.getString("NUMERIC_SCALE"));//bigint(21) unsigned
                    schema.setTableCatalog(resultSet.getString("DATETIME_PRECISION"));//bigint(21) unsigned
                    schema.setTableCatalog(resultSet.getString("CHARACTER_SET_NAME"));//varchar(32)
                    schema.setTableCatalog(resultSet.getString("COLLATION_NAME"));//varchar(32)
                    schema.setTableCatalog(resultSet.getString("COLUMN_TYPE"));//longtext
                    schema.setTableCatalog(resultSet.getString("COLUMN_KEY"));//varchar(3)
                    schema.setTableCatalog(resultSet.getString("EXTRA"));//varchar(30)
                    schema.setTableCatalog(resultSet.getString("PRIVILEGES"));//varchar(80)
                    schema.setTableCatalog(resultSet.getString("COLUMN_COMMENT"));//varchar(1024)
                    schema.setTableCatalog(resultSet.getString("GENERATION_EXPRESSION"));// longtext
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schema;
    }

    public static <T> T loadInfomationSchema(final DataSource dataSource, String dbName, String tableName, ResultSetHandler<T> handler) {
        String sql = String.format(INFOMATION_SCHEMA_SQL_FORMAT, dbName, tableName);
        ColumnInfoSchema schema = new ColumnInfoSchema();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                return handler.handle(resultSet, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
