package io.devpl.toolkit.mbp;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import io.devpl.toolkit.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.CompositeResourceLoader;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.core.resource.StartsWithMatcher;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static io.devpl.toolkit.dto.Constant.RESOURCE_PREFIX_FILE;

/**
 * 对原模板引擎进行改造，使其支持file和classpath两类加载模式
 */
@Slf4j
public class BeetlTemplateEngine extends AbstractTemplateEngine {

    private GroupTemplate groupTemplate;

    private final String templateStoreDir;

    private final NameConverter nameConverter;

    ClasspathResourceLoader classpathResourceLoader = new ClasspathResourceLoader(this.getClass().getClassLoader());

    public BeetlTemplateEngine(NameConverter nameConverter, String templateStoreDir) {
        this.templateStoreDir = templateStoreDir;
        this.nameConverter = nameConverter;
        try {
            log.info("模板根目录为：" + templateStoreDir);
            FileResourceLoader fileResourceLoader = new FileResourceLoader(templateStoreDir);
            CompositeResourceLoader loader = new CompositeResourceLoader();
            loader.addResourceLoader(new StartsWithMatcher("classpath:").withoutPrefix(), classpathResourceLoader);
            loader.addResourceLoader(new StartsWithMatcher("file:").withoutPrefix(), fileResourceLoader);
            groupTemplate = new GroupTemplate(loader, new Configuration());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void writer(@Nonnull Map<String, Object> objectMap, String templatePath, @Nonnull File outputFile) throws Exception {
        if (templatePath.startsWith("file:")) {
            templatePath = templatePath.replace(templateStoreDir, "");
        }
        log.info("templatePath:" + templatePath);
        Template template = groupTemplate.getTemplate(templatePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            template.binding(objectMap);
            template.renderTo(fileOutputStream);
        }
        log.info("已生成文件:" + outputFile.getPath());
    }

    @Override
    @Nonnull
    public AbstractTemplateEngine init(@Nonnull ConfigBuilder configBuilder) {
        return this;
    }


    @Override
    @Nonnull
    public String templateFilePath(@Nonnull String filePath) {
        return filePath;
    }

    public String write2String(Map<String, Object> objectMap, String templatePath) {
        if (templatePath.startsWith("file:")) {
            templatePath = templatePath.replace(templateStoreDir, "");
        }
        Template template = groupTemplate.getTemplate(templatePath);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            template.binding(objectMap);
            template.renderTo(baos);
            return baos.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void outputCustomFile(List<CustomFile> customFiles, TableInfo tableInfo, @Nonnull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String parentPath = getPathInfo(OutputFile.parent);
        customFiles.forEach(file -> {
            String filePath = StringUtils.hasText(file.getFilePath()) ? file.getFilePath() : parentPath;
            if (StringUtils.hasText(file.getPackageName())) {
                filePath = filePath + File.separator + file.getPackageName();
                filePath = filePath.replaceAll("\\.", "\\" + File.separator);
            }
            log.info("entityName {}", entityName);
            String fileName = filePath + File.separator + nameConverter.customFileNameConvert(file.getFileName(), entityName);
            outputFile(new File(fileName), objectMap, file.getTemplatePath(), file.isFileOverride());
        });
    }

}
