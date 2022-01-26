package io.doraemon.pocket.generator.sql;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ColumnMetaData {
    
    private final String name;
    private final int dataType;
    private final String dataTypeName;
    private final boolean primaryKey;
    private final boolean generated;
    private final boolean caseSensitive; // 是否大小写敏感
    
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