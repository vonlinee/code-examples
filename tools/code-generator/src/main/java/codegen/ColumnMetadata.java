package codegen;

import lombok.Data;

import java.io.Serializable;

/**
 * MySQL5下执行SHOW CREATE TABLE information_schema.`COLUMNS`结果如下：
 * CREATE TEMPORARY TABLE `COLUMNS` (
 * `TABLE_CATALOG` varchar(512) NOT NULL DEFAULT '',
 * `TABLE_SCHEMA` varchar(64) NOT NULL DEFAULT '',
 * `TABLE_NAME` varchar(64) NOT NULL DEFAULT '',
 * `COLUMN_NAME` varchar(64) NOT NULL DEFAULT '',
 * `ORDINAL_POSITION` bigint(21) unsigned NOT NULL DEFAULT '0',
 * `COLUMN_DEFAULT` longtext,
 * `IS_NULLABLE` varchar(3) NOT NULL DEFAULT '',
 * `DATA_TYPE` varchar(64) NOT NULL DEFAULT '',
 * `CHARACTER_MAXIMUM_LENGTH` bigint(21) unsigned DEFAULT NULL,
 * `CHARACTER_OCTET_LENGTH` bigint(21) unsigned DEFAULT NULL,
 * `NUMERIC_PRECISION` bigint(21) unsigned DEFAULT NULL,
 * `NUMERIC_SCALE` bigint(21) unsigned DEFAULT NULL,
 * `DATETIME_PRECISION` bigint(21) unsigned DEFAULT NULL,
 * `CHARACTER_SET_NAME` varchar(32) DEFAULT NULL,
 * `COLLATION_NAME` varchar(32) DEFAULT NULL,
 * `COLUMN_TYPE` longtext NOT NULL,
 * `COLUMN_KEY` varchar(3) NOT NULL DEFAULT '',
 * `EXTRA` varchar(30) NOT NULL DEFAULT '',
 * `PRIVILEGES` varchar(80) NOT NULL DEFAULT '',
 * `COLUMN_COMMENT` varchar(1024) NOT NULL DEFAULT '',
 * `GENERATION_EXPRESSION` longtext NOT NULL
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 */
@Data
public class ColumnMetadata implements Serializable {

    /**
     * `TABLE_CATALOG` varchar(512) NOT NULL DEFAULT '',
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
     * `COLUMN_NAME` varchar(64) NOT NULL DEFAULT ''
     */
    private String columnName;

    /**
     * `ORDINAL_POSITION` bigint(21) unsigned NOT NULL DEFAULT '0'
     */
    private long ordinalPosition;

    /**
     * `COLUMN_DEFAULT` longtext
     */
    private String columnDefault;

    /**
     * `IS_NULLABLE` varchar(3) NOT NULL DEFAULT ''
     */
    private String nullable;

    /**
     * `DATA_TYPE` varchar(64) NOT NULL DEFAULT ''
     */
    private String dataType;

    /**
     * `CHARACTER_MAXIMUM_LENGTH` bigint(21) unsigned DEFAULT NULL,
     */
    private Long characterMaximumLength;

    /**
     * `CHARACTER_OCTET_LENGTH` bigint(21) unsigned DEFAULT NULL
     */
    private Long characterOctetLength;

    /**
     * `NUMERIC_PRECISION` bigint(21) unsigned DEFAULT NULL,
     */
    private Long numericPrecision;

    /**
     * `NUMERIC_SCALE` bigint(21) unsigned DEFAULT NULL
     */
    private Long numericScale;

    /**
     * `DATETIME_PRECISION` bigint(21) unsigned DEFAULT NULL
     */
    private Long datetimePrecision;

    /**
     * `CHARACTER_SET_NAME` varchar(32) DEFAULT NULL
     */
    private String characterSetName;

    /**
     * `COLLATION_NAME` varchar(32) DEFAULT NULL
     */
    private String collationName;

    /**
     * `COLUMN_TYPE` longtext NOT NULL
     */
    private String columnType;

    /**
     * `COLUMN_KEY` varchar(3) NOT NULL DEFAULT ''
     */
    private String columnKey;

    /**
     * `EXTRA` varchar(30) NOT NULL DEFAULT ''
     */
    private String extra;

    /**
     * `PRIVILEGES` varchar(80) NOT NULL DEFAULT ''
     */
    private String privileges;

    /**
     * `COLUMN_COMMENT` varchar(1024) NOT NULL DEFAULT ''
     */
    private String columnComment;
    /**
     * `GENERATION_EXPRESSION` longtext NOT NULL
     */
    private String generationExpression;

    // ===========================================================================
    //              以下字段不在information_schema.`COLUMNS`表中
    // ===========================================================================
    private boolean readOnly;
    private boolean writable;
    private boolean sequence;
}
