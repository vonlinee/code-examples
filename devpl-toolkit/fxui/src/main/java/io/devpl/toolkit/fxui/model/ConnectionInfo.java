package io.devpl.toolkit.fxui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 连接信息
 */
public class ConnectionInfo {

    // 连接名称
    private final StringProperty name = new SimpleStringProperty();

    private Integer id;
    private String dbType;
    private String host;
    private String port;
    private String schema;
    private String username;
    private String password;
    private String encoding;
}
