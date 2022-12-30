package io.devpl.toolkit.fxui.model;

import io.devpl.toolkit.fxui.config.DatabaseConfig;
import io.devpl.toolkit.fxui.controller.FXMLPage;
import io.devpl.toolkit.fxui.controller.MainUIController;
import io.devpl.toolkit.fxui.controller.NewConnectionController;
import io.devpl.toolkit.fxui.framework.Alerts;
import io.devpl.toolkit.fxui.framework.JFX;
import io.devpl.toolkit.fxui.utils.ConfigHelper;
import io.devpl.toolkit.fxui.utils.DBUtils;
import io.devpl.toolkit.fxui.utils.StringUtils;
import io.devpl.toolkit.fxui.view.CodeGenMainView;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.sql.SQLRecoverableException;
import java.util.List;

/**
 * 数据库列表TreeView的单元格工厂
 */
public class DbTreeViewCellFactory implements Callback<TreeView<DbTreeViewItemValue>, TreeCell<DbTreeViewItemValue>> {

    MainUIController mainUIController;

    public DbTreeViewCellFactory(MainUIController mainUIController) {
        this.mainUIController = mainUIController;
    }

    StringConverter<DbTreeViewItemValue> converter = new FunctionalStringConverter<>(DbTreeViewItemValue::getTableName, null);

    @Override
    public TreeCell<DbTreeViewItemValue> call(TreeView<DbTreeViewItemValue> treeView) {
        TreeCell<DbTreeViewItemValue> cell = new TextFieldTreeCell<>(converter); // 创建一个单元格
        cell.setOnMouseClicked(event -> {
            @SuppressWarnings("unchecked") TreeCell<DbTreeViewItemValue> treeCell = (TreeCell<DbTreeViewItemValue>) event.getSource();
            // 获取单元格
            TreeItem<DbTreeViewItemValue> treeItem = treeCell.getTreeItem();
            int level = treeCell.getTreeView().getTreeItemLevel(treeItem);
            if (level == 1) { // 层级为1，点击每个连接
                addContexMenuIfRequired(treeCell);
                if (event.getClickCount() != 2) {
                    event.consume();
                    return;
                }
                treeItem.setExpanded(!treeItem.isExpanded()); // 双击切换是否展开
                displayTables(treeItem);
            }
            if (level == 2 && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) { // 双击
                mainUIController.selectedDatabaseConfig = (DatabaseConfig) treeItem.getParent()
                                                                                   .getGraphic()
                                                                                   .getUserData();

                mainUIController.tableNameField.setText(this.mainUIController.tableName = treeCell.getTreeItem()
                                                                                                  .getValue()
                                                                                                  .getTableName());
                mainUIController.domainObjectNameField.setText(StringUtils.dbStringToCamelStyle(this.mainUIController.tableName));
                mainUIController.mapperName.setText(mainUIController.domainObjectNameField.getText().concat("Mapper"));

                DbTreeViewItemValue value = treeItem.getValue();
                if (!value.isSelected()) {
                    value.setSelected(true);
                    CodeGenMainView view = (CodeGenMainView) mainUIController.tabTableConfig.getContent();
                    final DBTableListModel row = new DBTableListModel();
                    row.setSelected(true);
                    row.setTableName(mainUIController.tableName);
                    row.setTableComment(mainUIController.tableName);
                    view.addRow(row);
                }
            }
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
            DatabaseConfig selectedConfig = (DatabaseConfig) cell.getTreeItem().getGraphic().getUserData();
            NewConnectionController controller = (NewConnectionController) mainUIController.loadFXMLPage("编辑数据库连接", FXMLPage.NEW_CONNECTION, false);
            controller.setConfig(selectedConfig);
            // 此处MenuItem不是Node类型
            mainUIController.getStage(cell.getTreeView()).show();
        });
        final MenuItem menuItemDelConnection = new MenuItem("删除连接");
        menuItemDelConnection.setOnAction(event -> {
            try {
                final DatabaseConfig userData = (DatabaseConfig) cell.getTreeItem().getGraphic().getUserData();
                ConfigHelper.deleteDatabaseConfig(userData);
                mainUIController.loadLeftDBTree(); // 刷新界面
            } catch (Exception e) {
                Alerts.error("Delete connection failed! Reason: " + e.getMessage()).show();
            }
        });
        contextMenu.getItems().addAll(menuItemCloseConnection, menuItemEditConnection, menuItemDelConnection);
        cell.setContextMenu(contextMenu);
    }

    public void displayTables(TreeItem<DbTreeViewItemValue> treeItem) {
        if (treeItem.isLeaf() && !treeItem.isExpanded()) return;
        DatabaseConfig selectedConfig = (DatabaseConfig) treeItem.getGraphic().getUserData();
        try {
            String filter = mainUIController.filterTreeBox.getText();
            List<String> tables = DBUtils.getTableNames(selectedConfig, filter);
            if (tables.size() > 0) {
                ObservableList<TreeItem<DbTreeViewItemValue>> children = treeItem.getChildren();
                children.clear();
                for (String tableName : tables) {
                    TreeItem<DbTreeViewItemValue> newTreeItem = new TreeItem<>();
                    ImageView imageView = JFX.loadImageView("static/icons/table.png", 16);
                    newTreeItem.setGraphic(imageView);
                    DbTreeViewItemValue tableInfo = new DbTreeViewItemValue();
                    tableInfo.setTableName(tableName);
                    newTreeItem.setValue(tableInfo);
                    children.add(newTreeItem);
                }
            } else if (StringUtils.hasText(filter)) {
                treeItem.getChildren().clear();
            }
            String imageViewName = StringUtils.hasText(filter) ? "static/icons/filter.png" : "static/icons/computer.png";
            treeItem.setGraphic(JFX.loadImageView(imageViewName, 16, treeItem.getGraphic().getUserData()));
        } catch (SQLRecoverableException e) {
            Alerts.error("连接超时").show();
        } catch (Exception e) {
            Alerts.error(e.getMessage()).show();
        }
    }
}
