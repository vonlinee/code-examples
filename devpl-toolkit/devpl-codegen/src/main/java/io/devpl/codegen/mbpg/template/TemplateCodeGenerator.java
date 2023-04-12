package io.devpl.codegen.mbpg.template;

import io.devpl.codegen.mbpg.ProgressCallback;
import io.devpl.codegen.mbpg.config.ProjectConfiguration;
import io.devpl.codegen.mbpg.config.InjectionConfig;
import io.devpl.codegen.mbpg.config.StrategyConfig;
import io.devpl.codegen.mbpg.config.TemplateConfiguration;
import io.devpl.codegen.mbpg.config.Context;
import io.devpl.codegen.mbpg.config.po.TableInfo;
import io.devpl.codegen.mbpg.core.CodeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于模板的代码生成
 */
public class TemplateCodeGenerator implements CodeGenerator {

    AbstractTemplateEngine templateEngine;

    public TemplateCodeGenerator(AbstractTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * 步骤如下：
     * 1.先确定参与生成的模板
     * 2.根据配置初始化模板：初始化模板参数
     *
     * @param context          上下文对象
     * @param progressCallback 进度回调
     */
    @Override
    public void generate(Context context, ProgressCallback progressCallback) {
        try {
            context.prepare();
            List<TableInfo> tableInfoList = context.getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {
                // 准备模板
                List<Template> templates = this.prepareTemplates(context, tableInfo);

                // 确定生成哪些文件

                for (Template template : templates) {
                    template.initialize();
                }

                // 先初始化全局模板参数
                Map<String, Object> objectMap = new HashMap<>();

                objectMap.put("config", context);
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
                    if (io.devpl.sdk.util.StringUtils.isNotBlank(schemaName)) {
                        schemaName += ".";
                        tableInfo.setConvert(true);
                    }
                }
                objectMap.put("schemaName", schemaName);
                objectMap.put("table", tableInfo);
                objectMap.put("entity", tableInfo.getEntityName());

                // 类似于插件的配置项
                InjectionConfig injectionConfig = context.getInjectionConfig();
                if (injectionConfig != null) {
                    // 添加自定义属性
                    injectionConfig.beforeOutputFile(tableInfo, objectMap);
                    // 输出自定义文件
                    templateEngine.outputCustomFile(injectionConfig.getCustomFiles(), tableInfo, objectMap);
                }

                // 初始化模板参数
                Map<String, Object> controllerData = strategyConfig.controller().initialize(tableInfo);
                objectMap.putAll(controllerData);
                // controller
                templateEngine.outputController(tableInfo, objectMap);

                // Mapper
                Map<String, Object> mapperData = strategyConfig.mapper().initialize(tableInfo);
                objectMap.putAll(mapperData);
                // mapper and xml
                templateEngine.outputMapper(tableInfo, objectMap);

                // Service
                Map<String, Object> serviceData = strategyConfig.service().initialize(tableInfo);
                objectMap.putAll(serviceData);
                templateEngine.outputService(tableInfo, objectMap);

                // 实体类
                Map<String, Object> entityData = strategyConfig.entity().initialize(tableInfo);
                objectMap.putAll(entityData);
                // entity
                templateEngine.outputEntity(tableInfo, objectMap);
            }
        } catch (Exception e) {
            throw new RuntimeException("无法创建文件，请检查配置信息！", e);
        }
    }

    /**
     * TODO 待实现
     *
     * @param context
     * @param tableInfo
     * @return
     */
    public List<Template> prepareTemplates(Context context, TableInfo tableInfo) {
        // 模板配置
        TemplateConfiguration tc = context.getTemplateConfiguration();

        // 确定哪些模板参与生成
        List<Template> templates = new ArrayList<>();

        return new ArrayList<>();
    }
}
