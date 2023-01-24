package io.devpl.toolkit.fxui.view.navigation.impl;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.StringConverter;

import java.util.List;

/**
 * 数据库导航视图
 */
public class DatabaseNavigationView extends TreeView<NavigationItem> {

    public DatabaseNavigationView() {
        setRoot(new TreeItem<>());
        setShowRoot(false);
        setEditable(false);  // 禁用双击编辑操作，通过菜单的方式进行编辑
        setCellFactory(param -> {
            TreeCell<NavigationItem> cell = new TextFieldTreeCell<>(new StringConverter<>() {
                @Override
                public String toString(NavigationItem object) {
                    return object.getDispalyValue();
                }

                @Override
                public NavigationItem fromString(String string) {
                    TreeItem<NavigationItem> selectedItem = DatabaseNavigationView.this.getSelectionModel()
                            .getSelectedItem();
                    NavigationItem value = selectedItem.getValue();
                    value.setDispalyValue(string);
                    return value;
                }
            });
            cell.setEditable(false);
            return cell;
        });
    }

    public final void addConnections(List<ConnectionItem> connections) {
        ObservableList<TreeItem<NavigationItem>> children = getRoot().getChildren();
        // TODO 去重
        for (ConnectionItem connection : connections) {
            TreeItem<NavigationItem> newConnectionItem = new TreeItem<>();
            newConnectionItem.setValue(connection);
            connection.attach(newConnectionItem);
            children.add(newConnectionItem);
        }
    }
}
