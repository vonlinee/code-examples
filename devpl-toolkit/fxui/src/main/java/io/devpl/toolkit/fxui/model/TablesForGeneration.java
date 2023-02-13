package io.devpl.toolkit.fxui.model;

import java.util.HashMap;
import java.util.Map;

public class TablesForGeneration {

    /**
     * 第一层Key：ConnectionConfig#getUniqueKey
     * 第二层Key：数据库名称
     */
    private final Map<String, Map<String, TableCodeGenConfig>> tables = new HashMap<>();

    public void addTable(String connectionKey, String databaseName, TableCodeGenConfig config) {
        Map<String, TableCodeGenConfig> map = tables.get(connectionKey);
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(databaseName, config);
    }

    public Map<String, Map<String, TableCodeGenConfig>> getTables() {
        return tables;
    }
}