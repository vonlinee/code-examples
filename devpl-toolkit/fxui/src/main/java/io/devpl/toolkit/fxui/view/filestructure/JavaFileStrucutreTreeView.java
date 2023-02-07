package io.devpl.toolkit.fxui.view.filestructure;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Java文件结构树
 */
public class JavaFileStrucutreTreeView extends TreeView<String> {

    public JavaFileStrucutreTreeView() {
        setRoot(new TreeItem<>());
        setShowRoot(false);
        setEditable(true);
        setCellFactory(new JavaElementTreeCellFactory());
    }

    public final void addClass(ClassItem classItem) {
        getRoot().getChildren().add(classItem);
    }
}
