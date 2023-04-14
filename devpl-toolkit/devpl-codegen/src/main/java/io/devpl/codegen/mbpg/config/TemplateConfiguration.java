package io.devpl.codegen.mbpg.config;

import io.devpl.codegen.generator.template.AbstractTemplateEngine;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板配置项
 */
@Data
public class TemplateConfiguration {

    /**
     * key: 输出文件类型，不包含自定义文件
     * value：模板位置，如果该位置无法找到可用的模板，则会使用OutputFile自身的默认模板
     */
    Map<OutputFile, String> templates = new HashMap<>();

    AbstractTemplateEngine templateEngine;

    /**
     * 获取实体模板路径
     * @param kotlin 是否kotlin
     * @return 模板路径
     */
    public String getEntityTemplatePath(boolean kotlin) {
        OutputFile fileType = kotlin ? OutputFile.ENTITY_KOTLIN : OutputFile.ENTITY_JAVA;
        String templateLocation = templates.get(fileType);
        if (templateLocation == null) {
            templateLocation = fileType.getTemplate();
        }
        return templateLocation;
    }

    @NotNull
    public String getTemplate(OutputFile outputFile) {
        String template = templates.get(outputFile);
        if (template == null) {
            template = outputFile.getTemplate();
        }
        return template;
    }

    /**
     * 模板路径配置构建者
     * @author nieqiurong 3.5.0
     */
    public static class Builder {

        private final TemplateConfiguration templateConfig;

        /**
         * 默认生成一个空的
         */
        public Builder() {
            this.templateConfig = new TemplateConfiguration();
        }

        /**
         * 构建模板配置对象
         * @return 模板配置对象
         */
        public TemplateConfiguration build() {
            return this.templateConfig;
        }
    }
}
