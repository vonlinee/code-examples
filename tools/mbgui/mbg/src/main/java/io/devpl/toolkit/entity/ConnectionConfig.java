package io.devpl.toolkit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author devpl
 * @since 2023-03-16
 */
@Data
@TableName("connection_config")
public class ConnectionConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 连接名称
     */
    private String name;

    /**
     * 主机地址，IP地址
     */
    private String host;

    /**
     * 端口号
     */
    private String port;

    /**
     * 数据库类型
     */
    private String dbType;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 连接编码
     */
    private String encoding;
}
