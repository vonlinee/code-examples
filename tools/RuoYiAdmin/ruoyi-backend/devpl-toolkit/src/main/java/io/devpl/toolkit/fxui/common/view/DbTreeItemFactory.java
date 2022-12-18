package io.devpl.toolkit.fxui.common.view;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

public class DbTreeItemFactory implements Callback<TreeView<String>, TreeCell<String>> {

    private final TreeView<String> trvDbTreeList;

    public DbTreeItemFactory(TreeView<String> trvDbTreeList) {
        this.trvDbTreeList = trvDbTreeList;
    }

    @Override
    public TreeCell<String> call(TreeView<String> param) {

        return null;
    }
}
