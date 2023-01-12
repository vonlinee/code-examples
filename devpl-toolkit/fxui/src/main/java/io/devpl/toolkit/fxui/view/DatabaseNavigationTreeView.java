package io.devpl.toolkit.fxui.view;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;

/**
 * 数据库导航视图
 */
public class DatabaseNavigationTreeView extends TreeView<NavigationItem> {

    public DatabaseNavigationTreeView() {
        setRoot(new TreeItem<>());
        setShowRoot(false);
        setCellFactory(param -> {
            TextFieldTreeCell<NavigationItem> cell = new TextFieldTreeCell<>();
            cell.setEditable(true);
            return cell;
        });
    }
}
