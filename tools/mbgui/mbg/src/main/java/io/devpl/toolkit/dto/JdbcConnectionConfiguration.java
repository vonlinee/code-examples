package io.devpl.toolkit.dto;

import lombok.Data;

/**
 * JDBC连接配置
 */
@Data
public class JdbcConnectionConfiguration {

    private String connectionName;
    private String databaseName;
}
