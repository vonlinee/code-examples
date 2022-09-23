package io.devpl.spring.data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 存放所有的数据源信息
 */
public class DataSourceRegistry {

    private final Map<String, DataSourceProperties> dataSourceMetaInfoMap = new LinkedHashMap<>();
}
