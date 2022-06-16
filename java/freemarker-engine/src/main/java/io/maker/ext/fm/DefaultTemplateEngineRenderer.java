package io.maker.ext.fm;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DefaultTemplateEngineRenderer implements TemplateEngineRenderer<Map<String, Object>> {

	@Override
	public void render(Template template, Map<String, Object> dataModel, Writer output) {
		try {
			template.createProcessingEnvironment(dataModel, output, null).process();
		} catch (TemplateException | IOException e) {
			e.printStackTrace();
		}
	}
}
