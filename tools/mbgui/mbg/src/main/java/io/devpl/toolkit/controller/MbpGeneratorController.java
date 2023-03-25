package io.devpl.toolkit.controller;

import io.devpl.toolkit.common.BusinessException;
import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.Results;
import io.devpl.toolkit.dto.CodeGenParam;
import io.devpl.toolkit.mbp.MBPGBridge;
import io.devpl.toolkit.utils.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/mbpg")
public class MbpGeneratorController {

    @Resource
    private MBPGBridge mbpGenerator;

    /**
     * 进行代码生成
     *
     * @param param 代码生成参数
     * @return 代码生成结果
     */
    @PostMapping(value = "/codegen")
    public Result<?> genCode(@RequestBody CodeGenParam param) {
        mbpGenerator.checkGenSetting(param.getGenSetting());
        if (CollectionUtils.isNullOrEmpty(param.getTables())) {
            throw new BusinessException("表为空");
        }
        if (CollectionUtils.isNullOrEmpty(param.getGenSetting().getChoosedOutputFiles())) {
            throw new BusinessException("选择生成的文件类型为空");
        }
        mbpGenerator.doGenerate(param.getGenSetting(), param.getTables());
        return Results.of();
    }
}
