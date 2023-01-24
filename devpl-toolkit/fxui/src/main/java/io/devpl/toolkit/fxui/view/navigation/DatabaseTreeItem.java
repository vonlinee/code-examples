package io.devpl.toolkit.fxui.view.navigation;

import io.devpl.toolkit.core.DatabaseInfo;
import javafx.scene.control.TreeItem;

public class DatabaseTreeItem extends TreeItem<String> {

    private DatabaseInfo databaseInfo;

    public void setDatabaseName(String databaseName) {
        this.setValue(databaseName);
    }
}
