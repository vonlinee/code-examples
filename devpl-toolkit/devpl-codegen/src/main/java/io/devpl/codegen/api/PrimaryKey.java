package io.devpl.codegen.api;

import java.sql.DatabaseMetaData;

/**
 * @see DatabaseMetaData#getPrimaryKeys(String, String, String)
 */
public class PrimaryKey {

    /**
     * table catalog (maybe null)
     */
    private String tableCat;

    /**
     * table schema (maybe null)
     */
    private String tableSchem;

    /**
     * table name
     */
    private String tableName;

    /**
     * column name
     */
    private String columnName;

    /**
     * sequence number within primary key( a value of 1 represents the first column of the primary key, a value of 2 would represent the second column within the primary key).
     */
    private String keySeq;

    /**
     * primary key name (maybe null)
     */
    private String pkName;

    public String getTableCat() {
        return tableCat;
    }

    public void setTableCat(String tableCat) {
        this.tableCat = tableCat;
    }

    public String getTableSchem() {
        return tableSchem;
    }

    public void setTableSchem(String tableSchem) {
        this.tableSchem = tableSchem;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getKeySeq() {
        return keySeq;
    }

    public void setKeySeq(String keySeq) {
        this.keySeq = keySeq;
    }

    public String getPrimaryKeyName() {
        return pkName;
    }

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }
}
