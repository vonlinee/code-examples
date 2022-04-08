package org.setamv.shardingsphere.sample.dynamic.config;

/**
 * 动态数据源路由上下文。用于保存当前线程设置的数据源名称。如果未设置，将使用默认数据源。
 * <p>可选的数据源名称包括：<ul>
 *     <li>{@link DataSourceNames#SCM_DATA_SOURCE}</li>
 *     <li>{@link DataSourceNames#SHARDING_DATA_SOURCE}</li>
 * </ul>
 * @author setamv
 * @date 2021-04-16
 */
public class DynamicRouteDataSourceContext {

    /**
     * 持有当前线程上下文中使用的数据源名称
     */
    private static final ThreadLocal<String> DATA_SOURCE_NAME_HOLDER = new ThreadLocal<>();

    private DynamicRouteDataSourceContext() {

    }

    public static void setDataSource(String dataSourceName) {
        DATA_SOURCE_NAME_HOLDER.set(dataSourceName);
    }

    public static String getDataSource() {
        return DATA_SOURCE_NAME_HOLDER.get();
    }

    public static void clearDataSource() {
        DATA_SOURCE_NAME_HOLDER.remove();
    }
}
