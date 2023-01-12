package io.devpl.toolkit.fxui.view;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import lombok.Data;

@Data
public class ConnectionInfoTreeItem extends TreeItem<NavigationItem> implements NavigationItem {

    private String connectionName;

    public ConnectionInfoTreeItem() {
        ObservableList<TreeItem<NavigationItem>> children = getChildren();
        TreeItem<Object> item = new TreeItem<>();
        
    }

    @Override
    public String getDispalyValue() {
        return null;
    }
}
