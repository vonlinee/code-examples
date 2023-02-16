package io.devpl.tookit.fxui.view.navigation.impl;

import io.devpl.tookit.fxui.view.navigation.tree.TreeModel;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

public abstract class TreeModelBase implements TreeModel {

    private TreeItem<TreeModel> treeItem = newTreeItem();

    @Override
    public final void setTreeItem(TreeItem<TreeModel> model) {
        this.treeItem = model;
    }

    @Override
    public final TreeItem<TreeModel> getTreeItem() {
        return treeItem;
    }

    @Override
    public void setGraphic(Node graphic) {
        this.treeItem.setGraphic(graphic);
    }
}
