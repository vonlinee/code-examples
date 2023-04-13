package io.devpl.codegen.mbpg.template;

import io.devpl.codegen.api.GeneratedFile;
import io.devpl.codegen.mbpg.ProgressCallback;
import io.devpl.codegen.mbpg.config.Context;
import io.devpl.codegen.mbpg.config.po.IntrospectedTable;
import io.devpl.codegen.mbpg.core.CodeGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于模板的代码生成
 */
public class TemplateCodeGenerator extends AbstractGenerator implements CodeGenerator {

    AbstractTemplateEngine templateEngine;

    public TemplateCodeGenerator(AbstractTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * 步骤如下：
     * 1.先确定参与生成的模板
     * 2.根据配置初始化模板：初始化模板参数
     * @param context          上下文对象
     * @param progressCallback 进度回调
     */
    @Override
    public void generate(Context context, ProgressCallback progressCallback) {
        try {
            // 生成表信息
            context.introspectTables();

            List<IntrospectedTable> introspectedTables = context.getTableInfoList();

            for (IntrospectedTable introspectedTable : introspectedTables) {
                // 每个表确定哪些文件需要生成
                introspectedTable.calculateGeneratedFiles(null);
            }

            // 确定生成哪些文件
            List<GeneratedFile> generatedFiles = new ArrayList<>();

            context.calculateGeneratedFiles(generatedFiles);

        } catch (Exception e) {
            throw new RuntimeException("无法创建文件，请检查配置信息！", e);
        }
    }
}
