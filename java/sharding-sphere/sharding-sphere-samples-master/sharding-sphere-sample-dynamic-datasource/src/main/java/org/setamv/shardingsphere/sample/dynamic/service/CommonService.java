package org.setamv.shardingsphere.sample.dynamic.service;

import org.setamv.shardingsphere.sample.dynamic.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 公共Service
 * @author setamv
 * @date 2021-04-16
 */
@Service
public class CommonService {

    @Autowired(required = false)
    private CommonMapper commonDAO;

    /**
     * 执行一段自定义的SQL查询脚本
     * @param sqlText SQL查询脚本
     * @return 返回值列表
     */
    public List<Map<String, Object>> executeDqlSql(String sqlText) {
        return commonDAO.executeDqlSql(sqlText);
    }

    /**
     * 执行一段自定义的DML SQL脚本
     * @param sqlText DML SQL脚本
     * @return 受影响的行数
     */
    public Integer executeDmlSql(String sqlText) {
        return commonDAO.executeDmlSql(sqlText);
    }

    /**
     * 执行一段自定义的DDL SQL脚本
     * @param sqlText DML SQL脚本
     */
    @Transactional(rollbackFor = Exception.class)
    public void executeDdlSql(String sqlText) {
        commonDAO.executeDdlSql(sqlText);
    }
}
