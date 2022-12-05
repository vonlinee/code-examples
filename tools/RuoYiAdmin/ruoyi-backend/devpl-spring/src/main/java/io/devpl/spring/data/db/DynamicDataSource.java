package io.devpl.spring.data.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;

import javax.sql.DataSource;
import java.util.Properties;

public class DynamicDataSource extends AbstractRoutingDataSource {

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
	
    private DataSource createDataSource(Properties props) {
        DataSourceType[] dataSourceTypes = DataSourceType.values();
        DataSource dataSource = null;
        for (DataSourceType type : dataSourceTypes) {
            try {
                Class<?> dataSourceClass = Class.forName(type.getClassName());
                dataSource = (DataSource) dataSourceClass.newInstance();
                break;
            } catch (ClassNotFoundException e) {
                log.info(type.getClassName() + " does not exists in classpath!");
            } catch (InstantiationException e) {
                log.info(type.getClassName() + " cannot be instantiated");
            } catch (IllegalAccessException e) {
                log.info(type.getClassName() + " IllegalAccessException");
            }
        }
        buildDataSource(dataSource, props);
        return null;
    }

    private void buildDataSource(DataSource dataSource, Properties props) {

    }
}
