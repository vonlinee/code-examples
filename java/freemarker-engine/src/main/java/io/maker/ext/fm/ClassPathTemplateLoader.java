package io.maker.ext.fm;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.TemplateLoader;

public class ClassPathTemplateLoader implements TemplateLoader {

	private static final Logger log = LoggerFactory.getLogger(ClassPathTemplateLoader.class);

	private Map<String, String> templates = new HashMap<>();

	public ClassPathTemplateLoader() {
		scanTemplateFile();
	}

	/**
	 * hello_zh.ftl, hello_zh_CN.ftl, hello.ftl
	 */
	@Override
	public Object findTemplateSource(String name) throws IOException {
		return name;
	}

	@Override
	public long getLastModified(Object templateSource) {
		return 0;
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		String path = templates.get(templateSource);
		return new FileReader(new File(path));
	}

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		
	}

	public void scanTemplateFile() {
		String classpaths = System.getProperty("java.class.path");
		for (String classpath : classpaths.split(";")) {
			if (classpath.endsWith(".jar")) {
				// 扫描jar包中的模板文件
			} else {
				String path = Paths.get(classpath, "templates", "freemarker").toAbsolutePath().toString();
				lookupTemplates(new File(path), templates);
			}
		}
	}

	public void lookupTemplates(File file, Map<String, String> templates) {
		// 判断是否为文件夹
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			// 执行操作
			for (File f : listFiles) {
				lookupTemplates(f, templates); // 递归调用
			}
		}
		// 用file的endsWith()方法是通过后缀去筛选
		if (file.getName().endsWith(".ftl")) {
			if (log.isDebugEnabled()) {
				log.debug("find template {}", file.getAbsolutePath());
			}
			templates.put(file.getName(), file.getAbsolutePath());
		}
	}
}
