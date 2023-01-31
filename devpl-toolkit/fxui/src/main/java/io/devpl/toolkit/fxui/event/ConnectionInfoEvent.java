package io.devpl.toolkit.fxui.event;

import io.devpl.toolkit.fxui.model.props.ConnectionConfig;

public class ConnectionInfoEvent {

    private final ConnectionConfig connectionInfo;

    public ConnectionInfoEvent(ConnectionConfig connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public ConnectionConfig getConnectionInfo() {
        return connectionInfo;
    }
}
