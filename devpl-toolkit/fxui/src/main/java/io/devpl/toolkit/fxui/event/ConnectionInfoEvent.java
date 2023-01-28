package io.devpl.toolkit.fxui.event;

import io.devpl.toolkit.fxui.model.props.ConnectionInfo;

public class ConnectionInfoEvent {

    private final ConnectionInfo connectionInfo;

    public ConnectionInfoEvent(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }
}
