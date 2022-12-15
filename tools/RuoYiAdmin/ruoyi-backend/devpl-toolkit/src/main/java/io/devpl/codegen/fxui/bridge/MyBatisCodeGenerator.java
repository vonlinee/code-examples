package io.devpl.codegen.fxui.bridge;

import io.devpl.codegen.fxui.common.StringKey;
import io.devpl.codegen.fxui.config.Constants;
import io.devpl.codegen.fxui.config.DatabaseConfig;
import io.devpl.codegen.fxui.config.DBDriver;
import io.devpl.codegen.fxui.config.CodeGenConfiguration;
import io.devpl.codegen.fxui.plugins.*;
import io.devpl.codegen.fxui.utils.ConfigHelper;
import io.devpl.codegen.fxui.utils.DbUtils;
import io.devpl.codegen.fxui.utils.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;
import org.mybatis.generator.plugins.EqualsHashCodePlugin;
import org.mybatis.generator.plugins.SerializablePlugin;
import org.mybatis.generator.plugins.ToStringPlugin;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyBatisCodeGenerator {

    private static final Log log = LogFactory.getLog(MyBatisCodeGenerator.class);
    // 代码生成配置
    private CodeGenConfiguration generatorConfig;
    // 数据库配置
    private DatabaseConfig selectedDatabaseConfig;
    // 进度回调
    private ProgressCallback progressCallback;
    // 忽略的列
    private List<IgnoredColumn> ignoredColumns;
    // 覆盖的列
    private List<ColumnOverride> columnOverrides;

    public void setGeneratorConfig(CodeGenConfiguration generatorConfig) {
        this.generatorConfig = generatorConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.selectedDatabaseConfig = databaseConfig;
    }

    public void generate() throws Exception {
        Configuration configuration = new Configuration();
        Context context = new Context(ModelType.CONDITIONAL);
        configuration.addContext(context);

        context.addProperty(StringKey.JAVA_FILE_ENCODING, StandardCharsets.UTF_8.name());

        String dbType = selectedDatabaseConfig.getDbType();
        String connectorLibPath = ConfigHelper.findConnectorLibPath(dbType);
        log.info("connectorLibPath: {}", connectorLibPath);
        configuration.addClasspathEntry(connectorLibPath);
        // Table configuration
        TableConfiguration tableConfig = new TableConfiguration(context);
        tableConfig.setTableName(generatorConfig.getTableName());
        tableConfig.setDomainObjectName(generatorConfig.getDomainObjectName());
        if (!generatorConfig.isUseExample()) {
            tableConfig.setUpdateByExampleStatementEnabled(false);
            tableConfig.setCountByExampleStatementEnabled(false);
            tableConfig.setDeleteByExampleStatementEnabled(false);
            tableConfig.setSelectByExampleStatementEnabled(false);
        }

        context.addProperty(StringKey.AUTO_DELIMIT_KEYWORDS, "true");
        if (DBDriver.MySQL5.name().equals(dbType) || DBDriver.MySQL8.name().equals(dbType)) {
            tableConfig.setSchema(selectedDatabaseConfig.getSchema());
            // 由于beginningDelimiter和endingDelimiter的默认值为双引号(")，在Mysql中不能这么写，所以还要将这两个默认值改为`
            context.addProperty(StringKey.BEGINNING_DELIMITER, "`");
            context.addProperty(StringKey.ENDING_DELIMITER, "`");
        } else {
            tableConfig.setCatalog(selectedDatabaseConfig.getSchema());
        }
        if (generatorConfig.isUseSchemaPrefix()) {
            if (DBDriver.MySQL5.name().equals(dbType) || DBDriver.MySQL8.name().equals(dbType)) {
                tableConfig.setSchema(selectedDatabaseConfig.getSchema());
            } else if (DBDriver.ORACLE.name().equals(dbType)) {
                // Oracle的schema为用户名，如果连接用户拥有dba等高级权限，若不设schema，会导致把其他用户下同名的表也生成一遍导致mapper中代码重复
                tableConfig.setSchema(selectedDatabaseConfig.getUsername());
            } else {
                tableConfig.setCatalog(selectedDatabaseConfig.getSchema());
            }
        }
        // 针对 postgresql 单独配置
        if (DBDriver.POSTGRE_SQL.name().equals(dbType)) {
            tableConfig.setDelimitIdentifiers(true);
        }

        // 添加GeneratedKey主键生成
        if (StringUtils.isNotEmpty(generatorConfig.getGenerateKeys())) {
            String dbType2 = dbType;
            if (DBDriver.MySQL5.name().equals(dbType2) || DBDriver.MySQL8.name().equals(dbType)) {
                dbType2 = "JDBC";
                // dbType为JDBC，且配置中开启useGeneratedKeys时，Mybatis会使用Jdbc3KeyGenerator,
                // 使用该KeyGenerator的好处就是直接在一次INSERT 语句内，通过resultSet获取得到 生成的主键值，
                // 并很好的支持设置了读写分离代理的数据库
                // 例如阿里云RDS + 读写分离代理
                // 无需指定主库
                // 当使用SelectKey时，Mybatis会使用SelectKeyGenerator，INSERT之后，多发送一次查询语句，获得主键值
                // 在上述读写分离被代理的情况下，会得不到正确的主键
            }
            tableConfig.setGeneratedKey(new GeneratedKey(generatorConfig.getGenerateKeys(), dbType2, true, null));
        }

        if (generatorConfig.getMapperName() != null) {
            tableConfig.setMapperName(generatorConfig.getMapperName());
        }
        // add ignore columns
        if (ignoredColumns != null) {
            ignoredColumns.forEach(tableConfig::addIgnoredColumn);
        }
        if (columnOverrides != null) {
            columnOverrides.forEach(tableConfig::addColumnOverride);
        }
        if (generatorConfig.isUseActualColumnNames()) {
            tableConfig.addProperty(StringKey.USE_ACTUAL_COLUMN_NAMES, "true");
        }

        if (generatorConfig.isUseTableNameAlias()) {
            tableConfig.setAlias(generatorConfig.getTableName());
        }
        // Swagger支持
        if (generatorConfig.isSwaggerSupport()) {
            addPluginConfiguration(context, SwaggerSupportPlugin.class);
        }
        // MyBatis-Plus插件
        if (generatorConfig.isUseMyBatisPlus()) {
            addPluginConfiguration(context, MyBatisPlusPlugin.class);
        }

        JDBCConnectionConfiguration jdbcConfig = new JDBCConnectionConfiguration();
        if (DBDriver.MySQL5.name().equals(dbType) || DBDriver.MySQL8.name().equals(dbType)) {
            jdbcConfig.addProperty(StringKey.NULL_CATALOG_MEANS_CURRENT, "true");
            // useInformationSchema可以拿到表注释，从而生成类注释可以使用表的注释
            jdbcConfig.addProperty(StringKey.USE_INFORMATION_SCHEMA, "true");
        }
        jdbcConfig.setDriverClass(DBDriver.valueOf(dbType).getDriverClass());
        jdbcConfig.setConnectionURL(DbUtils.getConnectionUrlWithSchema(selectedDatabaseConfig));
        jdbcConfig.setUserId(selectedDatabaseConfig.getUsername());
        jdbcConfig.setPassword(selectedDatabaseConfig.getPassword());
        if (DBDriver.ORACLE.name().equals(dbType)) {
            jdbcConfig.getProperties().setProperty(StringKey.REMARKS_REPORTING, "true");
        }
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


        context.setId("myid");
        context.addTableConfiguration(tableConfig);
        context.setJdbcConnectionConfiguration(jdbcConfig);
        context.setJavaModelGeneratorConfiguration(modelConfig);
        context.setSqlMapGeneratorConfiguration(mapperConfig);
        context.setJavaClientGeneratorConfiguration(daoConfig);
        // Comment
        CommentGeneratorConfiguration commentConfig = new CommentGeneratorConfiguration();
        commentConfig.setConfigurationType(DbRemarksCommentGenerator.class.getName());
        if (generatorConfig.isComment()) {
            commentConfig.addProperty(StringKey.COLUMN_REMARKS, "true");
        }
        if (generatorConfig.isAnnotation()) {
            commentConfig.addProperty(StringKey.ANNOTATIONS, "true");
        }
        context.setCommentGeneratorConfiguration(commentConfig);
        // set java file encoding
        context.addProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING, generatorConfig.getEncoding());

        // 实体添加序列化
        addPluginConfiguration(context, SerializablePlugin.class);

        // Lombok 插件
        if (generatorConfig.isUseLombokPlugin()) {
            addPluginConfiguration(context, LombokPlugin.class);
        }
        // toString, hashCode, equals插件
        else if (generatorConfig.isNeedToStringHashcodeEquals()) {
            addPluginConfiguration(context, EqualsHashCodePlugin.class);
            addPluginConfiguration(context, ToStringPlugin.class);
        }
        // limit/offset插件  
        if (generatorConfig.isOffsetLimit()) {
            if (DBDriver.MySQL5.name().equals(dbType) || DBDriver.MySQL8.name().equals(dbType) || DBDriver.POSTGRE_SQL
                    .name().equals(dbType)) {
                addPluginConfiguration(context, MySQLLimitPlugin.class);
            }
        }
        // for JSR310
        if (generatorConfig.isJsr310Support()) {
            JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
            javaTypeResolverConfiguration.setConfigurationType("io.devpl.codegen.mbg.plugins.JavaTypeResolverJsr310Impl");
            context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);
        }
        // forUpdate 插件
        if (generatorConfig.isNeedForUpdate()) {
            if (DBDriver.MySQL5.name().equals(dbType) || DBDriver.POSTGRE_SQL.name().equals(dbType)) {
                addPluginConfiguration(context, MySQLForUpdatePlugin.class);
            }
        }
        // repository 插件
        if (generatorConfig.isAnnotationDAO()) {
            if (DBDriver.MySQL5.nameEquals(dbType) || DBDriver.MySQL8.nameEquals(dbType) || DBDriver.POSTGRE_SQL.nameEquals(dbType)) {
                addPluginConfiguration(context, RepositoryPlugin.class);
            }
        }
        if (generatorConfig.isUseDAOExtendStyle()) {
            if (DBDriver.MySQL5.nameEquals(dbType) || DBDriver.MySQL8.nameEquals(dbType) || DBDriver.POSTGRE_SQL.nameEquals(dbType)) {
                PluginConfiguration pf = addPluginConfiguration(context, CommonDAOInterfacePlugin.class);
                pf.addProperty(StringKey.USE_EXAMPLE, String.valueOf(generatorConfig.isUseExample()));
            }
        }

        if (generatorConfig.isSwaggerSupport()) {
            log.info("enable swagger");
        }

        if (generatorConfig.isFullMVCSupport()) {
            log.info("support mvc");
            addPluginConfiguration(context, FullMVCSupportPlugin.class);
        }

        // 设置运行时环境
        context.setTargetRuntime("MyBatis3");
        addPluginConfiguration(context, FullMVCSupportPlugin.class);
        List<String> warnings = new ArrayList<>();
        Set<String> fullyQualifiedTables = new HashSet<>();
        Set<String> contexts = new HashSet<>();
        ShellCallback shellCallback = new DefaultShellCallback(true); // override=true
        MyBatisGenerator mybatisGenerator = new MyBatisGenerator(configuration, shellCallback, warnings);
        // if overrideXML selected, delete oldXML ang generate new one
        if (generatorConfig.isOverrideXML()) {
            String mappingXMLFilePath = getMappingXMLFilePath(generatorConfig);
            File mappingXMLFile = new File(mappingXMLFilePath);
            if (mappingXMLFile.exists()) {
                boolean delete = mappingXMLFile.delete();
                if (delete) {
                    System.out.println("删除" + mappingXMLFile.getAbsolutePath() + "成功!");
                }
            }
        }
        log.info("开始生成");
        mybatisGenerator.generate(progressCallback, contexts, fullyQualifiedTables);
    }

    public PluginConfiguration addPluginConfiguration(Context context, Class<? extends Plugin> pluginClass) {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        String pluginClassName = pluginClass.getName();
        pluginConfiguration.addProperty(StringKey.PLUGIN_TYPE, pluginClassName);
        pluginConfiguration.setConfigurationType(pluginClassName);
        context.addPluginConfiguration(pluginConfiguration);
        return pluginConfiguration;
    }

    private String getMappingXMLFilePath(CodeGenConfiguration generatorConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append(generatorConfig.getProjectFolder()).append("/");
        sb.append(generatorConfig.getMappingXMLTargetFolder()).append("/");
        String mappingXMLPackage = generatorConfig.getMappingXMLPackage();
        if (StringUtils.isNotEmpty(mappingXMLPackage)) {
            sb.append(mappingXMLPackage.replace(".", "/")).append("/");
        }
        if (StringUtils.isNotEmpty(generatorConfig.getMapperName())) {
            sb.append(generatorConfig.getMapperName()).append(".xml");
        } else {
            sb.append(generatorConfig.getDomainObjectName()).append("Mapper.xml");
        }
        return sb.toString();
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
