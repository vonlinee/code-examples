package com.mzd.multipledatasources.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		DataSourceType.DataBaseType dataBaseType = DataSourceType.getDataBaseType();
		return dataBaseType;
	}

}

