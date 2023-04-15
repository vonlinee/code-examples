package io.devpl.codegen.api.gen.template;

import java.io.IOException;
import java.io.Writer;

/**
 * 每一个模板文件对应一个TemplateSource实例
 * 包含模板文件信息，模板所需的参数
 */
public interface TemplateSource {

    /**
     * set the name of this template
     * @param name the name of this template
     */
    void setName(String name);

    /**
     * @return the name of this template
     */
    String getName();

    /**
     * 渲染模板
     * @param arguments 模板参数
     * @param writer    输出位置
     * @throws IOException 渲染异常
     */
    void render(TemplateArguments arguments, Writer writer) throws IOException;
}
