package io.devpl.codegen.mbpg.jdbc.meta;

import java.io.Serializable;

public class PrimaryKey implements Serializable {

    private static final long serialVersionUID = 7207765860662369965L;

    /**
     * String => table catalog (may be null)
     */
    private String tableCat;

    /**
     * String => table schema (may be null)
     */
    private String tableSchem;

    /**
     * String => table name
     */
    private String tableName;

    /**
     * String => column name
     */
    private String columnName;

    /**
     * short => sequence number within primary key( a value of 1 represents
     * the first column of the primary key, a value of 2 would represent the
     * second column within the primary key).
     */
    private Short keySeq;

    /**
     * String => primary key name (may be null)
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

    public Short getKeySeq() {
        return keySeq;
    }

    public void setKeySeq(Short keySeq) {
        this.keySeq = keySeq;
    }

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PrimaryKey{");
        sb.append("tableCat='").append(tableCat).append('\'');
        sb.append(", tableSchem='").append(tableSchem).append('\'');
        sb.append(", tableName='").append(tableName).append('\'');
        sb.append(", columnName='").append(columnName).append('\'');
        sb.append(", keySeq=").append(keySeq);
        sb.append(", pkName='").append(pkName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
