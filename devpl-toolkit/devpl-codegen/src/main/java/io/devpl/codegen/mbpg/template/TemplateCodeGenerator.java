package io.devpl.codegen.mbpg.template;

import io.devpl.codegen.mbpg.ProgressCallback;
import io.devpl.codegen.mbpg.config.InjectionConfig;
import io.devpl.codegen.mbpg.config.builder.Context;
import io.devpl.codegen.mbpg.config.po.TableInfo;

import java.util.List;
import java.util.Map;

/**
 * 基于模板的代码生成
 */
public class TemplateCodeGenerator extends CodeGenerator {

    AbstractTemplateEngine templateEngine;

    public TemplateCodeGenerator(AbstractTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void generate(ProgressCallback progressCallback) {
        try {
            Context context = templateEngine.getContext();
            context.prepare();
            List<TableInfo> tableInfoList = context.getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {
                Map<String, Object> templateParamMap = templateEngine.getObjectMap(context, tableInfo);
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
}
