package io.devpl.spring.data.jdbc;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 多数据源加载接口，默认的实现为从yml信息中加载所有数据源 你可以自己实现从其他地方加载所有数据源
 */
public interface DataSourceProvider {

    /**
     * 提供数据源
     * 可以为空，也可以只有1个，如果有的话必须保证key和value都不为null，同时key必须是有意义的名称
     * @return 所有数据源，key为数据源唯一名称，value为DataSource实例
     */
    Map<String, DataSource> provide();
}