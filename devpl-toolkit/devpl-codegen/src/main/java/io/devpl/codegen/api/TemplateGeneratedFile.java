package io.devpl.codegen.api;

import io.devpl.codegen.mbpg.template.Template;
import io.devpl.codegen.mbpg.template.TemplateArguments;

import java.io.IOException;
import java.io.StringWriter;

/**
 * 基于模板生成的文件信息
 */
public class TemplateGeneratedFile extends GeneratedFile {

    private String filename;

    private Template templateFile;
    private TemplateArguments templateArguments;

    public Template getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(Template templateFile) {
        this.templateFile = templateFile;
    }

    public TemplateArguments getTemplateArguments() {
        return templateArguments;
    }

    public void setTemplateArguments(TemplateArguments templateArguments) {
        this.templateArguments = templateArguments;
    }

    @Override
    public String getFormattedContent() {
        StringWriter sw = new StringWriter();
        try {
            templateFile.write(templateArguments, sw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sw.toString();
    }

    @Override
    public String getFileName() {
        return filename;
    }

    @Override
    public boolean isMergeable() {
        return false;
    }

    /**
     * 该文件对应的模板文件
     *
     * @return 模板文件
     */
    public Template getTemplate() {
        return templateFile;
    }
}
