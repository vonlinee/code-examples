package io.devpl.tookit.fxui.model;

import io.devpl.tookit.utils.AppConfig;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库连接信息注册中心
 */
public class ConnectionRegistry {

    /**
     * 保存已注册的连接配置
     */
    static WeakReference<Map<String, ConnectionInfo>> connConfigRef;

    static {
        loadFromDatabase();
    }

    private static void loadFromDatabase() {
        List<ConnectionInfo> connConfigList = AppConfig.listConnectionInfo();
        Map<String, ConnectionInfo> registeredConnectionConfigMap = new ConcurrentHashMap<>();
        for (ConnectionInfo item : connConfigList) {
            registeredConnectionConfigMap.put(item.getConnectionName(), item);
        }
        connConfigRef = new WeakReference<>(registeredConnectionConfigMap);
    }

    public static boolean contains(String connectionName) {
        return getRegisteredConnectionConfigMap().containsKey(connectionName);
    }

    public static ConnectionInfo getConnectionConfiguration(String connectionName) {
        return getRegisteredConnectionConfigMap().get(connectionName);
    }

    public static Collection<ConnectionInfo> getConnectionConfigurations() {
        return getRegisteredConnectionConfigMap().values();
    }

    public static Map<String, ConnectionInfo> getRegisteredConnectionConfigMap() {
        Map<String, ConnectionInfo> map = connConfigRef.get();
        if (map == null) {
            loadFromDatabase();
        }
        return connConfigRef.get();
    }
}
