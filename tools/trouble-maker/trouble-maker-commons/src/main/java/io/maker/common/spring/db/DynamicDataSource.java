package io.maker.common.spring.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;

public final class DynamicDataSource extends AbstractRoutingDataSource {
	
	private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);
	
    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

	@Override
	public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
		super.setDataSourceLookup(dataSourceLookup);
	}

	@Override
	public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
		if (log.isDebugEnabled()) {
			log.debug("DefaultTargetDataSource => {}", defaultTargetDataSource);
		}
		super.setDefaultTargetDataSource(defaultTargetDataSource);
	}
}
