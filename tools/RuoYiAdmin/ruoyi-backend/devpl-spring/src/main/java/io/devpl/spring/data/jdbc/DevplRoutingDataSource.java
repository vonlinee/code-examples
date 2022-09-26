package io.devpl.spring.data.jdbc;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 根据预先提供的数据源名称动态路由数据源
 */
public class DevplRoutingDataSource extends AbstractRoutingDataSource {

    /**
     * 当前线程锁定的数据源标识
     */
    private final ThreadLocal<String> currentDataSource = new NamedInheritableThreadLocal<>("dynamic-data-source");

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
