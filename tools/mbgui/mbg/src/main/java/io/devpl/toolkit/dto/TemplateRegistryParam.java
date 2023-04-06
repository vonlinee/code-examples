package io.devpl.toolkit.dto;

import io.devpl.toolkit.entity.TemplateInfo;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 模板注册参数
 */
@Data
public class TemplateRegistryParam {

    /**
     * 模板信息
     */
    private TemplateInfo templateInfo;

    /**
     * 模板文件
     */
    private MultipartFile file;
}
