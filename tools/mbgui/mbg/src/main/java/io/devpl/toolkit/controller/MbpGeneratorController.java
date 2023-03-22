package io.devpl.toolkit.controller;

import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.Results;
import io.devpl.toolkit.dto.CodeGenParam;
import io.devpl.toolkit.mbp.MbpGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/mbpg")
public class MbpGeneratorController {

    @Resource
    private MbpGenerator mbpGenerator;

    @PostMapping(value = "/codegen")
    public Result<?> genCode(@RequestBody CodeGenParam param) {
        mbpGenerator.checkGenSetting(param.getGenSetting());
        mbpGenerator.doGenerate(param.getGenSetting(), param.getTables());
        return Results.of();
    }
}
