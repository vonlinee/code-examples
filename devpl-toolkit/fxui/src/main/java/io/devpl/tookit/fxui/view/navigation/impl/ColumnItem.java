package io.devpl.tookit.fxui.view.navigation.impl;

import io.devpl.tookit.fxui.view.navigation.tree.TreeModel;

import java.util.Collections;
import java.util.List;

public class ColumnItem extends TreeModelBase {

    private String columnName;

    DatabaseItem parent;

    @Override
    public String getDisplayValue() {
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
    public List<TreeModel> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public TreeModel getParent() {
        return parent;
    }

    @Override
    public <T extends TreeModel> void setParent(T parent) {
        this.parent = (DatabaseItem) parent;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
