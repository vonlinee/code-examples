/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.maker.codegen.mbp.keywords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

/**
 * mysql关键字处理
 * 这里选取了mysql8.0文档中的关键字和保留字（含移除）https://dev.mysql.com/doc/refman/8.0/en/keywords.html
 *
 * @author nieqiurong 2020/5/7.
 * @since 3.3.2
 */
public class MySqlKeyWordsHandler extends BaseKeyWordsHandler {

    private static final List<String> KEY_WORDS = new ArrayList<>(Arrays.asList(
        "ACCESSIBLE",
        "ACCOUNT",
        "ACTION",
        "ACTIVE",
        "ADD",
        "ADMIN",
        "AFTER",
        "AGAINST",
        "AGGREGATE",
        "ALGORITHM",
        "ALL",
        "ALTER",
        "ALWAYS",
        "ANALYSE",
        "ANALYZE",
        "AND",
        "ANY",
        "ARRAY",
        "AS",
        "ASC",
        "ASCII",
        "ASENSITIVE",
        "AT",
        "ATTRIBUTE",
        "AUTHENTICATION",
        "AUTOEXTEND_SIZE",
        "AUTO_INCREMENT",
        "AVG",
        "AVG_ROW_LENGTH",
        "BACKUP",
        "BEFORE",
        "BEGIN",
        "BETWEEN",
        "BIGINT",
        "BINARY",
        "BINLOG",
        "BIT",
        "BLOB",
        "BLOCK",
        "BOOL",
        "BOOLEAN",
        "BOTH",
        "BTREE",
        "BUCKETS",
        "BY",
        "BYTE",
        "CACHE",
        "CALL",
        "CASCADE",
        "CASCADED",
        "CASE",
        "CATALOG_NAME",
        "CHAIN",
        "CHALLENGE_RESPONSE",
        "CHANGE",
        "CHANGED",
        "CHANNEL",
        "CHAR",
        "CHARACTER",
        "CHARSET",
        "CHECK",
        "CHECKSUM",
        "CIPHER",
        "CLASS_ORIGIN",
        "CLIENT",
        "CLONE",
        "CLOSE",
        "COALESCE",
        "CODE",
        "COLLATE",
        "COLLATION",
        "COLUMN",
        "COLUMNS",
        "COLUMN_FORMAT",
        "COLUMN_NAME",
        "COMMENT",
        "COMMIT",
        "COMMITTED",
        "COMPACT",
        "COMPLETION",
        "COMPONENT",
        "COMPRESSED",
        "COMPRESSION",
        "CONCURRENT",
        "CONDITION",
        "CONNECTION",
        "CONSISTENT",
        "CONSTRAINT",
        "CONSTRAINT_CATALOG",
        "CONSTRAINT_NAME",
        "CONSTRAINT_SCHEMA",
        "CONTAINS",
        "CONTEXT",
        "CONTINUE",
        "CONVERT",
        "CPU",
        "CREATE",
        "CROSS",
        "CUBE",
        "CUME_DIST",
        "CURRENT",
        "CURRENT_DATE",
        "CURRENT_TIME",
        "CURRENT_TIMESTAMP",
        "CURRENT_USER",
        "CURSOR",
        "CURSOR_NAME",
        "DATA",
        "DATABASE",
        "DATABASES",
        "DATAFILE",
        "DATE",
        "DATETIME",
        "DAY",
        "DAY_HOUR",
        "DAY_MICROSECOND",
        "DAY_MINUTE",
        "DAY_SECOND",
        "DEALLOCATE",
        "DEC",
        "DECIMAL",
        "DECLARE",
        "DEFAULT",
        "DEFAULT_AUTH",
        "DEFINER",
        "DEFINITION",
        "DELAYED",
        "DELAY_KEY_WRITE",
        "DELETE",
        "DENSE_RANK",
        "DESC",
        "DESCRIBE",
        "DESCRIPTION",
        "DES_KEY_FILE",
        "DETERMINISTIC",
        "DIAGNOSTICS",
        "DIRECTORY",
        "DISABLE",
        "DISCARD",
        "DISK",
        "DISTINCT",
        "DISTINCTROW",
        "DIV",
        "DO",
        "DOUBLE",
        "DROP",
        "DUAL",
        "DUMPFILE",
        "DUPLICATE",
        "DYNAMIC",
        "EACH",
        "ELSE",
        "ELSEIF",
        "EMPTY",
        "ENABLE",
        "ENCLOSED",
        "ENCRYPTION",
        "END",
        "ENDS",
        "ENFORCED",
        "ENGINE",
        "ENGINE_ATTRIBUTE",
        "ENGINES",
        "ENUM",
        "ERROR",
        "ERRORS",
        "ESCAPE",
        "ESCAPED",
        "EVENT",
        "EVENTS",
        "EVERY",
        "EXCEPT",
        "EXCHANGE",
        "EXCLUDE",
        "EXECUTE",
        "EXISTS",
        "EXIT",
        "EXPANSION",
        "EXPIRE",
        "EXPLAIN",
        "EXPORT",
        "EXTENDED",
        "EXTENT_SIZE",
        "FACTOR",
        "FAILED_LOGIN_ATTEMPTS",
        "FALSE",
        "FAST",
        "FAULTS",
        "FETCH",
        "FIELDS",
        "FILE",
        "FILE_BLOCK_SIZE",
        "FILTER",
        "FINISH",
        "FIRST",
        "FIRST_VALUE",
        "FIXED",
        "FLOAT",
        "FLOAT4",
        "FLOAT8",
        "FLUSH",
        "FOLLOWING",
        "FOLLOWS",
        "FOR",
        "FORCE",
        "FOREIGN",
        "FORMAT",
        "FOUND",
        "FROM",
        "FULL",
        "FULLTEXT",
        "FUNCTION",
        "GENERAL",
        "GENERATED",
        "GEOMCOLLECTION",
        "GEOMETRY",
        "GEOMETRYCOLLECTION",
        "GET",
        "GET_FORMAT",
        "GET_MASTER_PUBLIC_KEY",
        "GET_SOURCE_PUBLIC_KEY",
        "GLOBAL",
        "GRANT",
        "GRANTS",
        "GROUP",
        "GROUP_REPLICATION",
        "GROUPING",
        "GROUPS",
        "GTID_ONLY",
        "HANDLER",
        "HASH",
        "HAVING",
        "HELP",
        "HIGH_PRIORITY",
        "HISTOGRAM",
        "HISTORY",
        "HOST",
        "HOSTS",
        "HOUR",
        "HOUR_MICROSECOND",
        "HOUR_MINUTE",
        "HOUR_SECOND",
        "IDENTIFIED",
        "IF",
        "IGNORE",
        "IGNORE_SERVER_IDS",
        "IMPORT",
        "IN",
        "INACTIVE",
        "INDEX",
        "INDEXES",
        "INFILE",
        "INITIAL",
        "INITIAL_SIZE",
        "INITIATE",
        "INNER",
        "INOUT",
        "INSENSITIVE",
        "INSERT",
        "INSERT_METHOD",
        "INSTALL",
        "INSTANCE",
        "INT",
        "INT1",
        "INT2",
        "INT3",
        "INT4",
        "INT8",
        "INTEGER",
        "INTERVAL",
        "INTO",
        "INVISIBLE",
        "INVOKER",
        "IO",
        "IO_AFTER_GTIDS",
        "IO_BEFORE_GTIDS",
        "IO_THREAD",
        "IPC",
        "IS",
        "ISOLATION",
        "ISSUER",
        "ITERATE",
        "JOIN",
        "JSON",
        "JSON_TABLE",
        "JSON_VALUE",
        "KEY",
        "KEYS",
        "KEY_BLOCK_SIZE",
        "KEYRING",
        "KILL",
        "LAG",
        "LANGUAGE",
        "LAST",
        "LAST_VALUE",
        "LATERAL",
        "LEAD",
        "LEADING",
        "LEAVE",
        "LEAVES",
        "LEFT",
        "LESS",
        "LEVEL",
        "LIKE",
        "LIMIT",
        "LINEAR",
        "LINES",
        "LINESTRING",
        "LIST",
        "LOAD",
        "LOCAL",
        "LOCALTIME",
        "LOCALTIMESTAMP",
        "LOCK",
        "LOCKED",
        "LOCKS",
        "LOGFILE",
        "LOGS",
        "LONG",
        "LONGBLOB",
        "LONGTEXT",
        "LOOP",
        "LOW_PRIORITY",
        "MASTER",
        "MASTER_AUTO_POSITION",
        "MASTER_BIND",
        "MASTER_COMPRESSION_ALGORITHMS",
        "MASTER_CONNECT_RETRY",
        "MASTER_DELAY",
        "MASTER_HEARTBEAT_PERIOD",
        "MASTER_HOST",
        "MASTER_LOG_FILE",
        "MASTER_LOG_POS",
        "MASTER_PASSWORD",
        "MASTER_PORT",
        "MASTER_PUBLIC_KEY_PATH",
        "MASTER_RETRY_COUNT",
        "MASTER_SERVER_ID",
        "MASTER_SSL",
        "MASTER_SSL_CA",
        "MASTER_SSL_CAPATH",
        "MASTER_SSL_CERT",
        "MASTER_SSL_CIPHER",
        "MASTER_SSL_CRL",
        "MASTER_SSL_CRLPATH",
        "MASTER_SSL_KEY",
        "MASTER_SSL_VERIFY_SERVER_CERT",
        "MASTER_TLS_CIPHERSUITES",
        "MASTER_TLS_VERSION",
        "MASTER_USER",
        "MASTER_ZSTD_COMPRESSION_LEVEL",
        "MATCH",
        "MAXVALUE",
        "MAX_CONNECTIONS_PER_HOUR",
        "MAX_QUERIES_PER_HOUR",
        "MAX_ROWS",
        "MAX_SIZE",
        "MAX_STATEMENT_TIME",
        "MAX_UPDATES_PER_HOUR",
        "MAX_USER_CONNECTIONS",
        "MEDIUM",
        "MEDIUMBLOB",
        "MEDIUMINT",
        "MEDIUMTEXT",
        "MEMBER",
        "MEMORY",
        "MERGE",
        "MESSAGE_TEXT",
        "MICROSECOND",
        "MIDDLEINT",
        "MIGRATE",
        "MINUTE",
        "MINUTE_MICROSECOND",
        "MINUTE_SECOND",
        "MIN_ROWS",
        "MOD",
        "MODE",
        "MODIFIES",
        "MODIFY",
        "MONTH",
        "MULTILINESTRING",
        "MULTIPOINT",
        "MULTIPOLYGON",
        "MUTEX",
        "MYSQL_ERRNO",
        "NAME",
        "NAMES",
        "NATIONAL",
        "NATURAL",
        "NCHAR",
        "NDB",
        "NDBCLUSTER",
        "NESTED",
        "NETWORK_NAMESPACE",
        "NEVER",
        "NEW",
        "NEXT",
        "NO",
        "NODEGROUP",
        "NONBLOCKING",
        "NONE",
        "NOT",
        "NOWAIT",
        "NO_WAIT",
        "NO_WRITE_TO_BINLOG",
        "NTH_VALUE",
        "NTILE",
        "NULL",
        "NULLS",
        "NUMBER",
        "NUMERIC",
        "NVARCHAR",
        "OF",
        "OFF",
        "OFFSET",
        "OJ",
        "OLD",
        "OLD_PASSWORD",
        "ON",
        "ONE",
        "ONLY",
        "OPEN",
        "OPTIMIZE",
        "OPTIMIZER_COSTS",
        "OPTION",
        "OPTIONAL",
        "OPTIONALLY",
        "OPTIONS",
        "OR",
        "ORDER",
        "ORDINALITY",
        "ORGANIZATION",
        "OTHERS",
        "OUT",
        "OUTER",
        "OUTFILE",
        "OVER",
        "OWNER",
        "PACK_KEYS",
        "PAGE",
        "PARSER",
        "PARSE_GCOL_EXPR",
        "PARTIAL",
        "PARTITION",
        "PARTITIONING",
        "PARTITIONS",
        "PASSWORD",
        "PASSWORD_LOCK_TIME",
        "PATH",
        "PERCENT_RANK",
        "PERSIST",
        "PERSIST_ONLY",
        "PHASE",
        "PLUGIN",
        "PLUGINS",
        "PLUGIN_DIR",
        "POINT",
        "POLYGON",
        "PORT",
        "PRECEDES",
        "PRECEDING",
        "PRECISION",
        "PREPARE",
        "PRESERVE",
        "PREV",
        "PRIMARY",
        "PRIVILEGE_CHECKS_USER",
        "PRIVILEGES",
        "PROCEDURE",
        "PROCESS",
        "PROCESSLIST",
        "PROFILE",
        "PROFILES",
        "PROXY",
        "PURGE",
        "QUARTER",
        "QUERY",
        "QUICK",
        "RANDOM",
        "RANGE",
        "RANK",
        "READ",
        "READS",
        "READ_ONLY",
        "READ_WRITE",
        "REAL",
        "REBUILD",
        "RECOVER",
        "RECURSIVE",
        "REDOFILE",
        "REDO_BUFFER_SIZE",
        "REDUNDANT",
        "REFERENCE",
        "REFERENCES",
        "REGEXP",
        "REGISTRATION",
        "RELAY",
        "RELAYLOG",
        "RELAY_LOG_FILE",
        "RELAY_LOG_POS",
        "RELAY_THREAD",
        "RELEASE",
        "RELOAD",
        "REMOVE",
        "RENAME",
        "REORGANIZE",
        "REPAIR",
        "REPEAT",
        "REPEATABLE",
        "REPLACE",
        "REPLICA",
        "REPLICAS",
        "REPLICATE_DO_DB",
        "REPLICATE_DO_TABLE",
        "REPLICATE_IGNORE_DB",
        "REPLICATE_IGNORE_TABLE",
        "REPLICATE_REWRITE_DB",
        "REPLICATE_WILD_DO_TABLE",
        "REPLICATE_WILD_IGNORE_TABLE",
        "REPLICATION",
        "REQUIRE",
        "REQUIRE_ROW_FORMAT",
        "RESET",
        "RESIGNAL",
        "RESOURCE",
        "RESPECT",
        "RESTART",
        "RESTORE",
        "RESTRICT",
        "RESUME",
        "RETAIN",
        "RETURN",
        "RETURNED_SQLSTATE",
        "RETURNING",
        "RETURNS",
        "REUSE",
        "REVERSE",
        "REVOKE",
        "RIGHT",
        "RLIKE",
        "ROLE",
        "ROLLBACK",
        "ROLLUP",
        "ROTATE",
        "ROUTINE",
        "ROW",
        "ROWS",
        "ROW_COUNT",
        "ROW_FORMAT",
        "ROW_NUMBER",
        "RTREE",
        "SAVEPOINT",
        "SCHEDULE",
        "SCHEMA",
        "SCHEMAS",
        "SCHEMA_NAME",
        "SECOND",
        "SECOND_MICROSECOND",
        "SECONDARY",
        "SECONDARY_ENGINE",
        "SECONDARY_ENGINE_ATTRIBUTE",
        "SECONDARY_LOAD",
        "SECONDARY_UNLOAD",
        "SECURITY",
        "SELECT",
        "SENSITIVE",
        "SEPARATOR",
        "SERIAL",
        "SERIALIZABLE",
        "SERVER",
        "SESSION",
        "SET",
        "SHARE",
        "SHOW",
        "SHUTDOWN",
        "SIGNAL",
        "SIGNED",
        "SIMPLE",
        "SKIP",
        "SLAVE",
        "SLOW",
        "SMALLINT",
        "SNAPSHOT",
        "SOCKET",
        "SOME",
        "SONAME",
        "SOUNDS",
        "SOURCE",
        "SOURCE_AUTO_POSITION",
        "SOURCE_BIND",
        "SOURCE_COMPRESSION_ALGORITHMS",
        "SOURCE_CONNECT_RETRY",
        "SOURCE_DELAY",
        "SOURCE_HEARTBEAT_PERIOD",
        "SOURCE_HOST",
        "SOURCE_LOG_FILE",
        "SOURCE_LOG_POS",
        "SOURCE_PASSWORD",
        "SOURCE_PORT",
        "SOURCE_PUBLIC_KEY_PATH",
        "SOURCE_RETRY_COUNT",
        "SOURCE_SSL",
        "SOURCE_SSL_CA",
        "SOURCE_SSL_CAPATH",
        "SOURCE_SSL_CERT",
        "SOURCE_SSL_CIPHER",
        "SOURCE_SSL_CRL",
        "SOURCE_SSL_CRLPATH",
        "SOURCE_SSL_KEY",
        "SOURCE_SSL_VERIFY_SERVER_CERT",
        "SOURCE_TLS_CIPHERSUITES",
        "SOURCE_TLS_VERSION",
        "SOURCE_USER",
        "SOURCE_ZSTD_COMPRESSION_LEVEL",
        "SPATIAL",
        "SPECIFIC",
        "SQL",
        "SQLEXCEPTION",
        "SQLSTATE",
        "SQLWARNING",
        "SRID",
        "SQL_AFTER_GTIDS",
        "SQL_AFTER_MTS_GAPS",
        "SQL_BEFORE_GTIDS",
        "SQL_BIG_RESULT",
        "SQL_BUFFER_RESULT",
        "SQL_CACHE",
        "SQL_CALC_FOUND_ROWS",
        "SQL_NO_CACHE",
        "SQL_SMALL_RESULT",
        "SQL_THREAD",
        "SQL_TSI_DAY",
        "SQL_TSI_HOUR",
        "SQL_TSI_MINUTE",
        "SQL_TSI_MONTH",
        "SQL_TSI_QUARTER",
        "SQL_TSI_SECOND",
        "SQL_TSI_WEEK",
        "SQL_TSI_YEAR",
        "SSL",
        "STACKED",
        "START",
        "STARTING",
        "STARTS",
        "STATS_AUTO_RECALC",
        "STATS_PERSISTENT",
        "STATS_SAMPLE_PAGES",
        "STATUS",
        "STOP",
        "STORAGE",
        "STORED",
        "STRAIGHT_JOIN",
        "STREAM",
        "STRING",
        "SUBCLASS_ORIGIN",
        "SUBJECT",
        "SUBPARTITION",
        "SUBPARTITIONS",
        "SUPER",
        "SUSPEND",
        "SWAPS",
        "SWITCHES",
        "SYSTEM",
        "TABLE",
        "TABLES",
        "TABLESPACE",
        "TABLE_CHECKSUM",
        "TABLE_NAME",
        "TEMPORARY",
        "TEMPTABLE",
        "TERMINATED",
        "TEXT",
        "THAN",
        "THEN",
        "THREAD_PRIORITY",
        "TIES",
        "TIME",
        "TIMESTAMP",
        "TIMESTAMPADD",
        "TIMESTAMPDIFF",
        "TINYBLOB",
        "TINYINT",
        "TINYTEXT",
        "TLS",
        "TO",
        "TRAILING",
        "TRANSACTION",
        "TRIGGER",
        "TRIGGERS",
        "TRUE",
        "TRUNCATE",
        "TYPE",
        "TYPES",
        "UNBOUNDED",
        "UNCOMMITTED",
        "UNDEFINED",
        "UNDO",
        "UNDOFILE",
        "UNDO_BUFFER_SIZE",
        "UNICODE",
        "UNINSTALL",
        "UNION",
        "UNIQUE",
        "UNKNOWN",
        "UNLOCK",
        "UNREGISTER",
        "UNSIGNED",
        "UNTIL",
        "UPDATE",
        "UPGRADE",
        "USAGE",
        "USE",
        "USER",
        "USER_RESOURCES",
        "USE_FRM",
        "USING",
        "UTC_DATE",
        "UTC_TIME",
        "UTC_TIMESTAMP",
        "VALIDATION",
        "VALUE",
        "VALUES",
        "VARBINARY",
        "VARCHAR",
        "VARCHARACTER",
        "VARIABLES",
        "VARYING",
        "VCPU",
        "VIEW",
        "VIRTUAL",
        "VISIBLE",
        "WAIT",
        "WARNINGS",
        "WEEK",
        "WEIGHT_STRING",
        "WHEN",
        "WHERE",
        "WHILE",
        "WINDOW",
        "WITH",
        "WITHOUT",
        "WORK",
        "WRAPPER",
        "WRITE",
        "X509",
        "XA",
        "XID",
        "XML",
        "XOR",
        "YEAR",
        "YEAR_MONTH",
        "ZEROFILL",
        "ZONE"));

    public MySqlKeyWordsHandler() {
        super(new HashSet<>(KEY_WORDS));
    }

    public MySqlKeyWordsHandler(@NotNull List<String> keyWords) {
        super(new HashSet<>(keyWords));
    }

    public MySqlKeyWordsHandler(@NotNull Set<String> keyWords) {
        super(keyWords);
    }


    @Override
    public @NotNull String formatStyle() {
        return "`%s`";
    }

}
