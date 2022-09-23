package io.devpl.spring.data.jdbc.ext;

import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 动态数据源
 */
@DependsOn(value = {
        "io.devpl.commons.db.jdbc.DataSourceProperties"
})
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    public void setOptionalDataSources(Map<String, DataSource> dataSources) {
        // 泛型丢失了，不用强转
        Map<Object, Object> optinalDataSources = new LinkedHashMap<>(dataSources);
        super.setTargetDataSources(optinalDataSources);
    }
}
