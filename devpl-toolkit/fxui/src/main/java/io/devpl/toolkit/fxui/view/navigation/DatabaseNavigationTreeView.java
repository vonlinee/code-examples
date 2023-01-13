package io.devpl.toolkit.fxui.view.navigation;

import io.devpl.toolkit.fxui.model.ConnectionInfo;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class DatabaseNavigationTreeView extends TreeView<String> {

    public DatabaseNavigationTreeView() {
        setRoot(new TreeItem<>());
        setShowRoot(false);
    }

    public void addConnection(ConnectionInfo connectionInfo) {
        ConnectionTreeItem treeItem = new ConnectionTreeItem(connectionInfo);
        treeItem.connect();
        getRoot().getChildren().add(treeItem);
    }
}
