package io.devpl.tookit.fxui.controller.mbg;

import io.devpl.codegen.mbpg.jdbc.meta.TableMetadata;
import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.fxtras.utils.StageManager;
import io.devpl.tookit.fxui.bridge.MyBatisCodeGenerator;
import io.devpl.tookit.fxui.controller.TableCustomizationController;
import io.devpl.tookit.fxui.event.Events;
import io.devpl.tookit.fxui.model.ConnectionInfo;
import io.devpl.tookit.fxui.model.ConnectionRegistry;
import io.devpl.tookit.fxui.model.ProjectConfiguration;
import io.devpl.tookit.fxui.model.TableCodeGeneration;
import io.devpl.tookit.utils.DBUtils;
import io.devpl.tookit.utils.FileUtils;
import io.devpl.tookit.utils.Messages;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.TextAlignment;
import org.greenrobot.eventbus.Subscribe;

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
    public TableView<TableCodeGeneration> tblvTableCustomization;
    @FXML
    public TableColumn<TableCodeGeneration, String> tblcTableName;
    @FXML
    public TableColumn<TableCodeGeneration, String> tblcTableComment;

    /**
     * 保存哪些表需要进行代码生成
     * 存放的key:TableCodeGenConfig#getUniqueKey()
     *
     * @see TableCodeGeneration#getUniqueKey()
     */
    private final Map<String, TableCodeGeneration> tableConfigsToBeGenerated = new ConcurrentHashMap<>(10);

    @FXML
    public ComboBox<String> cboxConnection;
    @FXML
    public ComboBox<String> cboxDatabase;
    // 选中的表
    @FXML
    public TableView<TableCodeGeneration> tblvTableSelected;
    @FXML
    public TableColumn<TableCodeGeneration, String> tblcSelectedTableName;
    @FXML
    public TableColumn<TableCodeGeneration, String> tblcSelectedTableComment;

    // 进度回调
    // ProgressDialog alert = new ProgressDialog(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cboxConnection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ConnectionInfo cf = ConnectionRegistry.getConnectionConfiguration(newValue);
                try (Connection connection = cf.getConnection()) {
                    List<String> databaseNames = DBUtils.getDatabaseNames(connection);
                    cboxDatabase.getItems().addAll(databaseNames);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        cboxDatabase.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String selectedItem = cboxConnection.getSelectionModel().getSelectedItem();
            ConnectionInfo cf = ConnectionRegistry.getConnectionConfiguration(selectedItem);
            final ObservableList<TableCodeGeneration> items = tblvTableCustomization.getItems();
            if (items.size() > 0) {
                items.clear();
            }
            try (Connection connection = cf.getConnection(newValue)) {
                List<TableMetadata> tablesMetadata = DBUtils.getTablesMetadata(connection);
                for (TableMetadata tablesMetadatum : tablesMetadata) {
                    TableCodeGeneration tcgf = new TableCodeGeneration();
                    tcgf.setConnectionName(selectedItem);
                    tcgf.setDatabaseName(newValue);
                    tcgf.setTableName(tablesMetadatum.getTableName());
                    items.add(tcgf);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        cboxConnection.getItems().addAll(ConnectionRegistry.getRegisteredConnectionConfigMap().keySet());
        if (!cboxConnection.getItems().isEmpty()) {
            cboxConnection.getSelectionModel().select(0);
        }

        tblcTableName.setCellValueFactory(new PropertyValueFactory<>("tableName"));
        tblcTableName.setCellFactory(param -> {
            TableCell<TableCodeGeneration, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.setTextAlignment(TextAlignment.CENTER);
            return cell;
        });
        tblcSelectedTableName.setCellValueFactory(new PropertyValueFactory<>("tableName"));
        tblcSelectedTableName.setCellFactory(param -> {
            TableCell<TableCodeGeneration, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.setTextAlignment(TextAlignment.CENTER);
            return cell;
        });

        tblvTableCustomization.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblvTableSelected.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblvTableSelected.setRowFactory(param -> {
            TableRow<TableCodeGeneration> row = new TableRow<>();
            MenuItem deleteThisRowMenuItem = new MenuItem("删除");
            MenuItem customizeMenuItem = new MenuItem("定制");
            deleteThisRowMenuItem.setOnAction(event -> {
                TableCodeGeneration item = param.getSelectionModel().getSelectedItem();
                param.getItems().remove(item);
                tableConfigsToBeGenerated.remove(item.getUniqueKey()); // 移除该表
            });
            customizeMenuItem.setOnAction(event -> {
                // 表定制事件
                TableCodeGeneration item = param.getSelectionModel().getSelectedItem();
                Parent root = ViewLoader.load(TableCustomizationController.class).getRoot();

                this.publish("CustomizeTable", item);
                StageManager.show("表生成定制", root);
                event.consume();
            });
            ContextMenu menu = new ContextMenu(deleteThisRowMenuItem, customizeMenuItem);
            row.setContextMenu(menu);
            return row;
        });

        tblvTableCustomization.setRowFactory(param -> {
            TableRow<TableCodeGeneration> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (Events.isPrimaryButtonDoubleClicked(event)) {
                    final TableCodeGeneration tableItem = row.getItem();
                    final String uniqueKey = tableItem.getUniqueKey();
                    if (!tableConfigsToBeGenerated.containsKey(uniqueKey)) {
                        tableConfigsToBeGenerated.put(uniqueKey, tableItem);
                        tblvTableSelected.getItems().add(tableItem);
                    }
                }
            });
            return row;
        });
    }

    @FXML
    public void generateCode(ActionEvent actionEvent) {
        if (tableConfigsToBeGenerated.isEmpty()) {
            Alerts.error("选择的数据表为空").show();
            return;
        }
        publish("PrepareGeneration", tableConfigsToBeGenerated);
    }

    @Subscribe(name = "DoGeneration")
    void generate(ProjectConfiguration projectConfig) {
        MyBatisCodeGenerator mbgGenerator = new MyBatisCodeGenerator();
        mbgGenerator.setGeneratorConfig(projectConfig);
        try {
            mbgGenerator.generate(tableConfigsToBeGenerated.values());
        } catch (Exception exception) {
            exception.printStackTrace();
            Alerts.exception("生成失败", exception).showAndWait();
        }
    }

    /**
     * 检查并创建不存在的文件夹
     *
     * @return 是否创建成功
     */
    private boolean checkDirs(ProjectConfiguration config) {
        List<Path> targetDirs = new ArrayList<>();
        targetDirs.add(Path.of(config.getProjectRootFolder()));
        // targetDirs.add(config.getEntityTargetDirectory());
        // targetDirs.add(config.getMappingXMLTargetDirectory());
        // targetDirs.add(config.getMapperTargetDirectory());
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

    /**
     * 添加一个表到需要进行代码生成的表
     *
     * @param tableInfo
     */
    @Subscribe
    public void addTable(TableCodeGeneration tableInfo) {
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

    @FXML
    public void openMBGConfigPane(ActionEvent actionEvent) {
        StageManager.show(MBGConfigurationView.class);
    }
}
