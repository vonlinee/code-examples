package io.devpl.toolkit.fxui.view.filestructure;

import javafx.geometry.Pos;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 * Java文件结构树视图
 */
public class JavaFileStrucutreTreeView extends TreeView<String> {

    public JavaFileStrucutreTreeView() {
        setRoot(new TreeItem<>());
        setShowRoot(false);
        setEditable(false);
        setCellFactory(param -> {
            JavaElementTreeCell treeCell = new JavaElementTreeCell();
            treeCell.setAlignment(Pos.CENTER_LEFT);
            return treeCell;
        });
    }

    public final void addClass(TopLevelClassItem classItem) {
        getRoot().getChildren().add(classItem);
    }
}
