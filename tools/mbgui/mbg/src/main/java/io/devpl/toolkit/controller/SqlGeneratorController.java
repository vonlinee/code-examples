package io.devpl.toolkit.controller;

import io.devpl.toolkit.config.props.GeneratorConfig;
import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.Results;
import io.devpl.toolkit.dto.GenDtoFromSqlReq;
import io.devpl.toolkit.service.SqlGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sql")
public class SqlGeneratorController {

    @Autowired
    private SqlGeneratorService sqlGeneratorService;

    @Autowired
    private GeneratorConfig generatorConfig;

    @GetMapping("/basepackage")
    public Result getBasepackage() {
        return Results.of(generatorConfig.getBasePackage());
    }


    @PostMapping("/gen-mapper-method")
    public Result genMapperMethodFromSQL(@RequestBody GenDtoFromSqlReq params) throws Exception {
        sqlGeneratorService.genMapperMethod(params);
        return Results.of();
    }


}
