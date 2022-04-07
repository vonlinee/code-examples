package sample.dynamic.datasource.common;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

@Component
public class DynamicDataSource extends AbstractRoutingDataSource implements InitializingBean {

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    @Override
    public void afterPropertiesSet() {
        // 初始化所有数据源

        super.afterPropertiesSet();
    }
}
