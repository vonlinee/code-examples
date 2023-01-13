package io.devpl.toolkit.fxui.view.navigation.impl;

import javafx.scene.control.TreeItem;

import java.util.Collections;
import java.util.List;

public class ColumnItem implements NavigationItem {

    private String columnName;

    @Override
    public String getDispalyValue() {
        return columnName;
    }

    @Override
    public void setDispalyValue(String dispalyValue) {
        columnName = dispalyValue;
    }

    @Override
    public boolean hasChild() {
        return false;
    }

    @Override
    public List<NavigationItem> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public void attach(TreeItem<NavigationItem> parent) {

    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
