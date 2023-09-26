package io.devpl.fxui.layout;

import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeCell;

/**
 * 导航面板
 */
public class LayoutPane extends SplitPane {

    MenuContainer menuPane;
    ContentContainer contentPane;

    public LayoutPane() {
        contentPane = new ContentContainer();

        menuPane = new MenuContainer();
        menuPane.setOnMenuClicked(event -> {
            TreeCell<?> cell = (TreeCell<?>) event.getSource();
            NavigationMenu menu = (NavigationMenu) cell.getTreeItem();
            contentPane.switchTo(menu.getContent());
        });

        getItems().addAll(menuPane, contentPane);

        contentPane.setStyle("-fx-background-color: #888787");

        getDividers().get(0).positionProperty()
                .addListener((observable, oldValue, newValue) -> {
                    menuPane.setPrefWidth(LayoutPane.this.getWidth() * newValue.doubleValue());
                    contentPane.setPrefWidth(LayoutPane.this.getWidth() * (1.0 - newValue.doubleValue()));
                });

        menuPane.prefHeightProperty().bind(this.heightProperty());
    }

    public final void addNavigationMenu(NavigationMenu menuItem) {
        menuPane.addNavigationMenu(menuItem);
    }
}
