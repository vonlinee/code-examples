package com.baomidou.mybatisplus.generator.jdbc;

import org.apache.ibatis.type.JdbcType;

public class ColumnInfo {

    private String name;    //列名
    private int length;     //列长度
    private boolean nullable;
    private String remarks;
    private String defaultValue;
    private int scale;
    private JdbcType jdbcType;

    public void setName(String name) {
        this.name = name;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getName() {
        return name;
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
}