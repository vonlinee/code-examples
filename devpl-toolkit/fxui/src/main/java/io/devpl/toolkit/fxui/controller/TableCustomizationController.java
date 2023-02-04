package io.devpl.toolkit.fxui.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import io.devpl.toolkit.fxui.model.ConnectionRegistry;
import io.devpl.toolkit.fxui.model.props.ConnectionConfig;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.fxtras.utils.StageHelper;
import io.devpl.toolkit.fxui.event.CommandEvent;
import io.devpl.toolkit.fxui.model.TableCodeGenOption;
import io.devpl.toolkit.fxui.model.TableCodeGenConfig;
import io.devpl.toolkit.fxui.model.props.ColumnCustomConfiguration;
import io.devpl.toolkit.fxui.utils.CollectionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;

/**
 * 表定制化控制器: 配置单个表的代码生成效果
 */
@FxmlLocation(location = "static/fxml/table_customization.fxml", title = "表生成定制")
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
    public CheckBox chbUseExample;
    @FXML
    public CheckBox chbOffsetLimit;
    @FXML
    public CheckBox chbComment;
    @FXML
    public CheckBox chbOverrideXML;
    @FXML
    public CheckBox chbUseLombokPlugin;
    @FXML
    public CheckBox chbNeedToStringHashcodeEquals;
    @FXML
    public CheckBox chbUseSchemaPrefix;
    @FXML
    public CheckBox chbAnnotationDao;
    @FXML
    public CheckBox chbForUpdate;
    @FXML
    public CheckBox chbMapperExtend;
    @FXML
    public CheckBox chbJsr310Support;
    @FXML
    public CheckBox annotationCheckBox;
    @FXML
    public CheckBox useActualColumnNamesCheckbox;
    @FXML
    public CheckBox useTableNameAliasCheckbox;
    @FXML
    public CheckBox addMapperAnnotationChcekBox;
    @FXML
    public CheckBox chbEnableSwagger;
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
        accoConfig.setExpandedPane(accoConfig.getPanes().get(1));
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
        // 初始化数据
        root.addEventHandler(CommandEvent.COMMAND, event -> {
            TableCodeGenConfig tableInfo = (TableCodeGenConfig) event.getData();
            // 获取数据库表的所有列信息
            List<ColumnCustomConfiguration> columns = new ArrayList<>();

            String connectionName = tableInfo.getConnectionName();
            ConnectionConfig connectionConfig = ConnectionRegistry.getConnectionConfiguration(connectionName);

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
            // 每次都是重新加载FXML
            initTableCodeGenerationOptionBindding(tableInfo.getOption());
        });
    }

    /**
     * 初始化表生成选项数据绑定
     * @param option 表生成选项
     */
    public void initTableCodeGenerationOptionBindding(TableCodeGenOption option) {
        // TODO 只绑定一次，如果每次绑定的根节点没有变化，那么无需进行绑定
        option.useExampleProperty().bind(chbUseExample.selectedProperty());
        option.annotationDAOProperty().bind(chbAnnotationDao.selectedProperty());
        option.commentProperty().bind(chbComment.selectedProperty());
        option.overrideXMLProperty().bind(chbOverrideXML.selectedProperty());
        option.jsr310SupportProperty().bind(chbJsr310Support.selectedProperty());
        option.useSchemaPrefixProperty().bind(chbUseSchemaPrefix.selectedProperty());
        option.useDAOExtendStyleProperty().bind(chbMapperExtend.selectedProperty());
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
        StageHelper.show("定制列配置", viewLoader.getRoot());
        TableColumnConfigsController controller = viewLoader.getViewController();
        controller.setColumnListView(this.columnListView);
        // controller.setTableName(this.tableName);
        getStage(event).show();
    }
}
