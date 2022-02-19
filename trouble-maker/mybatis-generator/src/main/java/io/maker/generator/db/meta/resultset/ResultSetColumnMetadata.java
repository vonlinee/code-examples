package io.maker.generator.db.meta.resultset;

import lombok.Data;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * ResultSet的每一列，字段根据ResultSetMetaData进行对应
 * 与ColumnMetadata区别开
 */
public class ResultSetColumnMetadata implements Serializable {

    private String columnClassName;
    private String columnName;
    private String catalogName;
    private int columnDisplaySize;
    private String columnLabel;
    private int columnType;
    private String columnTypeName;
    private int precision;
    private int scale;
    private String schemaName;
    private String tableName;
    private boolean autoIncrement;
    private boolean caseSensitive;
    private boolean currency;
    private boolean definitelyWritable;
    private int nullable;
    private boolean readonly;
    private boolean searchable;
    private boolean signed;
    private boolean writable;

    public ResultSetColumnMetadata(ResultSetMetaData rsmd, int index) {
        try {
            this.columnClassName = rsmd.getColumnClassName(index);
            this.columnName = rsmd.getColumnName(index);
            this.catalogName = rsmd.getCatalogName(index);
            this.columnDisplaySize = rsmd.getColumnDisplaySize(index);
            this.columnLabel = rsmd.getColumnLabel(index);
            this.columnType = rsmd.getColumnType(index);
            this.columnTypeName = rsmd.getColumnTypeName(index);
            this.precision = rsmd.getPrecision(index);
            this.scale = rsmd.getScale(index);
            this.schemaName = rsmd.getSchemaName(index);
            this.tableName = rsmd.getTableName(index);
            this.autoIncrement = rsmd.isAutoIncrement(index);
            this.caseSensitive = rsmd.isCaseSensitive(index);
            this.currency = rsmd.isCurrency(index);
            this.definitelyWritable = rsmd.isDefinitelyWritable(index);
            this.nullable = rsmd.isNullable(index);
            this.readonly = rsmd.isReadOnly(index);
            this.searchable = rsmd.isSearchable(index);
            this.signed = rsmd.isSigned(index);
            this.writable = rsmd.isWritable(index);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(20);
        map.put("columnClassName", this.columnClassName);
        map.put("columnName", this.columnName);
        map.put("catalogName", this.catalogName);
        map.put("columnDisplaySize", this.columnDisplaySize);
        map.put("columnLabel", this.columnLabel);
        map.put("columnType", this.columnType);
        map.put("columnTypeName", this.columnTypeName);
        map.put("precision", this.precision);
        map.put("scale", this.scale);
        map.put("schemaName", this.schemaName);
        map.put("tableName", this.tableName);
        map.put("autoIncrement", this.autoIncrement);
        map.put("caseSensitive", this.caseSensitive);
        map.put("currency", this.currency);
        map.put("definitelyWritable", this.definitelyWritable);
        map.put("nullable", this.nullable);
        map.put("readonly", this.readonly);
        map.put("searchable", this.searchable);
        map.put("signed", this.signed);
        map.put("writable", this.writable);
        return map;
    }

    public String getColumnClassName() {
        return columnClassName;
    }

    public void setColumnClassName(String columnClassName) {
        this.columnClassName = columnClassName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public int getColumnDisplaySize() {
        return columnDisplaySize;
    }

    public void setColumnDisplaySize(int columnDisplaySize) {
        this.columnDisplaySize = columnDisplaySize;
    }

    public String getColumnLabel() {
        return columnLabel;
    }

    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }

    public int getColumnType() {
        return columnType;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public void setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public boolean isCurrency() {
        return currency;
    }

    public void setCurrency(boolean currency) {
        this.currency = currency;
    }

    public boolean isDefinitelyWritable() {
        return definitelyWritable;
    }

    public void setDefinitelyWritable(boolean definitelyWritable) {
        this.definitelyWritable = definitelyWritable;
    }

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }
}
