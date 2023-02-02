package io.devpl.toolkit.fxui.model;

import io.devpl.toolkit.fxui.dao.ConnectionConfigurationDao;
import io.devpl.toolkit.fxui.model.props.ConnectionConfig;

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
    static WeakReference<Map<String, ConnectionConfig>> connConfigRef;

    static {
        ConnectionConfigurationDao dao = new ConnectionConfigurationDao();
        List<ConnectionConfig> connConfigList = dao.selectList();
        Map<String, ConnectionConfig> registeredConnectionConfigMap = new ConcurrentHashMap<>();
        for (ConnectionConfig item : connConfigList) {
            System.out.println(item.getConnectionName());
            registeredConnectionConfigMap.put(item.getConnectionName(), item);
        }
        connConfigRef = new WeakReference<>(registeredConnectionConfigMap);
    }

    private static void loadFromDatabase() {
        ConnectionConfigurationDao dao = new ConnectionConfigurationDao();
        List<ConnectionConfig> connConfigList = dao.selectList();
        Map<String, ConnectionConfig> registeredConnectionConfigMap = new ConcurrentHashMap<>();
        for (ConnectionConfig item : connConfigList) {
            registeredConnectionConfigMap.put(item.getConnectionName(), item);
        }
        connConfigRef = new WeakReference<>(registeredConnectionConfigMap);
    }

    public static boolean contains(String connectionName) {
        return getRegisteredConnectionConfigMap().containsKey(connectionName);
    }

    public static ConnectionConfig getConnectionConfiguration(String connectionName) {
        return getRegisteredConnectionConfigMap().get(connectionName);
    }

    public static Collection<ConnectionConfig> getConnectionConfigurations() {
        return getRegisteredConnectionConfigMap().values();
    }

    public static Map<String, ConnectionConfig> getRegisteredConnectionConfigMap() {
        Map<String, ConnectionConfig> map = connConfigRef.get();
        if (map == null) {
            loadFromDatabase();
        }
        return connConfigRef.get();
    }
}
