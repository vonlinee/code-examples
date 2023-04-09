package io.devpl.tookit.fxui.model;

import io.devpl.tookit.utils.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库连接信息注册中心
 */
public class ConnectionRegistry {

    static final Logger logger = LoggerFactory.getLogger(ConnectionRegistry.class);

    /**
     * 保存已注册的连接配置
     */
    static WeakReference<Map<String, ConnectionConfig>> connConfigRef;

    static {
        loadFromDatabase();
    }

    private static void loadFromDatabase() {
        List<ConnectionConfig> connConfigList = AppConfig.listConnectionInfo();
        Map<String, ConnectionConfig> registeredConnectionConfigMap = new ConcurrentHashMap<>();
        for (ConnectionConfig item : connConfigList) {
            registeredConnectionConfigMap.put(item.getConnectionName(), item);
        }
        connConfigRef = new WeakReference<>(registeredConnectionConfigMap);
    }

    public static boolean contains(String connectionName) {
        return getRegisteredConnectionConfigMap().containsKey(connectionName);
    }

    /**
     * 根据连接名称获取连接配置
     *
     * @param connectionName 连接名称
     * @return 连接配置
     */
    public static ConnectionConfig get(String connectionName) {
        return getRegisteredConnectionConfigMap().get(connectionName);
    }

    public static Collection<ConnectionConfig> getConnectionConfigurations() {
        return getRegisteredConnectionConfigMap().values();
    }

    /**
     * 同步方法
     *
     * @return key为连接名，value为对应的连接信息
     */
    public static synchronized Map<String, ConnectionConfig> getRegisteredConnectionConfigMap() {
        Map<String, ConnectionConfig> map = connConfigRef.get();
        if (map == null) {
            logger.info("reload ConnectionInfo from database");
            loadFromDatabase();
        }
        return connConfigRef.get();
    }
}
