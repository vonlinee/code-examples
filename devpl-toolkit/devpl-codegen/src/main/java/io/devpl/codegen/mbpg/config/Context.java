package io.devpl.codegen.mbpg.config;

import io.devpl.codegen.api.GeneratedFile;
import io.devpl.codegen.api.TemplateGeneratedFile;
import io.devpl.codegen.mbpg.config.po.IntrospectedTable;
import io.devpl.codegen.mbpg.query.DatabaseIntrospector;
import io.devpl.codegen.mbpg.template.impl.MapperTemplateArguments;
import io.devpl.codegen.mbpg.template.impl.ServiceImplTemplateArguments;
import io.devpl.codegen.mbpg.util.StringUtils;
import io.devpl.codegen.utils.StringPool;
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
     * @since 3.5.3
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

    public List<IntrospectedTable> getTableInfoList() {
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
     * 获取表信息
     */
    @Override
    public void introspectTables() {
        if (tableInfoList.isEmpty()) {
            List<IntrospectedTable> tableInfos = this.databaseIntrospector.introspecTables();
            if (!tableInfos.isEmpty()) {
                this.tableInfoList.addAll(tableInfos);
            }
        }
    }

    @Override
    public void refresh() {
        this.tableInfoList.clear();
    }

    /**
     * 当前Context生成哪些文件
     * @param generatedFiles
     */
    public void calculateGeneratedFiles(List<GeneratedFile> generatedFiles) {
        for (IntrospectedTable tableInfo : tableInfoList) {
            // 先初始化全局模板参数
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("config", this);
            // 包配置信息
            objectMap.put("package", packageConfig.getPackageInfo());
            ProjectConfiguration globalConfig = this.getGlobalConfig();
            objectMap.put("author", globalConfig.getAuthor());
            objectMap.put("kotlin", globalConfig.isKotlin());
            objectMap.put("swagger", globalConfig.isSwagger());
            objectMap.put("springdoc", globalConfig.isSpringdoc());
            objectMap.put("date", globalConfig.getCommentDate());
            // 包含代码生成的各项参数
            StrategyConfig strategyConfig = this.getStrategyConfig();
            // 启用 schema 处理逻辑
            String schemaName = "";
            if (strategyConfig.isEnableSchema()) {
                // 存在 schemaName 设置拼接 . 组合表名
                schemaName = this.getDataSourceConfig().getSchemaName();
                if (StringUtils.hasText(schemaName)) {
                    schemaName += ".";
                    tableInfo.setConvert(true);
                }
            }
            objectMap.put("schemaName", schemaName);
            objectMap.put("table", tableInfo);
            objectMap.put("entity", tableInfo.getEntityName());

            // 类似于插件的配置项
            InjectionConfig injectionConfig = this.getInjectionConfig();
            if (injectionConfig != null) {
                // 添加自定义属性
                injectionConfig.beforeOutputFile(tableInfo, objectMap);
                // 输出自定义文件
            }

            // Entity
            TemplateGeneratedFile entityFile = new TemplateGeneratedFile();
            entityFile.setTemplateArguments(strategyConfig.entity());
            generatedFiles.add(entityFile);

            // Mapper.java
            TemplateGeneratedFile mapperJavaFile = new TemplateGeneratedFile();
            entityFile.setTemplateArguments(new MapperTemplateArguments());
            generatedFiles.add(mapperJavaFile);

            // Mapper.xml
            TemplateGeneratedFile mapperXmlFile = new TemplateGeneratedFile();
            entityFile.setTemplateArguments(strategyConfig.mapper());
            generatedFiles.add(mapperXmlFile);

            // Service.java
            TemplateGeneratedFile serviceJavaFile = new TemplateGeneratedFile();
            entityFile.setTemplateArguments(strategyConfig.service());
            generatedFiles.add(serviceJavaFile);

            // ServiceImpl.java
            TemplateGeneratedFile serviceImplJavaFile = new TemplateGeneratedFile();
            entityFile.setTemplateArguments(new ServiceImplTemplateArguments());
            generatedFiles.add(serviceImplJavaFile);

            // Controller.java
            TemplateGeneratedFile controllerJavaFile = new TemplateGeneratedFile();
            entityFile.setTemplateArguments(strategyConfig.controller());
            generatedFiles.add(controllerJavaFile);
        }
    }
}
