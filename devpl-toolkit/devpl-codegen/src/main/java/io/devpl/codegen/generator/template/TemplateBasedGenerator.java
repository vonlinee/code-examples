package io.devpl.codegen.generator.template;

import io.devpl.codegen.api.Context;
import io.devpl.codegen.api.IntrospectedTable;
import io.devpl.codegen.api.TemplateGeneratedFile;
import io.devpl.codegen.generator.AbstractGenerator;
import io.devpl.codegen.generator.GeneratedFile;
import io.devpl.codegen.generator.template.impl.*;
import io.devpl.codegen.mbpg.config.OutputFile;
import io.devpl.codegen.mbpg.config.ProjectConfiguration;
import io.devpl.codegen.mbpg.config.StrategyConfig;
import io.devpl.codegen.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于模板的代码生成
 */
public class TemplateBasedGenerator extends AbstractGenerator {

    private Map<String, Object> globalTemplateArguments(Context context, IntrospectedTable table) {
        // 先初始化全局模板参数
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("config", this);
        // 包配置信息
        objectMap.put("package", context.getPackageConfig().getPackageInfo());
        ProjectConfiguration globalConfig = context.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("kotlin", globalConfig.isKotlin());
        objectMap.put("swagger", globalConfig.isSwagger());
        objectMap.put("springdoc", globalConfig.isSpringdoc());
        objectMap.put("date", globalConfig.getCommentDate());
        // 包含代码生成的各项参数
        StrategyConfig strategyConfig = context.getStrategyConfig();
        // 启用 schema 处理逻辑
        String schemaName = "";
        if (strategyConfig.isEnableSchema()) {
            // 存在 schemaName 设置拼接 . 组合表名
            schemaName = context.getDataSourceConfig().getSchemaName();
            if (StringUtils.hasText(schemaName)) {
                schemaName += ".";
                table.setConvert(true);
            }
        }
        objectMap.put("schemaName", schemaName);
        objectMap.put("table", table);
        objectMap.put("entity", table.getEntityName());
        return objectMap;
    }

    @Override
    public List<GeneratedFile> calculateGeneratedFiles(Context context, IntrospectedTable table) {
        List<GeneratedFile> generatedFiles = new ArrayList<>();
        // 策略配置
        StrategyConfig strategyConfig = context.getStrategyConfig();
        // 模板引擎
        AbstractTemplateEngine te = context.getTemplateConfiguration().getTemplateEngine();

        // Entity
        TemplateGeneratedFile entityFile = new TemplateGeneratedFile();
        entityFile.setFilename(table.getEntityName() + ".java");

        TemplateSource ts = te.load(OutputFile.ENTITY_JAVA.getTemplate());

        entityFile.setTemplateSource(ts);
        EntityTemplateArguments etArgs = strategyConfig.entityArguments();
        entityFile.setTemplateArguments(etArgs);
        generatedFiles.add(entityFile);

        // Mapper.java
        TemplateGeneratedFile mapperJavaFile = new TemplateGeneratedFile();
        MapperTemplateArguments mapperArgs = strategyConfig.mapperArguments();
        mapperJavaFile.setTemplateArguments(mapperArgs);
        mapperJavaFile.setTemplateSource(te.load(OutputFile.MAPPER.getTemplate()));
        generatedFiles.add(mapperJavaFile);

        // Mapper.xml
        TemplateGeneratedFile mapperXmlFile = new TemplateGeneratedFile();
        mapperXmlFile.setTemplateArguments(mapperArgs);
        mapperXmlFile.setTemplateSource(te.load(OutputFile.XML.getTemplate()));
        generatedFiles.add(mapperXmlFile);

        // Service.java
        TemplateGeneratedFile serviceJavaFile = new TemplateGeneratedFile();
        ServiceTemplateArguments serviceArgs = strategyConfig.service();
        serviceJavaFile.setTemplateArguments(serviceArgs);
        serviceJavaFile.setTemplateSource(te.load(OutputFile.SERVICE.getTemplate()));
        generatedFiles.add(serviceJavaFile);

        // ServiceImpl.java
        TemplateGeneratedFile serviceImplJavaFile = new TemplateGeneratedFile();
        serviceImplJavaFile.setTemplateArguments(serviceArgs);
        serviceImplJavaFile.setTemplateSource(te.load(OutputFile.SERVICE_IMPL.getTemplate()));
        generatedFiles.add(serviceImplJavaFile);

        // Controller.java
        TemplateGeneratedFile controllerJavaFile = new TemplateGeneratedFile();
        ControllerTemplateArguments controllerArgs = strategyConfig.controllerArguments();
        controllerJavaFile.setTemplateArguments(controllerArgs);
        controllerJavaFile.setTemplateSource(te.load(OutputFile.CONTROLLER.getTemplate()));
        generatedFiles.add(controllerJavaFile);

        return generatedFiles;
    }
}
