package io.devpl.toolkit.fxui.model;

import io.devpl.toolkit.fxui.model.props.ConnectionInfo;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库连接信息注册中心
 */
public class ConnectionRegistry {

    public static void registerConnection(ConnectionInfo config) {
        registeredConnectionConfigMap.put(config.getName(), config);
    }

    /**
     * 保存已注册的连接配置
     */
    private static final Map<String, ConnectionInfo>
            registeredConnectionConfigMap = new ConcurrentHashMap<>();

    public static boolean contains(String connectionName) {
        return registeredConnectionConfigMap.containsKey(connectionName);
    }

    public static ConnectionInfo getConnectionConfiguration(String connectionName) {
        return registeredConnectionConfigMap.get(connectionName);
    }

    public static Collection<ConnectionInfo> getConnectionConfigurations() {
        return registeredConnectionConfigMap.values();
    }
}
