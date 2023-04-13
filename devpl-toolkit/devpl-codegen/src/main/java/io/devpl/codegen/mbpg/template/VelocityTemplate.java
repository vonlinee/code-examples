package io.devpl.codegen.mbpg.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Hashtable;

/**
 * 包含Velocity模板信息
 */
public class VelocityTemplate extends Template {

    private final VelocityTemplateEngine templateEngine;

    public VelocityTemplate(VelocityTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void write(TemplateArguments arguments, Writer writer) throws IOException {
        templateEngine.write(arguments.getProperties(), getName(), writer);
    }
}
