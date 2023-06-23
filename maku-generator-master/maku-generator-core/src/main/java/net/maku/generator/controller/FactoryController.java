package net.maku.generator.controller;

import lombok.AllArgsConstructor;
import net.maku.generator.common.utils.Result;
import net.maku.generator.service.GeneratorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/maku-generator/factory")
public class FactoryController {

    private final GeneratorService generatorService;

    @GetMapping("/generator/file-tree")
    public Result<?> get(String rootPath) {
        return Result.ok(generatorService.getFileTree(rootPath));
    }

    /**
     * 获取文件文本内容
     * @param path 文件路径
     * @return 文本内容
     */
    @GetMapping("/generator/file")
    public Result<?> getFileContent(String path) {
        return Result.ok(generatorService.getFileContent(path));
    }
}
