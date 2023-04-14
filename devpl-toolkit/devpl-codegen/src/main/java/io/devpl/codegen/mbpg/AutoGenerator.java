package io.devpl.codegen.mbpg;

import io.devpl.codegen.api.Context;
import io.devpl.codegen.generator.GeneratedFile;
import io.devpl.codegen.api.ProgressCallback;
import io.devpl.codegen.generator.template.DefaultShellCallback;
import io.devpl.codegen.generator.template.ShellCallback;
import io.devpl.codegen.mbpg.config.*;
import io.devpl.codegen.api.IntrospectedTable;
import io.devpl.codegen.api.CodeGenerator;
import io.devpl.codegen.generator.template.AbstractTemplateEngine;
import io.devpl.codegen.generator.template.VelocityTemplateEngine;
import io.devpl.codegen.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成文件
 */
public class AutoGenerator implements CodeGenerator {

    private static final Logger logger = LoggerFactory.getLogger(AutoGenerator.class);

    /**
     * 配置信息
     */
    protected Context context;
    /**
     * 注入配置
     */
    protected InjectionConfig injection;
    /**
     * 数据源配置
     */
    private DataSourceConfig dataSource;
    /**
     * 数据库表配置
     */
    private StrategyConfig strategy;
    /**
     * 包 相关配置
     */
    private PackageConfiguration packageInfo;
    /**
     * 模板 相关配置
     */
    private TemplateConfiguration templateConfig;
    /**
     * 全局 相关配置
     */
    private ProjectConfiguration globalConfig;

    /**
     * 构造方法
     * @param dataSourceConfig 数据库配置
     * @since 3.5.0
     */
    public AutoGenerator(DataSourceConfig dataSourceConfig, InjectionConfig ic, PackageConfiguration pc, StrategyConfig sc) {
        // 这个是必须参数,其他都是可选的,后续去除默认构造更改成final
        this.dataSource = dataSourceConfig;
        this.injection = ic;
        this.packageInfo = pc;
        this.strategy = sc;
    }

    /**
     * 指定模板配置
     * @param templateConfig 模板配置
     * @return this
     * @since 3.5.0
     */
    public AutoGenerator template(TemplateConfiguration templateConfig) {
        this.templateConfig = templateConfig;
        return this;
    }

    /**
     * 指定全局配置
     * @param globalConfig 全局配置
     * @return this
     * @see 3.5.0
     */
    public AutoGenerator global(ProjectConfiguration globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    private final ShellCallback shellCallback = new DefaultShellCallback();

    /**
     * 生成代码
     * @param templateEngine 模板引擎
     */
    public void execute(AbstractTemplateEngine templateEngine) {
        logger.debug("==========================准备生成文件...==========================");
        // 初始化配置
        if (null == context) {
            context = new Context(packageInfo, dataSource, strategy, templateConfig, globalConfig, injection);
        }
        if (null == templateEngine) {
            // 为了兼容之前逻辑，采用 Velocity 引擎 【 默认 】
            templateEngine = new VelocityTemplateEngine();
        }
        templateEngine.setContext(context);
        templateConfig.setTemplateEngine(templateEngine);
        // 模板引擎初始化
        templateEngine.init(context);
        // 执行文件输出
        generate(context, null);

        templateEngine.open();
        logger.debug("==========================文件生成完成！！！==========================");
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
            // 所有的表信息
            List<IntrospectedTable> introspectedTables = context.getIntrospectedTables();

            for (IntrospectedTable introspectedTable : introspectedTables) {
                introspectedTable.initialize();
                // TODO 优化
                introspectedTable.setContext(context);
            }

            List<GeneratedFile> generatedFiles = new ArrayList<>();
            for (IntrospectedTable introspectedTable : introspectedTables) {
                // 由每个表确定哪些文件需要生成
                generatedFiles.addAll(introspectedTable.calculateGeneratedFiles(null));
            }

            for (GeneratedFile generatedFile : generatedFiles) {
                File directory = shellCallback.getDirectory(generatedFile.getFileName(), null);

                String formattedContent = generatedFile.getFormattedContent();

                System.out.println(formattedContent);
            }
        } catch (Exception e) {
            throw new RuntimeException("无法创建文件，请检查配置信息！", e);
        }
    }
}
