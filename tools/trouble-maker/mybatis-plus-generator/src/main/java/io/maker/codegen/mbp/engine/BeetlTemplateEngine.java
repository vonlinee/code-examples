package io.maker.codegen.mbp.engine;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.jetbrains.annotations.NotNull;

import io.maker.codegen.mbp.config.builder.ConfigBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Beetl 模板引擎实现文件输出
 *
 * @author yandixuan
 * @since 2018-12-16
 */
public class BeetlTemplateEngine extends AbstractTemplateEngine {

    private static Method method;

    static {
        try {
            method = GroupTemplate.class.getDeclaredMethod("getTemplate", Object.class);
        } catch (NoSuchMethodException e) {
            try {
                //3.2.x 方法签名修改成了object,其他低版本为string
                method = GroupTemplate.class.getDeclaredMethod("getTemplate", String.class);
            } catch (NoSuchMethodException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private GroupTemplate groupTemplate;

    @Override
    public @NotNull AbstractTemplateEngine init(@NotNull ConfigBuilder configBuilder) {
        try {
            Configuration cfg = Configuration.defaultConfiguration();
            groupTemplate = new GroupTemplate(new ClasspathResourceLoader("/"), cfg);
        } catch (IOException e) {
            logger.error("初始化模板引擎失败:", e);
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public void write(@NotNull Map<String, Object> objectMap, @NotNull String templatePath, @NotNull File outputFile) throws Exception {
        Template template = (Template) method.invoke(groupTemplate, templatePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            template.binding(objectMap);
            template.renderTo(fileOutputStream);
        }
    }

    @Override
    public @NotNull String templateFilePath(@NotNull String filePath) {
        return filePath + ".btl";
    }
}
