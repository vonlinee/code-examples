package io.devpl.spring.data.jdbc;

import io.devpl.spring.utils.SpringUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源注册中心，启动时进行注入数据源
 * 优先级比较高
 *
 */
public class DataSourceRegistry implements DataSourceLookup, InitializingBean {

    /**
     * 存储数据源的元数据信息
     */
    private final Map<String, DataSourceProperties> dataSourceMetadatas = new HashMap<>(4);

    /**
     * 存放数据源实例
     */
    private final Map<String, DataSource> dataSources = new HashMap<>(4);

    public DataSourceRegistry() {
        this(new HashMap<>());
    }

    public DataSourceRegistry(Map<String, DataSource> dataSources) {
        setDataSources(dataSources);
    }

    public DataSourceRegistry(String dataSourceName, DataSource dataSource) {
        addDataSource(dataSourceName, dataSource);
    }

    /**
     * Set the {@link Map} of {@link DataSource DataSources}; the keys
     * are {@link String Strings}, the values are actual {@link DataSource} instances.
     * <p>If the supplied {@link Map} is {@code null}, then this method
     * call effectively has no effect.
     *
     * @param dataSources said {@link Map} of {@link DataSource DataSources}
     */
    public void setDataSources(@Nullable Map<String, DataSource> dataSources) {
        if (dataSources != null) {
            this.dataSources.putAll(dataSources);
        }
    }

    /**
     * Get the {@link Map} of {@link DataSource DataSources} maintained by this object.
     * <p>The returned {@link Map} is {@link Collections#unmodifiableMap(Map) unmodifiable}.
     *
     * @return said {@link Map} of {@link DataSource DataSources} (never {@code null})
     */
    public Map<String, DataSource> getDataSources() {
        return Collections.unmodifiableMap(this.dataSources);
    }

    /**
     * Add the supplied {@link DataSource} to the map of {@link DataSource DataSources}
     * maintained by this object.
     *
     * @param dataSourceName the name under which the supplied {@link DataSource} is to be added
     * @param dataSource     the {@link DataSource} to be so added
     */
    public void addDataSource(String dataSourceName, DataSource dataSource) {
        Assert.notNull(dataSourceName, "DataSource name must not be null");
        Assert.notNull(dataSource, "DataSource must not be null");
        this.dataSources.put(dataSourceName, dataSource);
    }

    @Override
    public DataSource getDataSource(String dataSourceName) throws DataSourceLookupFailureException {
        Assert.notNull(dataSourceName, "DataSource name must not be null");
        DataSource dataSource = this.dataSources.get(dataSourceName);
        if (dataSource == null) {
            throw new DataSourceLookupFailureException(
                    "No DataSource with name '" + dataSourceName + "' registered");
        }
        return dataSource;
    }

    public int size() {
        return dataSources.size();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            Map<String, DataSourceProperties> dataSourcePropertiesMap = SpringUtils.getBeansOfType(DataSourceProperties.class);
            this.dataSourceMetadatas.putAll(dataSourcePropertiesMap);
            dataSourcePropertiesMap.forEach((name, props) -> {
                DataSource dataSource = props.initializeDataSourceBuilder().build();
                this.dataSources.put(name, dataSource);
            });
        } catch (BeanCreationException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public DataSourceProperties getMetaInfomation(String dataSourceName) {
        return dataSourceMetadatas.get(dataSourceName);
    }
}
