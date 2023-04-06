package io.devpl.tookit.fxui.controller.mbg;

import io.devpl.codegen.mbpg.jdbc.meta.TableMetadata;
import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.beans.ValueUpdateListener;
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
import io.devpl.tookit.utils.*;
import io.devpl.tookit.utils.fx.FileChooserDialog;
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

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
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
    @FXML
    public ComboBox<String> cboxConnection;
    @FXML
    public ComboBox<String> cboxDatabase;
    @FXML
    public TableView<TableCodeGeneration> tblvTableSelected; // 选中的表
    @FXML
    public TableColumn<TableCodeGeneration, String> tblcSelectedTableName;
    @FXML
    public TableColumn<TableCodeGeneration, String> tblcSelectedTableComment;
    @FXML
    public TextField txfParentPackageName;
    @FXML
    public TextField modelTargetPackage;
    @FXML
    public TextField mapperTargetPackage;
    @FXML
    public TextField txfMapperPackageName;  // DAO接口包名
    @FXML
    public TextField modelTargetProject; // 实体类存放目录
    @FXML
    public TextField mappingTargetProject; // 映射XML文件包名存放目录
    @FXML
    public TextField daoTargetProject;
    @FXML
    public TextField projectFolderField;
    @FXML
    public Button btnSaveConfig;
    @FXML
    public Button btnLoadConfig;
    @FXML
    public ChoiceBox<String> chobProjectLayout;
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
    public CheckBox chbForUpdate;
    @FXML
    public CheckBox chbAnnotationDao;
    @FXML
    public CheckBox chbJsr310Support;
    @FXML
    public CheckBox useActualColumnNamesCheckbox;
    @FXML
    public CheckBox annotationCheckBox;
    @FXML
    public CheckBox chbMapperExtend;
    @FXML
    public CheckBox useTableNameAliasCheckbox;
    @FXML
    public CheckBox addMapperAnnotationChcekBox;
    @FXML
    public CheckBox chbEnableSwagger;

    /**
     * 项目配置项
     */
    private final ProjectConfiguration projectConfig = new ProjectConfiguration();
    /**
     * 保存哪些表需要进行代码生成
     * 存放的key:TableCodeGenConfig#getUniqueKey()
     *
     * @see TableCodeGeneration#getUniqueKey()
     */
    private final Map<String, TableCodeGeneration> tableConfigsToBeGenerated = new ConcurrentHashMap<>(10);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnLoadConfig.setOnAction(event -> StageManager.show(ProjectConfigurationView.class));
        cboxConnection.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ConnectionInfo cf = ConnectionRegistry.getConnectionConfiguration(newValue);
            try (Connection connection = cf.getConnection()) {
                List<String> databaseNames = DBUtils.getDatabaseNames(connection);
                cboxDatabase.getItems().addAll(databaseNames);
            } catch (SQLException e) {
                log.error("连接失败{} ", newValue);
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

        ValueUpdateListener.bind(projectFolderField.textProperty(), projectConfig, ProjectConfiguration::setProjectRootFolder);
        ValueUpdateListener.bind(modelTargetPackage.textProperty(), projectConfig, ProjectConfiguration::setEntityPackageName);
        ValueUpdateListener.bind(modelTargetProject.textProperty(), projectConfig, ProjectConfiguration::setEntityPackageFolder);
        ValueUpdateListener.bind(txfParentPackageName.textProperty(), projectConfig, ProjectConfiguration::setParentPackage);
        ValueUpdateListener.bind(txfMapperPackageName.textProperty(), projectConfig, ProjectConfiguration::setMapperPackageName);
        ValueUpdateListener.bind(daoTargetProject.textProperty(), projectConfig, ProjectConfiguration::setMapperFolder);
        ValueUpdateListener.bind(mapperTargetPackage.textProperty(), projectConfig, ProjectConfiguration::setMapperXmlPackage);
        ValueUpdateListener.bind(mappingTargetProject.textProperty(), projectConfig, ProjectConfiguration::setMapperXmlFolder);

        chobProjectLayout.getItems().addAll("MAVEN");
        chobProjectLayout.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("MAVEN".equals(newValue)) {
                modelTargetProject.setText("src/main/java");
                daoTargetProject.setText("src/main/java");
                mappingTargetProject.setText("src/main/resources");
            }
        });
        chobProjectLayout.getSelectionModel().selectFirst();
    }

    @FXML
    public void generateCode(ActionEvent actionEvent) {
        if (tableConfigsToBeGenerated.isEmpty()) {
            Alerts.error("选择的数据表为空").show();
            return;
        }
        generate(this.projectConfig);
    }

    public void generate(ProjectConfiguration projectConfig) {
        MyBatisCodeGenerator mbgGenerator = new MyBatisCodeGenerator();
        mbgGenerator.setGeneratorConfig(projectConfig);
        try {
            mbgGenerator.generate(tableConfigsToBeGenerated.values());
        } catch (Exception exception) {
            exception.printStackTrace();
            Alerts.exception("生成失败", exception).showAndWait();
        } finally {
            // FileUtils.show(new File(projectConfig.getProjectRootFolder()));
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

    /**
     * 校验配置项
     *
     * @param projectConfig 项目配置
     */
    private String validateConfig(ProjectConfiguration projectConfig) {
        return Validator.target(projectConfig)
                .hasText(ProjectConfiguration::getProjectRootFolder, "项目根目录为空")
                .hasText(ProjectConfiguration::getMapperPackageName, "Mapper接口包名为空")
                .hasText(ProjectConfiguration::getEntityPackageName, "实体类包名为空")
                .hasText(ProjectConfiguration::getEntityPackageFolder, "实体类所在目录为空")
                .hasText(ProjectConfiguration::getMapperXmlPackage, "映射XML文件包名为空")
                .getErrorMessages();
    }

    /**
     * 选择项目文件夹
     *
     * @param event 事件
     */
    @FXML
    public void chooseProjectFolder(ActionEvent event) {
        FileChooserDialog.showDirectoryDialog(getStage(event))
                .ifPresent(file -> projectFolderField.setText(file.getAbsolutePath()));
    }

    /**
     * 保存配置
     *
     * @param actionEvent 事件
     */
    @FXML
    public void saveCodeGenConfig(ActionEvent actionEvent) {
        String errMsgs = validateConfig(this.projectConfig);
        if (StringUtils.hasText(errMsgs)) {
            Alerts.error(errMsgs).show();
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("保存代码生成配置");
        dialog.setContentText("输入配置名称:");
        Optional<String> resultOptional = dialog.showAndWait();
        if (resultOptional.isPresent()) {
            this.projectConfig.setName(resultOptional.get());
        } else {
            Alerts.error("配置名称不能为空");
        }
        AppConfig.saveProjectConfiguration(this.projectConfig);
    }

    /**
     * 加载配置信息，填充到界面上
     *
     * @param projectConfig 配置信息
     */
    @Subscribe(name = "LoadConfig")
    public void loadConfig(ProjectConfiguration projectConfig) {
        projectFolderField.setText(projectConfig.getProjectRootFolder());
        modelTargetPackage.setText(projectConfig.getEntityPackageName());
        modelTargetProject.setText(projectConfig.getEntityPackageFolder());
        txfParentPackageName.setText(projectConfig.getParentPackage());
        txfMapperPackageName.setText(projectConfig.getMapperPackageName());
        daoTargetProject.setText(projectConfig.getMapperFolder());
        mapperTargetPackage.setText(projectConfig.getMapperXmlPackage());
        mappingTargetProject.setText(projectConfig.getMapperXmlFolder());
    }
}
