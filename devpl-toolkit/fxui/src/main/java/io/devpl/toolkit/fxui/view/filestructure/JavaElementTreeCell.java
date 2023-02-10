package io.devpl.toolkit.fxui.view.filestructure;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.DefaultStringConverter;

/**
 * 单元格
 */
public class JavaElementTreeCell extends TextFieldTreeCell<String> {

    public JavaElementTreeCell() {
        super(new DefaultStringConverter());
        this.setOnMouseClicked(event -> {
            JavaElementTreeCell sourceTreeCell = (JavaElementTreeCell) event.getSource();
            // 更新选中的内容面板
            BorderPane parent = (BorderPane) sourceTreeCell.getTreeView().getParent();
            // 详情面板
            JavaElementDetailPane detailPane = (JavaElementDetailPane) parent.getCenter();
            detailPane.updateDetailInfo((JavaElementItem) sourceTreeCell.getTreeItem());
        });
    }

    /**
     * 更新选择项
     * 当从TreeItem A切换到另一个TreeItem B时，需要更新 A 的选中状态为false，B的状态为true，因此会调用2次
     *
     * @param selected whether to select this cell.
     */
    @Override
    public void updateSelected(boolean selected) {
        super.updateSelected(selected);
        if (selected && !isEmpty()) {
            initTreeCellContextMenuIfNeeded();
        }
    }

    /**
     * 初始化单元格的菜单项
     */
    private void initTreeCellContextMenuIfNeeded() {
        TreeItem<String> treeItem = getTreeView().getSelectionModel().getSelectedItem();
        if (treeItem instanceof TopLevelClassItem) {
            initClassContextMenu((TopLevelClassItem) treeItem);
        } else if (treeItem instanceof MethodItem) {
            initMethodContextMenu((MethodItem) treeItem);
        } else if (treeItem instanceof FieldItem) {
            initFieldContextMenu((FieldItem) treeItem);
        }
    }

    private void initClassContextMenu(TopLevelClassItem classItem) {
        ContextMenu contextMenu = getContextMenu();
        if (contextMenu == null) {
            contextMenu = new ContextMenu();
            MenuItem addMethodMenu = new MenuItem("添加方法");
            MenuItem addFieldMenu = new MenuItem("添加字段");
            addMethodMenu.setOnAction(event -> {
                MethodItem methodItem = new MethodItem();
                methodItem.setValue("New Method");
                classItem.getChildren().add(methodItem);
            });
            addFieldMenu.setOnAction(event -> {
                FieldItem fieldItem = new FieldItem();
                fieldItem.setValue("New Field");
                classItem.getChildren().add(fieldItem);
            });
            contextMenu.getItems().add(addMethodMenu);
            contextMenu.getItems().add(addFieldMenu);
            setContextMenu(contextMenu);
        }
    }

    private void initMethodContextMenu(MethodItem methodItem) {
        ContextMenu contextMenu = getContextMenu();
        if (contextMenu == null) {
            contextMenu = new ContextMenu();
            MenuItem editMenu = new MenuItem("编辑");
            contextMenu.getItems().add(editMenu);
            setContextMenu(contextMenu);
        }
    }

    private void initFieldContextMenu(FieldItem fieldItem) {
        ContextMenu contextMenu = getContextMenu();
        if (contextMenu == null) {
            contextMenu = new ContextMenu();
            MenuItem editMenu = new MenuItem("编辑");
            contextMenu.getItems().add(editMenu);
            setContextMenu(contextMenu);
        }
    }

    /**
     * 更新单元格时触发
     *
     * @param item  The new item for the cell.
     * @param empty whether this cell represents data from the list. If it
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
