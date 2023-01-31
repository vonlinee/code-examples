package io.devpl.toolkit.fxui.view.navigation.impl;

import javafx.scene.control.TreeItem;

public abstract class TreeModelBase implements TreeModel {

    private TreeItem<TreeModel> treeItem;

    @Override
    public final void setTreeItem(TreeItem<TreeModel> model) {
        this.treeItem = model;
    }

    @Override
    public final TreeItem<TreeModel> getTreeItem() {
        return treeItem;
    }
}
