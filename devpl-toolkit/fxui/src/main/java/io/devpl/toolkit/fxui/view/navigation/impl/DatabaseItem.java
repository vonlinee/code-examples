package io.devpl.toolkit.fxui.view.navigation.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class DatabaseItem implements NavigationItem {

    private String databaseName;

    List<TableItem> rawTableItems;
    ObservableList<TableItem> tableItems;

    public DatabaseItem() {
        rawTableItems = new ArrayList<>();
        tableItems = FXCollections.observableList(rawTableItems);
    }

    @Override
    public String getDispalyValue() {
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

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void addTable(TableItem tableItem) {
        rawTableItems.add(tableItem);
    }
}
