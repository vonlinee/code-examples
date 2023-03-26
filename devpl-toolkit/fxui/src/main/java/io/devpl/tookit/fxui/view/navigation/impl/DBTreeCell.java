package io.devpl.tookit.fxui.view.navigation.impl;

import io.devpl.codegen.mbpg.jdbc.meta.TableMetadata;
import io.devpl.tookit.fxui.event.Events;
import io.devpl.tookit.fxui.model.ConnectionRegistry;
import io.devpl.tookit.fxui.model.ConnectionInfo;
import io.devpl.tookit.fxui.view.IconKey;
import io.devpl.tookit.fxui.view.IconMap;
import io.devpl.tookit.utils.DBUtils;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBTreeCell extends TreeCell<String> {

    public DBTreeCell() {
        setEditable(false);
        setOnMouseClicked(new TreeItemMouseClickEventHandler());
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item);
            setGraphic(getTreeItem().getGraphic());
        }
    }

    static class TreeItemMouseClickEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            DBTreeCell clickedTreeCell = (DBTreeCell) event.getSource();
            TreeItem<String> clickedTreeItem = clickedTreeCell.getTreeItem();
            final TreeView<String> treeView = clickedTreeCell.getTreeView();
            int treeItemLevel = treeView.getTreeItemLevel(clickedTreeItem);
            // 数据库连接
            if (Events.isPrimaryButtonDoubleClicked(event)) {
                initializeChildren(clickedTreeItem, treeItemLevel);
            }
        }

        /**
         * 初始化子节点
         *
         * @param clickedTreeItem
         * @param treeItemLevel
         */
        private void initializeChildren(TreeItem<String> clickedTreeItem, int treeItemLevel) {
            if (!clickedTreeItem.isExpanded() && clickedTreeItem.getChildren().size() == 0) {
                if (treeItemLevel == 1) {
                    loadDatabases(clickedTreeItem);
                } else if (treeItemLevel == 2) {
                    loadTables(clickedTreeItem);
                }
                clickedTreeItem.setExpanded(true);
            }
        }

        /**
         * 加载所有数据库名称
         *
         * @param connectionItem 连接TreeCell
         */
        private void loadDatabases(TreeItem<String> connectionItem) {
            ConnectionInfo connectionInfo = ConnectionRegistry.getConnectionConfiguration(connectionItem.getValue());
            try (Connection connection = connectionInfo.getConnection()) {
                List<String> databaseNames = DBUtils.getDatabaseNames(connection);
                ObservableList<TreeItem<String>> children = connectionItem.getChildren();
                for (String databaseName : databaseNames) {
                    children.add(new TreeItem<>(databaseName, IconMap.loadSVG(IconKey.DB_OBJECT)));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        private void loadTables(TreeItem<String> databaseItem) {
            final TreeItem<String> connectionItem = databaseItem.getParent();
            ConnectionInfo connectionInfo = ConnectionRegistry.getConnectionConfiguration(connectionItem.getValue());
            try (Connection connection = connectionInfo.getConnection(databaseItem.getValue())) {
                final List<TableMetadata> tableMetadataList = DBUtils.getTablesMetadata(connection);
                ObservableList<TreeItem<String>> children = databaseItem.getChildren();
                for (TableMetadata tableMetadata : tableMetadataList) {
                    children.add(new TreeItem<>(tableMetadata.getTableName(), IconMap.fontIcon(MaterialDesignT.TABLE_COLUMN)));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}