package io.devpl.codegen.mbpg.template;

import java.io.Writer;

/**
 * 渲染模板接口
 */
public interface TemplateRenderer {

    /**
     * 渲染模板
     *
     * @param arguments 模板参数
     * @param template  模板信息
     * @param writer    渲染输出位置
     */
    void render(TemplateArguments arguments, Template template, Writer writer);
}
