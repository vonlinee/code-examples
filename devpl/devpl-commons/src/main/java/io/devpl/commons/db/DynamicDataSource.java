package io.devpl.commons.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public final class DynamicDataSource extends AbstractRoutingDataSource {

	private String key;
	
    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
