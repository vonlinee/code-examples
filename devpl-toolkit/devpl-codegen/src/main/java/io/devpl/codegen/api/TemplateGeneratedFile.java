package io.devpl.codegen.api;

import io.devpl.codegen.generator.GeneratedFile;
import io.devpl.codegen.generator.template.TemplateSource;
import io.devpl.codegen.generator.template.TemplateArguments;

import java.io.IOException;
import java.io.StringWriter;

/**
 * 基于模板生成的文件信息
 */
public class TemplateGeneratedFile extends GeneratedFile {

    private String filename;

    private TemplateSource templateSource;
    private TemplateArguments templateArguments;

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
            templateSource.render(templateArguments, sw);
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setTemplateSource(TemplateSource templateSource) {
        this.templateSource = templateSource;
    }
}
