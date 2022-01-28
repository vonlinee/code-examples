package code.example.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

public class Main {
	public static final String TEMPLATE_DIRECTORY = "D:\\Projects\\Github\\code-example\\javase-samples\\freemarker-samples\\src\\main\\resources\\templates";
	public static final Version VERSION = Configuration.VERSION_2_3_31;

	public static void main(String[] args) throws TemplateException, IOException {
		Configuration cfg = new Configuration(VERSION);
		cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_DIRECTORY));
		cfg.setEncoding(Locale.CHINA, "UTF-8");
		Template template = cfg.getTemplate("test.ftl");
		Map<String, Object> map = new HashMap<>();
		map.put("hello", "");
		StringWriter stringWriter = new StringWriter();
		template.process(map, stringWriter);
		String resultStr = stringWriter.toString();
		System.out.println(resultStr);
	}
}
