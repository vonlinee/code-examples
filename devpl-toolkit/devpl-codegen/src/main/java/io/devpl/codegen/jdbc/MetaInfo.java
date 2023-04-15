package io.devpl.codegen.jdbc;

import io.devpl.codegen.jdbc.meta.Column;

public class MetaInfo {
    private String name;

    private int length;

    private boolean nullable;

    private String remarks;

    private String defaultValue;

    private int scale;

    private JdbcType jdbcType;

    public MetaInfo(Column column) {
        if (column != null) {
            this.name = column.getName();
            this.length = column.getLength();
            this.nullable = column.isNullable();
            this.remarks = column.getRemarks();
            this.defaultValue = column.getDefaultValue();
            this.scale = column.getScale();
            this.jdbcType = column.getJdbcType();
        }
    }

    public int getLength() {
        return length;
    }

    public boolean isNullable() {
        return nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public int getScale() {
        return scale;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    @Override
    public String toString() {
        return "MetaInfo{" + "length=" + length + ", nullable=" + nullable + ", remarks='" + remarks + '\'' + ", defaultValue='" + defaultValue + '\'' + ", scale=" + scale + ", jdbcType=" + jdbcType + '}';
    }
}