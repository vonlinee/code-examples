package io.devpl.toolkit.fxui.controller;

import com.jcraft.jsch.Session;
import io.devpl.toolkit.fxui.bridge.MyBatisCodeGenerator;
import io.devpl.toolkit.fxui.common.ProgressDialog;
import io.devpl.toolkit.fxui.common.StageTitle;
import io.devpl.toolkit.fxui.common.model.ColumnCustomConfiguration;
import io.devpl.toolkit.fxui.config.CodeGenConfiguration;
import io.devpl.toolkit.fxui.config.Constants;
import io.devpl.toolkit.fxui.config.DatabaseConfig;
import io.devpl.toolkit.fxui.event.CustomEvent;
import io.devpl.toolkit.fxui.event.LoadDbTreeEvent;
import io.devpl.toolkit.fxui.event.UpdateCodeGenConfigEvent;
import io.devpl.toolkit.fxui.framework.Alerts;
import io.devpl.toolkit.fxui.framework.JFX;
import io.devpl.toolkit.fxui.framework.mvc.FXControllerBase;
import io.devpl.toolkit.fxui.model.DBTableListModel;
import io.devpl.toolkit.fxui.model.DbTreeViewCellFactory;
import io.devpl.toolkit.fxui.model.DbTreeViewItemValue;
import io.devpl.toolkit.fxui.utils.*;
import io.devpl.toolkit.fxui.view.CodeGenMainView;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.greenrobot.eventbus.Subscribe;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainUIController extends FXControllerBase {

    @FXML
    public Label dictConfigLabel; // 程序内部字典配置
    @FXML
    public CheckBox addMapperAnnotationChcekBox; // 是否添加@Mapper注解
    @FXML
    public Label labTextHandle;
    @FXML
    public CheckBox chbEnableSwagger;
    @FXML
    public Label labBeanDefCreate; // 创建Bean定义
    @FXML
    public Tab tabTableConfig;  // 表配置标签
    @FXML
    public ScrollPane scpTableConfigRoot;
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
    public TextField tableNameField;
    @FXML
    public TextField domainObjectNameField;
    @FXML
    private TextField generateKeysField;    // 主键ID
    @FXML
    private TextField modelTargetProject; // 实体类存放目录
    @FXML
    private TextField mappingTargetProject;
    @FXML
    private TextField daoTargetProject;
    @FXML
    public TextField mapperName;
    @FXML
    private TextField projectFolderField;
    @FXML
    private CheckBox offsetLimitCheckBox;
    @FXML
    private CheckBox commentCheckBox;
    @FXML
    private CheckBox overrideXML;
    @FXML
    private CheckBox needToStringHashcodeEquals;
    @FXML
    private CheckBox useLombokPlugin;
    @FXML
    private CheckBox forUpdateCheckBox;
    @FXML
    private CheckBox annotationDAOCheckBox;
    @FXML
    private CheckBox useTableNameAliasCheckbox;
    @FXML
    private CheckBox annotationCheckBox;
    @FXML
    private CheckBox useActualColumnNamesCheckbox;
    @FXML
    private CheckBox useExample;
    @FXML
    private CheckBox useDAOExtendStyle; // DAO方法是否抽出到公共父接口
    @FXML
    private CheckBox useSchemaPrefix;
    @FXML
    private CheckBox jsr310Support;
    @FXML
    private TreeView<DbTreeViewItemValue> trvDbTreeList; // 数据库表列表，只读
    @FXML
    public TextField filterTreeBox;
    @FXML
    private ChoiceBox<String> encodingChoice;
    // Current selected databaseConfig
    public DatabaseConfig selectedDatabaseConfig;
    // Current selected tableName
    public String tableName;

    private List<IgnoredColumn> ignoredColumns;
    private List<ColumnOverride> columnOverrides;

    CodeGenConfiguration codeGenConfig = new CodeGenConfiguration();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindCodeGenConfiguration(codeGenConfig);
        registerThis();
        encodingChoice.setItems(JFX.arrayOf(Constants.SUPPORTED_ENCODING));
        // 默认选中第一个，否则如果忘记选择，没有对应错误提示
        encodingChoice.getSelectionModel().selectFirst();
        initializePlaceholderValue();
        // 新建连接
        connectionLabel.setGraphic(JFX.loadImageView("static/icons/computer.png", 40));
        connectionLabel.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(FXMLPage.NEW_CONNECTION.getLocation());
            try {
                JFX.newDialogStage(StageTitle.NEW_CONNECTION, getStage(event), new Scene(loader.load())).show();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                Alerts.error(e.getMessage()).showAndWait();
            }
        });
        labTextHandle.setGraphic(JFX.loadImageView("static/icons/config-list.png", 40));
        labBeanDefCreate.setGraphic(JFX.loadImageView("static/icons/config-list.png", 40));
        // 生成配置管理
        configsLabel.setGraphic(JFX.loadImageView("static/icons/config-list.png", 40));
        configsLabel.setOnMouseClicked(event -> {
            FXMLLoader loader = new FXMLLoader(FXMLPage.GENERATOR_CONFIG.getLocation());
            try {
                // fix bug: 嵌套弹出时会发生dialogStage被覆盖的情况
                JFX.newDialogStage(StageTitle.CONFIG, getStage(event), new Scene(loader.load())).show();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                Alerts.error(e.getMessage()).showAndWait();
            }
        });
        // 字典配置
        // ImageView对象可以被多个控件同时使用吗？ 不能
        dictConfigLabel.setGraphic(JFX.loadImageView("static/icons/config-list.png", 40));
        useExample.setOnMouseClicked(event -> offsetLimitCheckBox.setDisable(!useExample.isSelected()));
        // selectedProperty().addListener 解决应用配置的时候未触发Clicked事件
        useLombokPlugin.selectedProperty()
                       .addListener((observable, oldValue, newValue) -> needToStringHashcodeEquals.setDisable(newValue));

        // trvDbTreeList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // 设置可以多选
        // trvDbTreeList.setShowRoot(false);
        trvDbTreeList.setRoot(new TreeItem<>()); // 根节点

        filterTreeBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Event.fireEvent(trvDbTreeList, new Event(CustomEvent.DISPLAY_TREE_VIEW));
                event.consume();
            }
        });

        trvDbTreeList.addEventHandler(CustomEvent.DISPLAY_TREE_VIEW, event -> {
            DbTreeViewCellFactory factory = (DbTreeViewCellFactory) trvDbTreeList.getCellFactory();
            trvDbTreeList.getRoot().getChildren().filtered(TreeItem::isExpanded).forEach(factory::displayTables);
            event.consume();
        });

        // 设置单元格工厂 Callback<TreeView<T>, TreeCell<T>> value
        trvDbTreeList.setCellFactory(new DbTreeViewCellFactory(this));
        loadLeftDBTree();
        setTooltip();
        tabTableConfig.setContent(new CodeGenMainView());
    }

    /**
     * 初始化文本控件的默认值
     */
    private void initializePlaceholderValue() {
        mapperTargetPackage.setText("mapping");
        txfMapperPackageName.setText("mapper");
        modelTargetPackage.setText("model");
        projectFolderField.setText("D:/Temp/test");
    }

    @Subscribe
    public void loadDbInfo(LoadDbTreeEvent event) {
        loadLeftDBTree();
    }

    public void loadLeftDBTree() {
        TreeItem<DbTreeViewItemValue> rootTreeItem = trvDbTreeList.getRoot();
        rootTreeItem.getChildren().clear();
        try {
            // 加载所有的数据库配置
            List<DatabaseConfig> dbConfigs = ConfigHelper.loadDatabaseConfig();
            for (DatabaseConfig dbConfig : dbConfigs) {
                TreeItem<DbTreeViewItemValue> treeItem = new TreeItem<>();
                final String name = dbConfig.getName();
                final DbTreeViewItemValue tableInfo = new DbTreeViewItemValue();
                tableInfo.setTableName(name);
                treeItem.setValue(tableInfo);
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

    private final MyBatisCodeGenerator mbgGenerator = new MyBatisCodeGenerator();

    @FXML
    public void generateCode() {
        if (tableName == null) {
            Alerts.warn("请先在左侧选择数据库表").showAndWait();
            return;
        }
        String result = validateConfig();
        if (result != null) {
            Alerts.error(result).showAndWait();
            return;
        }
        if (!checkDirs(codeGenConfig)) {
            return;
        }
        mbgGenerator.setGeneratorConfig(codeGenConfig);
        mbgGenerator.setDatabaseConfig(selectedDatabaseConfig);
        mbgGenerator.setIgnoredColumns(ignoredColumns);
        mbgGenerator.setColumnOverrides(columnOverrides);

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

    /**
     * 打开文本处理界面
     * @param mouseEvent 鼠标点击事件
     */
    @FXML
    public void openTextHandleToolkit(MouseEvent mouseEvent) {
        Alerts.error("暂未开发").showAndWait();
    }

    /**
     * 打开Bean定义创建界面
     * @param mouseEvent 鼠标点击事件
     */
    @FXML
    public void openBeanDefCreateFrame(MouseEvent mouseEvent) throws Exception {
        final Stage stage = new Stage();
        CodeGenMainView root = new CodeGenMainView();
        final Connection connection = ConnectionManager.getConnection();
        List<TableMetadata> tmds = DBUtils.getTablesMetadata(connection);
        for (TableMetadata tmd : tmds) {
            final DBTableListModel model = new DBTableListModel();
            model.setSelected(false);
            model.setId(model.hashCode());
            model.setTableName(tmd.getTableName());
            model.setTableComment(tmd.getRemarks());
            model.setCreateTime(LocalDateTime.now());
            root.addRow(model);
        }
        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    static class DoNothing extends Task<Void> {
        @Override
        protected Void call() throws Exception {
            Thread.sleep(3000);
            return null;
        }
    }

    /**
     * 校验配置项
     * @return 提示信息
     */
    private String validateConfig() {
        String projectFolder = projectFolderField.getText();
        if (StringUtils.isEmpty(projectFolder)) {
            return "项目目录不能为空";
        }
        if (StringUtils.isEmpty(domainObjectNameField.getText())) {
            return "类名不能为空";
        }
        
        return null;
    }

    private TextInputDialog dialog;

    public TextInputDialog getConfigSaveDialog() {
        if (dialog == null) {
            dialog = new TextInputDialog();
            dialog.setTitle("保存当前配置");
            dialog.setContentText("请输入配置名称");
            codeGenConfig.nameProperty().bind(this.dialog.resultProperty());
        }
        return dialog;
    }

    /**
     * 保存代码生成配置信息
     */
    @FXML
    public void saveGeneratorConfig(ActionEvent event) {
        final TextInputDialog dialog = getConfigSaveDialog();
        if (dialog.isShowing()) return;
        String name = this.dialog.showAndWait().orElse("");
        if (StringUtils.isEmpty(name)) {
            Alerts.error("名称不能为空").showAndWait();
            return;
        }
        try {
            ConfigHelper.deleteGeneratorConfig(name);
            ConfigHelper.saveGeneratorConfig(codeGenConfig);
        } catch (Exception e) {
            Alerts.error("保存配置失败").show();
        }
    }

    @Subscribe
    public void updateCodeGenConfig(UpdateCodeGenConfigEvent event) {
        log.info("更新代码生成配置", event);
        // TOOD
        final CodeGenConfiguration config = event.getGeneratorConfig();
    }

    /**
     * 绑定数据
     * @param generatorConfig 代码生成配置
     */
    public void bindCodeGenConfiguration(CodeGenConfiguration generatorConfig) {
        projectFolderField.textProperty().bindBidirectional(generatorConfig.projectFolderProperty());
        modelTargetPackage.textProperty().bindBidirectional(generatorConfig.modelPackageProperty());
        generateKeysField.textProperty().bindBidirectional(generatorConfig.generateKeysProperty());
        modelTargetProject.textProperty().bindBidirectional(generatorConfig.modelPackageTargetFolderProperty());
        txfParentPackageName.textProperty().bindBidirectional(generatorConfig.parentPackageProperty());
        txfMapperPackageName.textProperty().bindBidirectional(generatorConfig.daoPackageProperty());
        daoTargetProject.textProperty().bindBidirectional(generatorConfig.daoTargetFolderProperty());
        mapperTargetPackage.textProperty().bindBidirectional(generatorConfig.mappingXMLPackageProperty());
        mappingTargetProject.textProperty().bindBidirectional(generatorConfig.mappingXMLTargetFolderProperty());
        tableNameField.textProperty().bindBidirectional(generatorConfig.tableNameProperty());
        mapperName.textProperty().bindBidirectional(generatorConfig.mapperNameProperty());
        domainObjectNameField.textProperty().bindBidirectional(generatorConfig.domainObjectNameProperty());
        offsetLimitCheckBox.selectedProperty().bindBidirectional(generatorConfig.offsetLimitProperty());
        commentCheckBox.selectedProperty().bindBidirectional(generatorConfig.commentProperty());
        overrideXML.selectedProperty().bindBidirectional(generatorConfig.overrideXMLProperty());
        needToStringHashcodeEquals.selectedProperty()
                                  .bindBidirectional(generatorConfig.needToStringHashcodeEqualsProperty());
        useLombokPlugin.selectedProperty().bindBidirectional(generatorConfig.useLombokPluginProperty());
        // 是否使用表别名
        useTableNameAliasCheckbox.selectedProperty().bindBidirectional(generatorConfig.useTableNameAliasProperty());
        forUpdateCheckBox.selectedProperty().bindBidirectional(generatorConfig.needForUpdateProperty());
        annotationDAOCheckBox.selectedProperty().bindBidirectional(generatorConfig.annotationDAOProperty());
        annotationCheckBox.selectedProperty().bindBidirectional(generatorConfig.annotationProperty());
        useActualColumnNamesCheckbox.selectedProperty()
                                    .bindBidirectional(generatorConfig.useActualColumnNamesProperty());
        encodingChoice.valueProperty().bindBidirectional(generatorConfig.encodingProperty());
        useExample.selectedProperty().bindBidirectional(generatorConfig.useExampleProperty());
        useDAOExtendStyle.selectedProperty().bindBidirectional(generatorConfig.useDAOExtendStyleProperty());
        useSchemaPrefix.selectedProperty().bindBidirectional(generatorConfig.useSchemaPrefixProperty());
        jsr310Support.selectedProperty().bindBidirectional(generatorConfig.jsr310SupportProperty());
    }

    /**
     * 打开定制列面板
     */
    @FXML
    public void openTableColumnCustomizationPage(ActionEvent event) {
        if (tableName == null) {
            Alerts.warn("请先在左侧选择数据库表").showAndWait();
            return;
        }
        SelectTableColumnController controller = (SelectTableColumnController) loadFXMLPage("定制列", FXMLPage.SELECT_TABLE_COLUMN, true);
        controller.setMainUIController(this);
        try {
            // If select same schema and another table, update table data
            if (!tableName.equals(controller.getTableName())) {
                List<ColumnCustomConfiguration> tableColumns = DBUtils.getTableColumns(selectedDatabaseConfig, tableName);
                controller.setColumnList(FXCollections.observableList(tableColumns));
                controller.setTableName(tableName);
            }
            getStage(event).show();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Alerts.error(e.getMessage()).showAndWait();
        }
    }

    public void setIgnoredColumns(List<IgnoredColumn> ignoredColumns) {
        this.ignoredColumns = ignoredColumns;
    }

    public void setColumnOverrides(List<ColumnOverride> columnOverrides) {
        this.columnOverrides = columnOverrides;
    }

    /**
     * 检查并创建不存在的文件夹
     * @return 是否创建成功
     */
    private boolean checkDirs(CodeGenConfiguration config) {
        List<String> dirs = new ArrayList<>();
        dirs.add(config.getProjectFolder());
        dirs.add(config.getProjectFolder().concat("/").concat(config.getModelPackageTargetFolder()));
        dirs.add(config.getProjectFolder().concat("/").concat(config.getDaoTargetFolder()));
        dirs.add(config.getProjectFolder().concat("/").concat(config.getMappingXMLTargetFolder()));
        boolean haveNotExistFolder = false;
        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.exists()) {
                haveNotExistFolder = true;
            }
        }
        if (haveNotExistFolder) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(Messages.getString("PromptText.2"));
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.isPresent()) {
                if (ButtonType.OK == optional.get()) {
                    try {
                        for (String dir : dirs) {
                            FileUtils.forceMkdir(new File(dir));
                        }
                        return true;
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
    public void openTargetFolder() {
        try {
            FileUtils.show(new File(codeGenConfig.getProjectFolder()));
        } catch (Exception e) {
            Alerts.error("打开目录失败，请检查目录是否填写正确" + e.getMessage()).showAndWait();
        }
    }

    private void setTooltip() {
        generateKeysField.setTooltip(new Tooltip("insert时可以返回主键ID"));
        offsetLimitCheckBox.setTooltip(new Tooltip("是否要生成分页查询代码"));
        commentCheckBox.setTooltip(new Tooltip("使用数据库的列注释作为实体类字段名的Java注释 "));
        useActualColumnNamesCheckbox.setTooltip(new Tooltip("是否使用数据库实际的列名作为实体类域的名称"));
        useTableNameAliasCheckbox.setTooltip(new Tooltip("在Mapper XML文件中表名使用别名，并且列全部使用as查询"));
        overrideXML.setTooltip(new Tooltip("重新生成时把原XML文件覆盖，否则是追加"));
        useDAOExtendStyle.setTooltip(new Tooltip("将通用接口方法放在公共接口中，DAO接口留空"));
        forUpdateCheckBox.setTooltip(new Tooltip("在Select语句中增加for update后缀"));
        JFX.setTooltip(useLombokPlugin, "实体类使用Lombok @Data简化代码");
    }
}
