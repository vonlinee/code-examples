package io.devpl.sdk.support.spring.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public final class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
