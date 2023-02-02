package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.JFX;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.fxtras.utils.StageHelper;
import io.devpl.toolkit.fxui.bridge.MyBatisCodeGenerator;
import io.devpl.toolkit.fxui.common.ProgressDialog;
import io.devpl.toolkit.fxui.event.CommandEvent;
import io.devpl.toolkit.fxui.model.TableCodeGenConfig;
import io.devpl.toolkit.fxui.model.props.GenericConfiguration;
import io.devpl.toolkit.fxui.utils.FileChooserDialog;
import io.devpl.toolkit.fxui.utils.FileUtils;
import io.devpl.toolkit.fxui.utils.Messages;
import io.devpl.toolkit.fxui.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 主窗口控制器
 */
@FxmlLocation(location = "static/fxml/MainUI.fxml")
public class MainFrameController extends FxmlView {

    @FXML
    public VBox vboxCodeGenConfigRoot; // 代码生成配置根容器
    @FXML
    public HBox hboxCodeGenOperationRoot; // 代码生成操作根容器
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
    public TableView<TableCodeGenConfig> tblvTableCustomization;
    @FXML
    public TableColumn<TableCodeGenConfig, String> tblcDbName;
    @FXML
    public TableColumn<TableCodeGenConfig, String> tblcTableName;

    private final MyBatisCodeGenerator mbgGenerator = new MyBatisCodeGenerator();

    /**
     * 通用配置项
     */
    private final GenericConfiguration codeGenConfig = new GenericConfiguration();

    /**
     * 保存哪些表需要进行代码生成
     * 存放的key:TableCodeGenConfig#getUniqueKey()
     * @see io.devpl.toolkit.fxui.model.TableCodeGenConfig#getUniqueKey()
     */
    private final Map<String, TableCodeGenConfig> tableConfigsToBeGenerated
            = new ConcurrentHashMap<>(10);

    // 进度回调
    ProgressDialog alert = new ProgressDialog(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mbgGenerator.setProgressCallback(alert);
        bindCodeGenConfiguration(codeGenConfig);
        // 生成配置管理
        configsLabel.setOnMouseClicked(event -> StageHelper.show("生成配置", GeneratorConfigController.class));

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
                TableCodeGenConfig item = param.getSelectionModel().getSelectedItem();
                Parent root = ViewLoader.load(TableCustomizationController.class).getRoot();
                Event.fireEvent(root, new CommandEvent(CommandEvent.COMMAND, item));
                StageHelper.show("表生成定制", root);
                event.consume();
            });
            ContextMenu menu = new ContextMenu(deleteThisRowMenuItem, customizeMenuItem);
            row.setContextMenu(menu);
            return row;
        });

        // TODO 删除
        fillDefaultCodeGenConfig(null);
    }

    /**
     * 选择项目文件夹
     * @param event 事件
     */
    @FXML
    public void chooseProjectFolder(ActionEvent event) {
        FileChooserDialog.showDirectoryDialog(getStage(event))
                .ifPresent(file -> projectFolderField.setText(file.getAbsolutePath()));
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
        alert.show();
        try {
            mbgGenerator.generate(tableConfigsToBeGenerated.values());
        } catch (Exception exception) {
            exception.printStackTrace();
            alert.closeIfShowing();
            Alerts.exception("生成失败", exception).showAndWait();
        }
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
        StageHelper.show("类编辑", ClassDefinitionController.class);
    }

    @FXML
    public void showConnectinManagePane(MouseEvent mouseEvent) {
        StageHelper.show("连接管理", ConnectionManageController.class);
    }

    /**
     * 添加一个表到需要进行代码生成的表
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
    public void fillDefaultCodeGenConfig(ActionEvent actionEvent) {
        projectFolderField.setText("D:/Temp/");
        daoTargetProject.setText("src/main/java");
        mapperTargetPackage.setText("mapping");
        txfMapperPackageName.setText("mapper");
        mappingTargetProject.setText("src/main/resources");
        modelTargetPackage.setText("entity");
    }

    @FXML
    public void openTargetRootDirectory(ActionEvent actionEvent) {
        String directory = projectFolderField.getText();
        if (StringUtils.hasNotText(directory)) {
            return;
        }
        FileUtils.show(new File(directory));
    }
}
