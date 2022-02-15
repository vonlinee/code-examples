package com.baomidou.mybatisplus.generator.jdbc.sql;

public class ColumnMetaData {

    private String name;
    private int dataType;
    private String dataTypeName;
    private boolean primaryKey;
    private boolean generated;
    private boolean caseSensitive; // 是否大小写敏感

    private boolean autoIncrement = false; // 是否自增
    private boolean searchable; // 是否可搜索
    private boolean currency;
    private int nullable = 0;
    private boolean signed;
    private int columnDisplaySize;
    private String columnLabel;
    private String columnName;
    private String schemaName;
    private int precision;
    private int scale;
    private String tableName;
    private String catalogName;
    private int columnType;
    private String columnTypeName;
    private boolean readOnly;
    private boolean writable;
    private boolean definitelyWritable;
    private String columnClassName;

    public String getName() {
        return name;
    }

    public int getDataType() {
        return dataType;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isGenerated() {
        return generated;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public boolean isCurrency() {
        return currency;
    }

    public int getNullable() {
        return nullable;
    }

    public boolean isSigned() {
        return signed;
    }

    public int getColumnDisplaySize() {
        return columnDisplaySize;
    }

    public String getColumnLabel() {
        return columnLabel;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public String getTableName() {
        return tableName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public int getColumnType() {
        return columnType;
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public boolean isWritable() {
        return writable;
    }

    public boolean isDefinitelyWritable() {
        return definitelyWritable;
    }

    public String getColumnClassName() {
        return columnClassName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public void setCurrency(boolean currency) {
        this.currency = currency;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public void setColumnDisplaySize(int columnDisplaySize) {
        this.columnDisplaySize = columnDisplaySize;
    }

    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    public void setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    public void setDefinitelyWritable(boolean definitelyWritable) {
        this.definitelyWritable = definitelyWritable;
    }

    public void setColumnClassName(String columnClassName) {
        this.columnClassName = columnClassName;
    }
}
