package io.devpl.toolkit.controller;

import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.Results;
import io.devpl.toolkit.dto.OutputFileInfo;
import io.devpl.toolkit.service.OutputFileInfoService;
import io.devpl.toolkit.service.UserConfigStore;
import io.devpl.toolkit.strategy.*;
import io.devpl.toolkit.utils.ProjectPathResolver;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/output-file-info")
public class OutputFileInfoController {

    private OutputFileInfoService outputFileInfoService;
    private UserConfigStore userConfigStore;
    private ProjectPathResolver projectPathResolver;

    @GetMapping("/user-config")
    public Result<?> getUserConfig() {
        return Results.of(userConfigStore.getDefaultUserConfig());
    }

    @GetMapping("/project-root-path")
    public Result<?> getRootPath() {
        return Results.of(projectPathResolver.getBaseProjectPath());
    }

    @PostMapping("/delete")
    public Result<?> deleteOutputInfos(@RequestBody OutputFileInfo outputFileInfo) throws IOException {
        outputFileInfoService.deleteOutputFileInfo(outputFileInfo);
        return Results.of();
    }

    @PostMapping("/save")
    public Result<Void> saveOutputInfos(@RequestBody OutputFileInfo outputFileInfo) throws IOException {
        outputFileInfoService.saveOutputFileInfo(outputFileInfo);
        return Results.of();
    }

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
     * 查看当前项目是否存在配置文件
     *
     * @return
     */
    @GetMapping("/check-if-new-project")
    public Result<Boolean> checkIfNewProject() {
        return Results.of(!userConfigStore.checkUserConfigExisted());
    }

    /**
     * 获取本机所有已保存配置的项目列表
     *
     * @return
     */
    @GetMapping("/all-saved-project")
    public Result getAllSavedProject() {
        return Results.of(userConfigStore.getAllSavedProject());
    }

    /**
     * 为当前项目导入其它项目的配置文件
     *
     * @return
     */
    @PostMapping("/import-project-config/{sourceProjectPkg}")
    public Result importProjectConfig(@PathVariable("sourceProjectPkg") String sourceProjectPkg) throws IOException {
        userConfigStore.importProjectConfig(sourceProjectPkg);
        return Results.of();
    }
}
