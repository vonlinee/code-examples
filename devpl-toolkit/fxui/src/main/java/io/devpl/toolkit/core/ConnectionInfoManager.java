package io.devpl.toolkit.core;

import java.util.concurrent.CopyOnWriteArraySet;

public class ConnectionInfoManager {

    private static final CopyOnWriteArraySet<ConnectionInfo>
            registeredConnections = new CopyOnWriteArraySet<>();

    public static void registerConnectionInfo(ConnectionInfo connectionInfo) {
        registeredConnections.add(connectionInfo);
    }

    public static CopyOnWriteArraySet<ConnectionInfo> getRegisteredConnections() {
        return registeredConnections;
    }

    public static void clear() {
        registeredConnections.clear();
    }
}
