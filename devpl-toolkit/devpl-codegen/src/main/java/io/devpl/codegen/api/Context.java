package io.devpl.codegen.api;

import io.devpl.codegen.jdbc.query.DatabaseIntrospector;
import io.devpl.codegen.mbpg.config.*;
import io.devpl.codegen.utils.StringPool;
import io.devpl.codegen.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
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
    private final List<IntrospectedTable> tableInfoList = new ArrayList<>();

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
    private ProjectConfiguration globalConfig;

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
    private final PackageConfiguration packageConfig;

    /**
     * 数据库配置信息
     */
    private final DataSourceConfig dataSourceConfig;

    /**
     * 数据查询实例
     */
    private final DatabaseIntrospector databaseIntrospector;

    /**
     * 在构造器中处理配置
     * @param pkc 包配置
     * @param dsc 数据源配置
     * @param sc  表配置
     * @param tc  模板配置
     * @param pc  全局配置
     */
    public Context(PackageConfiguration pkc, DataSourceConfig dsc, StrategyConfig sc, TemplateConfiguration tc, ProjectConfiguration pc, InjectionConfig ic) {
        this.dataSourceConfig = dsc;
        this.strategyConfig = Objects.requireNonNullElse(sc, new StrategyConfig());
        this.globalConfig = Objects.requireNonNullElse(pc, new ProjectConfiguration());
        this.templateConfig = Objects.requireNonNullElse(tc, new TemplateConfiguration());
        this.packageConfig = Objects.requireNonNullElse(pkc, new PackageConfiguration());
        this.injectionConfig = Objects.requireNonNullElse(ic, new InjectionConfig());

        // 数据库信息提取
        Class<? extends DatabaseIntrospector> databaseQueryClass = dsc.getDatabaseQueryClass();
        try {
            Constructor<? extends DatabaseIntrospector> declaredConstructor = databaseQueryClass.getDeclaredConstructor(this.getClass());
            this.databaseIntrospector = declaredConstructor.newInstance(this);
            log.info("数据库查询接口 {}", databaseIntrospector);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException("创建IDatabaseQuery实例出现错误:", exception);
        }
    }

    private void putPathInfo(String template, OutputFile outputFile, String outputDir) {
        if (StringUtils.hasText(template)) {
            Map<String, String> packageInfoMap = packageConfig.getPackageInfo();
            String packageName = packageInfoMap.get(outputFile.getType());
            if (StringUtils.hasText(packageName)) {
                pathInfo.putIfAbsent(outputFile, joinPath(outputDir, packageName));
            }
        }
    }

    /**
     * 连接路径字符串
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty("java.io.tmpdir");
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
    }

    /**
     * 判断表名是否为正则表名(这表名规范比较随意,只能尽量匹配上特殊符号)
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

    public Context setGlobalConfig(ProjectConfiguration globalConfig) {
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

    public List<IntrospectedTable> getIntrospectedTables() {
        return tableInfoList;
    }

    public Map<OutputFile, String> getPathInfo() {
        return pathInfo;
    }

    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }

    public ProjectConfiguration getGlobalConfig() {
        return globalConfig;
    }

    public InjectionConfig getInjectionConfig() {
        return injectionConfig;
    }

    public PackageConfiguration getPackageConfig() {
        return packageConfig;
    }

    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    /**
     * Introspect tables based on the configuration specified in the
     * constructor. This method is long running.
     * @param callback                 a progress callback if progress information is desired, or
     *                                 <code>null</code>
     * @param warnings                 any warning generated from this method will be added to the
     *                                 List. Warnings are always Strings.
     * @param fullyQualifiedTableNames a set of table names to generate. The elements of the set must
     *                                 be Strings that exactly match what's specified in the
     *                                 configuration. For example, if table name = "foo" and schema =
     *                                 "bar", then the fully qualified table name is "foo.bar". If
     *                                 the Set is null or empty, then all tables in the configuration
     *                                 will be used for code generation.
     * @throws SQLException         if some error arises while introspecting the specified
     *                              database tables.
     * @throws InterruptedException if the progress callback reports a cancel
     */
    @Override
    public void introspectTables(ProgressCallback callback, List<String> warnings, Set<String> fullyQualifiedTableNames) {
        if (tableInfoList.isEmpty()) {
            List<IntrospectedTable> introspectedTables = this.databaseIntrospector.introspecTables();
            // 性能优化，只处理需执行表字段 https://github.com/baomidou/mybatis-plus/issues/219
            introspectedTables.forEach(table -> {
                databaseIntrospector.convertTableFields(table);
                table.importPackage();
            });
            this.tableInfoList.addAll(introspectedTables);
        }
    }

    @Override
    public void refresh() {
        this.tableInfoList.clear();
    }
}
