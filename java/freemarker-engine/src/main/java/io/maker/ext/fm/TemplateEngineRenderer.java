package io.maker.ext.fm;

import java.io.Writer;

import freemarker.template.Template;

public interface TemplateEngineRenderer<T> {
	void render(Template template, T dataModel, Writer output);
}
