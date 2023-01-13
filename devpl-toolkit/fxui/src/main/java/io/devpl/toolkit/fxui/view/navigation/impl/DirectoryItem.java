package io.devpl.toolkit.fxui.view.navigation.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * 目录展示项
 */
public class DirectoryItem<T extends NavigationItem> implements NavigationItem {

    private String directoryName;

    private List<T> rawChildren;
    private ObservableList<T> children;

    public DirectoryItem(String directoryName) {
        this.directoryName = directoryName;
        this.children = FXCollections.observableList(rawChildren = new ArrayList<>());
    }

    @Override
    public String getDispalyValue() {
        return directoryName;
    }

    @Override
    public void setDispalyValue(String dispalyValue) {
        this.directoryName = dispalyValue;
    }

    @Override
    public boolean hasChild() {
        return true;
    }

    @Override
    public List<T> getChildren() {
        return children;
    }

    public <K extends NavigationItem> void addChildItem(K item) {
        rawChildren.add((T) item);
    }
}
