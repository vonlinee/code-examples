package io.devpl.spring.data.jdbc;

import org.springframework.core.NamedInheritableThreadLocal;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> CURRENT = new NamedInheritableThreadLocal<>("thread-data-source");

    @Override
    protected String determineCurrentLookupKey() {
        return CURRENT.get() == null ? "orc" : CURRENT.get();
    }
}
