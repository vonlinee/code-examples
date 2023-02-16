package io.devpl.tookit.fxui.view.navigation.impl;

import io.devpl.tookit.fxui.view.navigation.tree.TreeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * 目录展示项
 */
public class DirectoryItem<T extends TreeModel> extends TreeModelBase  {

    private String directoryName;

    private List<T> rawChildren;
    private ObservableList<T> children;

    public DirectoryItem(String directoryName) {
        this.directoryName = directoryName;
        this.children = FXCollections.observableList(rawChildren = new ArrayList<>());
    }

    TreeModel parent;

    @Override
    public String getDisplayValue() {
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

    @Override
    public TreeModel getParent() {
        return parent;
    }

    @Override
    public <N extends TreeModel> void setParent(N parent) {
        this.parent = parent;
    }

    public <K extends TreeModel> void addChildItem(K item) {
        rawChildren.add((T) item);
    }
}
