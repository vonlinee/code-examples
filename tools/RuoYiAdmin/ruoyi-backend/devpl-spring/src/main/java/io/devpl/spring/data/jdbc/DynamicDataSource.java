package io.devpl.spring.data.jdbc;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * 动态数据源
 * 1.动态增加移除数据源
 * 2.自定义数据源来源，即数据库连接信息来源这些配置信息
 * 3.动态切换数据源
 * 4.本地多数据源事务管理
 * 5.支持无数据源启动，支持懒加载数据源（需要的时候再创建连接）
 * 6.数据源分组管理
 */
public abstract class DynamicDataSource extends AbstractRoutingDataSource {

    private Map<String, DataSourceProperties> metadata;

    private Object lock = new Object();

    protected DataSourceProvider provider;

    /**
     * 刷新已经有的数据源信息
     */
    public abstract void refresh();
}
