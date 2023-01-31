package io.devpl.toolkit.fxui.view.navigation.impl;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableItem extends TreeModelBase {

    private String tableName;

    private final ObservableList<ColumnItem> children;
    private final List<ColumnItem> rawChildren;

    DatabaseItem databaseItem;

    public TableItem() {
        rawChildren = new ArrayList<>();
        children = FXCollections.observableList(rawChildren);
    }

    @Override
    public String getDisplayValue() {
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

    @Override
    public TreeModel getParent() {
        return databaseItem;
    }

    @Override
    public <T extends TreeModel> void setParent(T parent) {

    }
}
