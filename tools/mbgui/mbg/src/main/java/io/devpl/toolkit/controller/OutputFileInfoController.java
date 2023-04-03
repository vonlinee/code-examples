package io.devpl.toolkit.controller;

import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.Results;
import io.devpl.toolkit.dto.OutputFileInfo;
import io.devpl.toolkit.service.CodeGenConfigService;
import io.devpl.toolkit.service.OutputFileInfoService;
import io.devpl.toolkit.strategy.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 输出文件信息
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/output-file-info")
public class OutputFileInfoController {

    private OutputFileInfoService outputFileInfoService;
    private CodeGenConfigService userConfigStore;

    /**
     * 获取配置信息
     *
     * @return 配置信息
     */
    @GetMapping("/user-config")
    public Result<?> getUserConfig() {
        return Results.of(userConfigStore.getDefaultUserConfig());
    }

    @PostMapping("/delete")
    public Result<?> deleteOutputInfos(@RequestBody OutputFileInfo outputFileInfo) throws IOException {
        outputFileInfoService.deleteOutputFileInfo(outputFileInfo);
        return Results.of();
    }

    /**
     * 保存输出文件信息
     *
     * @param outputFileInfo 输出文件信息
     * @return
     * @throws IOException
     */
    @PostMapping("/save")
    public Result<Void> saveOutputInfos(@RequestBody OutputFileInfo outputFileInfo) throws IOException {
        outputFileInfoService.saveOutputFileInfo(outputFileInfo);
        return Results.of();
    }

    /**
     * 保存实体策略
     *
     * @param entityStrategy 实体策略
     * @throws IOException
     */
    @PostMapping("/save-entity-strategy")
    public Result<Void> saveEntityStrategy(@RequestBody EntityStrategy entityStrategy) throws IOException {
        outputFileInfoService.saveEntityStrategy(entityStrategy);
        return Results.of();
    }

    @PostMapping("/save-mapper-strategy")
    public Result<Void> saveMapperStrategy(@RequestBody MapperStrategy mapperStrategy) throws IOException {
        outputFileInfoService.saveMapperStrategy(mapperStrategy);
        return Results.of();
    }

    /**
     * 保存策略 XML文件生成策略
     *
     * @param mapperXmlStrategy
     * @return
     * @throws IOException
     */
    @PostMapping("/save-mapper-xml-strategy")
    public Result<Void> saveMapperXmlStrategy(@RequestBody MapperXmlStrategy mapperXmlStrategy) throws IOException {
        outputFileInfoService.saveMapperXmlStrategy(mapperXmlStrategy);
        return Results.of();
    }

    @PostMapping("/save-controller-strategy")
    public Result<Void> saveControllerStrategy(@RequestBody ControllerStrategy controllerStrategy) throws IOException {
        outputFileInfoService.saveControllerStrategy(controllerStrategy);
        return Results.of();
    }

    @PostMapping("/save-service-strategy")
    public Result<Void> saveServiceStrategy(@RequestBody ServiceStrategy serviceStrategy) throws IOException {
        outputFileInfoService.saveServiceStrategy(serviceStrategy);
        return Results.of();
    }

    @PostMapping("/save-service-impl-strategy")
    public Result<Void> saveServiceImplStrategy(@RequestBody ServiceImplStrategy ServiceImplStrategy) throws IOException {
        outputFileInfoService.saveServiceImplStrategy(ServiceImplStrategy);
        return Results.of();
    }

    /**
     * 获取本机所有已保存配置的项目列表
     *
     * @return 项目列表
     */
    @GetMapping("/all-saved-project")
    public Result<?> getAllSavedProject() {
        return Results.of(userConfigStore.getAllSavedProject());
    }

    /**
     * 为当前项目导入其它项目的配置文件
     *
     * @return 导入结果
     */
    @PostMapping("/import-project-config/{sourceProjectPkg}")
    public Result<?> importProjectConfig(@PathVariable("sourceProjectPkg") String sourceProjectPkg) throws IOException {
        userConfigStore.importProjectConfig(sourceProjectPkg);
        return Results.of();
    }
}
