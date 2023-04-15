package io.devpl.codegen.jdbc.meta;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.DataClassRowMapper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库数据元包装类
 */
public class DatabaseMetaDataHolder {

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

    /**
     * 获取列的元数据信息
     * @param connection
     * @return
     */
    public List<ColumnMetadata> getColumns(Connection connection, String tableNamePattern) {
        List<ColumnMetadata> columnMetadataList = new ArrayList<>();
        try {
            String catalog = connection.getCatalog();
            String schema = connection.getSchema();
            DatabaseMetaData dbmd = connection.getMetaData();
            BeanPropertyRowMapper<ColumnMetadata> rowMapper = new BeanPropertyRowMapper<>(ColumnMetadata.class);
            try (ResultSet resultSet = dbmd.getColumns(catalog, schema, tableNamePattern, "%")) {
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

    public List<PrimaryKey> getPrimaryKeys(Connection connection, String tableName) {
        List<PrimaryKey> primaryKeys = new ArrayList<>();
        try {
            String catalog = connection.getCatalog();
            String schema = connection.getSchema();
            DatabaseMetaData dbmd = connection.getMetaData();
            BeanPropertyRowMapper<PrimaryKey> rowMapper = new BeanPropertyRowMapper<>(PrimaryKey.class);
            try (ResultSet primaryKeysResultSet = dbmd.getPrimaryKeys(catalog, schema, tableName)) {
                int rowIndex = 0;
                while (primaryKeysResultSet.next()) {
                    primaryKeys.add(rowMapper.mapRow(primaryKeysResultSet, rowIndex++));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return primaryKeys;
    }
}
