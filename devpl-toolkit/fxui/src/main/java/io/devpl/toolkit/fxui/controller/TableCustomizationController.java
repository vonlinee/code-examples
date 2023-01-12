package io.devpl.toolkit.fxui.controller;

import io.devpl.toolkit.framework.mvc.FxmlView;
import io.devpl.toolkit.framework.mvc.ViewLoader;
import io.devpl.toolkit.framework.utils.StageHelper;
import io.devpl.toolkit.fxui.event.CommandEvent;
import io.devpl.toolkit.framework.Alerts;
import io.devpl.toolkit.framework.mvc.AbstractViewController;
import io.devpl.toolkit.fxui.model.TableCodeGeneration;
import io.devpl.toolkit.fxui.model.TableCodeGenOption;
import io.devpl.toolkit.fxui.model.props.ColumnCustomConfiguration;
import io.devpl.toolkit.fxui.utils.CollectionUtils;
import io.devpl.toolkit.fxui.utils.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 表定制化控制器
 */
@FxmlView(location = "static/fxml/table_customization.fxml")
public class TableCustomizationController extends AbstractViewController {

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

    TableCodeGeneration tableInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accoConfig.setExpandedPane(accoConfig.getPanes().get(1));
        accoConfig.expandedPaneProperty().addListener((observable, oldValue, newValue) -> {
            final ObservableList<TitledPane> panes = accoConfig.getPanes();
            int expanedCount = 0;
            for (TitledPane pane : panes) {
                if (pane.isExpanded()) expanedCount++;
            }
            if (expanedCount == 0) {
                accoConfig.setExpandedPane(panes.get(0));
            }
        });

        checkedColumn.setCellFactory(param -> {
            TableCell<ColumnCustomConfiguration, Boolean> cell = new CheckBoxTableCell<>(null);
            cell.setTooltip(new Tooltip("如果不想生成某列请取消勾选对应的列"));
            return cell;
        });

        checkedColumn.setCellValueFactory(new PropertyValueFactory<>("checked"));
        columnNameColumn.setCellValueFactory(new PropertyValueFactory<>("columnName"));
        jdbcTypeColumn.setCellValueFactory(new PropertyValueFactory<>("jdbcType"));
        propertyNameColumn.setCellValueFactory(new PropertyValueFactory<>("propertyName"));
        typeHandlerColumn.setCellValueFactory(new PropertyValueFactory<>("typeHandler"));

        jdbcTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        jdbcTypeColumn.setCellFactory(param -> {
            Tooltip tooltip = new Tooltip("如果要定制列的Java数据类型, 编辑Java Type和Property Name或者你自己的Type Handler, 注意要按Enter键保存，然后再点击确认方可生效。");
            TextFieldTableCell<ColumnCustomConfiguration, String> cell = new TextFieldTableCell<>();
            cell.setTooltip(tooltip);
            return cell;
        });

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
        // 初始化数据
        root.addEventHandler(CommandEvent.COMMAND, event -> {
            tableInfo = (TableCodeGeneration) event.getData();
            List<ColumnCustomConfiguration> tableColumns = null;
            try {
                tableColumns = DBUtils.getTableColumns(tableInfo.getDatabaseInfo(), tableInfo.getTableName());
            } catch (Exception e) {
                Alerts.exception("", e).show();
            }
            assert tableColumns != null;
            labelCurrentTableName.setText(tableInfo.getTableName());
            columnListView.setItems(FXCollections.observableList(tableColumns));
            // 每次都是重新加载FXML
            initTableCodeGenerationOptionBindding(tableInfo.getOption());
        });
        labelCurrentTableName.setOnMouseClicked(event1 -> {
            System.out.println(tableInfo.getOption());
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
        final ViewLoader viewLoader = ViewLoader.load(TableColumnConfigsController.class);
        StageHelper.show("定制列配置", viewLoader.getRoot());
        TableColumnConfigsController controller = viewLoader.getViewController();
        controller.setColumnListView(this.columnListView);
        // controller.setTableName(this.tableName);
        getStage(event).show();
    }
}
