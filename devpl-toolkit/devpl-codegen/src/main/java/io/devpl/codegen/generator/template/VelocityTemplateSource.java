package io.devpl.codegen.generator.template;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

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

    @Override
    public void render(TemplateArguments arguments, Writer writer) throws IOException {
        Map<String, Object> argumentsMap = arguments.asMap();
        template.merge(argumentsMap == null ? new VelocityContext() : new VelocityContext(argumentsMap), writer);
    }
}
