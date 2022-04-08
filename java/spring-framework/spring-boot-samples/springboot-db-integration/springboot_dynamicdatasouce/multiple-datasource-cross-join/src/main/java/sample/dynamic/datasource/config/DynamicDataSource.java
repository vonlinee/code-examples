package sample.dynamic.datasource.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

@Component
@Primary // 将该Bean设置为主要注入Bean
public class DynamicDataSource extends AbstractRoutingDataSource {

	public static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    // 写
    @Autowired(required=true)
    @Qualifier("ordercenterDataSource")
    private DataSource ordercenterDataSource;
    // 读
    @Autowired(required=true)
    @Qualifier("businessDataSource")
    private DataSource businessDataSource;

    // 返回当前数据源标识
    @Override
    protected Object determineCurrentLookupKey() {
        return "ds_" + DataSourceDecision.getDatabaseId();
    }

    @Override
    public void afterPropertiesSet() {
        // 为targetDataSources初始化所有数据源
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("ds_orc", ordercenterDataSource);
        targetDataSources.put("ds_business", businessDataSource);
        super.setTargetDataSources(targetDataSources);
        // 为defaultTargetDataSource 设置默认的数据源
        super.setDefaultTargetDataSource(ordercenterDataSource);
        super.afterPropertiesSet();
        logger.info("dataSource {}", ordercenterDataSource);
        logger.info("{}", businessDataSource);
    }
}
