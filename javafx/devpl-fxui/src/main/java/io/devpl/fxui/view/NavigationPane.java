package io.devpl.fxui.view;

import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.StackPane;

/**
 * 导航面板
 */
public class NavigationPane extends SplitPane {

    NavigationMenu menu = new NavigationMenu();
    StackPane contentPane = new StackPane();

    public NavigationPane() {

        menu.setRoot(new TreeItem<>("Root"));
        menu.getRoot().getChildren().add(new TreeItem<>("A"));
        menu.getRoot().getChildren().add(new TreeItem<>("A"));
        menu.getRoot().getChildren().add(new TreeItem<>("A"));
        menu.getRoot().getChildren().add(new TreeItem<>("A"));

        getItems().addAll(menu, contentPane);
    }
}
