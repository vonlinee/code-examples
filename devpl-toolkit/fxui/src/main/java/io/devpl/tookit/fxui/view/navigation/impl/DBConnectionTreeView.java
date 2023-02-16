package io.devpl.tookit.fxui.view.navigation.impl;

import io.devpl.tookit.fxui.model.props.ConnectionInfo;
import io.devpl.tookit.fxui.view.IconKey;
import io.devpl.tookit.fxui.view.IconMap;
import io.devpl.tookit.utils.EventUtils;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;

import java.util.List;

/**
 * 数据库导航视图
 */
public class DBConnectionTreeView extends TreeView<String> {

    public DBConnectionTreeView() {
        setRoot(new TreeItem<>());
        setShowRoot(false);
        setEditable(false);  // 禁用双击编辑操作，通过菜单的方式进行编辑
        setCellFactory(param -> {
            TreeCell<String> cell = new TextFieldTreeCell<>();
            cell.setOnMouseClicked(event -> {
                TreeCell<?> clickedTreeCell = (TreeCell<?>) event.getSource();
                if (EventUtils.isPrimaryButtonDoubleClicked(event)) {
                    TreeItem<String> clickedTreeItem = cell.getTreeItem();
                    if (!clickedTreeItem.getChildren().isEmpty()) {
                        return;
                    }
                    clickedTreeItem.setExpanded(clickedTreeItem.isExpanded());
                    String item = clickedTreeItem.getValue();

                    CellType cellType = getTreeCellType(clickedTreeCell);
                    initializeChildren(cellType);
                }
            });
            cell.setEditable(false);
            return cell;
        });

    }

    /**
     * 初始化子节点
     *
     * @param cellType 单元格类型枚举
     */
    private void initializeChildren(CellType cellType) {
        switch (cellType) {
            case CONNECTION:
                break;
            case DATABASE:
                break;
            case TABLE:
                break;
            default:
                break;
        }
    }

    /**
     * 添加连接
     *
     * @param connectionInfo
     */
    public final void addConnection(ConnectionInfo connectionInfo) {
        ConnectionItem connectionItem = new ConnectionItem();
        connectionItem.setConnectionInfo(connectionInfo);
        TreeItem<String> newConnectionItem = new TreeItem<>();
        newConnectionItem.setGraphic(IconMap.loadSVG(IconKey.DB_MYSQL));
        getRoot().getChildren().add(newConnectionItem);
    }

    public final void addConnections(List<ConnectionItem> connections) {
        ObservableList<TreeItem<String>> children = getRoot().getChildren();
        for (ConnectionItem connection : connections) {
            TreeItem<String> newConnectionItem = new TreeItem<>();
            newConnectionItem.setValue(connection.getDisplayValue());
            children.add(newConnectionItem);
        }
    }

    private static final String DATA_KEY_CELL_TYPE = "cellType";

    enum CellType {
        DIRECTORY,
        CONNECTION,
        DATABASE,
        TABLE,
        COLUMN
    }

    private void setTreeCellType(TreeCell<?> treeCell, CellType cellType) {
        treeCell.getProperties().put(DATA_KEY_CELL_TYPE, cellType);
    }

    private CellType getTreeCellType(TreeCell<?> treeCell) {
        return (CellType) treeCell.getProperties().get(DATA_KEY_CELL_TYPE);
    }
}
