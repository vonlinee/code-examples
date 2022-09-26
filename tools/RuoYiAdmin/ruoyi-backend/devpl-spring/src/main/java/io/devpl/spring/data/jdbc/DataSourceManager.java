package io.devpl.spring.data.jdbc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源注册中心，保存数据库的元数据信息
 * @see RoutingDynamicDataSource
 */
public class DataSourceManager {

    /**
     * 存储数据源的元数据信息，根据这些信息来创建数据源实例
     */
    private final Map<String, DataSourceInformation> metadata = new ConcurrentHashMap<>(2);

    public DataSourceInformation getDataSourceInformation(String dataSourceName) {
        return metadata.get(dataSourceName);
    }

    public Map<String, DataSourceInformation> getDataSourceInformations() {
        return this.metadata;
    }
}
