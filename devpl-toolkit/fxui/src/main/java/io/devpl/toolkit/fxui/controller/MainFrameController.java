package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.JFX;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.fxtras.utils.StageHelper;
import io.devpl.toolkit.fxui.bridge.MyBatisCodeGenerator;
import io.devpl.toolkit.fxui.common.ProgressDialog;
import io.devpl.toolkit.fxui.event.CommandEvent;
import io.devpl.toolkit.fxui.model.TableCodeGeneration;
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
import javafx.scene.text.TextAlignment;

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
    public Button entityEditor;
    @FXML
    private Label connectionLabel;
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
    public TableView<TableCodeGeneration> tblvTableCustomization;
    @FXML
    public TableColumn<TableCodeGeneration, String> tblcDbName;
    @FXML
    public TableColumn<TableCodeGeneration, String> tblcTableName;

    private final MyBatisCodeGenerator mbgGenerator = new MyBatisCodeGenerator();
    // 通用配置项
    private final GenericConfiguration codeGenConfig = new GenericConfiguration();
    // 保存哪些表需要进行代码生成
    private final Map<String, TableCodeGeneration> tableConfigsToBeGenerated = new ConcurrentHashMap<>(10);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindCodeGenConfiguration(codeGenConfig);
        // 新建连接
        connectionLabel.setGraphic(JFX.loadImageView("static/icons/computer.png", 40));
        connectionLabel.setOnMouseClicked(event -> StageHelper.show("新建连接", NewConnectionController.class));
        // 生成配置管理
        configsLabel.setOnMouseClicked(event -> StageHelper.show("生成配置", GeneratorConfigController.class));

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
                TableCodeGeneration item = param.getSelectionModel().getSelectedItem();
                param.getItems().remove(item);
                tableConfigsToBeGenerated.remove(item.getTableName()); // 移除该表
            });
            customizeMenuItem.setOnAction(event -> {
                TableCodeGeneration item = param.getSelectionModel().getSelectedItem();
                Parent root = ViewLoader.load(TableCustomizationController.class).getRoot();
                Event.fireEvent(root, new CommandEvent(CommandEvent.COMMAND, item));
                StageHelper.show("表生成定制", root);
                event.consume();
            });
            ContextMenu menu = new ContextMenu(deleteThisRowMenuItem, customizeMenuItem);
            row.setContextMenu(menu);
            return row;
        });
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
        // 进度回调
        ProgressDialog alert = new ProgressDialog(Alert.AlertType.INFORMATION);
        mbgGenerator.setProgressCallback(alert);
        alert.show();
        try {
            try {
                mbgGenerator.generate();
            } catch (Exception exception) {
                alert.closeIfShowing();
                Alerts.exception("生成失败", exception).showAndWait();
            }
        } catch (Exception e) {
            Alerts.error(e.getMessage()).showAndWait();
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
            Optional<ButtonType> optional = Alerts.confirm("以下目录不存在, 是否创建?\n" + sb).showAndWait();
            if (optional.isEmpty()) {
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

    @FXML
    public void showEntityEditorDialog(ActionEvent actionEvent) {
        StageHelper.show("实体类编辑", PojoEditorController.class);
    }

    @FXML
    public void showClassEditorDialogPane(ActionEvent actionEvent) {
        StageHelper.show("类编辑", ClassDefinitionController.class);
    }
}
