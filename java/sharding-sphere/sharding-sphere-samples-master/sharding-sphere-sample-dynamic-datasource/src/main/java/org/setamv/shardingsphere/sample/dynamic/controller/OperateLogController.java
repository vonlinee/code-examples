package org.setamv.shardingsphere.sample.dynamic.controller;

import com.github.pagehelper.PageInfo;
import org.setamv.shardingsphere.sample.dynamic.dto.OperateLogGetReqDTO;
import org.setamv.shardingsphere.sample.dynamic.dto.OperateLogQueryReqDTO;
import org.setamv.shardingsphere.sample.dynamic.model.OperateLog;
import org.setamv.shardingsphere.sample.dynamic.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 操作日志Controller
 *
 * @author setamv
 * @date 2021-04-15
 */
@RestController
@RequestMapping("/operateLog")
public class OperateLogController {

    @Autowired
    private OperateLogService operateLogService;

    /**
     * 新增操作日志。
     * @param log 操作日志
     * @return 操作日志ID
     */
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object add(@RequestBody OperateLog log) {
        return operateLogService.add(log);
    }

    /**
     * 获取操作日志
     * @param reqDTO 请求参数
     * @return 操作日志
     */
    @PostMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public OperateLog get(OperateLogGetReqDTO reqDTO) {
        return operateLogService.get(reqDTO);
    }

    /**
     * 查询操作日志列表
     * @param queryParams 查询参数
     * @return 操作日志列表
     */
    @PostMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OperateLog> query(@RequestBody OperateLogQueryReqDTO queryParams) {
        return operateLogService.query(queryParams);
    }

    /**
     * 分页查询操作日志列表
     * @param queryParams 查询参数
     * @return 操作日志分页结果
     */
    @PostMapping(value = "/pageQuery", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<OperateLog> pageQuery(@RequestBody OperateLogQueryReqDTO queryParams) {
        return operateLogService.queryPage(queryParams);
    }
}
