package io.devpl.toolkit.controller;

import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.Results;
import io.devpl.toolkit.dto.GenDtoFromSqlReq;
import io.devpl.toolkit.service.SqlGeneratorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/sql")
public class SqlGeneratorController {

    @Resource
    private SqlGeneratorService sqlGeneratorService;

    @GetMapping("/basepackage")
    public Result<String> getBasepackage() {
        return Results.of("");
    }

    @PostMapping("/gen-mapper-method")
    public Result<String> genMapperMethodFromSQL(@RequestBody GenDtoFromSqlReq params) throws Exception {
        sqlGeneratorService.genMapperMethod(params);
        return Results.of();
    }
}
