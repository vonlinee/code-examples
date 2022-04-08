package org.setamv.shardingsphere.sample.dynamic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.setamv.shardingsphere.sample.dynamic.dto.SysConfigQueryReqDTO;
import org.setamv.shardingsphere.sample.dynamic.model.SysConfig;

import java.util.List;

/**
 * 系统配置Mapper
 *
 * @author setamv
 * @date 2021-04-16
 */
@Mapper
public interface SysConfigMapper {

    /**
     * 新增系统配置
     * @param config 系统配置
     * @return 受影响的行数
     */
    int insert(SysConfig config);

    /**
     * 更新系统配置
     * @param config 系统配置
     * @return 受影响的行数
     */
    int update(SysConfig config);

    /**
     * 获取系统配置
     * @param configCode 系统配置编码
     * @return 系统配置
     */
    SysConfig get(String configCode);

    /**
     * 查询系统配置
     * @param queryParams 查询参数
     * @return 系统配置列表
     */
    List<SysConfig> query(SysConfigQueryReqDTO queryParams);
}
