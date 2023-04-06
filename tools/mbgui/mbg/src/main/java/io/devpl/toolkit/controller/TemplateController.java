package io.devpl.toolkit.controller;

import io.devpl.toolkit.common.BusinessException;
import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.Results;
import io.devpl.toolkit.dto.OutputFileInfo;
import io.devpl.toolkit.dto.UserConfig;
import io.devpl.toolkit.entity.TemplateInfo;
import io.devpl.toolkit.service.CodeGenConfigService;
import io.devpl.toolkit.service.TemplateService;
import io.devpl.toolkit.utils.IOUtils;
import io.devpl.toolkit.utils.StringUtils;
import io.devpl.toolkit.utils.TemplateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板管理控制器
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/template")
public class TemplateController {

    private CodeGenConfigService userConfigStore;
    private TemplateService templateService;

    /**
     * 注册模板
     */
    @PostMapping("/register")
    public Result<TemplateInfo> addTemplate(@RequestBody TemplateInfo templateInfo) {
        templateInfo.setTemplatePath(templateService.assignPath(templateInfo));
        if (templateService.addNewTemplate(templateInfo)) {

        }
        return Results.of();
    }

    /**
     * 下载模板
     * TODO 调整逻辑
     *
     * @param res      HttpServletResponse
     * @param fileType 文件类型
     * @throws IOException
     */
    @GetMapping("/download")
    public void download(HttpServletResponse res, @RequestParam String fileType) throws IOException {
        if (!StringUtils.hasLength(fileType)) {
            log.error("fileType不能为空");
            return;
        }
        UserConfig userConfig = userConfigStore.getUserConfigFromFile();
        if (userConfig == null) {
            // 获取配置失败
            InputStream tplIn = TemplateUtil.getBuiltInTemplate(fileType);
            download(res, tplIn);
            return;
        }
        List<OutputFileInfo> fileInfos = userConfig.getOutputFiles();
        for (OutputFileInfo fileInfo : fileInfos) {
            if (fileType.equals(fileInfo.getFileType())) {
                if (fileInfo.isBuiltin() && StringUtils.hasLength(fileInfo.getTemplatePath())) {
                    download(res, templateService.loadTemplate(fileType));
                } else {
                    String tplPath = fileInfo.getTemplatePath();
                    if (tplPath.startsWith("file:")) {
                        tplPath = tplPath.replaceFirst("file:", "");
                    }
                    File tplFile = new File(tplPath);
                    if (tplFile.exists()) {
                        download(res, Files.newInputStream(tplFile.toPath()));
                    } else {
                        throw new BusinessException("未找到模板文件：" + fileInfo.getTemplatePath());
                    }
                }
                break;
            }
        }
    }

    /**
     * 模板文件上传
     *
     * @param file 模板文件
     * @return 结果
     */
    @PostMapping("/upload")
    public Result<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) {
        Map<String, Object> params = new HashMap<>();
        params.put("templatePath", templateService.uploadTemplate(file));
        params.put("templateName", file.getOriginalFilename());
        return Results.of(params);
    }

    /**
     * 下载文件
     *
     * @param res   HttpServletResponse
     * @param tplIn 模板输入流
     */
    private void download(HttpServletResponse res, InputStream tplIn) {
        if (tplIn != null) {
            res.setCharacterEncoding("utf-8");
            res.setContentType("multipart/form-data;charset=UTF-8");
            try {
                IOUtils.copy(tplIn, res.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
