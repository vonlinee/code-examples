package io.devpl.tookit.fxui.controller;

import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.fxtras.utils.StageManager;
import io.devpl.tookit.fxui.model.ConnectionRegistry;
import io.devpl.tookit.fxui.model.TableCodeGeneration;
import io.devpl.tookit.fxui.model.props.ColumnCustomConfiguration;
import io.devpl.tookit.fxui.model.ConnectionInfo;
import io.devpl.tookit.utils.CollectionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import org.greenrobot.eventbus.Subscribe;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 表定制化控制器: 配置单个表的代码生成效果
 */
@FxmlLocation(location = "layout/table_customization.fxml", title = "表生成定制")
public class TableCustomizationController extends FxmlView {

    @FXML
    public BorderPane root;
    @FXML
    public Label labelCurrentTableName;
    @FXML
    public Accordion accoConfig;
    @FXML
    public TitledPane titpColumnConfig;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkedColumn.setCellFactory(param -> {
            TableCell<ColumnCustomConfiguration, Boolean> cell = new CheckBoxTableCell<>(null);
            cell.setTooltip(new Tooltip("如果不想生成某列请取消勾选对应的列"));
            return cell;
        });

        checkedColumn.setCellValueFactory(param -> param.getValue().checkedProperty());
        columnNameColumn.setCellValueFactory(param -> param.getValue().columnNameProperty());
        jdbcTypeColumn.setCellValueFactory(param -> param.getValue().jdbcTypeProperty());
        propertyNameColumn.setCellValueFactory(param -> param.getValue().propertyNameProperty());
        typeHandlerColumn.setCellValueFactory(param -> param.getValue().typeHandleProperty());

        jdbcTypeColumn.setCellFactory(param -> {
            Tooltip tooltip = new Tooltip("如果要定制列的Java数据类型, 编辑Java Type和Property Name或者你自己的Type Handler, 注意要按Enter键保存，然后再点击确认方可生效。");
            TextFieldTableCell<ColumnCustomConfiguration, String> cell = new TextFieldTableCell<>();
            cell.setTooltip(tooltip);
            return cell;
        });

        jdbcTypeColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setJdbcType(event.getNewValue());
        });
        javaTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
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
     * 定制表配置
     *
     * @param tableInfo 表生成信息
     */
    @Subscribe(name = "CustomizeTable")
    public void customize(TableCodeGeneration tableInfo) {
        // 获取数据库表的所有列信息
        List<ColumnCustomConfiguration> columns = new ArrayList<>();

        String connectionName = tableInfo.getConnectionName();
        ConnectionInfo connectionConfig = ConnectionRegistry.getConnectionConfiguration(connectionName);

        try (Connection conn = connectionConfig.getConnection()) {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getColumns(tableInfo.getDatabaseName(), null, tableInfo.getTableName(), null);
            while (rs.next()) {
                ColumnCustomConfiguration columnVO = new ColumnCustomConfiguration();
                columnVO.setColumnName(rs.getString("COLUMN_NAME"));
                columnVO.setJdbcType(rs.getString("TYPE_NAME"));
                columns.add(columnVO);
            }
        } catch (Exception e) {
            Alerts.exception("", e).show();
        }
        labelCurrentTableName.setText(tableInfo.getTableName());
        columnListView.setItems(FXCollections.observableList(columns));
    }

    /**
     * 应用配置项目
     */
    @FXML
    public void applyConfig(ActionEvent event) {
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
        }
    }

    @FXML
    public void configAction(ActionEvent event) {
        ViewLoader viewLoader = ViewLoader.load(TableColumnConfigsController.class);
        StageManager.show("定制列配置", viewLoader.getRoot());
        TableColumnConfigsController controller = viewLoader.getViewController();
        controller.setColumnListView(this.columnListView);
        // controller.setTableName(this.tableName);
        getStage(event).show();
    }
}
