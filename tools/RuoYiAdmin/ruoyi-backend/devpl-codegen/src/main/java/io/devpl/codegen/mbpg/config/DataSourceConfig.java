package io.devpl.codegen.mbpg.config;

import io.devpl.codegen.mbpg.config.converts.MySqlTypeConvert;
import io.devpl.codegen.mbpg.config.converts.TypeConverts;
import io.devpl.codegen.mbpg.config.querys.DbQueryDecorator;
import io.devpl.codegen.mbpg.config.querys.DbQueryRegistry;
import io.devpl.codegen.mbpg.jdbc.DbType;
import io.devpl.codegen.mbpg.query.AbstractDatabaseQuery;
import io.devpl.codegen.mbpg.query.DefaultQuery;
import io.devpl.codegen.mbpg.query.IDatabaseQuery;
import io.devpl.codegen.mbpg.query.SQLQuery;
import io.devpl.codegen.mbpg.type.ITypeConvertHandler;
import io.devpl.sdk.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * 数据库配置
 * @author YangHu, hcl, hubin
 * @since 2016/8/30
 */
public class DataSourceConfig {
    protected final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    private DataSourceConfig() {
    }

    /**
     * 数据库信息查询
     */
    private IDbQuery dbQuery;

    /**
     * schemaName
     */
    private String schemaName;

    /**
     * 类型转换
     */
    private ITypeConvert typeConvert;

    /**
     * 关键字处理器
     * @since 3.3.2
     */
    private IKeyWordsHandler keyWordsHandler;

    /**
     * 驱动连接的URL
     */
    private String url;

    /**
     * 数据库连接用户名
     */
    private String username;

    /**
     * 数据库连接密码
     */
    private String password;

    /**
     * 数据源实例
     * @since 3.5.0
     */
    private DataSource dataSource;

    /**
     * 数据库连接
     * @since 3.5.0
     */
    private Connection connection;

    /**
     * 数据库连接属性
     * @since 3.5.3
     */
    private final Map<String, String> connectionProperties = new HashMap<>();

    /**
     * 查询方式
     * @see DefaultQuery 默认查询方式，配合{@link #getTypeConvertHandler()} 使用
     * @see SQLQuery SQL语句查询方式，配合{@link #typeConvert} 使用
     * @since 3.5.3
     */
    private Class<? extends AbstractDatabaseQuery> databaseQueryClass = DefaultQuery.class;

    /**
     * 类型转换处理
     * @since 3.5.3
     */
    private ITypeConvertHandler typeConvertHandler;

    /**
     * 获取数据库查询
     */
    @NotNull
    public IDbQuery getDbQuery() {
        if (null == dbQuery) {
            DbType dbType = getDbType();
            DbQueryRegistry dbQueryRegistry = new DbQueryRegistry();
            // 默认 MYSQL
            dbQuery = Optional
                    .ofNullable(dbQueryRegistry.getDbQuery(dbType))
                    .orElseGet(() -> dbQueryRegistry.getDbQuery(DbType.MYSQL));
        }
        return dbQuery;
    }

    /**
     * 判断数据库类型
     * @return 类型枚举值
     */
    @NotNull
    public DbType getDbType() {
        return this.getDbType(this.url.toLowerCase());
    }

    /**
     * 判断数据库类型
     * @param str url
     * @return 类型枚举值，如果没找到，则返回 null
     */
    @NotNull
    private DbType getDbType(@NotNull String str) {
        if (str.contains(":mysql:") || str.contains(":cobar:")) {
            return DbType.MYSQL;
        } else if (str.contains(":oracle:")) {
            return DbType.ORACLE;
        } else if (str.contains(":postgresql:")) {
            return DbType.POSTGRE_SQL;
        } else if (str.contains(":sqlserver:")) {
            return DbType.SQL_SERVER;
        } else if (str.contains(":db2:")) {
            return DbType.DB2;
        } else if (str.contains(":mariadb:")) {
            return DbType.MARIADB;
        } else if (str.contains(":sqlite:")) {
            return DbType.SQLITE;
        } else if (str.contains(":h2:")) {
            return DbType.H2;
        } else if (str.contains(":kingbase:") || str.contains(":kingbase8:")) {
            return DbType.KINGBASE_ES;
        } else if (str.contains(":dm:")) {
            return DbType.DM;
        } else if (str.contains(":zenith:")) {
            return DbType.GAUSS;
        } else if (str.contains(":oscar:")) {
            return DbType.OSCAR;
        } else if (str.contains(":firebird:")) {
            return DbType.FIREBIRD;
        } else if (str.contains(":xugu:")) {
            return DbType.XU_GU;
        } else if (str.contains(":clickhouse:")) {
            return DbType.CLICK_HOUSE;
        } else if (str.contains(":sybase:")) {
            return DbType.SYBASE;
        } else {
            return DbType.OTHER;
        }
    }

    /**
     * 获取数据库字段类型转换
     */
    @NotNull
    public ITypeConvert getTypeConvert() {
        if (null == typeConvert) {
            DbType dbType = getDbType();
            // 默认 MYSQL
            typeConvert = TypeConverts.getTypeConvert(dbType);
            if (null == typeConvert) {
                typeConvert = MySqlTypeConvert.INSTANCE;
            }
        }
        return typeConvert;
    }

    /**
     * 创建数据库连接对象
     * 这方法建议只调用一次，毕竟只是代码生成，用一个连接就行。
     * @return Connection
     * @see DbQueryDecorator#getConnection()
     */
    @NotNull
    public Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            } else {
                return getConnectionInternal();
            }
        } catch (SQLException e) {
            logger.error("获取数据库连接失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 从数据源获取数据库连接，或者直接进行获取
     * @return Connection
     * @throws SQLException SQLException
     */
    private Connection getConnectionInternal() throws SQLException {
        synchronized (this) {
            if (dataSource != null) {
                this.connection = dataSource.getConnection();
            }
            Properties properties = new Properties();
            connectionProperties.forEach(properties::setProperty);
            properties.put("user", username);
            properties.put("password", password);
            // 使用元数据查询方式时，有些数据库需要增加属性才能读取注释
            this.processProperties(properties);
            logger.info("\n获取数据库连接: {} \nproperties: {}", url, properties);
            this.connection = DriverManager.getConnection(url, properties);
        }
        return this.connection;
    }

    /**
     * 使用元数据查询方式时，有些数据库需要增加属性才能读取注释
     * @param properties 数据库连接配置
     */
    private void processProperties(Properties properties) {
        if (this.databaseQueryClass.getName().equals(DefaultQuery.class.getName())) {
            switch (this.getDbType()) {
                case MYSQL:
                    properties.put("remarks", "true");
                    properties.put("useInformationSchema", "true");
                    break;
                case ORACLE:
                    properties.put("remarks", "true");
                    properties.put("remarksReporting", "true");
                    break;
            }
        }
    }

    /**
     * 获取数据库默认schema
     * @return 默认schema
     * @since 3.5.0
     */
    @Nullable
    protected String getDefaultSchema() {
        DbType dbType = getDbType();
        String schema = null;
        if (DbType.POSTGRE_SQL == dbType) {
            // pg 默认 schema=public
            schema = "public";
        } else if (DbType.KINGBASE_ES == dbType) {
            // kingbase 默认 schema=PUBLIC
            schema = "PUBLIC";
        } else if (DbType.DB2 == dbType) {
            // db2 默认 schema=current schema
            schema = "current schema";
        } else if (DbType.ORACLE == dbType) {
            // oracle 默认 schema=username
            schema = this.username.toUpperCase();
        }
        return schema;
    }

    @Nullable
    public String getSchemaName() {
        return schemaName;
    }

    @Nullable
    public IKeyWordsHandler getKeyWordsHandler() {
        return keyWordsHandler;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    @Nullable
    public String getUsername() {
        return username;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    @NotNull
    public Class<? extends IDatabaseQuery> getDatabaseQueryClass() {
        return databaseQueryClass;
    }

    @Nullable
    public ITypeConvertHandler getTypeConvertHandler() {
        return typeConvertHandler;
    }

    /**
     * 数据库配置构建者
     * @author nieqiurong 2020/10/10.
     * @since 3.5.0
     */
    public static class Builder implements io.devpl.codegen.mbpg.config.Builder<DataSourceConfig> {

        private final DataSourceConfig dataSourceConfig;

        private Builder() {
            this.dataSourceConfig = new DataSourceConfig();
        }

        /**
         * 构造初始化方法
         * @param url      数据库连接地址
         * @param username 数据库账号
         * @param password 数据库密码
         */
        public Builder(@NotNull String url, String username, String password) {
            this();
            if (StringUtils.isBlank(url)) {
                throw new RuntimeException("无法创建文件，请正确输入 url 配置信息！");
            }
            this.dataSourceConfig.url = url;
            this.dataSourceConfig.username = username;
            this.dataSourceConfig.password = password;
        }

        /**
         * 构造初始化方法
         * @param dataSource 外部数据源实例
         */
        public Builder(@NotNull DataSource dataSource) {
            this();
            this.dataSourceConfig.dataSource = dataSource;
            try {
                Connection conn = dataSource.getConnection();
                this.dataSourceConfig.url = conn.getMetaData().getURL();
                try {
                    this.dataSourceConfig.schemaName = conn.getSchema();
                } catch (Throwable exception) {
                    // ignore  如果使用低版本的驱动，这里由于是1.7新增的方法，所以会报错，如果驱动太低，需要自行指定了。
                }
                this.dataSourceConfig.connection = conn;
                this.dataSourceConfig.username = conn.getMetaData().getUserName();
            } catch (SQLException ex) {
                throw new RuntimeException("构建数据库配置对象失败!", ex);
            }
        }

        /**
         * 设置数据库查询实现
         * @param dbQuery 数据库查询实现
         * @return this
         */
        public Builder dbQuery(@NotNull IDbQuery dbQuery) {
            this.dataSourceConfig.dbQuery = dbQuery;
            return this;
        }

        /**
         * 设置数据库schema
         * @param schemaName 数据库schema
         * @return this
         */
        public Builder schema(@NotNull String schemaName) {
            this.dataSourceConfig.schemaName = schemaName;
            return this;
        }

        /**
         * 设置类型转换器
         * @param typeConvert 类型转换器
         * @return this
         */
        public Builder typeConvert(@NotNull ITypeConvert typeConvert) {
            this.dataSourceConfig.typeConvert = typeConvert;
            return this;
        }

        /**
         * 设置数据库关键字处理器
         * @param keyWordsHandler 关键字处理器
         * @return this
         */
        public Builder keyWordsHandler(@NotNull IKeyWordsHandler keyWordsHandler) {
            this.dataSourceConfig.keyWordsHandler = keyWordsHandler;
            return this;
        }

        /**
         * 指定数据库查询方式
         * @param databaseQueryClass 查询类
         * @return this
         * @since 3.5.3
         */
        public Builder databaseQueryClass(@NotNull Class<? extends AbstractDatabaseQuery> databaseQueryClass) {
            this.dataSourceConfig.databaseQueryClass = databaseQueryClass;
            return this;
        }

        /**
         * 指定类型转换器
         * @param typeConvertHandler 类型转换器
         * @return this
         * @since 3.5.3
         */
        public Builder typeConvertHandler(@NotNull ITypeConvertHandler typeConvertHandler) {
            this.dataSourceConfig.typeConvertHandler = typeConvertHandler;
            return this;
        }

        /**
         * 增加数据库连接属性
         * @param key   属性名
         * @param value 属性值
         * @return this
         * @since 3.5.3
         */
        public Builder addConnectionProperty(@NotNull String key, @NotNull String value) {
            this.dataSourceConfig.connectionProperties.put(key, value);
            return this;
        }

        /**
         * 构建数据库配置
         * @return 数据库配置
         */
        @Override
        public DataSourceConfig build() {
            return this.dataSourceConfig;
        }
    }
}
