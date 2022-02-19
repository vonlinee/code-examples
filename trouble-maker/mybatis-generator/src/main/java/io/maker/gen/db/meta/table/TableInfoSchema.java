package io.maker.gen.db.meta.table;

import java.io.Serializable;
import java.sql.Timestamp;

/*
SELECT
    T.TABLE_SCHEMA,   -- 模式
    T.TABLE_NAME,     -- 表名
    T.TABLE_COMMENT,  -- 表名注释
    C.COLUMN_NAME,    -- 字段名
    C.COLUMN_TYPE,    -- 字段类型
    C.COLUMN_COMMENT  -- 字段注释
FROM
    `information_schema`.`TABLES` T, `information_schema`.`COLUMNS` C
WHERE T.TABLE_NAME = C.TABLE_NAME AND T.TABLE_SCHEMA = 'dbName'
ORDER BY T.TABLE_NAME, C.ORDINAL_POSITION
*/
/**
 * SELECT * FROM `information_schema`.`TABLES` T
 */
public class TableInfoSchema implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tableCatalog;

	private String tableSchema;

	private String tableName;

	private String tableType;

	private String engine;

	private Integer version;

	private String rowFormat;

	private Long tableRows;

	private Long avgRowLength;

	private Long dataLength;

	private Long maxDataLength;

	private Long indexLength;

	private Long dataFree;

	private Long autoIncrement;

	private Timestamp createTime;

	private Timestamp updateTime;

	private Timestamp checkTime;

	private String tableCollation;

	private Long checksum;

	private String createOptions;

	private String tableComment;

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
