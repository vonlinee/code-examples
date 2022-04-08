package org.setamv.shardingsphere.sample.dynamic.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 公共Mapper
 * @author setamv
 * @date 2021-04-16
 */
@Mapper
public interface CommonMapper {

    /**
     * 执行一段自定义的SQL查询脚本
     * @param sqlText SQL查询脚本
     * @return 返回值列表
     */
    List<Map<String, Object>> executeDqlSql(String sqlText);

    /**
     * 执行一段自定义的DML SQL脚本
     * @param sqlText DML SQL脚本
     * @return 受影响的行数
     */
    Integer executeDmlSql(String sqlText);

    /**
     * 执行一段自定义的DDL SQL脚本
     * @param sqlText DDL SQL脚本
     * @return 受影响的行数
     */
    void executeDdlSql(String sqlText);
}
