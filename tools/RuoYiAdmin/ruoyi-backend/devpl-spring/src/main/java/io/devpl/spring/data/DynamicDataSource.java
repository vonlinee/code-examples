package io.devpl.spring.data;

import javax.sql.DataSource;
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
public interface DynamicDataSource extends DataSource {

    /**
     * 获取数据源
     *
     * @return 数据源实例，不能为空
     */
    DataSource getDataSource(String name);

    void addDataSource(String name, DataSource dataSource);

    void removeDataSource(String name);

    String[] getDataSourceNames();

    Map<String, DataSource> getDataSources();

    /**
     * 刷新已经有的数据源信息
     */
    void refresh();
}
