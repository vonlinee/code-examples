package io.devpl.toolkit.core;

import io.devpl.toolkit.fxui.common.JDBCDriver;
import lombok.Data;

import java.util.Properties;

/**
 * 数据库连接信息
 */
@Data
public class ConnectionInfo {

    /**
     * 唯一ID：UUID
     */
    private String id;

    /**
     * 连接名称：用于界面数据展示
     */
    private String connectionName;
    private String schema;
    private String username = "root";
    private String password;
    private String hostname = "127.0.0.1";
    private String port = "3306";
    private String encoding;

    /**
     * JDBC 驱动信息
     */
    private JDBCDriver driver;

    /**
     * 连接属性
     */
    private Properties properties;

    /**
     * SSH连接信息
     */
    private SSHConnectionInfo sshConnectionInfo;
}
