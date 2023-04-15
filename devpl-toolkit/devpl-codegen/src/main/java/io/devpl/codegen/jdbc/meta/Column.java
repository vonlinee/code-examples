package io.devpl.codegen.jdbc.meta;

import io.devpl.codegen.jdbc.JdbcType;
import lombok.Data;

@Data
public class Column {

    private boolean primaryKey;

    private boolean autoIncrement;

    private String name;

    private int length;

    private boolean nullable;

    private String remarks;

    private String defaultValue;

    private int scale;

    private JdbcType jdbcType;

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

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }
}