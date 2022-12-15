package io.devpl.toolkit.fxui.controller;

import com.jcraft.jsch.Session;
import io.devpl.toolkit.fxui.bridge.MyBatisCodeGenerator;
import io.devpl.toolkit.fxui.common.model.ColumnCustomConfiguration;
import io.devpl.toolkit.fxui.common.utils.ProgressDialog;
import io.devpl.toolkit.fxui.config.CodeGenConfiguration;
import io.devpl.toolkit.fxui.config.Constants;
import io.devpl.toolkit.fxui.config.DatabaseConfig;
import io.devpl.toolkit.fxui.framework.Alerts;
import io.devpl.toolkit.fxui.framework.JFX;
import io.devpl.toolkit.fxui.utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.io.File;
import java.net.URL;
import java.sql.SQLRecoverableException;
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
    public TextField txfParentPackageName;
    @FXML
    private Label connectionLabel; // toolbar buttons
    @FXML
    private Label configsLabel;
    @FXML
    private TextField modelTargetPackage;
    @FXML
    private TextField mapperTargetPackage;
    @FXML
    private TextField daoTargetPackage;  // DAO接口包名
    @FXML
    private TextField tableNameField;
    @FXML
    private TextField domainObjectNameField;
    @FXML
    private TextField generateKeysField;    // 主键ID
    @FXML
    private TextField modelTargetProject;
    @FXML
    private TextField mappingTargetProject;
    @FXML
    private TextField daoTargetProject;
    @FXML
    private TextField mapperName;
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
    private TreeView<String> trvDbTreeList; // 数据库表列表
    @FXML
    public TextField filterTreeBox;
    // Current selected databaseConfig
    private DatabaseConfig selectedDatabaseConfig;
    // Current selected tableName
    private String tableName;

    private List<IgnoredColumn> ignoredColumns;

    private List<ColumnOverride> columnOverrides;

    @FXML
    private ChoiceBox<String> encodingChoice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        encodingChoice.setItems(JFX.arrayOf(Constants.SUPPORTED_ENCODING));
        // 默认选中第一个，否则如果忘记选择，没有对应错误提示
        encodingChoice.getSelectionModel().selectFirst();
        initializePlaceholderValue();
        // 新建连接
        ImageView dbImage = JFX.loadImageView("static/icons/computer.png", 40, 40);
        connectionLabel.setGraphic(dbImage);
        connectionLabel.setOnMouseClicked(event -> {
            TabPaneController controller = (TabPaneController) loadFXMLPage("新建数据库连接", FXMLPage.NEW_CONNECTION, false);
            controller.setMainUIController(this);
            controller.showDialogStage();
        });
        // 字典管理界面
        dictConfigLabel.setOnMouseClicked(event -> {
            DictConfigController controller = (DictConfigController) loadFXMLPage("字典管理", FXMLPage.DICT_CONFIG, false);
            controller.showDialogStage();
        });

        labTextHandle.setGraphic(JFX.loadImageView("static/icons/config-list.png", 40));
        labBeanDefCreate.setGraphic(JFX.loadImageView("static/icons/config-list.png", 40));

        // 生成配置管理
        configsLabel.setGraphic(JFX.loadImageView("static/icons/config-list.png", 40));
        configsLabel.setOnMouseClicked(event -> {
            GeneratorConfigController controller = (GeneratorConfigController) loadFXMLPage("配置", FXMLPage.GENERATOR_CONFIG, false);
            controller.setMainUIController(this);
            controller.showDialogStage();
        });
        // 字典配置
        // ImageView对象可以被多个控件同时使用吗？ 不能
        dictConfigLabel.setGraphic(JFX.loadImageView("static/icons/config-list.png", 40));
        useExample.setOnMouseClicked(event -> offsetLimitCheckBox.setDisable(!useExample.isSelected()));
        // selectedProperty().addListener 解决应用配置的时候未触发Clicked事件
        useLombokPlugin.selectedProperty()
                       .addListener((observable, oldValue, newValue) -> needToStringHashcodeEquals.setDisable(newValue));

        // 设置可以多选
        trvDbTreeList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        trvDbTreeList.setShowRoot(false);
        trvDbTreeList.setRoot(new TreeItem<>());
        filterTreeBox.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                trvDbTreeList.getRoot().getChildren().filtered(TreeItem::isExpanded).forEach(this::displayTables);
                ev.consume();
            }
        });

        // 设置单元格工厂 Callback<TreeView<T>, TreeCell<T>> value
        trvDbTreeList.setCellFactory((TreeView<String> tv) -> {
            // 创建一个单元格
            TreeCell<String> cell = new TextFieldTreeCell<>(JFX.DEFAULT_STRING_CONVERTER);
            cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                // 获取单元格
                @SuppressWarnings("unchecked") TreeCell<String> treeCell = (TreeCell<String>) event.getSource();
                TreeItem<String> treeItem = treeCell.getTreeItem();
                int level = trvDbTreeList.getTreeItemLevel(treeItem);
                // 层级为1，点击每个连接
                if (level == 1) {
                    final ContextMenu contextMenu = new ContextMenu();
                    MenuItem item1 = new MenuItem("关闭连接");
                    MenuItem item2 = new MenuItem("编辑连接");
                    item1.setOnAction(event1 -> treeItem.getChildren().clear());
                    item2.setOnAction(event1 -> {
                        DatabaseConfig selectedConfig = (DatabaseConfig) treeItem.getGraphic().getUserData();
                        TabPaneController controller = (TabPaneController) loadFXMLPage("编辑数据库连接", FXMLPage.NEW_CONNECTION, false);
                        controller.setMainUIController(this);
                        controller.setConfig(selectedConfig);
                        controller.showDialogStage();
                    });
                    MenuItem item3 = new MenuItem("删除连接");
                    item3.setOnAction(event1 -> {
                        DatabaseConfig selectedConfig = (DatabaseConfig) treeItem.getGraphic().getUserData();
                        try {
                            ConfigHelper.deleteDatabaseConfig(selectedConfig);
                            this.loadLeftDBTree();
                        } catch (Exception e) {
                            Alerts.error("Delete connection failed! Reason: " + e.getMessage()).show();
                        }
                    });
                    contextMenu.getItems().addAll(item1, item2, item3);
                    cell.setContextMenu(contextMenu);
                }
                // 双击
                if (event.getClickCount() == 2) {
                    if (treeItem == null) {
                        return;
                    }
                    treeItem.setExpanded(true);
                    if (level == 1) {
                        displayTables(treeItem);
                    } else if (level == 2) { // left DB tree level3
                        String tableName = treeCell.getTreeItem().getValue();
                        selectedDatabaseConfig = (DatabaseConfig) treeItem.getParent().getGraphic().getUserData();
                        this.tableName = tableName;
                        tableNameField.setText(tableName);
                        domainObjectNameField.setText(StringUtils.dbStringToCamelStyle(tableName));
                        mapperName.setText(domainObjectNameField.getText().concat("Mapper"));
                    }
                }
            });
            return cell;
        });
        loadLeftDBTree();
        setTooltip();
    }

    /**
     * 初始化文本控件的默认值
     */
    private void initializePlaceholderValue() {
        mapperTargetPackage.setText("mapping");
        daoTargetPackage.setText("mapper");
        modelTargetPackage.setText("model");
        projectFolderField.setText("D:/Temp/test");
    }

    private void displayTables(TreeItem<String> treeItem) {
        if (treeItem == null || !treeItem.isExpanded()) {
            return;
        }
        DatabaseConfig selectedConfig = (DatabaseConfig) treeItem.getGraphic().getUserData();
        try {
            String filter = filterTreeBox.getText();
            List<String> tables = DbUtils.getTableNames(selectedConfig, filter);
            if (tables.size() > 0) {
                ObservableList<TreeItem<String>> children = treeItem.getChildren();
                children.clear();
                for (String tableName : tables) {
                    TreeItem<String> newTreeItem = new TreeItem<>();
                    ImageView imageView = JFX.loadImageView("static/icons/table.png", 16, 16);
                    newTreeItem.setGraphic(imageView);
                    newTreeItem.setValue(tableName);
                    children.add(newTreeItem);
                }
            } else if (StringUtils.hasText(filter)) {
                treeItem.getChildren().clear();
            }
            String imageViewName = StringUtils.hasText(filter) ? "static/icons/filter.png" : "static/icons/computer.png";
            treeItem.setGraphic(JFX.loadImageView(imageViewName, 16, 16, treeItem.getGraphic().getUserData()));
        } catch (SQLRecoverableException e) {
            log.error(e.getMessage(), e);
            Alerts.error("连接超时").show();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Alerts.error(e.getMessage()).show();
        }
    }

    private void setTooltip() {
        encodingChoice.setTooltip(new Tooltip("生成文件的编码，必选"));
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

    void loadLeftDBTree() {
        TreeItem<String> rootTreeItem = trvDbTreeList.getRoot();
        rootTreeItem.getChildren().clear();
        try {
            // 加载所有的数据库配置
            List<DatabaseConfig> dbConfigs = ConfigHelper.loadDatabaseConfig();
            for (DatabaseConfig dbConfig : dbConfigs) {
                TreeItem<String> treeItem = new TreeItem<>();
                treeItem.setValue(dbConfig.getName());
                treeItem.setGraphic(JFX.loadImageView("static/icons/computer.png", 16, dbConfig));
                rootTreeItem.getChildren().add(treeItem);
            }
        } catch (Exception e) {
            log.error("connect db failed, reason", e);
            Alerts.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e)).showAndWait();
        }
    }

    @FXML
    public void chooseProjectFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedFolder = directoryChooser.showDialog(getPrimaryStage());
        if (selectedFolder != null) {
            projectFolderField.setText(selectedFolder.getAbsolutePath());
        }
    }

    private final MyBatisCodeGenerator mbgGenerator = new MyBatisCodeGenerator();

    @FXML
    public void generateCode() {
        if (tableName == null) {
            Alerts.warn("请先在左侧选择数据库表").show();
            return;
        }
        String result = validateConfig();
        if (result != null) {
            Alerts.error(result).showAndWait();
            return;
        }
        CodeGenConfiguration generatorConfig = getGeneratorConfigFromUI();
        if (!checkDirs(generatorConfig)) {
            return;
        }

        mbgGenerator.setGeneratorConfig(generatorConfig);
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
            Session sshSession = DbUtils.getSSHSession(selectedDatabaseConfig);
            DbUtils.engagePortForwarding(sshSession, selectedDatabaseConfig);
            if (sshSession != null) {
                pictureProcessStateController = new PictureProcessStateController();
                pictureProcessStateController.setDialogStage(getDialogStage());
                pictureProcessStateController.startPlay();
            }
            // 生成之前清除待生成的根目录下的文件
            // TODO 做成配置
            // String projectFolder = projectFolderField.getText();
            // FileUtils.cleanDirectory(new File(projectFolder));
            try {
                mbgGenerator.generate();
            } catch (Exception exception) {
                Alerts.error("生成失败: " + exception.getMessage()).showAndWait();
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

    }

    /**
     * 打开Bean定义创建界面
     * @param mouseEvent 鼠标点击事件
     */
    public void openBeanDefCreateFrame(MouseEvent mouseEvent) {
        final Scene scene = JFX.createScene("static/fxml/class_definition.fxml");
        final Stage stage = new Stage();
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
//        if (StringUtils.isAnyEmpty(modelTargetPackage.getText(), mapperTargetPackage.getText(), daoTargetPackage.getText())) {
//            return "包名不能为空";
//        }
        return null;
    }

    @FXML
    public void saveGeneratorConfig() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("保存当前配置");
        dialog.setContentText("请输入配置名称");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String name = result.get();
            if (org.apache.commons.lang3.StringUtils.isEmpty(name)) {
                Alerts.error("名称不能为空").show();
                return;
            }
            log.info("user choose name: {}", name);
            try {
                CodeGenConfiguration generatorConfig = getGeneratorConfigFromUI();
                generatorConfig.setName(name);
                ConfigHelper.deleteGeneratorConfig(name);
                ConfigHelper.saveGeneratorConfig(generatorConfig);
            } catch (Exception e) {
                log.error("保存配置失败", e);
                Alerts.error("保存配置失败").show();
            }
        }
    }

    public CodeGenConfiguration getGeneratorConfigFromUI() {
        CodeGenConfiguration generatorConfig = new CodeGenConfiguration();
        generatorConfig.setProjectFolder(projectFolderField.getText());
        generatorConfig.setModelPackage(modelTargetPackage.getText());
        generatorConfig.setGenerateKeys(generateKeysField.getText());
        generatorConfig.setModelPackageTargetFolder(modelTargetProject.getText());
        generatorConfig.setDaoPackage(daoTargetPackage.getText());
        generatorConfig.setDaoTargetFolder(daoTargetProject.getText());
        generatorConfig.setMapperName(mapperName.getText());
        generatorConfig.setMappingXMLPackage(mapperTargetPackage.getText());
        generatorConfig.setMappingXMLTargetFolder(mappingTargetProject.getText());
        generatorConfig.setTableName(tableNameField.getText());
        generatorConfig.setDomainObjectName(domainObjectNameField.getText());
        generatorConfig.setOffsetLimit(offsetLimitCheckBox.isSelected());
        generatorConfig.setComment(commentCheckBox.isSelected());
        generatorConfig.setOverrideXML(overrideXML.isSelected());
        generatorConfig.setNeedToStringHashcodeEquals(needToStringHashcodeEquals.isSelected());
        generatorConfig.setUseLombokPlugin(useLombokPlugin.isSelected());
        generatorConfig.setSwaggerSupport(chbEnableSwagger.isSelected());
        generatorConfig.setUseTableNameAlias(useTableNameAliasCheckbox.isSelected());
        generatorConfig.setNeedForUpdate(forUpdateCheckBox.isSelected());
        generatorConfig.setAnnotationDAO(annotationDAOCheckBox.isSelected());
        generatorConfig.setAnnotation(annotationCheckBox.isSelected());
        generatorConfig.setUseActualColumnNames(useActualColumnNamesCheckbox.isSelected());
        generatorConfig.setEncoding(encodingChoice.getValue());
        generatorConfig.setUseExample(useExample.isSelected());
        generatorConfig.setUseDAOExtendStyle(useDAOExtendStyle.isSelected());
        generatorConfig.setUseSchemaPrefix(useSchemaPrefix.isSelected());
        generatorConfig.setJsr310Support(jsr310Support.isSelected());
        generatorConfig.setParentPackage(txfParentPackageName.getText());
        return generatorConfig;
    }

    /**
     * 将配置更新到UI
     * @param generatorConfig 代码生成配置
     */
    public void setGeneratorConfigIntoUI(CodeGenConfiguration generatorConfig) {
        projectFolderField.setText(generatorConfig.getProjectFolder());
        modelTargetPackage.setText(generatorConfig.getModelPackage());
        generateKeysField.setText(generatorConfig.getGenerateKeys());
        modelTargetProject.setText(generatorConfig.getModelPackageTargetFolder());
        daoTargetPackage.setText(generatorConfig.getDaoPackage());
        daoTargetProject.setText(generatorConfig.getDaoTargetFolder());
        mapperTargetPackage.setText(generatorConfig.getMappingXMLPackage());
        mappingTargetProject.setText(generatorConfig.getMappingXMLTargetFolder());
        if (StringUtils.hasText(tableNameField.getText())) {
            tableNameField.setText(generatorConfig.getTableName());
            mapperName.setText(generatorConfig.getMapperName());
            domainObjectNameField.setText(generatorConfig.getDomainObjectName());
        }
        offsetLimitCheckBox.setSelected(generatorConfig.isOffsetLimit());
        commentCheckBox.setSelected(generatorConfig.isComment());
        overrideXML.setSelected(generatorConfig.isOverrideXML());
        needToStringHashcodeEquals.setSelected(generatorConfig.isNeedToStringHashcodeEquals());
        useLombokPlugin.setSelected(generatorConfig.isUseLombokPlugin());
        useTableNameAliasCheckbox.setSelected(generatorConfig.getUseTableNameAlias());
        forUpdateCheckBox.setSelected(generatorConfig.isNeedForUpdate());
        annotationDAOCheckBox.setSelected(generatorConfig.isAnnotationDAO());
        annotationCheckBox.setSelected(generatorConfig.isAnnotation());
        useActualColumnNamesCheckbox.setSelected(generatorConfig.isUseActualColumnNames());
        encodingChoice.setValue(generatorConfig.getEncoding());
        useExample.setSelected(generatorConfig.isUseExample());
        useDAOExtendStyle.setSelected(generatorConfig.isUseDAOExtendStyle());
        useSchemaPrefix.setSelected(generatorConfig.isUseSchemaPrefix());
        jsr310Support.setSelected(generatorConfig.isJsr310Support());
    }

    /**
     * 打开定制列面板
     */
    @FXML
    public void openTableColumnCustomizationPage() {
        if (tableName == null) {
            Alerts.warn("请先在左侧选择数据库表").showAndWait();
            return;
        }
        SelectTableColumnController controller = (SelectTableColumnController) loadFXMLPage("定制列", FXMLPage.SELECT_TABLE_COLUMN, true);
        controller.setMainUIController(this);
        try {
            // If select same schema and another table, update table data
            if (!tableName.equals(controller.getTableName())) {
                List<ColumnCustomConfiguration> tableColumns = DbUtils.getTableColumns(selectedDatabaseConfig, tableName);
                controller.setColumnList(FXCollections.observableList(tableColumns));
                controller.setTableName(tableName);
            }
            controller.showDialogStage();
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
        CodeGenConfiguration generatorConfig = getGeneratorConfigFromUI();
        String projectFolder = generatorConfig.getProjectFolder();
        try {
            FileUtils.show(new File(projectFolder));
        } catch (Exception e) {
            Alerts.error("打开目录失败，请检查目录是否填写正确" + e.getMessage()).showAndWait();
        }
    }
}
