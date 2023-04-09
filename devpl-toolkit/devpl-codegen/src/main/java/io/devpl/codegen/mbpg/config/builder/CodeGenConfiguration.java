package io.devpl.codegen.mbpg.config.builder;

import io.devpl.codegen.mbpg.config.po.TableInfo;
import io.devpl.codegen.mbpg.query.DatabaseIntrospector;
import io.devpl.codegen.mbpg.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 配置汇总 传递给文件生成工具
 */
public class CodeGenConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CodeGenConfiguration.class);

    /**
     * 模板路径配置信息
     */
    private final TemplateConfig templateConfig;

    /**
     * 数据库表信息
     */
    private final List<TableInfo> tableInfoList = new ArrayList<>();

    /**
     * 路径配置信息
     */
    private final Map<OutputFile, String> pathInfo = new HashMap<>();

    /**
     * 策略配置信息
     */
    private StrategyConfig strategyConfig;

    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig;

    /**
     * 注入配置信息
     */
    private InjectionConfig injectionConfig;

    /**
     * 过滤正则
     */
    private static final Pattern REGX = Pattern.compile("[~!/@#$%^&*()+\\\\\\[\\]|{};:'\",<.>?]+");

    /**
     * 包配置信息
     */
    private final PackageConfig packageConfig;

    /**
     * 数据库配置信息
     */
    private final DataSourceConfig dataSourceConfig;

    /**
     * 数据查询实例
     *
     * @since 3.5.3
     */
    private final DatabaseIntrospector databaseQuery;

    /**
     * 在构造器中处理配置
     *
     * @param packageConfig    包配置
     * @param dataSourceConfig 数据源配置
     * @param strategyConfig   表配置
     * @param templateConfig   模板配置
     * @param globalConfig     全局配置
     */
    public CodeGenConfiguration(PackageConfig packageConfig, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, TemplateConfig templateConfig, GlobalConfig globalConfig, InjectionConfig injectionConfig) {
        this.dataSourceConfig = dataSourceConfig;
        if (strategyConfig == null) {
            this.strategyConfig = new StrategyConfig.Builder().build();
        } else {
            this.strategyConfig = strategyConfig;
        }
        this.globalConfig = Optional.ofNullable(globalConfig).orElseGet(GeneratorBuilder::globalConfig);
        this.templateConfig = Optional.ofNullable(templateConfig).orElseGet(GeneratorBuilder::templateConfig);
        this.packageConfig = Optional.ofNullable(packageConfig).orElseGet(GeneratorBuilder::packageConfig);
        this.injectionConfig = Optional.ofNullable(injectionConfig).orElseGet(GeneratorBuilder::injectionConfig);
        this.pathInfo.putAll(new PathInfoHandler(this.globalConfig, this.templateConfig, this.packageConfig).getPathInfo());
        Class<? extends DatabaseIntrospector> databaseQueryClass = dataSourceConfig.getDatabaseQueryClass();
        try {
            Constructor<? extends DatabaseIntrospector> declaredConstructor = databaseQueryClass.getDeclaredConstructor(this.getClass());
            this.databaseQuery = declaredConstructor.newInstance(this);
            log.info("数据库查询接口 {}", databaseQuery);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException("创建IDatabaseQuery实例出现错误:", exception);
        }
    }

    /**
     * 判断表名是否为正则表名(这表名规范比较随意,只能尽量匹配上特殊符号)
     *
     * @param tableName 表名
     * @return 是否正则
     * @since 3.5.0
     */
    public static boolean matcherRegTable(String tableName) {
        return REGX.matcher(tableName).find();
    }


    public CodeGenConfiguration setStrategyConfig(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
        return this;
    }


    public CodeGenConfiguration setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }


    public CodeGenConfiguration setInjectionConfig(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }


    public TemplateConfig getTemplateConfig() {
        return templateConfig;
    }


    public List<TableInfo> getTableInfoList() {
        if (tableInfoList.isEmpty()) {
            List<TableInfo> tableInfos = this.databaseQuery.queryTables();
            if (!tableInfos.isEmpty()) {
                this.tableInfoList.addAll(tableInfos);
            }
        }
        return tableInfoList;
    }


    public Map<OutputFile, String> getPathInfo() {
        return pathInfo;
    }


    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }


    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }


    public InjectionConfig getInjectionConfig() {
        return injectionConfig;
    }


    public PackageConfig getPackageConfig() {
        return packageConfig;
    }


    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }
}
