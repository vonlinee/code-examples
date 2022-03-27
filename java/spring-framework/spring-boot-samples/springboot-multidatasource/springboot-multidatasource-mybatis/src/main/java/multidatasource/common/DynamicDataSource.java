package multidatasource.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.BeanFactoryDataSourceLookup;

import java.sql.SQLException;

/**
 * 动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource() {
    }

    private void init() {
        try {
            setLoginTimeout(3000);
            setDataSourceLookup(new BeanFactoryDataSourceLookup());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
