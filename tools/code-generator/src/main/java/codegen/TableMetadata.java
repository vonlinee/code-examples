package codegen;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * MySQL5下执行SHOW CREATE TABLE information_schema.`TABLES`结果如下：
 * CREATE TEMPORARY TABLE `TABLES` (
 * `TABLE_CATALOG` varchar(512) NOT NULL DEFAULT '',
 * `TABLE_SCHEMA` varchar(64) NOT NULL DEFAULT '',
 * `TABLE_NAME` varchar(64) NOT NULL DEFAULT '',
 * `TABLE_TYPE` varchar(64) NOT NULL DEFAULT '',
 * `ENGINE` varchar(64) DEFAULT NULL,
 * `VERSION` bigint(21) unsigned DEFAULT NULL,
 * `ROW_FORMAT` varchar(10) DEFAULT NULL,
 * `TABLE_ROWS` bigint(21) unsigned DEFAULT NULL,
 * `AVG_ROW_LENGTH` bigint(21) unsigned DEFAULT NULL,
 * `DATA_LENGTH` bigint(21) unsigned DEFAULT NULL,
 * `MAX_DATA_LENGTH` bigint(21) unsigned DEFAULT NULL,
 * `INDEX_LENGTH` bigint(21) unsigned DEFAULT NULL,
 * `DATA_FREE` bigint(21) unsigned DEFAULT NULL,
 * `AUTO_INCREMENT` bigint(21) unsigned DEFAULT NULL,
 * `CREATE_TIME` datetime DEFAULT NULL,
 * `UPDATE_TIME` datetime DEFAULT NULL,
 * `CHECK_TIME` datetime DEFAULT NULL,
 * `TABLE_COLLATION` varchar(32) DEFAULT NULL,
 * `CHECKSUM` bigint(21) unsigned DEFAULT NULL,
 * `CREATE_OPTIONS` varchar(255) DEFAULT NULL,
 * `TABLE_COMMENT` varchar(2048) NOT NULL DEFAULT ''
 * ) ENGINE=MEMORY DEFAULT CHARSET=utf8
 */
@Data
public class TableMetadata implements Serializable {

    /**
     * `TABLE_CATALOG` varchar(512) NOT NULL DEFAULT ''
     */
    private String tableCatalog;

    /**
     * `TABLE_SCHEMA` varchar(64) NOT NULL DEFAULT '',
     */
    private String tableSchema;

    /**
     * `TABLE_NAME` varchar(64) NOT NULL DEFAULT '',
     */
    private String tableName;

    /**
     * `TABLE_TYPE` varchar(64) NOT NULL DEFAULT '',
     */
    private String tableType;

    /**
     * `ENGINE` varchar(64) DEFAULT NULL,
     */
    private String engine;

    /**
     * `VERSION` bigint(21) unsigned DEFAULT NULL,
     */
    private Long version;

    /**
     * `ROW_FORMAT` varchar(10) DEFAULT NULL,
     */
    private String rowFormat;

    /**
     * `TABLE_ROWS` bigint(21) unsigned DEFAULT NULL,
     */
    private Long tableRows;

    /**
     * `AVG_ROW_LENGTH` bigint(21) unsigned DEFAULT NULL,
     */
    private Long avgRowLength;

    /**
     * `DATA_LENGTH` bigint(21) unsigned DEFAULT NULL,
     */
    private Long dataLength;

    /**
     * `MAX_DATA_LENGTH` bigint(21) unsigned DEFAULT NULL,
     */
    private Long maxDataLength;

    /**
     * `INDEX_LENGTH` bigint(21) unsigned DEFAULT NULL,
     */
    private Long indexLength;

    /**
     * `DATA_FREE` bigint(21) unsigned DEFAULT NULL,
     */
    private Long dataFree;

    /**
     * `AUTO_INCREMENT` bigint(21) unsigned DEFAULT NULL,
     */
    private Long autoIncrement;

    /**
     * `CREATE_TIME` datetime DEFAULT NULL,
     */
    private LocalDateTime createTime;

    /**
     * `UPDATE_TIME` datetime DEFAULT NULL,
     */
    private LocalDateTime updateTime;

    /**
     * `CHECK_TIME` datetime DEFAULT NULL,
     */
    private LocalDateTime checkTime;

    /**
     * `TABLE_COLLATION` varchar(32) DEFAULT NULL,
     */
    private String tableCollation;

    /**
     * `CHECKSUM` bigint(21) unsigned DEFAULT NULL,
     */
    private Long checkSum;

    /**
     * `CREATE_OPTIONS` varchar(255) DEFAULT NULL,
     */
    private String createOptions;

    /**
     * `TABLE_COMMENT` varchar(2048) NOT NULL DEFAULT ''
     */
    private String tableComment;
}
