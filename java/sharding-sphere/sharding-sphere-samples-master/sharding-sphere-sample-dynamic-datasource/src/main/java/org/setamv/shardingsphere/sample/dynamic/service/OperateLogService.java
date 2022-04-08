package org.setamv.shardingsphere.sample.dynamic.service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.setamv.shardingsphere.sample.dynamic.IdGenerator;
import org.setamv.shardingsphere.sample.dynamic.dto.OperateLogGetReqDTO;
import org.setamv.shardingsphere.sample.dynamic.dto.OperateLogQueryReqDTO;
import org.setamv.shardingsphere.sample.dynamic.mapper.OperateLogMapper;
import org.setamv.shardingsphere.sample.dynamic.model.OperateLog;
import org.setamv.shardingsphere.sample.dynamic.spel.DynamicDataSourceRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 操作日志Service
 *
 * @author setamv
 * @date 2021-04-15
 */
@Service
public class OperateLogService {

    public static final int OPERATE_TIME_SHARDING_UPPER_BOUND = 2021;

    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * 新增操作日志
     * @param log 操作日志
     * @return 操作日志ID
     */
    public Long add(OperateLog log) {
        log.setId(IdGenerator.generateId());
        operateLogMapper.insert(log);
        return log.getId();
    }

    /**
     * 更新操作日志
     * @param log 操作日志
     * @return 受影响的行数
     */
    public void update(OperateLog log) {
        operateLogMapper.update(log);
    }

    /**
     * 获取操作日志
     * @param params 查询参数
     * @return 操作日志
     */
    public OperateLog get(OperateLogGetReqDTO params) {
        return operateLogMapper.get(params);
    }

    @Autowired
    private CommonService commonService;

    @Autowired
    private OperateLogService operateLogService;

    @Transactional(rollbackFor = Exception.class)
    public List<OperateLog> query(OperateLogQueryReqDTO queryParams) {
        // 先查询租户表，测试事务中切换数据源
        commonService.executeDqlSql("SELECT * FROM t_tenant");

        // 执行真实的查询
        return operateLogService.doQuery(queryParams);
    }

    /**
     * 查询操作日志
     * @param queryParams 查询参数
     * @return 操作日志列表
     */
    @DynamicDataSourceRouter(path = {"#queryParams.startOperateTime", "#queryParams.endOperateTime"})
    public List<OperateLog> doQuery(OperateLogQueryReqDTO queryParams) {
        return operateLogMapper.query(queryParams);
    }

    /**
     * 查询操作日志
     * @param queryParams 查询参数
     * @return 操作日志列表
     */
    @DynamicDataSourceRouter(path = {"#queryParams.startOperateTime", "#queryParams.endOperateTime"})
    public PageInfo<OperateLog> queryPage(OperateLogQueryReqDTO queryParams) {
        return PageMethod
                .startPage(queryParams.getPageNum(), queryParams.getPageSize())
                .doSelectPageInfo(() -> operateLogMapper.query(queryParams));
    }

}
