package org.setamv.shardingsphere.sample.dynamic.controller;

import org.setamv.shardingsphere.sample.dynamic.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 公共Controller
 *
 * @author setamv
 * @date 2021-04-15
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    /**
     * 执行一段自定义的SQL查询脚本
     * @param sqlText SQL查询脚本
     * @return 返回值列表
     */
    @PostMapping(value = "/executeDQLSQL", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> executeDqlSql(@RequestBody String sqlText) {
        return commonService.executeDqlSql(sqlText);
    }

    /**
     * 执行一段自定义的DML SQL脚本
     * @param sqlText DML SQL脚本
     * @return 受影响的行数
     */
    @PostMapping(value = "/executeDMLSQL", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer executeDmlSql(@RequestBody String sqlText) {
        return commonService.executeDmlSql(sqlText);
    }

    /**
     * 执行一段自定义的DDL SQL脚本
     * @param sqlText DML SQL脚本
     * @return 受影响的行数
     */
    @PostMapping(value = "/executeDDLSQL", produces = MediaType.APPLICATION_JSON_VALUE)
    public void executeDdlSql(@RequestBody String sqlText) {
        commonService.executeDdlSql(sqlText);
    }
}
