package io.devpl.codegen.mbpg;

import io.devpl.codegen.mbpg.config.*;
import io.devpl.codegen.mbpg.config.builder.Context;
import io.devpl.codegen.mbpg.config.po.TableInfo;
import io.devpl.codegen.mbpg.template.AbstractTemplateEngine;
import io.devpl.codegen.mbpg.template.CodeGenerator;
import io.devpl.codegen.mbpg.template.TemplateCodeGenerator;
import io.devpl.codegen.mbpg.template.VelocityTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
    private PackageConfig packageInfo;
    /**
     * 模板 相关配置
     */
    private TemplateConfig templateConfig;
    /**
     * 全局 相关配置
     */
    private GlobalConfig globalConfig;

    private AutoGenerator() {
        // 不推荐使用
    }

    /**
     * 构造方法
     *
     * @param dataSourceConfig 数据库配置
     * @since 3.5.0
     */
    public AutoGenerator(DataSourceConfig dataSourceConfig) {
        // 这个是必须参数,其他都是可选的,后续去除默认构造更改成final
        this.dataSource = dataSourceConfig;
    }

    /**
     * 注入配置
     *
     * @param injectionConfig 注入配置
     * @return this
     * @since 3.5.0
     */
    public AutoGenerator injection(InjectionConfig injectionConfig) {
        this.injection = injectionConfig;
        return this;
    }

    /**
     * 生成策略
     *
     * @param strategyConfig 策略配置
     * @return this
     * @since 3.5.0
     */
    public AutoGenerator strategy(StrategyConfig strategyConfig) {
        this.strategy = strategyConfig;
        return this;
    }

    /**
     * 指定包配置信息
     *
     * @param packageConfig 包配置
     * @return this
     * @since 3.5.0
     */
    public AutoGenerator packageInfo(PackageConfig packageConfig) {
        this.packageInfo = packageConfig;
        return this;
    }

    /**
     * 指定模板配置
     *
     * @param templateConfig 模板配置
     * @return this
     * @since 3.5.0
     */
    public AutoGenerator template(TemplateConfig templateConfig) {
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
    public AutoGenerator global(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    /**
     * 设置配置汇总
     *
     * @param context 连接配置上下文对象
     * @return this
     * @since 3.5.0
     */
    public AutoGenerator config(Context context) {
        this.context = context;
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
        // 模板引擎初始化执行文件输出
        AbstractTemplateEngine te = templateEngine.init(context);

        this.generator = new TemplateCodeGenerator(te);

        generator.generate(null);
        te.open();
        logger.debug("==========================文件生成完成！！！==========================");
    }
}
