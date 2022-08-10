package io.maker.codegen.core.db.meta.schema;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * SELECT T.TABLE_SCHEMA, -- 模式 T.TABLE_NAME, -- 表名 T.TABLE_COMMENT, -- 表名注释
 * C.COLUMN_NAME, -- 字段名 C.COLUMN_TYPE, -- 字段类型 C.COLUMN_COMMENT -- 字段注释 FROM
 * `information_schema`.`TABLES` T, `information_schema`.`COLUMNS` C WHERE
 * T.TABLE_NAME = C.TABLE_NAME AND T.TABLE_SCHEMA = 'dbName' ORDER BY
 * T.TABLE_NAME, C.ORDINAL_POSITION 使用于MySQL SELECT * FROM
 * `information_schema`.`TABLES` T
 */
public class TableInfoSchema implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tableCatalog; // 数据表登记目录
	private String tableSchema; // 数据表所属的数据库名，部分数据库可能不支持此字段
	private String tableName;// 表名称
	private String tableType;// 表类型[system view|base table]
	private String engine;// 使用的数据库引擎[MyISAM|CSV|InnoDB]
	private Integer version;// 版本，默认值10

	/**
	 * 若一张表里面不存在varchar、text以及其变形、blob以及其变形的字段的话，那么张这个表其实也叫静态表，
	 * 即该表的row_format是fixed，就是说每条记录所占用的字节一样。其优点读取快，缺点浪费额外一部分空间。
	 * 若一张表里面存在varchar、text以及其变形、blob以及其变形的字段的话，那么张这个表其实也叫动态表，即该表的row_format是dynamic，就是说每条记录所占用的字节是动态的。其优点节省空间，缺点增加读取的时间开销。
	 * 所以，做搜索查询量大的表一般都以空间来换取时间，设计成静态表。 row_format还有其他一些值：DEFAULT | FIXED | DYNAMIC |
	 * COMPRESSED | REDUNDANT | COMPACT 修改行格式: ALTER TABLE table_name ROW_FORMAT =
	 * DEFAULT 修改过程导致： fixed--->dynamic: 这会导致CHAR变成VARCHAR dynamic--->fixed:
	 * 这会导致VARCHAR变成CHAR
	 */
	private String rowFormat;// 行格式[Compact|Dynamic|Fixed]
	private Long tableRows;// 表里所存多少行数据
	private Long avgRowLength;// 平均行长度
	private Long dataLength;// 数据长度
	private Long maxDataLength;// 最大数据长度
	private Long indexLength;// 索引长度

	/**
	 * 每当MySQL从你的列表中删除了一行内容，该段空间就会被留空。而在一段时间内的大量删除操作，会使这种留空的空间变得
	 * 比存储列表内容所使用的空间更大。当MySQL对数据进行扫描时，它扫描的对象实际是列表的容量需求上限，也就是数据被写
	 * 入的区域中处于峰值位置的部分。如果进行新的插入操作，MySQL将尝试利用这些留空的区域，但仍然无法将其彻底占用。 1.查询数据库空间碎片： select
	 * table_name, data_free, engine from information_schema.tables where
	 * table_schema = '数据库名称'; 2.对数据表优化：optimeze table `table_name`;
	 */
	private Long dataFree;// 空间碎片
	private Long autoIncrement;// 做自增主键的自动增量当前值
	private Timestamp createTime;// 表的创建时间
	private Timestamp updateTime;// 表的更新时间
	private Timestamp checkTime;// 表的检查时间
	private String tableCollation;// 表的字符校验编码集
	private Long checksum;// 校验和
	private String createOptions;// 创建选项
	private String tableComment;// 表的注释、备注

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

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getRowFormat() {
		return rowFormat;
	}

	public void setRowFormat(String rowFormat) {
		this.rowFormat = rowFormat;
	}

	public Long getTableRows() {
		return tableRows;
	}

	public void setTableRows(Long tableRows) {
		this.tableRows = tableRows;
	}

	public Long getAvgRowLength() {
		return avgRowLength;
	}

	public void setAvgRowLength(Long avgRowLength) {
		this.avgRowLength = avgRowLength;
	}

	public Long getDataLength() {
		return dataLength;
	}

	public void setDataLength(Long dataLength) {
		this.dataLength = dataLength;
	}

	public Long getMaxDataLength() {
		return maxDataLength;
	}

	public void setMaxDataLength(Long maxDataLength) {
		this.maxDataLength = maxDataLength;
	}

	public Long getIndexLength() {
		return indexLength;
	}

	public void setIndexLength(Long indexLength) {
		this.indexLength = indexLength;
	}

	public Long getDataFree() {
		return dataFree;
	}

	public void setDataFree(Long dataFree) {
		this.dataFree = dataFree;
	}

	public Long getAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(Long autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

	public String getTableCollation() {
		return tableCollation;
	}

	public void setTableCollation(String tableCollation) {
		this.tableCollation = tableCollation;
	}

	public Long getChecksum() {
		return checksum;
	}

	public void setChecksum(Long checksum) {
		this.checksum = checksum;
	}

	public String getCreateOptions() {
		return createOptions;
	}

	public void setCreateOptions(String createOptions) {
		this.createOptions = createOptions;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	@Override
	public String toString() {
		return "Tables{" + "tableCatalog=" + tableCatalog + ", tableSchema=" + tableSchema + ", tableName=" + tableName
				+ ", tableType=" + tableType + ", engine=" + engine + ", version=" + version + ", rowFormat="
				+ rowFormat + ", tableRows=" + tableRows + ", avgRowLength=" + avgRowLength + ", dataLength="
				+ dataLength + ", maxDataLength=" + maxDataLength + ", indexLength=" + indexLength + ", dataFree="
				+ dataFree + ", autoIncrement=" + autoIncrement + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", checkTime=" + checkTime + ", tableCollation=" + tableCollation + ", checksum="
				+ checksum + ", createOptions=" + createOptions + ", tableComment=" + tableComment + "}";
	}
}
