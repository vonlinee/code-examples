package io.devpl.tookit.fxui.view.navigation.impl;

import io.devpl.tookit.fxui.model.props.ConnectionInfo;
import io.devpl.tookit.fxui.view.navigation.tree.TreeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * 连接信息单元格
 */
public class ConnectionItem extends TreeModelBase {

    private ConnectionInfo connectionInfo;

    private final List<TreeModel> rawChildren;
    private ObservableList<TreeModel> children;

    public ConnectionItem() {
        rawChildren = new ArrayList<>();
        children = FXCollections.observableList(rawChildren);
    }

    @Override
    public String getDisplayValue() {
        return connectionInfo.getConnectionName();
    }

    @Override
    public void setDispalyValue(String dispalyValue) {

    }

    @Override
    public boolean hasChild() {
        return true;
    }

    @Override
    public List<TreeModel> getChildren() {
        return rawChildren;
    }

    @Override
    public TreeModel getParent() {
        return null;
    }

    @Override
    public <T extends TreeModel> void setParent(T parent) {

    }

    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public ConnectionInfo getConnectionConfig() {
        return connectionInfo;
    }
}
