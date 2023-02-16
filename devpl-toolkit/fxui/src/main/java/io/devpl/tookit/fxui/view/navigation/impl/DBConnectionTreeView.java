package io.devpl.tookit.fxui.view.navigation.impl;

import io.devpl.codegen.mbpg.jdbc.meta.ColumnMetadata;
import io.devpl.codegen.mbpg.jdbc.meta.TableMetadata;
import io.devpl.tookit.fxui.model.props.ConnectionInfo;
import io.devpl.tookit.fxui.view.IconKey;
import io.devpl.tookit.fxui.view.IconMap;
import io.devpl.tookit.fxui.view.navigation.tree.TreeModel;
import io.devpl.tookit.utils.DBUtils;
import io.devpl.tookit.utils.EventUtils;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.StringConverter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 数据库导航视图
 */
public class DBConnectionTreeView extends TreeView<TreeModel> {

    public DBConnectionTreeView() {
        setRoot(new TreeItem<>());
        setShowRoot(false);
        setEditable(false);  // 禁用双击编辑操作，通过菜单的方式进行编辑
        setCellFactory(param -> {
            TreeCell<TreeModel> cell = new TextFieldTreeCell<>(new StringConverter<>() {
                @Override
                public String toString(TreeModel object) {
                    return object.getDisplayValue();
                }

                @Override
                public TreeModel fromString(String string) {
                    TreeItem<TreeModel> selectedItem = DBConnectionTreeView.this.getSelectionModel().getSelectedItem();
                    TreeModel value = selectedItem.getValue();
                    value.setDispalyValue(string);
                    return value;
                }
            });

            cell.setOnMouseClicked(event -> {
                if (EventUtils.isPrimaryButtonDoubleClicked(event)) {
                    event.consume();
                    TreeItem<TreeModel> clickedTreeItem = cell.getTreeItem();
                    TreeModel item = clickedTreeItem.getValue();
                    if (item.isType(ConnectionItem.class)) {
                        ConnectionItem connectionItem = item.getThis();
                        try (Connection connection = connectionItem.getConnectionConfig().getConnection()) {
                            List<String> databaseNames = DBUtils.getDatabaseNames(connection);
                            for (String databaseName : databaseNames) {
                                DatabaseItem databaseItem = new DatabaseItem();
                                databaseItem.setDatabaseName(databaseName);
                                databaseItem.setGraphic(IconMap.loadSVG(IconKey.DB_OBJECT));
                                connectionItem.getChildren().add(databaseItem);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        connectionItem.attach(clickedTreeItem);
                    } else if (item.isType(DatabaseItem.class)) {
                        DatabaseItem databaseItem = item.getThis();
                        TreeItem<TreeModel> connTreeItem = clickedTreeItem.getParent();
                        ConnectionItem connTreeItemValue = connTreeItem.getValue().getThis();
                        ConnectionInfo connectionInfo = connTreeItemValue.getConnectionConfig();
                        try (Connection connection = connectionInfo.getConnection(databaseItem.getDisplayValue())) {
                            List<TableMetadata> tablesMetadata = DBUtils.getTablesMetadata(connection, null, null);
                            for (TableMetadata tableMetadata : tablesMetadata) {
                                TableItem tableItem = new TableItem();
                                tableItem.setDispalyValue(tableMetadata.getTableName());
                                tableItem.setGraphic(IconMap.loadSVG(IconKey.DB_TABLE));
                                databaseItem.getChildren().add(tableItem);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        databaseItem.attach(clickedTreeItem);
                    } else if (item.isType(TableItem.class)) {
                        TableItem tableItem = item.getThis();
                        DatabaseItem databaseItem = clickedTreeItem.getParent().getValue().getThis();
                        ConnectionItem connectionItem = clickedTreeItem.getParent().getParent().getValue().getThis();
                        try (Connection connection = connectionItem.getConnectionConfig()
                                .getConnection(databaseItem.getDisplayValue())) {
                            List<ColumnMetadata> columns = DBUtils.getColumnsMetadata(connection, tableItem.getDisplayValue());
                            for (ColumnMetadata column : columns) {
                                ColumnItem columnItem = new ColumnItem();
                                columnItem.setColumnName(column.getColumnName());
                                columnItem.setGraphic(IconMap.loadSVG(IconKey.DB_COLUMN));
                                tableItem.getChildren().add(columnItem);
                            }
                        } catch (SQLException exception) {
                            exception.printStackTrace();
                        }
                        tableItem.attach(clickedTreeItem);
                    }
                    if (!clickedTreeItem.getChildren().isEmpty()) {
                        clickedTreeItem.setExpanded(true);
                    }
                }
            });
            cell.setEditable(false);
            return cell;
        });
    }

    /**
     * 添加连接
     *
     * @param connectionInfo
     */
    public final void addConnection(ConnectionInfo connectionInfo) {
        ConnectionItem connectionItem = new ConnectionItem();
        connectionItem.setConnectionInfo(connectionInfo);
        TreeItem<TreeModel> newConnectionItem = new TreeItem<>(connectionItem);
        newConnectionItem.setGraphic(IconMap.loadSVG(IconKey.DB_MYSQL));
        getRoot().getChildren().add(newConnectionItem);
    }

    public final void addConnections(List<ConnectionItem> connections) {
        ObservableList<TreeItem<TreeModel>> children = getRoot().getChildren();
        for (ConnectionItem connection : connections) {
            TreeItem<TreeModel> newConnectionItem = new TreeItem<>();
            newConnectionItem.setValue(connection);
            children.add(newConnectionItem);
        }
    }
}
