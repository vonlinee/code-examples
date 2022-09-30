package io.devpl.spring.data.jdbc;

import lombok.Data;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class DataSourceManager {

    private Map<String, DataSourceInformation> informations = new ConcurrentHashMap<>();

    public void registerDataSource(String name, DataSourceInformation information) {
        if (!informations.containsKey(name)) {
            informations.put(name, information);
        }
    }
}
