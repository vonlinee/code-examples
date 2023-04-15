package io.devpl.codegen.generator.template;

import io.devpl.codegen.api.Context;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

/**
 * Velocity 模板引擎实现文件输出
 */
public class VelocityTemplateEngine extends AbstractTemplateEngine {

    private VelocityEngine velocityEngine;

    @Override
    public void init(Context context) {
        if (null == velocityEngine) {
            Properties p = new Properties();
            // 设置velocity资源加载方式为file
            p.setProperty(Velocity.RESOURCE_LOADER, "file");
            // 加载类路径下的模板文件 /templates/*.vm
            // //设置velocity资源加载方式为file时的处理类
            p.setProperty("file.resource.loader.class", ClasspathResourceLoader.class.getName());
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, FileResourceLoader.class.getName());
            p.setProperty(Velocity.ENCODING_DEFAULT, StandardCharsets.UTF_8.name());
            p.setProperty(Velocity.INPUT_ENCODING, StandardCharsets.UTF_8.name());
            p.setProperty("file.resource.loader.unicode", "true");
            velocityEngine = new VelocityEngine(p);
        }
    }

    @Override
    public void write(Map<String, Object> templateParamMap, String template, Writer writer) throws IOException {
        // 加载模板
        Template vt = velocityEngine.getTemplate(template, StandardCharsets.UTF_8.name());
        if (writer instanceof BufferedWriter) {
            vt.merge(new VelocityContext(templateParamMap), writer);
        } else {
            vt.merge(new VelocityContext(templateParamMap), new BufferedWriter(writer));
        }
    }

    @Override
    public TemplateSource load(String templateName) {
        if (templateName == null || templateName.length() == 0) {
            throw new RuntimeException("the name of template cannot be empty");
        }
        try {
            if (!templateName.endsWith(".vm")) {
                templateName += ".vm";
            }
            Template vt = velocityEngine.getTemplate(templateName, StandardCharsets.UTF_8.name());
            return new VelocityTemplateSource(vt, this);
        } catch (Exception exception) {
            throw new RuntimeException("加载模板失败" + exception.getMessage());
        }
    }
}
