package io.devpl.codegen.mbpg.template;

import io.devpl.codegen.mbpg.config.ConstVal;
import io.devpl.codegen.mbpg.config.builder.Context;
import io.devpl.codegen.mbpg.util.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * Velocity 模板引擎实现文件输出
 * </p>
 *
 * @author hubin
 * @since 2018-01-10
 */
public class VelocityTemplateEngine extends AbstractTemplateEngine {

    private static final String DOT_VM = ".vm";
    private VelocityEngine velocityEngine;

    @Override
    public VelocityTemplateEngine init(Context context) {
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
        return this;
    }

    @Override
    public void write(@NotNull Map<String, Object> objectMap, @NotNull String templatePath, @NotNull File outputFile) throws Exception {
        if (StringUtils.isEmpty(templatePath)) {
            return;
        }
        Template template = velocityEngine.getTemplate(templatePath, ConstVal.UTF8);
        FileOutputStream fos = new FileOutputStream(outputFile);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, ConstVal.UTF8));
        template.merge(new VelocityContext(objectMap), writer);
        writer.close();
        log.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }

    @Override
    public @NotNull String templateFilePath(@NotNull String filePath) {
        if (filePath.contains(DOT_VM)) {
            return filePath;
        }
        return filePath + DOT_VM;
    }
}
