package io.devpl.toolkit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class JdbcConnInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 连接名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 主机地址，IP地址
     */
    @TableField(value = "host")
    private String host;

    /**
     * 端口号
     */
    @TableField(value = "port")
    private String port = "3306";

    /**
     * 数据库类型
     *
     * @see io.devpl.toolkit.codegen.JDBCDriver
     */
    @TableField(value = "db_type")
    private String dbType;

    /**
     * 数据库名称
     */
    @TableField(value = "db_name")
    private String dbName;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 连接编码
     */
    @TableField(value = "encoding")
    private String encoding;
}
