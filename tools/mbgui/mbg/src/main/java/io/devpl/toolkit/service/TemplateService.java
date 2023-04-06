package io.devpl.toolkit.service;

import io.devpl.toolkit.entity.TemplateInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface TemplateService {

    /**
     * 加载模板输入流
     *
     * @param templateId 模板唯一ID
     * @return 模板输入流
     */
    InputStream loadTemplate(String templateId);

    /**
     * 指定模板路径
     *
     * @param templateInfo 模板信息
     * @return 模板路径
     */
    String assignPath(TemplateInfo templateInfo);

    /**
     * 新增一条模板信息
     *
     * @param templateInfo 模板信息
     * @return 成功/失败
     */
    boolean addNewTemplate(TemplateInfo templateInfo);

    /**
     * 模板上传
     *
     * @param file
     * @return
     */
    String uploadTemplate(MultipartFile file);
}
