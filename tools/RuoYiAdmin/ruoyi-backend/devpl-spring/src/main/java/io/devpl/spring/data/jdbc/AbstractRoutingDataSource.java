package io.devpl.spring.data.jdbc;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态路由数据源
 */
public abstract class AbstractRoutingDataSource extends AbstractDataSource {

    /**
     * 数据源的信息
     */
    private final Map<String, DataSourceInformation> dataSourceInformationMap = new ConcurrentHashMap<>();

    @Nullable
    private DataSource defaultTargetDataSource;

    private boolean lenientFallback = true;

    private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();

    private final Map<String, DataSource> resolvedDataSources = new ConcurrentHashMap<>(2);

    @Nullable
    private DataSource resolvedDefaultDataSource;

    public void setLenientFallback(boolean lenientFallback) {
        this.lenientFallback = lenientFallback;
    }

    public void setDataSourceLookup(@Nullable DataSourceLookup dataSourceLookup) {
        this.dataSourceLookup = (dataSourceLookup != null ? dataSourceLookup : new JndiDataSourceLookup());
    }

    public void refresh() {
        resolvedDataSources.clear();
        this.dataSourceInformationMap.forEach((key, value) -> {
            DataSourceBuilder<?> builder = value.initializeDataSourceBuilder();
            try {
                resolvedDataSources.put(key, builder.build());
            } catch (Exception exception) {
                logger.error("failed to initialize datasource " + value, exception);
            }
        });
    }

    @Nullable
    public DataSource getResolvedDefaultDataSource() {
        return this.resolvedDefaultDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return determineTargetDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface));
    }

    protected DataSource determineTargetDataSource() {
        Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
        String lookupKey = determineCurrentLookupKey();

        if (lookupKey == null) {
            // 选择默认数据源
            return defaultTargetDataSource;
        }

        DataSource dataSource = this.resolvedDataSources.get(lookupKey);
        if (dataSource == null && this.lenientFallback) {
            dataSource = this.resolvedDefaultDataSource;
        }
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }

    protected abstract String determineCurrentLookupKey();

    /**
     * 添加数据源
     * @param name
     * @param information
     */
    public void addDataSource(String name, DataSourceInformation information) {
        this.dataSourceInformationMap.put(name, information);
        DataSource dataSource = information.initializeDataSourceBuilder().build();
        if (information.isDefault()) {
            this.defaultTargetDataSource = dataSource;
        }
        this.resolvedDataSources.put(name, dataSource);
    }

    public void removeDataSource(String name) {
        this.dataSourceInformationMap.remove(name);
        this.resolvedDataSources.remove(name);
    }
}
