package io.maker.codegen.mbp.config;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 表数据查询接口
 *
 * @author hubin
 * @since 2018-01-16
 */
public interface IDbQuery {
    /**
     * 表信息查询 SQL
     */
    String tablesSql();

    /**
     * 表字段信息查询 SQL
     */
    String tableFieldsSql();

    /**
     * 表名称
     */
    String tableName();

    /**
     * 表注释
     * @deprecated 3.5.3
     */
    @Deprecated
    default String tableComment() {
        return null;
    }

    /**
     * 字段名称
     */
    String fieldName();

    /**
     * 字段类型
     */
    String fieldType();

    /**
     * 字段注释
     */
    default String fieldComment() {
        return null;
    }

    /**
     * 主键字段
     */
    default String fieldKey(){
        return null;
    }

    /**
     * 判断主键是否为identity
     * @param results ResultSet
     * @return 主键是否为identity
     * @throws SQLException ignore
     */
    default boolean isKeyIdentity(ResultSet results) throws SQLException {
        return false;
    }

    /**
     * 自定义字段名称
     */
    String[] fieldCustom();
}
