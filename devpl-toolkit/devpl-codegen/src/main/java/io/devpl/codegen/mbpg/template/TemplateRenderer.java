package io.devpl.codegen.mbpg.template;

import java.io.Writer;

/**
 * 渲染模板接口
 */
public interface TemplateRenderer {

    /**
     * 渲染模板
     *
     * @param arguments    模板参数
     * @param templateFile 模板文件
     * @param writer       渲染输出位置
     */
    void render(Template templateFile, TemplateArguments arguments, Writer writer);
}
