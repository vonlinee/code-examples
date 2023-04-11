package io.devpl.codegen.mbpg.config.builder;

import io.devpl.codegen.mbpg.config.po.TableInfo;
import io.devpl.codegen.mbpg.query.DatabaseIntrospector;
import io.devpl.codegen.mbpg.config.*;
import io.devpl.codegen.mbpg.util.StringPool;
import io.devpl.codegen.mbpg.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.regex.Pattern;

/**
 * <p>配置汇总 传递给文件生成工具</p>
 * 一个上下文只能有一个数据库连接连接
 */
public class Context extends AbstractContext {

    private static final Logger log = LoggerFactory.getLogger(Context.class);

    /**
     * 模板路径配置信息
     */
    private final TemplateConfiguration templateConfig;

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
    private final DatabaseIntrospector databaseIntrospector;

    /**
     * 在构造器中处理配置
     *
     * @param packageConfig    包配置
     * @param dataSourceConfig 数据源配置
     * @param strategyConfig   表配置
     * @param tc               模板配置
     * @param globalConfig     全局配置
     */
    public Context(PackageConfig packageConfig, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, TemplateConfiguration tc, GlobalConfig globalConfig, InjectionConfig injectionConfig) {
        this.dataSourceConfig = dataSourceConfig;
        this.strategyConfig = Objects.requireNonNullElse(strategyConfig, new StrategyConfig());
        this.globalConfig = Objects.requireNonNullElse(globalConfig, new GlobalConfig());
        this.templateConfig = Objects.requireNonNullElse(tc, new TemplateConfiguration());
        this.packageConfig = Objects.requireNonNullElse(packageConfig, new PackageConfig());
        this.injectionConfig = Objects.requireNonNullElse(injectionConfig, new InjectionConfig());

        final String outputDir = globalConfig.getOutputDir();
        // 设置默认输出路径
        putPathInfo(templateConfig.getEntityTemplatePath(globalConfig.isKotlin()), OutputFile.ENTITY, outputDir);
        putPathInfo(templateConfig.getMapperTemplatePath(), OutputFile.MAPPER, outputDir);
        putPathInfo(templateConfig.getXml(), OutputFile.XML, outputDir);
        putPathInfo(templateConfig.getService(), OutputFile.SERVICE, outputDir);
        putPathInfo(templateConfig.getServiceImpl(), OutputFile.SERVICE_IMPL, outputDir);
        putPathInfo(templateConfig.getController(), OutputFile.CONTROLLER, outputDir);
        pathInfo.putIfAbsent(OutputFile.PARENT, joinPath(outputDir, packageConfig.getPackageInfo(ConstVal.PARENT)));
        // 如果有配置则覆盖自定义路径
        Map<OutputFile, String> pathInfo = packageConfig.getPathInfo();
        if (pathInfo != null && !pathInfo.isEmpty()) {
            this.pathInfo.putAll(pathInfo);
        }

        // 数据库信息提取
        Class<? extends DatabaseIntrospector> databaseQueryClass = dataSourceConfig.getDatabaseQueryClass();
        try {
            Constructor<? extends DatabaseIntrospector> declaredConstructor = databaseQueryClass.getDeclaredConstructor(this.getClass());
            this.databaseIntrospector = declaredConstructor.newInstance(this);
            log.info("数据库查询接口 {}", databaseIntrospector);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException("创建IDatabaseQuery实例出现错误:", exception);
        }
    }

    private void putPathInfo(String template, OutputFile outputFile, String outputDir) {
        if (StringUtils.isNotBlank(template)) {
            pathInfo.putIfAbsent(outputFile, joinPath(outputDir, packageConfig.getPackageInfo(outputFile.getType())));
        }
    }

    /**
     * 连接路径字符串
     *
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
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

    public Context setStrategyConfig(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
        return this;
    }

    public Context setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    public Context setInjectionConfig(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }

    public TemplateConfiguration getTemplateConfiguration() {
        return templateConfig;
    }

    public List<TableInfo> getTableInfoList() {
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

    @Override
    public void prepare() {
        if (tableInfoList.isEmpty()) {
            List<TableInfo> tableInfos = this.databaseIntrospector.introspecTables();
            if (!tableInfos.isEmpty()) {
                this.tableInfoList.addAll(tableInfos);
            }
        }
    }

    @Override
    public void refresh() {

    }
}
