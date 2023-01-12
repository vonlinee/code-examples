package org.mybatis.generator.config;

import org.mybatis.generator.api.*;
import org.mybatis.generator.internal.JDBCConnectionFactory;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.PluginAggregator;
import org.mybatis.generator.internal.db.DatabaseIntrospector;
import org.mybatis.generator.internal.util.StringUtils;
import org.mybatis.generator.internal.util.messages.Messages;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 全局上下文对象
 */
public class Context extends PropertyHolder {

    private final Log log = LogFactory.getLog(Context.class);

    private String id;

    private JDBCConnectionConfiguration jdbcConnectionConfiguration;

    private ConnectionFactoryConfiguration connectionFactoryConfiguration;

    // Mapper.xml配置项
    private SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration;

    // Java类型解析器配置项
    private JavaTypeResolverConfiguration javaTypeResolverConfiguration;

    // Entity生成配置项
    private JavaModelGeneratorConfiguration javaModelGeneratorConfiguration;

    // Mapper.java接口配置项
    private JavaClientGeneratorConfiguration javaClientGeneratorConfiguration;

    private final ArrayList<TableConfiguration> tableConfigurations;

    private final ModelType defaultModelType;

    private String beginningDelimiter = "\""; //$NON-NLS-1$

    private String endingDelimiter = "\""; //$NON-NLS-1$

    private CommentGeneratorConfiguration commentGeneratorConfiguration;

    private CommentGenerator commentGenerator;

    private PluginAggregator pluginAggregator;

    private final List<PluginConfiguration> pluginConfigurations;

    private String targetRuntime;

    private String introspectedColumnImpl;

    private Boolean autoDelimitKeywords;

    private JavaFormatter javaFormatter;

    private KotlinFormatter kotlinFormatter;

    private XmlFormatter xmlFormatter;

    public Context(ModelType defaultModelType) {
        super();
        this.defaultModelType = defaultModelType == null ? ModelType.CONDITIONAL : defaultModelType;
        tableConfigurations = new ArrayList<>();
        pluginConfigurations = new ArrayList<>();
    }

    public void addTableConfiguration(TableConfiguration tc) {
        tableConfigurations.add(tc);
    }

    public JavaClientGeneratorConfiguration getJavaClientGeneratorConfiguration() {
        return javaClientGeneratorConfiguration;
    }

    public JavaModelGeneratorConfiguration getJavaModelGeneratorConfiguration() {
        return javaModelGeneratorConfiguration;
    }

    public JavaTypeResolverConfiguration getJavaTypeResolverConfiguration() {
        return javaTypeResolverConfiguration;
    }

    public SqlMapGeneratorConfiguration getSqlMapGeneratorConfiguration() {
        return sqlMapGeneratorConfiguration;
    }

    public void addPluginConfiguration(PluginConfiguration pluginConfiguration) {
        pluginConfigurations.add(pluginConfiguration);
    }

    public <T extends Plugin> void addPluginConfiguration(Class<T> pluginClass) {
        final PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.addProperty("type", pluginClass.getName());
        pluginConfiguration.setConfigurationType(pluginClass.getName());
        addPluginConfiguration(pluginConfiguration);
    }

    /**
     * This method does a simple validate, it makes sure that all required fields have been filled in. It does not do
     * any more complex operations such as validating that database tables exist or validating that named columns exist
     * @param errors the errors
     */
    public void validate(List<String> errors) {
        if (!StringUtils.hasLength(id)) {
            errors.add(Messages.getString("ValidationError.16")); //$NON-NLS-1$
        }

        if (jdbcConnectionConfiguration == null && connectionFactoryConfiguration == null) {
            // must specify one
            errors.add(Messages.getString("ValidationError.10", id)); //$NON-NLS-1$
        } else if (jdbcConnectionConfiguration != null && connectionFactoryConfiguration != null) {
            // must not specify both
            errors.add(Messages.getString("ValidationError.10", id)); //$NON-NLS-1$
        } else if (jdbcConnectionConfiguration != null) {
            jdbcConnectionConfiguration.validate(errors);
        } else {
            connectionFactoryConfiguration.validate(errors);
        }

        if (javaModelGeneratorConfiguration == null) {
            errors.add(Messages.getString("ValidationError.8", id)); //$NON-NLS-1$
        } else {
            javaModelGeneratorConfiguration.validate(errors, id);
        }

        if (javaClientGeneratorConfiguration != null) {
            javaClientGeneratorConfiguration.validate(errors, id);
        }

        IntrospectedTable it = null;
        try {
            it = ObjectFactory.createIntrospectedTableForValidation(this);
        } catch (Exception e) {
            errors.add(Messages.getString("ValidationError.25", id)); //$NON-NLS-1$
        }

        if (it != null && it.requiresXMLGenerator()) {
            if (sqlMapGeneratorConfiguration == null) {
                errors.add(Messages.getString("ValidationError.9", id)); //$NON-NLS-1$
            } else {
                sqlMapGeneratorConfiguration.validate(errors, id);
            }
        }

        if (tableConfigurations.isEmpty()) {
            errors.add(Messages.getString("ValidationError.3", id)); //$NON-NLS-1$
        } else {
            for (int i = 0; i < tableConfigurations.size(); i++) {
                TableConfiguration tc = tableConfigurations.get(i);
                tc.validate(errors, i);
            }
        }

        for (PluginConfiguration pluginConfiguration : pluginConfigurations) {
            pluginConfiguration.validate(errors, id);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJavaClientGeneratorConfiguration(JavaClientGeneratorConfiguration javaClientGeneratorConfiguration) {
        this.javaClientGeneratorConfiguration = javaClientGeneratorConfiguration;
    }

    public void setJavaModelGeneratorConfiguration(JavaModelGeneratorConfiguration javaModelGeneratorConfiguration) {
        this.javaModelGeneratorConfiguration = javaModelGeneratorConfiguration;
    }

    public void setJavaTypeResolverConfiguration(JavaTypeResolverConfiguration javaTypeResolverConfiguration) {
        this.javaTypeResolverConfiguration = javaTypeResolverConfiguration;
    }

    public void setJdbcConnectionConfiguration(JDBCConnectionConfiguration jdbcConnectionConfiguration) {
        this.jdbcConnectionConfiguration = jdbcConnectionConfiguration;
    }

    public void setSqlMapGeneratorConfiguration(SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration) {
        this.sqlMapGeneratorConfiguration = sqlMapGeneratorConfiguration;
    }

    public ModelType getDefaultModelType() {
        return defaultModelType;
    }

    public String getBeginningDelimiter() {
        return beginningDelimiter;
    }

    public String getEndingDelimiter() {
        return endingDelimiter;
    }

    @Override
    public void addProperty(String name, String value) {
        super.addProperty(name, value);

        if (ConfigKeyRegistry.CONTEXT_BEGINNING_DELIMITER.equals(name)) {
            beginningDelimiter = value;
        } else if (ConfigKeyRegistry.CONTEXT_ENDING_DELIMITER.equals(name)) {
            endingDelimiter = value;
        } else if (ConfigKeyRegistry.CONTEXT_AUTO_DELIMIT_KEYWORDS.equals(name) && StringUtils.hasLength(value)) {
            autoDelimitKeywords = StringUtils.isTrue(value);
        }
    }

    public CommentGenerator getCommentGenerator() {
        if (commentGenerator == null) {
            commentGenerator = ObjectFactory.createCommentGenerator(this);
        }

        return commentGenerator;
    }

    public JavaFormatter getJavaFormatter() {
        if (javaFormatter == null) {
            javaFormatter = ObjectFactory.createJavaFormatter(this);
        }
        return javaFormatter;
    }

    public KotlinFormatter getKotlinFormatter() {
        if (kotlinFormatter == null) {
            kotlinFormatter = ObjectFactory.createKotlinFormatter(this);
        }

        return kotlinFormatter;
    }

    public XmlFormatter getXmlFormatter() {
        if (xmlFormatter == null) {
            xmlFormatter = ObjectFactory.createXmlFormatter(this);
        }

        return xmlFormatter;
    }

    public CommentGeneratorConfiguration getCommentGeneratorConfiguration() {
        return commentGeneratorConfiguration;
    }

    public void setCommentGeneratorConfiguration(CommentGeneratorConfiguration commentGeneratorConfiguration) {
        this.commentGeneratorConfiguration = commentGeneratorConfiguration;
    }

    public Plugin getPlugins() {
        return pluginAggregator;
    }

    public String getTargetRuntime() {
        return targetRuntime;
    }

    public void setTargetRuntime(String targetRuntime) {
        this.targetRuntime = targetRuntime;
    }

    public String getIntrospectedColumnImpl() {
        return introspectedColumnImpl;
    }

    public void setIntrospectedColumnImpl(String introspectedColumnImpl) {
        this.introspectedColumnImpl = introspectedColumnImpl;
    }

    // methods related to code generation.
    //
    // Methods should be called in this order:
    //
    // 1. getIntrospectionSteps()
    // 2. introspectTables()
    // 3. getGenerationSteps()
    // 4. generateFiles()
    //

    private final List<IntrospectedTable> introspectedTables = new ArrayList<>();

    /**
     * This method could be useful for users that use the library for introspection only
     * and not for code generation.
     * @return a list containing the results of table introspection. The list will be empty
     * if this method is called before introspectTables(), or if no tables are found that
     * match the configuration
     */
    public List<IntrospectedTable> getIntrospectedTables() {
        return introspectedTables;
    }

    public int getIntrospectionSteps() {
        int steps = 0;

        steps++; // connect to database

        // for each table:
        //
        // 1. Create introspected table implementation

        steps += tableConfigurations.size();

        return steps;
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
    public void introspectTables(ProgressCallback callback, List<String> warnings, Set<String> fullyQualifiedTableNames) throws SQLException, InterruptedException {

        introspectedTables.clear();
        JavaTypeResolver javaTypeResolver = ObjectFactory.createJavaTypeResolver(this, warnings);

        Connection connection = null;
        try {
            callback.startTask(Messages.getString("Progress.0")); //$NON-NLS-1$
            connection = getConnection();
            DatabaseIntrospector databaseIntrospector = new DatabaseIntrospector(this, connection.getMetaData(), javaTypeResolver, warnings);

            for (TableConfiguration tc : tableConfigurations) {
                String tableName = StringUtils.composeFullyQualifiedTableName(tc.getCatalog(), tc.getSchema(), tc.getTableName(), '.');

                if (fullyQualifiedTableNames != null && !fullyQualifiedTableNames.isEmpty() && !fullyQualifiedTableNames.contains(tableName)) {
                    continue;
                }

                if (!tc.areAnyStatementsEnabled()) {
                    warnings.add(Messages.getString("Warning.0", tableName)); //$NON-NLS-1$
                    continue;
                }

                callback.startTask(Messages.getString("Progress.1", tableName)); //$NON-NLS-1$
                List<IntrospectedTable> tables = databaseIntrospector.introspectTables(tc);

                if (tables != null) {
                    introspectedTables.addAll(tables);
                }

                callback.checkCancel();
            }
        } finally {
            closeConnection(connection);
        }
    }

    public int getGenerationSteps() {
        int steps = 0;

        for (IntrospectedTable introspectedTable : introspectedTables) {
            steps += introspectedTable.getGenerationSteps();
        }

        return steps;
    }

    public void generateFiles(ProgressCallback callback, List<GeneratedJavaFile> generatedJavaFiles, List<GeneratedXmlFile> generatedXmlFiles, List<GeneratedKotlinFile> generatedKotlinFiles, List<GeneratedFile> otherGeneratedFiles, List<String> warnings) throws InterruptedException {
        pluginAggregator = new PluginAggregator();
        // 添加运行时需要的插件
        for (PluginConfiguration pluginConfiguration : pluginConfigurations) {
            Plugin plugin = ObjectFactory.createPlugin(this, pluginConfiguration);
            if (plugin.validate(warnings)) {
                log.info("添加插件 {}", plugin);
                pluginAggregator.addPlugin(plugin);
            } else {
                warnings.add(Messages.getString("Warning.24", //$NON-NLS-1$
                        pluginConfiguration.getConfigurationType(), id));
            }
        }

        // 初始化操作
        // initialize everything first before generating. This allows plugins to know about other
        // items in the configuration.
        for (IntrospectedTable introspectedTable : introspectedTables) {
            callback.checkCancel();
            introspectedTable.initialize();
            introspectedTable.calculateGenerators(warnings, callback);
        }

        for (IntrospectedTable introspectedTable : introspectedTables) {
            callback.checkCancel();
            generatedJavaFiles.addAll(introspectedTable.getGeneratedJavaFiles());
            generatedXmlFiles.addAll(introspectedTable.getGeneratedXmlFiles());
            generatedKotlinFiles.addAll(introspectedTable.getGeneratedKotlinFiles());
            // 执行插件
            generatedJavaFiles.addAll(pluginAggregator.contextGenerateAdditionalJavaFiles(introspectedTable));
            generatedXmlFiles.addAll(pluginAggregator.contextGenerateAdditionalXmlFiles(introspectedTable));
            generatedKotlinFiles.addAll(pluginAggregator.contextGenerateAdditionalKotlinFiles(introspectedTable));
            otherGeneratedFiles.addAll(pluginAggregator.contextGenerateAdditionalFiles(introspectedTable));
        }
        generatedJavaFiles.addAll(pluginAggregator.contextGenerateAdditionalJavaFiles());
        generatedXmlFiles.addAll(pluginAggregator.contextGenerateAdditionalXmlFiles());
        generatedKotlinFiles.addAll(pluginAggregator.contextGenerateAdditionalKotlinFiles());
        otherGeneratedFiles.addAll(pluginAggregator.contextGenerateAdditionalFiles());
    }

    /**
     * This method creates a new JDBC connection from the values specified in the configuration file.
     * If you call this method, then you are responsible
     * for closing the connection (See {@link Context#closeConnection(Connection)}). If you do not
     * close the connection, then there could be connection leaks.
     * @return a new connection created from the values in the configuration file
     * @throws SQLException if any error occurs while creating the connection
     */
    public Connection getConnection() throws SQLException {
        ConnectionFactory connectionFactory;
        if (jdbcConnectionConfiguration != null) {
            connectionFactory = new JDBCConnectionFactory(jdbcConnectionConfiguration);
        } else {
            connectionFactory = ObjectFactory.createConnectionFactory(this);
        }
        return connectionFactory.getConnection();
    }

    /**
     * This method closes a JDBC connection and ignores any errors. If the passed connection is null,
     * then the method does nothing.
     * @param connection a JDBC connection to close, may be null
     */
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    public boolean autoDelimitKeywords() {
        return autoDelimitKeywords != null && autoDelimitKeywords;
    }

    public ConnectionFactoryConfiguration getConnectionFactoryConfiguration() {
        return connectionFactoryConfiguration;
    }

    public void setConnectionFactoryConfiguration(ConnectionFactoryConfiguration connectionFactoryConfiguration) {
        this.connectionFactoryConfiguration = connectionFactoryConfiguration;
    }
}
