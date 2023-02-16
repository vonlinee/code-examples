package io.devpl.tookit.fxui.view.navigation;

import io.devpl.tookit.fxui.model.DatabaseInfo;
import javafx.scene.control.TreeItem;

public class DatabaseTreeItem extends TreeItem<String> {

	/**
	 * 数据库信息
	 */
    private DatabaseInfo databaseInfo;

    public void setDatabaseName(String databaseName) {
        this.setValue(databaseName);
    }
}
