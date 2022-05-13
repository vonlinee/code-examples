package io.maker.common.spring.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    private DataSource createDataSource(Properties props) {
        DataSourceType[] dataSourceTypes = DataSourceType.values();
        DataSource dataSource = null;
        for (DataSourceType type : dataSourceTypes) {
            try {
                Class<?> dataSourceClass = Class.forName(type.name);
                dataSource = (DataSource) dataSourceClass.newInstance();
                break;
            } catch (ClassNotFoundException e) {
                log.info(type.getName() + " does not exists in classpath!");
            } catch (InstantiationException e) {
                log.info(type.getName() + " cannot be instantiated");
            } catch (IllegalAccessException e) {
                log.info(type.getName() + " IllegalAccessException");
            }
        }
        buildDataSource(dataSource, props);
        return null;
    }

    private void buildDataSource(DataSource dataSource, Properties props) {

    }
}
