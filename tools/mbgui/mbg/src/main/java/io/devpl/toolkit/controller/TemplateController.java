package io.devpl.toolkit.controller;

import io.devpl.toolkit.common.BusinessException;
import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.Results;
import io.devpl.toolkit.dto.OutputFileInfo;
import io.devpl.toolkit.dto.UserConfig;
import io.devpl.toolkit.service.CodeGenConfigService;
import io.devpl.toolkit.utils.StringUtils;
import io.devpl.toolkit.utils.TemplateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/template")
public class TemplateController {

    private CodeGenConfigService userConfigStore;

    /**
     * 下载模板
     *
     * @param res
     * @param fileType
     * @throws IOException
     */
    @GetMapping("/download")
    public void download(HttpServletResponse res, @RequestParam String fileType) throws IOException {
        if (StringUtils.isNullOrEmpty(fileType)) {
            log.error("fileType不能为空");
            return;
        }
        UserConfig userConfig = userConfigStore.getUserConfigFromFile();
        if (userConfig == null) {
            InputStream tplIn = TemplateUtil.getBuiltInTemplate(fileType);
            download(res, tplIn);
            return;
        }
        List<OutputFileInfo> fileInfos = userConfig.getOutputFiles();
        for (OutputFileInfo fileInfo : fileInfos) {
            if (fileType.equals(fileInfo.getFileType())) {
                if (fileInfo.isBuiltIn()
                        && StringUtils.isNullOrEmpty(fileInfo.getTemplatePath())) {
                    InputStream tplIn = TemplateUtil.getBuiltInTemplate(fileType);
                    download(res, tplIn);
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
     * @param file     模板文件
     * @param fileType 模板文件类型
     * @return 结果
     */
    @PostMapping("/upload")
    public Result<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("fileType") String fileType) {
        Map<String, Object> params = new HashMap<>();
        String storePath = userConfigStore.uploadTemplate(file);
        params.put("templatePath", storePath);
        params.put("templateName", file.getOriginalFilename());
        return Results.of(params);
    }

    /**
     * 下载文件
     *
     * @param res   HttpServletResponse
     * @param tplIn
     */
    private void download(HttpServletResponse res, InputStream tplIn) {
        if (tplIn != null) {
            res.setCharacterEncoding("utf-8");
            res.setContentType("multipart/form-data;charset=UTF-8");
            try (tplIn) {
                OutputStream os = res.getOutputStream();
                byte[] b = new byte[2048];
                int length;
                while ((length = tplIn.read(b)) > 0) {
                    os.write(b, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
