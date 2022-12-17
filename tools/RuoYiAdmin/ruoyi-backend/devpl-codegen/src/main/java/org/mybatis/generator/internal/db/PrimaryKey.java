package org.mybatis.generator.internal.db;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class PrimaryKey {

    private String tableCat;
    private String tableSchem;
    private String tableName;
    private String columnName;
    private String keySeq;
    private String pkName;

    public static PrimaryKey of(ResultSet resultSet) {
        final PrimaryKey primaryKey = new PrimaryKey();
        try {
            primaryKey.setTableCat(resultSet.getString("TABLE_CAT"));
            primaryKey.setTableSchem(resultSet.getString("TABLE_SCHEM"));
            primaryKey.setTableName(resultSet.getString("TABLE_NAME"));
            primaryKey.setColumnName(resultSet.getString("COLUMN_NAME"));
            primaryKey.setKeySeq(resultSet.getString("KEY_SEQ"));
            primaryKey.setPkName(resultSet.getString("PK_NAME"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
        return primaryKey;
    }
}
