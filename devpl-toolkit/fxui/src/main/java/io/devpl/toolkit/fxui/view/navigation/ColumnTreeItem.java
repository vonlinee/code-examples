package io.devpl.toolkit.fxui.view.navigation;

import javafx.scene.control.TreeItem;

public class ColumnTreeItem extends TreeItem<String> {

    public void setColumnName(String columnName) {
        this.setValue(columnName);
    }
}
