package io.devpl.toolkit.fxui.view.filestructure;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class JavaElementTreeCell extends TextFieldTreeCell<String> {

    public JavaElementTreeCell() {
        super(new DefaultStringConverter());
    }

    // 类对应的菜单
    private final ContextMenu classContextMenu = new ContextMenu();
    // 方法的菜单
    private final ContextMenu methodContextMenu = new ContextMenu();

    {
        classContextMenu.getItems().add(new MenuItem("添加方法"));
        classContextMenu.getItems().add(new MenuItem("添加字段"));
        methodContextMenu.getItems().add(new MenuItem("添加参数"));
    }

    @Override
    public void updateSelected(boolean selected) {
        super.updateSelected(selected);
        // 更新菜单
        TreeItem<String> item = getTreeView().getSelectionModel().getSelectedItem();
        if (item instanceof TopLevelClassItem) {
            setContextMenu(classContextMenu);
        } else if (item instanceof MethodItem) {
            setContextMenu(methodContextMenu);
        } else if (item instanceof FieldItem) {
            setContextMenu(null);
        }
    }

    /**
     * 更新单元格时触发
     *
     * @param item  The new item for the cell.
     * @param empty whether or not this cell represents data from the list. If it
     *              is empty, then it does not represent any domain data, but is a cell
     *              being used to render an "empty" row.
     */
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
        } else {
            setText(item);
        }
    }
}
