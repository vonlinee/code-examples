package io.devpl.toolkit.controller;

import io.devpl.toolkit.common.BusinessException;
import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.Results;
import io.devpl.toolkit.dto.GenConfigDTO;
import io.devpl.toolkit.dto.GenDtoFromSqlReq;
import io.devpl.toolkit.service.SqlGeneratorService;
import io.devpl.toolkit.utils.SecurityUtils;
import io.devpl.toolkit.utils.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * SQL代码生成控制器
 */
@RestController
@RequestMapping("/api/sql")
public class SqlGeneratorController {

    @Resource
    private SqlGeneratorService sqlGeneratorService;

    /**
     * 获取基础包名
     *
     * @return 基础包名
     */
    @GetMapping("/basepackage")
    public Result<String> getBasePackage() {
        return Results.of("");
    }

    /**
     * 根据SQL生成Mapper
     *
     * @param param 参数
     * @return 结果
     * @throws Exception 异常
     */
    @PostMapping("/gen-mapper-method")
    public Result<String> genMapperMethodFromSQL(@RequestBody GenDtoFromSqlReq param) throws Exception {
        if (StringUtils.hasLength(param.getSql())) {
            throw new BusinessException("数据源SQL不能为空");
        }
        String sql = SecurityUtils.decodeBase64(param.getSql());
        if (!sql.trim().toLowerCase().startsWith("select")) {
            throw new BusinessException("只能通过查询语句生成DTO对象，请检查SQL");
        }
        GenConfigDTO config = param.getConfig();
        if (!StringUtils.hasLength(config.getFullPackage())) {
            try {
                Class.forName(config.getFullPackage());
            } catch (Throwable t) {
                config.setAutoCreatedResultDto(true);
            }
        }
        sqlGeneratorService.genMapperMethod(sql, config);
        return Results.of();
    }
}
