package io.maker.extension.mybatis.internal;

import org.apache.ibatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 动态切换数据源 org.apache.ibatis.datasource.jndi.JndiDataSourceFactory
 */
public class DynamicDataSourceFactory implements DataSourceFactory {

    private Properties properties = null;

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public DataSource getDataSource() {
        return null;
    }
}
