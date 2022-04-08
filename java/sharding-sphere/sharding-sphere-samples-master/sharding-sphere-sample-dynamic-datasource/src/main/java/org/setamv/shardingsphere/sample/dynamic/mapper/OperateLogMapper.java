package org.setamv.shardingsphere.sample.dynamic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.setamv.shardingsphere.sample.dynamic.dto.OperateLogGetReqDTO;
import org.setamv.shardingsphere.sample.dynamic.dto.OperateLogQueryReqDTO;
import org.setamv.shardingsphere.sample.dynamic.model.OperateLog;

import java.util.List;

/**
 * 操作日志Mapper
 *
 * @author setamv
 * @date 2021-04-16
 */
@Mapper
public interface OperateLogMapper {

    /**
     * 新增操作日志
     * @param log 操作日志
     * @return 受影响的行数
     */
    int insert(OperateLog log);

    /**
     * 更新操作日志
     * @param log 操作日志
     * @return 受影响的行数
     */
    int update(OperateLog log);

    /**
     * 获取操作日志
     * @param params 查询参数
     * @return 操作日志
     */
    OperateLog get(OperateLogGetReqDTO params);

    /**
     * 查询操作日志
     * @param queryParams 查询参数
     * @return 操作日志列表
     */
    List<OperateLog> query(OperateLogQueryReqDTO queryParams);
}
