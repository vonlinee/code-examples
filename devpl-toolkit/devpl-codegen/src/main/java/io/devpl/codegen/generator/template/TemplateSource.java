package io.devpl.codegen.generator.template;

import java.io.IOException;
import java.io.Writer;

/**
 * 每一个模板文件对应一个TemplateSource实例
 * 包含模板文件信息，模板所需的参数
 */
public abstract class TemplateSource {

    protected String templateName;

    /**
     * 渲染模板
     * @param arguments 模板参数
     * @param writer    输出位置
     * @throws IOException 渲染异常
     */
    public abstract void render(TemplateArguments arguments, Writer writer) throws IOException;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String name) {
        this.templateName = name;
    }
}
