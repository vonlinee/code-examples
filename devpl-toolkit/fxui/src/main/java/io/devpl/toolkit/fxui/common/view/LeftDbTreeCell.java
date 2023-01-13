package io.devpl.toolkit.fxui.common.view;

import io.devpl.toolkit.fxui.model.DatabaseInfo;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

import java.lang.ref.WeakReference;

/**
 * 数据库属性列表单元格
 */
public class LeftDbTreeCell extends TreeCell<DatabaseInfo> {

    private HBox hbox;

    private WeakReference<TreeItem<DatabaseInfo>> treeItemRef;

    private final InvalidationListener treeItemGraphicListener = observable -> {
        updateDisplay(getItem(), isEmpty());
    };

    private final InvalidationListener treeItemListener = new InvalidationListener() {
        @Override
        public void invalidated(Observable observable) {
            TreeItem<DatabaseInfo> oldTreeItem = treeItemRef == null ? null : treeItemRef.get();
            if (oldTreeItem != null) {
                oldTreeItem.graphicProperty().removeListener(weakTreeItemGraphicListener);
            }
            TreeItem<DatabaseInfo> newTreeItem = getTreeItem();
            if (newTreeItem != null) {
                newTreeItem.graphicProperty().addListener(weakTreeItemGraphicListener);
                treeItemRef = new WeakReference<>(newTreeItem);
            }
        }
    };

    private final WeakInvalidationListener weakTreeItemGraphicListener =
            new WeakInvalidationListener(treeItemGraphicListener);

    private WeakInvalidationListener weakTreeItemListener =
            new WeakInvalidationListener(treeItemListener);

    public LeftDbTreeCell() {
        treeItemProperty().addListener(weakTreeItemListener);

        if (getTreeItem() != null) {
            getTreeItem().graphicProperty().addListener(weakTreeItemGraphicListener);
        }
    }

    void updateDisplay(DatabaseInfo item, boolean empty) {
        if (item == null || empty) {
            hbox = null;
            setText(null);
            setGraphic(null);
        } else {
            // update the graphic if one is set in the TreeItem
            TreeItem<DatabaseInfo> treeItem = getTreeItem();
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
    public void updateItem(DatabaseInfo item, boolean empty) {
        super.updateItem(item, empty);
        updateDisplay(item, empty);
    }
}