package io.devpl.toolkit.fxui.controller.mbg;

import io.devpl.codegen.mbpg.jdbc.meta.TableMetadata;
import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.fxtras.utils.StageHelper;
import io.devpl.toolkit.fxui.bridge.MyBatisCodeGenerator;
import io.devpl.toolkit.fxui.controller.ClassDefinitionController;
import io.devpl.toolkit.fxui.controller.ConnectionManageController;
import io.devpl.toolkit.fxui.controller.TableCustomizationController;
import io.devpl.toolkit.fxui.event.CommandEvent;
import io.devpl.toolkit.fxui.model.ConnectionRegistry;
import io.devpl.toolkit.fxui.model.TableCodeGenConfig;
import io.devpl.toolkit.fxui.model.props.ConnectionConfig;
import io.devpl.toolkit.fxui.model.props.GenericConfiguration;
import io.devpl.toolkit.fxui.utils.DBUtils;
import io.devpl.toolkit.fxui.utils.FileUtils;
import io.devpl.toolkit.fxui.utils.Messages;
import io.devpl.toolkit.fxui.utils.StringUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MyBatis Code Generation
 */
@FxmlLocation(location = "layout/mbg/MyBatisCodeGenerationView.fxml")
public class MyBatisCodeGenerationView extends FxmlView {

    @FXML
    public HBox hboxCodeGenOperationRoot; // 代码生成操作根容器
    @FXML
    public TableView<TableCodeGenConfig> tblvTableCustomization;
    @FXML
    public TableColumn<TableCodeGenConfig, String> tblcSelected;
    @FXML
    public TableColumn<TableCodeGenConfig, String> tblcDbName;
    @FXML
    public TableColumn<TableCodeGenConfig, String> tblcTableName;

    private final MyBatisCodeGenerator mbgGenerator = new MyBatisCodeGenerator();

    /**
     * 保存哪些表需要进行代码生成
     * 存放的key:TableCodeGenConfig#getUniqueKey()
     *
     * @see io.devpl.toolkit.fxui.model.TableCodeGenConfig#getUniqueKey()
     */
    private final Map<String, TableCodeGenConfig> tableConfigsToBeGenerated = new ConcurrentHashMap<>(10);

    @FXML
    public ComboBox<String> cboxConnection;
    @FXML
    public ComboBox<String> cboxDatabase;

    // 进度回调
    // ProgressDialog alert = new ProgressDialog(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cboxConnection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ConnectionConfig cf = ConnectionRegistry.getConnectionConfiguration(newValue);
                try (Connection connection = cf.getConnection()) {
                    List<String> databaseNames = DBUtils.getDatabaseNames(connection);
                    cboxDatabase.getItems().addAll(databaseNames);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        cboxDatabase.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedItem = cboxConnection.getSelectionModel().getSelectedItem();
                ConnectionConfig cf = ConnectionRegistry.getConnectionConfiguration(selectedItem);
                try (Connection connection = cf.getConnection(newValue)) {
                    List<TableMetadata> tablesMetadata = DBUtils.getTablesMetadata(connection);
                    for (TableMetadata tablesMetadatum : tablesMetadata) {
                        TableCodeGenConfig tcgf = new TableCodeGenConfig();
                        tcgf.setDatabaseName(newValue);
                        tcgf.setTableName(tablesMetadatum.getTableName());
                        tblvTableCustomization.getItems().add(tcgf);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        cboxConnection.getItems().addAll(ConnectionRegistry.getRegisteredConnectionConfigMap().keySet());
        if (!cboxConnection.getItems().isEmpty()) {
            cboxConnection.getSelectionModel().select(0);
        }

        // mbgGenerator.setProgressCallback(alert);

        tblcDbName.setCellValueFactory(new PropertyValueFactory<>("databaseName"));
        tblcDbName.setCellFactory(param -> {
            TableCell<TableCodeGenConfig, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.setTextAlignment(TextAlignment.CENTER);
            return cell;
        });
        tblcTableName.setCellValueFactory(new PropertyValueFactory<>("tableName"));
        tblcTableName.setCellFactory(param -> {
            TableCell<TableCodeGenConfig, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.setTextAlignment(TextAlignment.CENTER);
            return cell;
        });

        tblcSelected.setCellFactory(ChoiceBoxTableCell.forTableColumn());

        tblvTableCustomization.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblvTableCustomization.setRowFactory(param -> {
            TableRow<TableCodeGenConfig> row = new TableRow<>();
            MenuItem deleteThisRowMenuItem = new MenuItem("删除");
            MenuItem customizeMenuItem = new MenuItem("定制列");
            deleteThisRowMenuItem.setOnAction(event -> {
                TableCodeGenConfig item = param.getSelectionModel().getSelectedItem();
                param.getItems().remove(item);
                tableConfigsToBeGenerated.remove(item.getUniqueKey()); // 移除该表
            });
            customizeMenuItem.setOnAction(event -> {
                // 表定制事件
                TableCodeGenConfig item = param.getSelectionModel().getSelectedItem();
                Parent root = ViewLoader.load(TableCustomizationController.class).getRoot();
                Event.fireEvent(root, new CommandEvent(CommandEvent.COMMAND, item));
                StageHelper.show("", root);
                event.consume();
            });
            ContextMenu menu = new ContextMenu(deleteThisRowMenuItem, customizeMenuItem);
            row.setContextMenu(menu);
            return row;
        });
    }

    @FXML
    public void generateCode(ActionEvent actionEvent) {
        if (tableConfigsToBeGenerated.isEmpty()) {
            return;
        }
        mbgGenerator.setGeneratorConfig(null);
        // alert.show();
        try {
            mbgGenerator.generate(tableConfigsToBeGenerated.values());
        } catch (Exception exception) {
            exception.printStackTrace();
            // alert.closeIfShowing();
            Alerts.exception("生成失败", exception).showAndWait();
        }
    }

    /**
     * 检查并创建不存在的文件夹
     *
     * @return 是否创建成功
     */
    private boolean checkDirs(GenericConfiguration config) {
        List<Path> targetDirs = new ArrayList<>();
        targetDirs.add(Path.of(config.getProjectFolder()));
        targetDirs.add(config.getEntityTargetDirectory());
        targetDirs.add(config.getMappingXMLTargetDirectory());
        targetDirs.add(config.getMapperTargetDirectory());
        StringBuilder sb = new StringBuilder();
        for (Path dir : targetDirs) {
            if (!Files.exists(dir)) {
                sb.append(dir).append("\n");
            }
        }
        if (sb.length() > 0) {
            Alerts.confirm("以下目录不存在, 是否创建?\n" + sb).showAndWait().ifPresent(buttonType -> {
                if (ButtonType.OK == buttonType) {
                    try {
                        FileUtils.forceMkdir(targetDirs);
                    } catch (Exception e) {
                        Alerts.error(Messages.getString("PromptText.3")).show();
                    }
                }
            });
        }
        return true;
    }

    @FXML
    public void showClassEditorDialogPane(MouseEvent mouseEvent) {
        StageHelper.show(ClassDefinitionController.class);
    }

    @FXML
    public void showConnectinManagePane(MouseEvent mouseEvent) {
        StageHelper.show(ConnectionManageController.class);
    }

    /**
     * 添加一个表到需要进行代码生成的表
     *
     * @param tableInfo
     */
    @Subscribe
    public void addTable(TableCodeGenConfig tableInfo) {
        String key = tableInfo.getUniqueKey();
        if (tableConfigsToBeGenerated.containsKey(key)) {
            return;
        }
        tableConfigsToBeGenerated.put(key, tableInfo);
        tblvTableCustomization.getItems().add(tableInfo);
    }

    @FXML
    public void openTargetRootDirectory(ActionEvent actionEvent) {

    }
}
