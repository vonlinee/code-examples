package io.maker.codegen.core.db.meta.schema;

import java.io.Serializable;

/**
 * SELECT * FROM `information_schema`.`COLUMNS` T
 * 对应于information_schema.COLUMNS表的字段
 * 
 * SHOW COLUMNS from innodb_table from test;show 命令只显示该表中一些栏位
 * 第一个from后是表名，第二from后是数据库名称
 * 
 * 官方文档：https://dev.mysql.com/doc/refman/5.7/en/columns-table.html
 */
public class ColumnInfoSchema implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 包含列的表所属的目录的名称，该值总是def
	 */
	private String tableCatalog;
	
	/**
	 * 包含列的表所属的数据库的名称
	 */
	private String tableSchema;
	
	/**
	 * 包含列的表名
	 */
	private String tableName;
	
	/**
	 * 列名
	 */
	private String columnName;
	
	/**
	 * 该列在表中的位置
	 */
	private Long ordinalPosition;
	
	/**
	 * 列的默认值，如果未定义或者显式的指定为NULL，则该值为NULL
	 */
	private String columnDefault;
	
	/**
	 * 该列是否为空(YES/NO)
	 */
	private String isNullable;
	
	/**
	 * 列的数据类型，不包含其他信息，如数据类型的精度
	 */
	private String dataType;
	
	/**
	 * 字符串类型的列的最大长度，字符为单位
	 */
	private Long characterMaximumLength;
	
	/**
	 * 字符串类型的列的最大长度，字符为字节
	 */
	private Long characterOctetLength;
	
	/**
	 * number类型的列的精度
	 */
	private Long numericPrecision;
	
	/**
	 * number类型的列的scale
	 */
	private Long numericScale;
	
	/**
	 * 对于日期类型的列的分数秒精度
	 */
	private Long datetimePrecision;
	
	/**
	 * 对于字符串的列，其字符集的名称
	 */
	private String characterSetName;
	
	/**
	 * 对于字符串的列，其排序规则的名称
	 */
	private String collationName;
	
	/**
	 * 列的数据类型，除了类型外可能包含其他信息，例如精度等
	 */
	private String columnType;
	
	/**
	 * 该列是否被索引，该列显示列是否被索引，其有如下可能值：
	 * 	1.空代表没有被索引，或者是一个多列的非唯一的索引的次要列
	 *  2.PRI 代表是主键，或者是一个多列主键的其中一个栏位
	 *  3.UNI 代表是一个唯一索引的第一个列，一个唯一索引是可以有多个空值的
	 *  3.MUL 代表该列是一个非唯一索引的第一个列
	 *  如果一个栏位在多个索引中，COLUMN_KEY只会显示其中优先级最高的一个，顺序为PRI, UNI, MUL，如果表中无主键，
	 *  如果一个唯一索引不可以包含空值(定义非空)，该列其可能会被显示为PRI。一个复合索引如果是唯一的，该列也有可能会被显示为MUL
	 */
	private String columnKey;
	
	/**
	 * 列的其他一些信息
	 * auto_increment 代表该列有AUTO_INCREMENT属性
	 * on update 对于TIMESTAMP 或 DATETIME类型的列,
	 * CURRENT_TIMESTAM:有ON UPDATE CURRENT_TIMESTAMP属性
	 * VIRTUAL GENERATED或者 VIRTUAL STORED 对于生成列的一些信息
	 */
	private String extra;
	
	/**
	 * 对该列所拥有的权限，不同用户查询的结果不同
	 */
	private String privileges;
	
	/**
	 * 列的注释
	 */
	private String columnComment;
	
	/**
	 * 如果是生成列，这里显示用来继续其值的表达式，否则为空
	 */
	private String generationExpression;

	public String getTableCatalog() {
		return tableCatalog;
	}

	public void setTableCatalog(String tableCatalog) {
		this.tableCatalog = tableCatalog;
	}

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
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

	public Long getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(Long ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getColumnDefault() {
		return columnDefault;
	}

	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Long getCharacterMaximumLength() {
		return characterMaximumLength;
	}

	public void setCharacterMaximumLength(Long characterMaximumLength) {
		this.characterMaximumLength = characterMaximumLength;
	}

	public Long getCharacterOctetLength() {
		return characterOctetLength;
	}

	public void setCharacterOctetLength(Long characterOctetLength) {
		this.characterOctetLength = characterOctetLength;
	}

	public Long getNumericPrecision() {
		return numericPrecision;
	}

	public void setNumericPrecision(Long numericPrecision) {
		this.numericPrecision = numericPrecision;
	}

	public Long getNumericScale() {
		return numericScale;
	}

	public void setNumericScale(Long numericScale) {
		this.numericScale = numericScale;
	}

	public Long getDatetimePrecision() {
		return datetimePrecision;
	}

	public void setDatetimePrecision(Long datetimePrecision) {
		this.datetimePrecision = datetimePrecision;
	}

	public String getCharacterSetName() {
		return characterSetName;
	}

	public void setCharacterSetName(String characterSetName) {
		this.characterSetName = characterSetName;
	}

	public String getCollationName() {
		return collationName;
	}

	public void setCollationName(String collationName) {
		this.collationName = collationName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getGenerationExpression() {
		return generationExpression;
	}

	public void setGenerationExpression(String generationExpression) {
		this.generationExpression = generationExpression;
	}

	@Override
	public String toString() {
		return "Columns{" +
				"tableCatalog=" + tableCatalog +
				", tableSchema=" + tableSchema +
				", tableName=" + tableName +
				", columnName=" + columnName +
				", ordinalPosition=" + ordinalPosition +
				", columnDefault=" + columnDefault +
				", isNullable=" + isNullable +
				", dataType=" + dataType +
				", characterMaximumLength=" + characterMaximumLength +
				", characterOctetLength=" + characterOctetLength +
				", numericPrecision=" + numericPrecision +
				", numericScale=" + numericScale +
				", datetimePrecision=" + datetimePrecision +
				", characterSetName=" + characterSetName +
				", collationName=" + collationName +
				", columnType=" + columnType +
				", columnKey=" + columnKey +
				", extra=" + extra +
				", privileges=" + privileges +
				", columnComment=" + columnComment +
				", generationExpression=" + generationExpression +
				"}";
	}
}
