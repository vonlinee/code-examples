package io.devpl.toolkit.fxui.model;

import io.devpl.toolkit.fxui.event.CommandEvent;
import io.devpl.toolkit.fxui.event.LoadDbTreeEvent;
import io.devpl.toolkit.framework.Alerts;
import io.devpl.toolkit.framework.JFX;
import io.devpl.toolkit.fxui.utils.ConfigHelper;
import io.devpl.toolkit.fxui.utils.DBUtils;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;

import java.sql.SQLRecoverableException;
import java.util.List;

/**
 * 数据库列表TreeView的单元格工厂
 */
public class DbTreeViewCellFactory implements Callback<TreeView<String>, TreeCell<String>> {

    @Override
    public TreeCell<String> call(TreeView<String> treeView) {
        TreeCell<String> cell = new TextFieldTreeCell<>(); // 创建一个单元格
        cell.setOnMouseClicked(event -> {
            if (!isPrimaryButtonDoubleClicked(event)) {
                event.consume();
                return;
            }
            // 获取单元格
            TreeItem<String> treeItem = cell.getTreeItem();
            int level = cell.getTreeView().getTreeItemLevel(treeItem);
            if (level == 1) { // 层级为1，点击每个连接
                addContexMenuIfRequired(cell);
                if (treeItem.getChildren().isEmpty()) {
                    displayTables(treeItem);
                }
                // 有子节点则双击切换展开状态
                treeItem.setExpanded(!treeItem.isExpanded());
            } else if (level == 2) { // 双击表
                Event.fireEvent(treeView, new CommandEvent(CommandEvent.TABLES_SELECTED, null));
            }
            event.consume();
        });
        return cell;
    }

    private void addContexMenuIfRequired(TreeCell<?> cell) {
        if (cell.getContextMenu() != null) return;
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem menuItemCloseConnection = new MenuItem("关闭连接");
        menuItemCloseConnection.setOnAction(event -> {
            cell.getTreeItem().getChildren().clear();
        });
        final MenuItem menuItemEditConnection = new MenuItem("编辑连接");
        menuItemEditConnection.setOnAction(event -> {
            DatabaseInfo selectedConfig = (DatabaseInfo) cell.getTreeItem().getGraphic().getUserData();
            Event.fireEvent(cell.getTreeView(), new CommandEvent(CommandEvent.OPEN_DB_CONNECTION, selectedConfig));
        });
        final MenuItem menuItemDelConnection = new MenuItem("删除连接");
        menuItemDelConnection.setOnAction(event -> {
            try {
                final DatabaseInfo userData = (DatabaseInfo) cell.getTreeItem().getGraphic().getUserData();
                ConfigHelper.deleteDatabaseConfig(userData);
                EventBus.getDefault().post(new LoadDbTreeEvent());
            } catch (Exception e) {
                Alerts.error("Delete connection failed! Reason: " + e.getMessage()).show();
            }
        });
        contextMenu.getItems().addAll(menuItemCloseConnection, menuItemEditConnection, menuItemDelConnection);
        cell.setContextMenu(contextMenu);
    }

    /**
     * 展示数据表
     * @param treeItem
     */
    private void displayTables(TreeItem<String> treeItem) {
        DatabaseInfo dbConfig = (DatabaseInfo) treeItem.getGraphic().getUserData();
        try {
            List<String> tables = DBUtils.getTableNames(dbConfig, "");
            if (tables.size() > 0) {
                ObservableList<TreeItem<String>> children = treeItem.getChildren();
                children.clear();
                for (String tableName : tables) {
                    TreeItem<String> newTreeItem = new TreeItem<>();
                    ImageView imageView = JFX.loadImageView("static/icons/table.png", 16);
                    newTreeItem.setGraphic(imageView);
                    newTreeItem.setValue(tableName);
                    children.add(newTreeItem);
                }
            }
            final Object userData = treeItem.getGraphic().getUserData();
            treeItem.setGraphic(JFX.loadImageView("static/icons/computer.png", 16, userData));
        } catch (SQLRecoverableException e) {
            Alerts.error("连接超时").show();
        } catch (Exception e) {
            Alerts.error(e.getMessage()).show();
        }
    }

    private boolean isPrimaryButtonDoubleClicked(MouseEvent event) {
        return event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2;
    }
}
