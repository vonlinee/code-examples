package io.maker.codegen;

import java.io.IOException;
import java.io.Reader;

import freemarker.cache.TemplateLoader;

public class ClassPathTemplateLoader implements TemplateLoader {

	@Override
	public Object findTemplateSource(String name) throws IOException {
		return null;
	}

	@Override
	public long getLastModified(Object templateSource) {
		return 0;
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		return null;
	}

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {

	}

}
