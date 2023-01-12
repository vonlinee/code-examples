package io.devpl.toolkit.fxui.model;

import io.devpl.toolkit.fxui.common.DynamicPropertyBean;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 连接信息
 */
public class ConnectionInfo {

    // 连接名称
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty dbType = new SimpleStringProperty();
    private final StringProperty host = new SimpleStringProperty();
    private final IntegerProperty port = new SimpleIntegerProperty(3306);
    private final StringProperty schema = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty encoding = new SimpleStringProperty();

    // 基本信息
    DynamicPropertyBean commonInfo;
}
