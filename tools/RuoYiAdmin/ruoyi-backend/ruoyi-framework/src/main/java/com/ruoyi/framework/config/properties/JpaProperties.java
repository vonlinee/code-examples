package com.ruoyi.framework.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "spring.jpa")
public class JpaProperties {

    private final Map<String, String> properties = new HashMap<>();
    private final List<String> mappingResources = new ArrayList<>();
    private String databasePlatform;
    // 要操作的目标数据库，默认自动检测。也可以使用“databasePlatform”属性进行设置。
    private Database database;
    // 是否在启动时初始化 schema
    private boolean generateDdl = true;
    // 是否开启打印 sql 语句，这个在测试时非常常见
    private boolean showSql = false;
    // 用于决定是否注册 OpenEntityManagerInViewInterceptor，它会一个请求线程绑定一个JPA EntityManager
    private boolean openInView;
}