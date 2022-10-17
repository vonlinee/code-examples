package io.devpl.codegen.fxui.view;

import io.devpl.codegen.fxui.model.DatabaseConfiguration;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

import java.lang.ref.WeakReference;

public class DbConfigurationTreeCell extends TreeCell<DatabaseConfiguration> {

    private HBox hbox;

    private WeakReference<TreeItem<DatabaseConfiguration>> treeItemRef;

    private final InvalidationListener treeItemGraphicListener = observable -> {
        updateDisplay(getItem(), isEmpty());
    };

    private final InvalidationListener treeItemListener = new InvalidationListener() {
        @Override
        public void invalidated(Observable observable) {
            TreeItem<DatabaseConfiguration> oldTreeItem = treeItemRef == null ? null : treeItemRef.get();
            if (oldTreeItem != null) {
                oldTreeItem.graphicProperty().removeListener(weakTreeItemGraphicListener);
            }
            TreeItem<DatabaseConfiguration> newTreeItem = getTreeItem();
            if (newTreeItem != null) {
                newTreeItem.graphicProperty().addListener(weakTreeItemGraphicListener);
                treeItemRef = new WeakReference<>(newTreeItem);
            }
        }
    };

    private final WeakInvalidationListener weakTreeItemGraphicListener =
            new WeakInvalidationListener(treeItemGraphicListener);

    private final WeakInvalidationListener weakTreeItemListener =
            new WeakInvalidationListener(treeItemListener);

    public DbConfigurationTreeCell() {
        treeItemProperty().addListener(weakTreeItemListener);
        if (getTreeItem() != null) {
            getTreeItem().graphicProperty().addListener(weakTreeItemGraphicListener);
        }
    }

    void updateDisplay(DatabaseConfiguration item, boolean empty) {
        if (item == null || empty) {
            hbox = null;
            setText(null);
            setGraphic(null);
        } else {
            // update the graphic if one is set in the TreeItem
            TreeItem<DatabaseConfiguration> treeItem = getTreeItem();
            if (treeItem != null && treeItem.getGraphic() != null) {
                hbox = null;
                setText(item.toString());
                setGraphic(treeItem.getGraphic());
            } else {
                hbox = null;
                setText(item.getName());
                setGraphic(null);
            }
        }
    }

    @Override
    public void updateItem(DatabaseConfiguration item, boolean empty) {
        super.updateItem(item, empty);
        updateDisplay(item, empty);
    }
}