package io.devpl.toolkit.fxui.controller;

import com.jcraft.jsch.Session;
import de.saxsys.mvvmfx.core.FxmlLocation;
import de.saxsys.mvvmfx.utils.StageHelper;
import io.devpl.toolkit.fxui.bridge.MyBatisCodeGenerator;
import io.devpl.toolkit.fxui.common.FXMLPage;
import io.devpl.toolkit.fxui.common.ProgressDialog;
import io.devpl.toolkit.fxui.model.props.GenericConfiguration;
import io.devpl.toolkit.fxui.model.DatabaseInfo;
import io.devpl.toolkit.fxui.model.TableCodeGeneration;
import io.devpl.toolkit.fxui.event.CommandEvent;
import io.devpl.toolkit.fxui.event.LoadDbTreeEvent;
import io.devpl.toolkit.fxui.event.UpdateCodeGenConfigEvent;
import io.devpl.toolkit.fxui.framework.Alerts;
import io.devpl.toolkit.fxui.framework.JFX;
import io.devpl.toolkit.fxui.framework.mvc.FXControllerBase;
import io.devpl.toolkit.fxui.model.DbTreeViewCellFactory;
import io.devpl.toolkit.fxui.utils.*;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@FxmlLocation(location = "static/fxml/MainUI.fxml")
public class MainUIController extends FXControllerBase {

    @FXML
    public VBox vboxLeft;
    @FXML
    private Label connectionLabel; // toolbar buttons
    @FXML
    private Label configsLabel;
    @FXML
    public TextField txfParentPackageName;
    @FXML
    private TextField modelTargetPackage;
    @FXML
    private TextField mapperTargetPackage;
    @FXML
    private TextField txfMapperPackageName;  // DAO接口包名
    @FXML
    private TextField modelTargetProject; // 实体类存放目录
    @FXML
    private TextField mappingTargetProject;
    @FXML
    private TextField daoTargetProject;
    @FXML
    private TextField projectFolderField;
    @FXML
    private TreeView<String> trvDbTreeList; // 数据库表列表，只读
    @FXML
    public TextField filterTreeBox; // TODO 过滤数据库表
    @FXML
    public TableView<TableCodeGeneration> tblvTableCustomization;
    @FXML
    public TableColumn<TableCodeGeneration, String> tblcDbName;
    @FXML
    public TableColumn<TableCodeGeneration, String> tblcTableName;

    // Current selected databaseConfig
    public DatabaseInfo selectedDatabaseConfig;
    private final MyBatisCodeGenerator mbgGenerator = new MyBatisCodeGenerator();
    // 通用配置项
    private final GenericConfiguration codeGenConfig = new GenericConfiguration();
    // 保存哪些表需要进行代码生成
    private final Map<String, TableCodeGeneration> tableConfigsToBeGenerated = new ConcurrentHashMap<>(10);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindCodeGenConfiguration(codeGenConfig);
        registerThis();
        // 新建连接
        connectionLabel.setGraphic(JFX.loadImageView("static/icons/computer.png", 40));
        connectionLabel.setOnMouseClicked(event -> {
            StageHelper.show(FXMLLoaderUtils.load(FXMLPage.NEW_CONNECTION.getLocation()));
        });
        // 生成配置管理
        configsLabel.setGraphic(JFX.loadImageView("static/icons/config-list.png", 40));
        configsLabel.setOnMouseClicked(event -> {
            StageHelper.show(FXMLLoaderUtils.load(FXMLPage.GENERATOR_CONFIG.getLocation()));
        });
        trvDbTreeList.prefHeightProperty().bind(vboxLeft.heightProperty().subtract(filterTreeBox.getHeight()));
        trvDbTreeList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // 设置可以多选

        // 设置单元格工厂 Callback<TreeView<T>, TreeCell<T>> value
        trvDbTreeList.setCellFactory(new DbTreeViewCellFactory());
        loadLeftDBTree();

        // 新增一行
        trvDbTreeList.addEventHandler(CommandEvent.TABLES_SELECTED, event -> {
            ObservableList<TreeItem<String>> selectedItems = trvDbTreeList.getSelectionModel().getSelectedItems();
            for (TreeItem<String> selectedItem : selectedItems) {
                String tableName = selectedItem.getValue();
                if (tableConfigsToBeGenerated.containsKey(tableName)) {
                    continue;
                }
                TableCodeGeneration tableConfig = new TableCodeGeneration();
                tableConfig.setTableName(tableName);
                tableConfig.setDatabaseInfo((DatabaseInfo) selectedItem.getParent().getGraphic().getUserData());
                tableConfig.setDbName(selectedItem.getParent().getValue());
                tableConfigsToBeGenerated.put(tableName, tableConfig);
                tblvTableCustomization.getItems().add(tableConfig);
            }
            event.consume();
        });

        trvDbTreeList.addEventHandler(CommandEvent.OPEN_DB_CONNECTION, event -> {
            NewConnectionController controller = (NewConnectionController) loadFXMLPage("编辑数据库连接", FXMLPage.NEW_CONNECTION, false);
            controller.setConfig((DatabaseInfo) event.getData());
            // 此处MenuItem不是Node类型
            getStage(trvDbTreeList).show();
        });

        tblcDbName.setCellValueFactory(new PropertyValueFactory<>("dbName"));
        tblcDbName.setCellFactory(param -> {
            TableCell<TableCodeGeneration, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.setTextAlignment(TextAlignment.CENTER);
            return cell;
        });
        tblcTableName.setCellValueFactory(new PropertyValueFactory<>("tableName"));
        tblcTableName.setCellFactory(param -> {
            TableCell<TableCodeGeneration, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.setTextAlignment(TextAlignment.CENTER);
            return cell;
        });
        tblvTableCustomization.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblvTableCustomization.setRowFactory(param -> {
            TableRow<TableCodeGeneration> row = new TableRow<>();
            MenuItem deleteThisRowMenuItem = new MenuItem("删除");
            MenuItem customizeMenuItem = new MenuItem("定制列");
            deleteThisRowMenuItem.setOnAction(event -> {
                final TableCodeGeneration item = param.getSelectionModel().getSelectedItem();
                param.getItems().remove(item);
                tableConfigsToBeGenerated.remove(item.getTableName()); // 移除该表
            });
            customizeMenuItem.setOnAction(event -> {
                TableCodeGeneration item = param.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(FXMLPage.SELECT_TABLE_COLUMN.getLocation());
                try {
                    Parent root = loader.load();
                    Event.fireEvent(root, new CommandEvent(CommandEvent.COMMAND, item));
                    final Stage stage = new Stage();
                    stage.setTitle("表生成定制");
                    stage.setScene(new Scene(root));
                    stage.show();
                    event.consume();
                } catch (IOException e) {
                    Alerts.exception("", e).show();
                }
            });

            ContextMenu menu = new ContextMenu(deleteThisRowMenuItem, customizeMenuItem);
            row.setContextMenu(menu);
            return row;
        });
    }

    @Subscribe
    public void loadDbInfo(LoadDbTreeEvent event) {
        loadLeftDBTree();
    }

    public void loadLeftDBTree() {
        TreeItem<String> rootTreeItem = trvDbTreeList.getRoot();
        rootTreeItem.getChildren().clear();
        try {
            // 加载所有的数据库配置
            List<DatabaseInfo> dbConfigs = ConfigHelper.loadDatabaseConfig();
            for (DatabaseInfo dbConfig : dbConfigs) {
                TreeItem<String> treeItem = new TreeItem<>();
                treeItem.setValue(dbConfig.getName());
                treeItem.setGraphic(JFX.loadImageView("static/icons/computer.png", 16, dbConfig));
                rootTreeItem.getChildren().add(treeItem);
            }
        } catch (Exception e) {
            Alerts.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e)).showAndWait();
        }
    }

    /**
     * 选择项目文件夹
     * @param event 事件
     */
    @FXML
    public void chooseProjectFolder(ActionEvent event) {
        FileChooserDialog.showDirectoryDialog(getStage(event)).ifPresent(file -> {
            projectFolderField.setText(file.getAbsolutePath());
        });
    }

    @FXML
    public void generateCode(ActionEvent actionEvent) {
        if (tableConfigsToBeGenerated.isEmpty()) {
            return;
        }
        if (StringUtils.hasNotText(codeGenConfig.getProjectFolder())) {
            Alerts.error("项目根目录不能为空").show();
            return;
        }
        if (!checkDirs(codeGenConfig)) {
            return;
        }
        mbgGenerator.setGeneratorConfig(codeGenConfig);
        mbgGenerator.setDatabaseConfig(selectedDatabaseConfig);
        // 进度回调
        ProgressDialog alert = new ProgressDialog(Alert.AlertType.INFORMATION);
        mbgGenerator.setProgressCallback(alert);
        alert.show();
        PictureProcessStateController pictureProcessStateController = null;
        try {
            // Engage PortForwarding
            Session sshSession = DBUtils.getSSHSession(selectedDatabaseConfig);
            DBUtils.engagePortForwarding(sshSession, selectedDatabaseConfig);
            if (sshSession != null) {
                pictureProcessStateController = new PictureProcessStateController();
                pictureProcessStateController.startPlay();
            }
            try {
                mbgGenerator.generate();
            } catch (Exception exception) {
                alert.closeIfShowing();
                Alerts.exception("生成失败", exception).showAndWait();
            }
            if (pictureProcessStateController != null) {
                Task<Void> task = new DoNothing();
                PictureProcessStateController finalPictureProcessStateController = pictureProcessStateController;
                task.setOnSucceeded(event -> finalPictureProcessStateController.close());
                task.setOnFailed(event -> finalPictureProcessStateController.close());
                new Thread(task).start();
            }
        } catch (Exception e) {
            Alerts.error(e.getMessage()).showAndWait();
            if (pictureProcessStateController != null) {
                pictureProcessStateController.close();
                pictureProcessStateController.playFailState(e.getMessage(), true);
            }
        }
    }

    static class DoNothing extends Task<Void> {
        @Override
        protected Void call() throws Exception {
            Thread.sleep(3000);
            return null;
        }
    }

    @Subscribe
    public void updateCodeGenConfig(UpdateCodeGenConfigEvent event) {
        log.info("更新代码生成配置", event);
    }

    /**
     * 绑定数据
     * @param generatorConfig 代码生成配置
     */
    public void bindCodeGenConfiguration(GenericConfiguration generatorConfig) {
        projectFolderField.textProperty().bindBidirectional(generatorConfig.projectFolderProperty());
        modelTargetPackage.textProperty().bindBidirectional(generatorConfig.modelPackageProperty());
        modelTargetProject.textProperty().bindBidirectional(generatorConfig.modelPackageTargetFolderProperty());
        txfParentPackageName.textProperty().bindBidirectional(generatorConfig.parentPackageProperty());
        txfMapperPackageName.textProperty().bindBidirectional(generatorConfig.daoPackageProperty());
        daoTargetProject.textProperty().bindBidirectional(generatorConfig.daoTargetFolderProperty());
        mapperTargetPackage.textProperty().bindBidirectional(generatorConfig.mappingXMLPackageProperty());
        mappingTargetProject.textProperty().bindBidirectional(generatorConfig.mappingXMLTargetFolderProperty());
    }

    /**
     * 检查并创建不存在的文件夹
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
            Optional<ButtonType> optional = Alerts.confirm("以下目录不存在, 是否创建?\n" + sb).showAndWait();
            if (!optional.isPresent()) {
                System.out.println(111);
            } else {
                if (ButtonType.OK == optional.get()) {
                    try {
                        return FileUtils.forceMkdir(targetDirs);
                    } catch (Exception e) {
                        Alerts.error(Messages.getString("PromptText.3")).show();
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    // private void setTooltip() {
    //     generateKeysField.setTooltip(new Tooltip("insert时可以返回主键ID"));
    //     offsetLimitCheckBox.setTooltip(new Tooltip("是否要生成分页查询代码"));
    //     commentCheckBox.setTooltip(new Tooltip("使用数据库的列注释作为实体类字段名的Java注释 "));
    //     useActualColumnNamesCheckbox.setTooltip(new Tooltip("是否使用数据库实际的列名作为实体类域的名称"));
    //     useTableNameAliasCheckbox.setTooltip(new Tooltip("在Mapper XML文件中表名使用别名，并且列全部使用as查询"));
    //     overrideXML.setTooltip(new Tooltip("重新生成时把原XML文件覆盖，否则是追加"));
    //     useDAOExtendStyle.setTooltip(new Tooltip("将通用接口方法放在公共接口中，DAO接口留空"));
    //     forUpdateCheckBox.setTooltip(new Tooltip("在Select语句中增加for update后缀"));
    //     JFX.setTooltip(useLombokPlugin, "实体类使用Lombok @Data简化代码");
    // }
}
