package io.devpl.toolkit.service.impl;

import io.devpl.toolkit.common.BusinessException;
import io.devpl.toolkit.entity.TemplateInfo;
import io.devpl.toolkit.mapper.TemplateInfoMapper;
import io.devpl.toolkit.service.TemplateService;
import io.devpl.toolkit.utils.DateTimeUtils;
import io.devpl.toolkit.utils.FileUtils;
import io.devpl.toolkit.utils.PathUtils;
import io.devpl.toolkit.utils.TemplateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * 模板信息
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    /**
     * 存储目录
     */
    @Value("${save.parent:D:/Temp}")
    private String saveParent;

    @Value("${codegen.template.location}")
    private String templateLocation;

    @Resource
    private TemplateInfoMapper templateInfoMapper;

    @Override
    public InputStream loadTemplate(String templateId) {
        return TemplateUtil.getBuiltInTemplate(templateId);
    }

    @Override
    public String assignPath(TemplateInfo templateInfo) {
        return templateLocation + File.separator + templateInfo.getTemplateName();
    }

    @Override
    public boolean addNewTemplate(TemplateInfo templateInfo) {
        return templateInfoMapper.insert(templateInfo) > 0;
    }

    /**
     * 上传模板
     *
     * @param file 模板文件
     * @return 返回模板文件路径
     */
    @Override
    public String uploadTemplate(MultipartFile file)  {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String fileSuffix = FileUtils.getExtension(fileName);
        // 保存文件名
        String saveFileName = fileName.substring(0, fileName.lastIndexOf(fileSuffix)) + DateTimeUtils.nowDateTime();
        // 模板文件存放目录
        String savePath = PathUtils.join(this.saveParent, "template", saveFileName);
        try {
            file.transferTo(Path.of(savePath));
        } catch (IOException e) {
            throw new BusinessException("上传模板文件失败", e);
        }
        return "file:" + savePath;
    }
}
