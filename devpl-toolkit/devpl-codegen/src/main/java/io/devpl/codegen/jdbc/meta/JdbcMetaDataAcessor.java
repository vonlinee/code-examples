package io.devpl.codegen.jdbc.meta;

import io.devpl.codegen.jdbc.JdbcType;
import io.devpl.codegen.mbpg.config.DataSourceConfig;
import io.devpl.codegen.utils.StringPool;
import io.devpl.sdk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.DataClassRowMapper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 数据库数据元包装类
 */
public class JdbcMetaDataAcessor {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMetaDataAcessor.class);

    private final DatabaseMetaData databaseMetaData;

    // TODO 暂时只支持一种
    private final String catalog;

    // TODO 暂时只支持一种
    private final String schema;

    /**
     * 获取表的元数据
     * @param connection 连接信息
     * @return 表的元数据
     */
    public List<TableMetadata> getTables(Connection connection) {
        List<TableMetadata> tmdList = new ArrayList<>();
        try {
            String catalog = connection.getCatalog();
            String schema = connection.getSchema();
            DatabaseMetaData dbmd = connection.getMetaData();
            BeanPropertyRowMapper<TableMetadata> rowMapper = new DataClassRowMapper<>(TableMetadata.class);

            try (ResultSet resultSet = dbmd.getTables(catalog, schema, "%", new String[]{"TABLE", "VIEW"})) {
                int rowIndex = 0;
                while (resultSet.next()) {
                    TableMetadata tmd = rowMapper.mapRow(resultSet, rowIndex++);
                    tmdList.add(tmd);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tmdList;
    }

    public JdbcMetaDataAcessor(DataSourceConfig dataSourceConfig) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            this.databaseMetaData = connection.getMetaData();
            this.catalog = connection.getCatalog();
            if (dataSourceConfig.getSchemaName() == null) {
                this.schema = connection.getSchema();
            } else {
                this.schema = dataSourceConfig.getSchemaName();
            }
            if (schema == null) {
                logger.warn("schema is null, {}", dataSourceConfig.getUrl());
            }
        } catch (SQLException e) {
            throw new RuntimeException("获取元数据错误:", e);
        }
    }

    public Map<String, Column> getColumnsInfo(String tableNamePattern, boolean queryPrimaryKey) {
        return getColumnsInfo(this.catalog, this.schema, tableNamePattern, queryPrimaryKey);
    }

    /**
     * 获取列的元数据信息
     * @param connection
     * @return
     */
    public List<ColumnMetadata> getColumns(Connection connection) {
        List<ColumnMetadata> columnMetadataList = new ArrayList<>();
        try {
            String catalog = connection.getCatalog();
            String schema = connection.getSchema();
            DatabaseMetaData dbmd = connection.getMetaData();
            BeanPropertyRowMapper<ColumnMetadata> rowMapper = new DataClassRowMapper<>(ColumnMetadata.class);

            try (ResultSet resultSet = dbmd.getTables(catalog, schema, "%", new String[]{"TABLE", "VIEW"})) {
                int rowIndex = 0;
                while (resultSet.next()) {
                    columnMetadataList.add(rowMapper.mapRow(resultSet, rowIndex++));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return columnMetadataList;
    }

    /**
     * 获取表字段信息
     * @return 表字段信息 (小写字段名->字段信息)
     */
    public Map<String, Column> getColumnsInfo(String catalog, String schema, String tableName, boolean queryPrimaryKey) {
        Set<String> primaryKeys = new HashSet<>();
        if (queryPrimaryKey) {
            try (ResultSet primaryKeysResultSet = databaseMetaData.getPrimaryKeys(catalog, schema, tableName)) {
                while (primaryKeysResultSet.next()) {
                    String columnName = primaryKeysResultSet.getString("COLUMN_NAME");
                    primaryKeys.add(columnName);
                }
                if (primaryKeys.size() > 1) {
                    logger.warn("当前表:{}，存在多主键情况！", tableName);
                }
            } catch (SQLException e) {
                throw new RuntimeException("读取表主键信息:" + tableName + "错误:", e);
            }
        }
        Map<String, Column> columnsInfoMap = new LinkedHashMap<>();
        try (ResultSet resultSet = databaseMetaData.getColumns(catalog, schema, tableName, "%")) {
            while (resultSet.next()) {
                Column column = new Column();
                column.setName(resultSet.getString("COLUMN_NAME"));
                column.setPrimaryKey(primaryKeys.contains(column.getName()));
                column.setJdbcType(JdbcType.forCode(resultSet.getInt("DATA_TYPE")));
                column.setLength(resultSet.getInt("COLUMN_SIZE"));
                column.setScale(resultSet.getInt("DECIMAL_DIGITS"));
                column.setRemarks(formatComment(resultSet.getString("REMARKS")));
                column.setDefaultValue(resultSet.getString("COLUMN_DEF"));
                column.setNullable(resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable);
                try {
                    column.setAutoIncrement("YES".equals(resultSet.getString("IS_AUTOINCREMENT")));
                } catch (SQLException sqlException) {
                    logger.warn("获取IS_AUTOINCREMENT出现异常:", sqlException);
                }
                columnsInfoMap.put(column.getName().toLowerCase(), column);
            }
            return Collections.unmodifiableMap(columnsInfoMap);
        } catch (SQLException e) {
            throw new RuntimeException("读取表字段信息:" + tableName + "错误:", e);
        }
    }

    public String formatComment(String comment) {
        return StringUtils.isBlank(comment) ? StringPool.EMPTY : comment.replaceAll("\r\n", "\t");
    }

    public Table getTableInfo(String tableName) {
        return getTableInfo(this.catalog, this.schema, tableName);
    }

    public List<Table> getTables(String tableNamePattern, String[] types) {
        return getTables(this.catalog, this.schema, tableNamePattern, types);
    }

    public List<Table> getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) {
        List<Table> tables = new ArrayList<>();
        try (ResultSet resultSet = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types)) {
            Table table;
            while (resultSet.next()) {
                table = new Table();
                table.setName(resultSet.getString("TABLE_NAME"));
                table.setRemarks(formatComment(resultSet.getString("REMARKS")));
                table.setTableType(resultSet.getString("TABLE_TYPE"));
                tables.add(table);
            }
        } catch (SQLException e) {
            throw new RuntimeException("读取数据库表信息出现错误", e);
        }
        return tables;
    }

    public Table getTableInfo(String catalog, String schema, String tableName) {
        Table table = new Table();
        try (ResultSet resultSet = databaseMetaData.getTables(catalog, schema, tableName, new String[]{"TABLE", "VIEW"})) {
            table.setName(tableName);
            while (resultSet.next()) {
                table.setRemarks(formatComment(resultSet.getString("REMARKS")));
                table.setTableType(resultSet.getString("TABLE_TYPE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("读取表信息:" + tableName + "错误:", e);
        }
        return table;
    }
}
