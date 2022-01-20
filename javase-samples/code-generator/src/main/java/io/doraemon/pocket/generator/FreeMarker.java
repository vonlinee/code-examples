package io.doraemon.pocket.generator;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;

public final class FreeMarker extends AbstractTemplateEngine {

    public static final Version VERSION = Configuration.VERSION_2_3_31;

    private final Configuration config;

    public FreeMarker() {
        config = new Configuration(VERSION);
        config.setAutoFlush(true);
        config.setDefaultEncoding("UTF-8");
        try {
            config.setDirectoryForTemplateLoading(new File("templates"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Environment createEnvironment(Template template, Object dataModel, Writer out) {
        try {
            return template.createProcessingEnvironment(dataModel, out, null);
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void method() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
        //创建数据模型
        // Map<String, String> root = new HashMap<String, String>();
        Map<String, Object> root = new HashMap<String, Object>();
        //加载模板文件
        // 显示生成的数据
        Writer out = new OutputStreamWriter(System.out);
        Template template = null;
        template.process(root, out);
        out.flush();
        out.close();
    }

    public Template loadTemplate(String name) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
        return config.getTemplate(name);
    }
}
