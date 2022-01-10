package org.example.springboot.thirdlib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

public class FreeMarker {
	
	private Configuration cfg;
	
	public FreeMarker() {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		cfg.setFallbackOnNullLoopVariable(false);
		TemplateLoader templateLoader = null;
		try {
			templateLoader = new FileTemplateLoader(new File("templates"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		cfg.setTemplateLoader(templateLoader);
		this.cfg = cfg;
	}
	
	public static void main(String[] args) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		Map<String, Object> root = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		String path = "D:\\workspace\\mine\\springboot-demo\\src\\main\\resources\\1.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))){
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.length() == 0) {
					continue;
				}
				Map<String, Object> map = new HashMap<>();
				String[] split = line.split(":");
				map.put("desc", split[0]);
				String name = split[1];
				map.put("url", name);
				int i = name.lastIndexOf("/");
				int j = name.lastIndexOf(".");
				if (j < 0) {
					j = name.length();
				}
				String methodName = split[1].substring(i+1, j);
				map.put("methodName", methodName);
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		root.put("feignclientList", list);
		FreeMarker freeMarker = new FreeMarker();
		freeMarker.write("feignclient.ftlh", root);
	}
	
	public void write(String templateName, Map<String, Object> data) {
		try (final Writer out = new OutputStreamWriter(new FileOutputStream(new File("2.txt")))){
			Template template = cfg.getTemplate(templateName);
			template.process(data, out);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
	}
}
