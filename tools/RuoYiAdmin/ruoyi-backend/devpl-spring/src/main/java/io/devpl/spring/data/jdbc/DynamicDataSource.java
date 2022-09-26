package io.devpl.spring.data.jdbc;

import org.springframework.core.NamedInheritableThreadLocal;

/**
 * 数据源注册中心，保存数据库的元数据信息
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> CURRENT = new NamedInheritableThreadLocal<>("thread-data-source");

    @Override
    protected String determineCurrentLookupKey() {
        return CURRENT.get();
    }
}
