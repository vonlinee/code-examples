package io.maker.codegen.mbp.engine;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.maker.codegen.mbp.config.ConstVal;
import io.maker.codegen.mbp.config.builder.ConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * Freemarker 模板引擎实现文件输出
 */
public class FreemarkerTemplateEngine extends AbstractTemplateEngine {

    private Configuration configuration;

    private static final Logger logger = LoggerFactory.getLogger(FreemarkerTemplateEngine.class);

    @Override
    public FreemarkerTemplateEngine init(ConfigBuilder configBuilder) {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding(ConstVal.UTF8);
        // 类路径
        configuration.setClassForTemplateLoading(FreemarkerTemplateEngine.class, StringPool.SLASH);
        return this;
    }

    @Override
    public void write(Map<String, Object> objectMap, String templatePath, File outputFile) throws Exception {
        logger.info("FreeMarker Write Template[{}] => [{}]", templatePath, outputFile);
        Template template = configuration.getTemplate(templatePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            template.process(objectMap, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public String templateFilePath(String filePath) {
        return filePath + ".ftl";
    }
}
