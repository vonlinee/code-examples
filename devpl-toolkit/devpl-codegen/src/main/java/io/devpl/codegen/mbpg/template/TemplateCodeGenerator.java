package io.devpl.codegen.mbpg.template;

import io.devpl.codegen.mbpg.ProgressCallback;
import io.devpl.codegen.mbpg.config.GlobalConfig;
import io.devpl.codegen.mbpg.config.InjectionConfig;
import io.devpl.codegen.mbpg.config.StrategyConfig;
import io.devpl.codegen.mbpg.config.TemplateConfiguration;
import io.devpl.codegen.mbpg.config.builder.Context;
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

                for (Template template : templates) {
                    template.initialize();
                }

                Map<String, Object> templateParamMap = getObjectMap(context, tableInfo);
                // 类似于插件的配置项
                InjectionConfig injectionConfig = context.getInjectionConfig();
                if (injectionConfig != null) {
                    // 添加自定义属性
                    injectionConfig.beforeOutputFile(tableInfo, templateParamMap);
                    // 输出自定义文件
                    templateEngine.outputCustomFile(injectionConfig.getCustomFiles(), tableInfo, templateParamMap);
                }
                // entity
                templateEngine.outputEntity(tableInfo, templateParamMap);
                // mapper and xml
                templateEngine.outputMapper(tableInfo, templateParamMap);
                // service
                templateEngine.outputService(tableInfo, templateParamMap);
                // controller
                templateEngine.outputController(tableInfo, templateParamMap);
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

        return new ArrayList<>();
    }

    /**
     * 准备模板数据
     *
     * @param context   配置信息
     * @param tableInfo 表信息对象
     * @return ignore
     */
    public Map<String, Object> getObjectMap(Context context, TableInfo tableInfo) {
        // 包含代码生成的各项参数
        StrategyConfig strategyConfig = context.getStrategyConfig();

        // 初始化模板参数
        Map<String, Object> controllerData = strategyConfig.controller().initialize(tableInfo);
        Map<String, Object> objectMap = new HashMap<>(controllerData);
        Map<String, Object> mapperData = strategyConfig.mapper().initialize(tableInfo);
        objectMap.putAll(mapperData);
        Map<String, Object> serviceData = strategyConfig.service().initialize(tableInfo);
        objectMap.putAll(serviceData);

        // 实体类
        Map<String, Object> entityData = strategyConfig.entity().initialize(tableInfo);
        objectMap.putAll(entityData);
        objectMap.put("config", context);
        // 包配置信息
        objectMap.put("package", context.getPackageConfig().getPackageInfo());

        GlobalConfig globalConfig = context.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("kotlin", globalConfig.isKotlin());
        objectMap.put("swagger", globalConfig.isSwagger());
        objectMap.put("springdoc", globalConfig.isSpringdoc());
        objectMap.put("date", globalConfig.getCommentDate());
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
        return objectMap;
    }
}
