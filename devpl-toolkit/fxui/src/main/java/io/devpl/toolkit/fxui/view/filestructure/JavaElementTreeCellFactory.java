package io.devpl.toolkit.fxui.view.filestructure;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

public class JavaElementTreeCellFactory implements Callback<TreeView<String>, TreeCell<String>> {

    @Override
    public TreeCell<String> call(TreeView<String> param) {
        return new JavaElementTreeCell();
    }
}
