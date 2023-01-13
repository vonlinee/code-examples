package io.devpl.toolkit.fxui.view.navigation.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TableItem implements NavigationItem {

    private String tableName;

    private final ObservableList<ColumnItem> children;
    private final List<ColumnItem> rawChildren;

    public TableItem() {
        rawChildren = new ArrayList<>();
        children = FXCollections.observableList(rawChildren);
    }

    @Override
    public String getDispalyValue() {
        return tableName;
    }

    @Override
    public void setDispalyValue(String dispalyValue) {
        this.tableName = dispalyValue;
    }

    @Override
    public boolean hasChild() {
        return rawChildren.isEmpty();
    }

    @Override
    public List<ColumnItem> getChildren() {
        return rawChildren;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void addColumn(ColumnItem columnItem) {
        rawChildren.add(columnItem);
    }
}
