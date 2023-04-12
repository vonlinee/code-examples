package io.devpl.codegen.mbpg;

import io.devpl.codegen.mbpg.config.*;
import io.devpl.codegen.mbpg.config.Context;
import io.devpl.codegen.mbpg.template.AbstractTemplateEngine;
import io.devpl.codegen.mbpg.core.CodeGenerator;
import io.devpl.codegen.mbpg.template.TemplateCodeGenerator;
import io.devpl.codegen.mbpg.template.VelocityTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成文件
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
public class AutoGenerator {

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
     *
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
     *
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
     *
     * @param globalConfig 全局配置
     * @return this
     * @see 3.5.0
     */
    public AutoGenerator global(ProjectConfiguration globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    CodeGenerator generator;

    /**
     * 生成代码
     *
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
        // 模板引擎初始化
        templateEngine.init(context);
        this.generator = new TemplateCodeGenerator(templateEngine);
        // 执行文件输出
        generator.generate(context, null);
        templateEngine.open();
        logger.debug("==========================文件生成完成！！！==========================");
    }
}
