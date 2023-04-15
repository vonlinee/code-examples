package io.devpl.codegen.api.gen.template;

import io.devpl.codegen.api.ContextAware;

/**
 * template engine
 */
public interface TemplateEngine extends ContextAware {

    void render(TemplateSource template);
}
