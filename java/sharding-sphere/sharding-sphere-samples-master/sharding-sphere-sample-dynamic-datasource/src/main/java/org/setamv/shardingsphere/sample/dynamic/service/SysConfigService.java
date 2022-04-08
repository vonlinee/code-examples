package org.setamv.shardingsphere.sample.dynamic.service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.setamv.shardingsphere.sample.dynamic.IdGenerator;
import org.setamv.shardingsphere.sample.dynamic.dto.SysConfigQueryReqDTO;
import org.setamv.shardingsphere.sample.dynamic.mapper.SysConfigMapper;
import org.setamv.shardingsphere.sample.dynamic.model.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统配置Service
 *
 * @author setamv
 * @date 2021-04-15
 */
@Service
public class SysConfigService {

    public static final int OPERATE_TIME_SHARDING_UPPER_BOUND = 2021;

    @Autowired
    private SysConfigMapper sysConfigMapper;

    /**
     * 新增系统配置
     *
     * @param config 系统配置
     * @return 系统配置ID
     */
    public Long add(SysConfig config) {
        config.setConfigId(IdGenerator.generateId());
        sysConfigMapper.insert(config);
        return config.getConfigId();
    }

    /**
     * 更新系统配置
     *
     * @param config 系统配置
     * @return 受影响的行数
     */
    public int update(SysConfig config) {
        return sysConfigMapper.update(config);
    }

    /**
     * 获取系统配置
     *
     * @param configCode 配置编码
     * @return 系统配置
     */
    public SysConfig get(String configCode) {
        return sysConfigMapper.get(configCode);
    }

    /**
     * 查询系统配置
     *
     * @param queryParams 查询参数
     * @return 系统配置列表
     */
    public List<SysConfig> query(SysConfigQueryReqDTO queryParams) {
        return sysConfigMapper.query(queryParams);
    }

    /**
     * 查询系统配置
     *
     * @param queryParams 查询参数
     * @return 系统配置列表
     */
    public PageInfo<SysConfig> queryPage(SysConfigQueryReqDTO queryParams) {
        return PageMethod
                .startPage(queryParams.getPageNum(), queryParams.getPageSize())
                .doSelectPageInfo(() -> sysConfigMapper.query(queryParams));
    }

}
