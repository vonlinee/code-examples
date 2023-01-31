package io.devpl.toolkit.fxui.view.navigation.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DatabaseItem extends TreeModelBase  {

    private String databaseName;

    List<TableItem> rawTableItems;
    ObservableList<TableItem> tableItems;

    ConnectionItem parent;

    public DatabaseItem() {
        rawTableItems = new ArrayList<>();
        tableItems = FXCollections.observableList(rawTableItems);
    }

    @Override
    public String getDisplayValue() {
        return databaseName;
    }

    @Override
    public void setDispalyValue(String dispalyValue) {
        this.databaseName = dispalyValue;
    }

    @Override
    public boolean hasChild() {
        return tableItems.isEmpty();
    }

    @Override
    public List<TableItem> getChildren() {
        return tableItems;
    }

    @Override
    public TreeModel getParent() {
        return parent;
    }

    @Override
    public <T extends TreeModel> void setParent(T parent) {

    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void addTable(TableItem tableItem) {
        rawTableItems.add(tableItem);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DatabaseItem) {
            return Objects.equals(this.databaseName, ((DatabaseItem) obj).databaseName);
        }
        return false;
    }
}
