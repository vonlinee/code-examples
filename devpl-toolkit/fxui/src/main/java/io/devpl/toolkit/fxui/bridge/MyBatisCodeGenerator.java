package io.devpl.toolkit.fxui.bridge;

import io.devpl.toolkit.fxui.common.Constants;
import io.devpl.toolkit.fxui.common.JDBCDriver;
import io.devpl.toolkit.fxui.common.StringKey;
import io.devpl.toolkit.fxui.model.ConnectionRegistry;
import io.devpl.toolkit.fxui.model.TableCodeGenOption;
import io.devpl.toolkit.fxui.model.TableCodeGenConfig;
import io.devpl.toolkit.fxui.model.props.ConnectionConfig;
import io.devpl.toolkit.fxui.model.props.GenericConfiguration;
import io.devpl.toolkit.fxui.plugins.*;
import io.devpl.toolkit.fxui.utils.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;
import org.mybatis.generator.plugins.EqualsHashCodePlugin;
import org.mybatis.generator.plugins.SerializablePlugin;
import org.mybatis.generator.plugins.ToStringPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * MyBatisCodeGenerator
 */
public class MyBatisCodeGenerator {

    private static final Log log = LogFactory.getLog(MyBatisCodeGenerator.class);
    // 代码生成配置
    private GenericConfiguration generatorConfig;
    // 进度回调
    private ProgressCallback progressCallback;
    // 忽略的列
    private List<IgnoredColumn> ignoredColumns;
    // 覆盖的列
    private List<ColumnOverride> columnOverrides;

    public void setGeneratorConfig(GenericConfiguration generatorConfig) {
        this.generatorConfig = generatorConfig;
    }

    public void generate(Collection<TableCodeGenConfig> tablesForGeneration) throws Exception {
        Configuration configuration = new Configuration();

        // ClassPathEntry
        // 加载驱动文件
        // String connectorLibPath = ConfigHelper.findConnectorLibPath(dbType);
        // log.info("connectorLibPath: {}", connectorLibPath);
        // configuration.addClasspathEntry(connectorLibPath);

        // 注释生成器
        CommentGeneratorConfiguration commentConfig = new CommentGeneratorConfiguration();
        commentConfig.setConfigurationType(DbRemarksCommentGenerator.class.getName());
        commentConfig.addProperty(StringKey.COLUMN_REMARKS, Boolean.TRUE.toString());
        commentConfig.addProperty(StringKey.ANNOTATIONS, Boolean.TRUE.toString());

        // 按连接名称进行分组
        tablesForGeneration.stream()
                .collect(Collectors.groupingBy(TableCodeGenConfig::getConnectionName))
                .forEach((connectionName, tableCodeGenConfigs) -> {
                    Context context = prepareContextForSingleConnection(connectionName, tableCodeGenConfigs);
                    context.setCommentGeneratorConfiguration(commentConfig);
                    configuration.addContext(context);
                });
        generate(configuration);
    }

    /**
     * 每个连接生成一个Context
     * @param connectionName             连接名称
     * @param tableConfigOfOneConnection 该连接下被选中的参与代码生成的表配置信息
     * @return MyBatis Generator Context
     */
    private Context prepareContextForSingleConnection(String connectionName, List<TableCodeGenConfig> tableConfigOfOneConnection) {
        Context context = new Context(ModelType.CONDITIONAL);
        context.setId(connectionName);
        // 设置运行时环境
        context.setTargetRuntime("MyBatis3");
        context.addProperty(StringKey.JAVA_FILE_ENCODING, StandardCharsets.UTF_8.name());
        context.addProperty(StringKey.AUTO_DELIMIT_KEYWORDS, "true");
        // 父包名
        context.addProperty(StringKey.PARENT_PACKAGE, generatorConfig.getParentPackage());
        // 文件编码
        context.addProperty(ConfigKeyRegistry.CONTEXT_JAVA_FILE_ENCODING, StandardCharsets.UTF_8.name());

        ConnectionConfig connectionConfig = ConnectionRegistry.getConnectionConfiguration(connectionName);

        for (TableCodeGenConfig tableCodeGenConfig : tableConfigOfOneConnection) {
            TableConfiguration tableConfig = prepareTableConfiguration(context, tableCodeGenConfig, connectionConfig);

            // 一系列准备工作
            preparePlugins(context, tableCodeGenConfig, connectionConfig);
            prepareCommonConfig(context, generatorConfig);
            prepareJdbcConnectionConfig(context, tableCodeGenConfig, connectionConfig.getDriverInfo(), connectionConfig);
            prepareTableOption(tableCodeGenConfig.getOption());

            context.addTableConfiguration(tableConfig);
        }
        return context;
    }

    /**
     * 准备表配置信息
     * @param context            Context对象
     * @param tableCodeGenConfig 表生成配置信息
     * @param connectionConfig   连接配置信息
     * @return TableConfiguration对象
     */
    private TableConfiguration prepareTableConfiguration(Context context, TableCodeGenConfig tableCodeGenConfig, ConnectionConfig connectionConfig) {
        String tableName = tableCodeGenConfig.getTableName();
        String domainObjectName = StringUtils.underlineToCamel(tableName);
        // 单表的配置选项
        TableCodeGenOption option = tableCodeGenConfig.getOption();
        // TableTreeItem configuration
        TableConfiguration tableConfig = new TableConfiguration(context);
        tableConfig.setTableName(tableName);
        tableConfig.setDomainObjectName(domainObjectName);
        if (!option.isUseExample()) {
            tableConfig.setUpdateByExampleStatementEnabled(false);
            tableConfig.setCountByExampleStatementEnabled(false);
            tableConfig.setDeleteByExampleStatementEnabled(false);
            tableConfig.setSelectByExampleStatementEnabled(false);
        }
        // 数据库schema

        JDBCDriver driverType = connectionConfig.getDriverInfo();

        // Oracle的不知道，但是Mysql的Catalog是数据库名，Schema不支持
        if (driverType == JDBCDriver.MYSQL5 || driverType == JDBCDriver.MYSQL8) {
            tableConfig.setSchema(tableCodeGenConfig.getDatabaseName());
            // 由于beginningDelimiter和endingDelimiter的默认值为双引号(")，在Mysql中不能这么写，所以还要将这两个默认值改为`
            context.addProperty(StringKey.BEGINNING_DELIMITER, "`");
            context.addProperty(StringKey.ENDING_DELIMITER, "`");
            tableConfig.setCatalog(tableCodeGenConfig.getDatabaseName());
        } else {
            tableConfig.setCatalog(tableCodeGenConfig.getDatabaseName());
        }
        // 针对 postgresql 单独配置
        if (driverType == JDBCDriver.POSTGRE_SQL) {
            tableConfig.setDelimitIdentifiers(true);
        }

        if (option.isUseSchemaPrefix()) {
            if (driverType == JDBCDriver.MYSQL5 || driverType == JDBCDriver.MYSQL8) {
                tableConfig.setSchema(tableCodeGenConfig.getDatabaseName());
            } else if (driverType == JDBCDriver.ORACLE) {
                // Oracle的schema为用户名，如果连接用户拥有dba等高级权限，若不设schema，会导致把其他用户下同名的表也生成一遍导致mapper中代码重复
                tableConfig.setSchema(connectionConfig.getUsername());
            } else {
                tableConfig.setCatalog(connectionConfig.getSchema());
            }
        }
        // 添加GeneratedKey主键生成
        if (StringUtils.isNotEmpty(option.getGenerateKeys())) {
            String databaseType = driverType.name();
            if (driverType == JDBCDriver.MYSQL5 || driverType == JDBCDriver.MYSQL8) {
                databaseType = "JDBC";
                // dbType为JDBC，且配置中开启useGeneratedKeys时，Mybatis会使用Jdbc3KeyGenerator,
                // 使用该KeyGenerator的好处就是直接在一次INSERT 语句内，通过resultSet获取得到生成的主键值，
                // 并很好的支持设置了读写分离代理的数据库
                // 例如阿里云RDS + 读写分离代理
                // 无需指定主库
                // 当使用SelectKey时，Mybatis会使用SelectKeyGenerator，INSERT之后，多发送一次查询语句，获得主键值
                // 在上述读写分离被代理的情况下，会得不到正确的主键
            }
            tableConfig.setGeneratedKey(new GeneratedKey(option.getGenerateKeys(), databaseType, true, null));
        }
        // Mapper名称
        tableConfig.setMapperName(tableName + "Mapper");
        // add ignore columns
        if (ignoredColumns != null) {
            ignoredColumns.forEach(tableConfig::addIgnoredColumn);
        }
        if (columnOverrides != null) {
            columnOverrides.forEach(tableConfig::addColumnOverride);
        }
        if (option.isUseActualColumnNames()) {
            tableConfig.addProperty(StringKey.USE_ACTUAL_COLUMN_NAMES, "true");
        }
        if (option.isUseTableNameAlias()) {
            tableConfig.setAlias("");
        }
        // Swagger支持
        if (option.isSwaggerSupport()) {
            context.addPluginConfiguration(SwaggerSupportPlugin.class);
        }
        // MyBatis-Plus插件
        if (option.isUseMyBatisPlus()) {
            context.addPluginConfiguration(MyBatisPlusPlugin.class);
        }
        return tableConfig;
    }

    /**
     * 采用指定的配置进行代码生成
     * @param configuration 总配置类
     * @throws InvalidConfigurationException
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     */
    private void generate(Configuration configuration) throws InvalidConfigurationException, SQLException, IOException, InterruptedException {
        List<String> warnings = new ArrayList<>();
        Set<String> fullyQualifiedTables = new HashSet<>();

        // 待生成的Context ID
        Set<String> contextIds = configuration.getContexts().stream().map(Context::getId).collect(Collectors.toSet());

        ShellCallback shellCallback = new InternalShellCallback(true); // override=true
        MyBatisGenerator mbg = new MyBatisGenerator(configuration, shellCallback, warnings);
        mbg.generate(progressCallback, contextIds, fullyQualifiedTables);
    }

    /**
     * 单表选项配置消费
     * @param option
     */
    public void prepareTableOption(TableCodeGenOption option) {
        // if overrideXML selected, delete oldXML ang generate new one
        if (option.isOverrideXML()) {
            String mappingXMLFilePath = getMappingXMLFilePath(generatorConfig);
            File mappingXMLFile = new File(mappingXMLFilePath);
            if (mappingXMLFile.exists()) {
                boolean delete = mappingXMLFile.delete();
                if (delete) {
                    System.out.println("删除" + mappingXMLFile.getAbsolutePath() + "成功!");
                }
            }
        }
    }

    /**
     * 准备上下文中用到的插件
     * @param context
     * @param generation
     */
    private void preparePlugins(Context context, TableCodeGenConfig generation, ConnectionConfig connectionConfig) {
        TableCodeGenOption option = generation.getOption();
        String dbType = connectionConfig.getDbType();
        // 实体添加序列化
        context.addPluginConfiguration(SerializablePlugin.class);
        // Lombok 插件
        if (option.isUseLombokPlugin()) {
            context.addPluginConfiguration(LombokPlugin.class);
        }
        // toString, hashCode, equals插件
        else if (option.isNeedToStringHashcodeEquals()) {
            context.addPluginConfiguration(ToStringPlugin.class);
            context.addPluginConfiguration(EqualsHashCodePlugin.class);
        }
        // limit/offset插件
        if (option.isOffsetLimit()) {
            if (JDBCDriver.MYSQL5.name().equals(dbType) || JDBCDriver.MYSQL8.name()
                    .equals(dbType) || JDBCDriver.POSTGRE_SQL.name().equals(dbType)) {
                context.addPluginConfiguration(MySQLLimitPlugin.class);
            }
        }
        // for JSR310
        if (option.isJsr310Support()) {
            JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
            javaTypeResolverConfiguration.setConfigurationType("io.devpl.codegen.mbg.plugins.JavaTypeResolverJsr310Impl");
            context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);
        }
        // forUpdate 插件
        if (option.isNeedForUpdate()) {
            if (JDBCDriver.MYSQL5.name().equals(dbType) || JDBCDriver.POSTGRE_SQL.name().equals(dbType)) {
                context.addPluginConfiguration(MySQLForUpdatePlugin.class);
            }
        }
        // repository 插件
        if (option.isAnnotationDAO()) {
            if (JDBCDriver.MYSQL5.name().equals(dbType) || JDBCDriver.MYSQL8.name()
                    .equals(dbType) || JDBCDriver.POSTGRE_SQL.nameEquals(dbType)) {
                context.addPluginConfiguration(RepositoryPlugin.class);
            }
        }
        if (option.isUseDAOExtendStyle()) {
            if (JDBCDriver.MYSQL5.nameEquals(dbType) || JDBCDriver.MYSQL8.nameEquals(dbType) || JDBCDriver.POSTGRE_SQL.nameEquals(dbType)) {
                PluginConfiguration pf = context.addPluginConfiguration(CommonDAOInterfacePlugin.class);
                pf.addProperty(StringKey.USE_EXAMPLE, String.valueOf(option.isUseExample()));
            }
        }
        if (option.isSwaggerSupport()) {
            context.addPluginConfiguration(SwaggerSupportPlugin.class);
        }
        if (option.isFullMVCSupport()) {
            context.addPluginConfiguration(WebMVCSupportPlugin.class);
        }
    }

    private void prepareJdbcConnectionConfig(Context context, TableCodeGenConfig tableCodeGenConfig, JDBCDriver driverType, ConnectionConfig connectionConfig) {
        JDBCConnectionConfiguration jdbcConfig = new JDBCConnectionConfiguration();
        if (JDBCDriver.MYSQL5 == driverType || JDBCDriver.MYSQL8 == driverType) {
            jdbcConfig.addProperty(StringKey.NULL_CATALOG_MEANS_CURRENT, "true");
            // useInformationSchema可以拿到表注释，从而生成类注释可以使用表的注释
            jdbcConfig.addProperty(StringKey.USE_INFORMATION_SCHEMA, "true");
        }
        jdbcConfig.setDriverClass(driverType.getDriverClassName());
        // 一定要填入正确的数据库名，不然拿不到该数据库的表信息
        jdbcConfig.setConnectionURL(connectionConfig.getConnectionUrl(tableCodeGenConfig.getDatabaseName()));
        jdbcConfig.setUserId(connectionConfig.getUsername());
        jdbcConfig.setPassword(connectionConfig.getPassword());
        if (JDBCDriver.ORACLE == driverType) {
            jdbcConfig.getProperties().setProperty(StringKey.REMARKS_REPORTING, "true");
        }
        context.setJdbcConnectionConfiguration(jdbcConfig);
    }

    /**
     * 通用配置信息
     * @param context
     * @param generatorConfig
     */
    private void prepareCommonConfig(Context context, GenericConfiguration generatorConfig) {
        // java model
        JavaModelGeneratorConfiguration modelConfig = new JavaModelGeneratorConfiguration();
        modelConfig.setTargetPackage(generatorConfig.getModelPackage());
        modelConfig.setTargetProject(generatorConfig.getProjectFolder() + "/" + generatorConfig.getModelPackageTargetFolder());
        // Mapper configuration
        SqlMapGeneratorConfiguration mapperConfig = new SqlMapGeneratorConfiguration();
        mapperConfig.setTargetPackage(generatorConfig.getMappingXMLPackage());
        mapperConfig.setTargetProject(generatorConfig.getProjectFolder() + "/" + generatorConfig.getMappingXMLTargetFolder());
        // DAO
        JavaClientGeneratorConfiguration daoConfig = new JavaClientGeneratorConfiguration();
        daoConfig.setConfigurationType(Constants.CONFIGURATION_TYPE_XML_MAPPER);
        daoConfig.setTargetPackage(generatorConfig.getDaoPackage());
        daoConfig.setTargetProject(generatorConfig.getProjectFolder() + "/" + generatorConfig.getDaoTargetFolder());
        context.setJavaModelGeneratorConfiguration(modelConfig);
        context.setSqlMapGeneratorConfiguration(mapperConfig);
        context.setJavaClientGeneratorConfiguration(daoConfig);
    }

    private String getMappingXMLFilePath(GenericConfiguration generatorConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append(generatorConfig.getProjectFolder()).append("/");
        sb.append(generatorConfig.getMappingXMLTargetFolder()).append("/");
        String mappingXMLPackage = generatorConfig.getMappingXMLPackage();
        if (StringUtils.isNotEmpty(mappingXMLPackage)) {
            sb.append(mappingXMLPackage.replace(".", "/")).append("/");
        }
        return sb.toString();
    }

    /**
     * 新增方法
     * @param pluginClass 插件类
     * @return PluginConfiguration
     */
    private void addPluginConfiguration(Context context, Class<? extends Plugin> pluginClass) {
        final PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.addProperty("type", pluginClass.getName());
        pluginConfiguration.setConfigurationType(pluginClass.getName());
        context.addPluginConfiguration(pluginConfiguration);
    }

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }

    public void setIgnoredColumns(List<IgnoredColumn> ignoredColumns) {
        this.ignoredColumns = ignoredColumns;
    }

    public void setColumnOverrides(List<ColumnOverride> columnOverrides) {
        this.columnOverrides = columnOverrides;
    }
}
