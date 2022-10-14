package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.fxui.model.TableColumnCustomization;
import io.devpl.codegen.fxui.utils.FXMLPage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectTableColumnController extends FXController {

    @FXML
    private TableView<TableColumnCustomization> columnListView;
    @FXML
    private TableColumn<TableColumnCustomization, Boolean> checkedColumn;
    @FXML
    private TableColumn<TableColumnCustomization, String> columnNameColumn;
    @FXML
    private TableColumn<TableColumnCustomization, String> jdbcTypeColumn;
    @FXML
    private TableColumn<TableColumnCustomization, String> javaTypeColumn;
    @FXML
    private TableColumn<TableColumnCustomization, String> propertyNameColumn;
    @FXML
    private TableColumn<TableColumnCustomization, String> typeHandlerColumn;

    private MainController mainUIController;

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
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setTypeHandle(event.getNewValue());
        });
    }

    @FXML
    public void apply() {
        ObservableList<TableColumnCustomization> items = columnListView.getItems();
        if (items != null && items.size() > 0) {
            List<IgnoredColumn> ignoredColumns = new ArrayList<>();
            List<ColumnOverride> columnOverrides = new ArrayList<>();
            items.forEach(item -> {
                if (!item.getChecked()) {
                    IgnoredColumn ignoredColumn = new IgnoredColumn(item.getColumnName());
                    ignoredColumns.add(ignoredColumn);
                } else if (item.getTypeHandle() != null || item.getJavaType() != null || item.getPropertyName() != null) { // unchecked and have typeHandler value
                    ColumnOverride columnOverride = new ColumnOverride(item.getColumnName());
                    columnOverride.setTypeHandler(item.getTypeHandle());
                    columnOverride.setJdbcType(item.getJdbcType());
                    columnOverride.setJavaProperty(item.getPropertyName());
                    columnOverride.setJavaType(item.getJavaType());
                    columnOverrides.add(columnOverride);
                }
            });
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
    public void configAction(MouseEvent event) {
        // FXMLHelper.load(FXMLPage.TABLE_COLUMN_CONFIG.getFxml()).ifPresent(parent -> {
        //     FXUtils.createDialogStage("定制列配置", ((Node) event.getSource()).getScene().getWindow(), parent).show();
        // });

        TableColumnConfigsController controller = (TableColumnConfigsController) loadFXMLPage("定制列配置", FXMLPage.TABLE_COLUMN_CONFIG, true);
        controller.setColumnListView(this.columnListView);
        controller.setTableName(this.tableName);
        controller.showDialogStage();
    }

    public void setColumnList(ObservableList<TableColumnCustomization> columns) {
        columnListView.setItems(columns);
    }

    public void setMainUIController(MainController mainUIController) {
        this.mainUIController = mainUIController;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
