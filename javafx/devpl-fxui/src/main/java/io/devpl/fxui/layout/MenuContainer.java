package io.devpl.fxui.layout;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.util.function.Consumer;

/**
 * 菜单容器
 */
public class MenuContainer extends Region {

    private final TreeView<String> menuTreeView;

    public MenuContainer(Consumer<Node> consumer) {
        this.menuTreeView = new TreeView<>();
        this.menuTreeView.setRoot(new TreeItem<>());
        this.menuTreeView.setShowRoot(false);

        getChildren().add(menuTreeView);
        menuTreeView.setCellFactory(param -> {
            TextFieldTreeCell<String> cell = new TextFieldTreeCell<>();
            cell.setOnMouseClicked(event -> {
                Object source = event.getSource();
                if (source instanceof TreeCell<?> treeCell) {
                    TreeItem<?> treeItem = treeCell.getTreeItem();
                    if (treeItem instanceof MenuItem menuItem) {
                        if (menuItem.getContent() != null) {
                            consumer.accept(menuItem.getContent());
                        }
                    }
                }
            });
            return cell;
        });
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }

    public final void addMenu(MenuItem menuItem) {
        this.menuTreeView.getRoot().getChildren().add(menuItem);
    }
}
