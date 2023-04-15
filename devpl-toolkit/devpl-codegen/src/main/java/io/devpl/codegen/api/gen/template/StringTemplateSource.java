package io.devpl.codegen.api.gen.template;

import java.io.IOException;
import java.io.Writer;

/**
 * 字符串模板
 */
public class StringTemplateSource extends AbstractTemplateSource {

    @Override
    public void render(TemplateArguments arguments, Writer writer) throws IOException {

    }

    @Override
    boolean isNameValid(String templateName) {
        return true;
    }
}
