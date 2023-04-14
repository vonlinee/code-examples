package io.devpl.codegen.generator.template;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.IOException;
import java.io.Writer;

/**
 * 包含Velocity模板信息
 */
public class VelocityTemplateSource extends TemplateSource {

    Template template;

    VelocityTemplateEngine templateEngine;

    public VelocityTemplateSource(Template template, VelocityTemplateEngine templateEngine) {
        this.template = template;
        this.templateEngine = templateEngine;
    }

    /**
     * 模板文件路径
     */
    private String path;

    @Override
    public void initialize() {

    }

    @Override
    public void render(TemplateArguments arguments, Writer writer) throws IOException {
        template.merge(new VelocityContext(arguments.getProperties()), writer);
    }
}
