package io.devpl.toolkit.fxui.controller;

import io.devpl.toolkit.fxui.common.model.ColumnCustomConfiguration;
import io.devpl.toolkit.fxui.utils.CollectionUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectTableColumnController extends FXControllerBase {

    @FXML
    private TableView<ColumnCustomConfiguration> columnListView;
    @FXML
    private TableColumn<ColumnCustomConfiguration, Boolean> checkedColumn;
    @FXML
    private TableColumn<ColumnCustomConfiguration, String> columnNameColumn;
    @FXML
    private TableColumn<ColumnCustomConfiguration, String> jdbcTypeColumn;
    @FXML
    private TableColumn<ColumnCustomConfiguration, String> javaTypeColumn;
    @FXML
    private TableColumn<ColumnCustomConfiguration, String> propertyNameColumn;
    @FXML
    private TableColumn<ColumnCustomConfiguration, String> typeHandlerColumn;

    private MainUIController mainUIController;

    private String tableName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // cellvaluefactory
        checkedColumn.setCellValueFactory(new PropertyValueFactory<>("checked"));
        columnNameColumn.setCellValueFactory(new PropertyValueFactory<>("columnName"));
        jdbcTypeColumn.setCellValueFactory(new PropertyValueFactory<>("jdbcType"));
        propertyNameColumn.setCellValueFactory(new PropertyValueFactory<>("propertyName"));
        typeHandlerColumn.setCellValueFactory(new PropertyValueFactory<>("typeHandler"));
        // Cell Factory that customize how the cell should render
        checkedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkedColumn));
        jdbcTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        // handle commit event to save the user input data
        jdbcTypeColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setJdbcType(event.getNewValue());
        });
        javaTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        // handle commit event to save the user input data
        javaTypeColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setJavaType(event.getNewValue());
        });
        propertyNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        propertyNameColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPropertyName(event.getNewValue());
        });
        typeHandlerColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeHandlerColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setTypeHandler(event.getNewValue());
        });
    }

    /**
     * 应用配置项目
     */
    @FXML
    public void applyConfig() {
        ObservableList<ColumnCustomConfiguration> items = columnListView.getItems();
        if (CollectionUtils.isNotEmpty(items)) {
            List<IgnoredColumn> ignoredColumns = new ArrayList<>();
            List<ColumnOverride> columnOverrides = new ArrayList<>();
            for (ColumnCustomConfiguration item : items) {
                if (!item.isChecked()) {
                    ignoredColumns.add(new IgnoredColumn(item.getColumnName()));
                    continue;
                }
                // unchecked and have typeHandler value
                if (item.getTypeHandler() != null || item.getJavaType() != null || item.getPropertyName() != null) {
                    ColumnOverride columnOverride = new ColumnOverride(item.getColumnName());
                    columnOverride.setTypeHandler(item.getTypeHandler());
                    columnOverride.setJdbcType(item.getJdbcType());
                    columnOverride.setJavaProperty(item.getPropertyName());
                    columnOverride.setJavaType(item.getJavaType());
                    columnOverrides.add(columnOverride);
                }
            }
            mainUIController.setIgnoredColumns(ignoredColumns);
            mainUIController.setColumnOverrides(columnOverrides);
        }
        getDialogStage().close();
    }

    @FXML
    public void cancel() {
        getDialogStage().close();
    }

    @FXML
    public void configAction() {
        TableColumnConfigsController controller = (TableColumnConfigsController) loadFXMLPage("定制列配置", FXMLPage.TABLE_COLUMN_CONFIG, true);
        controller.setColumnListView(this.columnListView);
        controller.setTableName(this.tableName);
        controller.showDialogStage();
    }

    public void setColumnList(ObservableList<ColumnCustomConfiguration> columns) {
        columnListView.setItems(columns);
    }

    public void setMainUIController(MainUIController mainUIController) {
        this.mainUIController = mainUIController;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


}
