package io.devpl.toolkit.fxui.view.navigation;

import io.devpl.toolkit.fxui.model.DatabaseInfo;
import javafx.scene.control.TreeItem;
import lombok.Data;

@Data
public class DatabaseTreeItem extends TreeItem<String> {

    private DatabaseInfo databaseInfo;

    public void setDatabaseName(String databaseName) {
        this.setValue(databaseName);
    }
}
