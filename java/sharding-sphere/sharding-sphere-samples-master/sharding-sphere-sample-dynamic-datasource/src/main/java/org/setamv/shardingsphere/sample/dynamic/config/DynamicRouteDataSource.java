package org.setamv.shardingsphere.sample.dynamic.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态路由数据源。
 * <p>动态路由数据源将根据当前线程上线文中设置的数据源名称决定使用对应的数据源。
 * <p>如果当前上下文未设置数据源名称，将使用默认的数据源。默认的数据源通过{@link DynamicRouteDataSource#setDefaultTargetDataSource(Object)}设置。
 * <p>参考{@link DynamicDataSourceConfig#dynamicRouteDataSource}的配置
 * @author setamv
 * @date 2021-04-16
 */
public class DynamicRouteDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicRouteDataSourceContext.getDataSource();
    }
}
