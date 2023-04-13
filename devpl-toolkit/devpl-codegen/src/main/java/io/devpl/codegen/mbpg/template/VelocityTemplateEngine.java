package io.devpl.codegen.mbpg.template;

import io.devpl.codegen.mbpg.config.Context;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

/**
 * Velocity 模板引擎实现文件输出
 */
public class VelocityTemplateEngine extends AbstractTemplateEngine {

    private static final String DOT_VM = ".vm";
    private VelocityEngine velocityEngine;

    @Override
    public void init(Context context) {
        if (null == velocityEngine) {
            Properties p = new Properties();
            // 加载类路径下的模板文件 /templates/*.vm
            p.setProperty("file.resource.loader.class", ClasspathResourceLoader.class.getName());
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "");
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
    public String getTemplateFilePath(String filePath) {
        if (filePath.contains(DOT_VM)) {
            return filePath;
        }
        return filePath + DOT_VM;
    }
}
