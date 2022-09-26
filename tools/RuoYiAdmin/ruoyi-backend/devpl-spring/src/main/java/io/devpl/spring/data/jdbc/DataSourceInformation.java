package io.devpl.spring.data.jdbc;

import lombok.Data;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

/**
 * 配置一个数据源的所有配置项
 * @see org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
 */
@Data
public class DataSourceInformation implements Serializable {

    private boolean isDefault;

    /**
     * 是否只读
     */
    private boolean readOnly;

    /**
     * 数据源唯一名称
     */
    private String name;

    /**
     * 是否随机生成数据库名称
     * @see DataSourceInformation#name
     */
    private boolean generateUniqueName = true;

    /**
     * 连接池实现类的全限定类名，默认情况下有框架从类路径下自动探测
     */
    private Class<? extends DataSource> type;

    /**
     * 驱动类的全限定类名
     * Fully qualified name of the JDBC driver. Auto-detected based on the URL by
     * default.
     */
    private String driverClassName;

    /**
     * JDBC URL of the database.
     */
    private String url;

    /**
     * Login username of the database.
     */
    private String username;

    /**
     * Login password of the database.
     */
    private String password;

    /**
     * JNDI location of the datasource. Class, url, username and password are
     * ignored when set.
     */
    private String jndiName;

    /**
     * Mode to apply when determining if DataSource initialization should be
     * performed using the available DDL and DML scripts.
     */
    private DataSourceInitializationMode initializationMode = DataSourceInitializationMode.EMBEDDED;

    /**
     * Platform to use in the DDL or DML scripts (such as schema-${platform}.sql or
     * data-${platform}.sql).
     */
    private String platform = "all";

    /**
     * Schema (DDL) script resource references.
     */
    private List<String> schema;

    /**
     * Username of the database to execute DDL scripts (if different).
     */
    private String schemaUsername;

    /**
     * Password of the database to execute DDL scripts (if different).
     */
    private String schemaPassword;

    /**
     * Data (DML) script resource references.
     */
    private List<String> data;

    /**
     * Username of the database to execute DML scripts (if different).
     */
    private String dataUsername;

    /**
     * Password of the database to execute DML scripts (if different).
     */
    private String dataPassword;

    /**
     * Whether to stop if an error occurs while initializing the database.
     */
    private boolean continueOnError = false;

    /**
     * Statement separator in SQL initialization scripts.
     */
    private String separator = ";";

    /**
     * SQL scripts encoding.
     */
    private Charset sqlScriptEncoding;

    private EmbeddedDatabaseConnection embeddedDatabaseConnection = EmbeddedDatabaseConnection.NONE;

    /**
     * 唯一名称
     */
    private String uniqueName;

    /**
     * Initialize a {@link DataSourceBuilder} with the state of this instance.
     * @return a {@link DataSourceBuilder} initialized with the customizations
     * defined on this instance
     */
    public DataSourceBuilder<?> initializeDataSourceBuilder() {
        return DataSourceBuilder.create(this.getClass().getClassLoader())
                .type(getType())
                .driverClassName(determineDriverClassName())
                .url(determineUrl())
                .username(determineUsername())
                .password(determinePassword());
    }

    /**
     * 根据此配置和环境确定要使用的驱动程序
     * @return the driver to use
     * @since 1.4.0
     */
    public String determineDriverClassName() {
        if (StringUtils.hasText(this.driverClassName)) {
            Assert.state(driverClassIsLoadable(), () -> "Cannot load driver class: " + this.driverClassName);
            return this.driverClassName;
        }
        String driverClassName = null;
        if (StringUtils.hasText(this.url)) {
            driverClassName = DatabaseDriver.fromJdbcUrl(this.url).getDriverClassName();
        }
        if (!StringUtils.hasText(driverClassName)) {
            driverClassName = this.embeddedDatabaseConnection.getDriverClassName();
        }
        if (!StringUtils.hasText(driverClassName)) {
            throw new BeanCreationException("Failed to determine a suitable driver class");
        }
        return driverClassName;
    }

    private boolean driverClassIsLoadable() {
        try {
            ClassUtils.forName(this.driverClassName, null);
            return true;
        } catch (UnsupportedClassVersionError ex) {
            // Driver library has been compiled with a later JDK, propagate error
            throw ex;
        } catch (Throwable ex) {
            return false;
        }
    }

    /**
     * Determine the url to use based on this configuration and the environment.
     * @return the url to use
     * @since 1.4.0
     */
    public String determineUrl() {
        if (StringUtils.hasText(this.url)) {
            return this.url;
        }
        String databaseName = determineDatabaseName();
        String url = (databaseName != null) ? this.embeddedDatabaseConnection.getUrl(databaseName) : null;
        if (!StringUtils.hasText(url)) {
            throw new BeanCreationException("Failed to determine suitable jdbc url");
        }
        return url;
    }

    /**
     * Determine the name to used based on this configuration.
     * @return the database name to use or {@code null}
     * @since 2.0.0
     */
    public String determineDatabaseName() {
        if (this.generateUniqueName) {
            if (this.uniqueName == null) {
                this.uniqueName = UUID.randomUUID().toString();
            }
            return this.uniqueName;
        }
        if (StringUtils.hasLength(this.name)) {
            return this.name;
        }
        if (this.embeddedDatabaseConnection != EmbeddedDatabaseConnection.NONE) {
            return "testdb";
        }
        return null;
    }

    /**
     * Return the configured username or {@code null} if none was configured.
     * @return the configured username
     * @see #determineUsername()
     */
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Determine the username to use based on this configuration and the
     * environment.
     * @return the username to use
     * @since 1.4.0
     */
    public String determineUsername() {
        if (StringUtils.hasText(this.username)) {
            return this.username;
        }
        if (EmbeddedDatabaseConnection.isEmbedded(determineDriverClassName())) {
            return "sa";
        }
        return null;
    }

    /**
     * Return the configured password or {@code null} if none was configured.
     * @return the configured password
     * @see #determinePassword()
     */
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Determine the password to use based on this configuration and the
     * environment.
     * @return the password to use
     * @since 1.4.0
     */
    public String determinePassword() {
        if (StringUtils.hasText(this.password)) {
            return this.password;
        }
        if (EmbeddedDatabaseConnection.isEmbedded(determineDriverClassName())) {
            return "";
        }
        return "";
    }
}
