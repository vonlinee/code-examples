package io.devpl.spring.data.jdbc;

import lombok.Data;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="https://blog.csdn.net/band_mmbx/article/details/126904989">关于SpringBoot的多数据源以及事务解决方案</a>
 */
@Data
public class DataSourceManager {

    private Map<String, DataSourceInformation> informations = new ConcurrentHashMap<>();

    /**
     * 注册数据源
     * @param name
     * @param information
     */
    public void registerDataSource(String name, DataSourceInformation information) {
        Assert.hasLength(name, "the name of datasource must be meaningful!");
        if (!informations.containsKey(name)) {
            informations.put(name, information);
        }
    }

    public void removeDataSource(String name) {
        informations.remove(name);
    }
}
