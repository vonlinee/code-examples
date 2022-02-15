package com.baomidou.mybatisplus.generator.jdbc.sql;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
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
}
