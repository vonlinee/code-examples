package io.devpl.codegen.mbpg.template;

public interface TemplateRenderer {

    void render(String template, DataMap data) throws Exception;
}
