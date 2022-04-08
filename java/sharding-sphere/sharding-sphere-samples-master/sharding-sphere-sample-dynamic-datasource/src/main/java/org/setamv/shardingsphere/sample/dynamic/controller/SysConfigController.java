package org.setamv.shardingsphere.sample.dynamic.controller;

import com.github.pagehelper.PageInfo;
import org.setamv.shardingsphere.sample.dynamic.dto.SysConfigQueryReqDTO;
import org.setamv.shardingsphere.sample.dynamic.model.SysConfig;
import org.setamv.shardingsphere.sample.dynamic.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统配置Controller
 *
 * @author setamv
 * @date 2021-04-15
 */
@RestController
@RequestMapping("/sysConfig")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 新增系统配置。
     * @param config 系统配置
     * @return 系统配置ID
     */
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object add(@RequestBody SysConfig config) {
        return sysConfigService.add(config);
    }

    /**
     * 更新系统配置。
     * @param config 系统配置
     * @return 系统配置ID
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(@RequestBody SysConfig config) {
        int affectedRows = sysConfigService.update(config);
        return "共" + affectedRows + "行受影响";
    }

    /**
     * 获取系统配置
     * @param configCode 配置编码
     * @return 系统配置
     */
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public SysConfig get(@RequestParam String configCode) {
        return sysConfigService.get(configCode);
    }

    /**
     * 查询系统配置列表
     * @param queryParams 查询参数
     * @return 系统配置列表
     */
    @PostMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SysConfig> query(@RequestBody SysConfigQueryReqDTO queryParams) {
        return sysConfigService.query(queryParams);
    }

    /**
     * 分页查询系统配置列表
     * @param queryParams 查询参数
     * @return 系统配置分页结果
     */
    @PostMapping(value = "/pageQuery", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<SysConfig> queryPage(@RequestBody SysConfigQueryReqDTO queryParams) {
        return sysConfigService.queryPage(queryParams);
    }
}
