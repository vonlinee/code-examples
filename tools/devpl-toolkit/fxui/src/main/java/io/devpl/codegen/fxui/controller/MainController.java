package io.devpl.codegen.fxui.controller;

import com.jcraft.jsch.Session;
import io.devpl.codegen.common.utils.*;
import io.devpl.codegen.fxui.bridge.MyBatisCodeGenerator;
import io.devpl.codegen.fxui.bridge.UIProgressCallback;
import io.devpl.codegen.fxui.frame.FXController;
import io.devpl.codegen.fxui.model.CodeGenConfiguration;
import io.devpl.codegen.fxui.model.DatabaseConfiguration;
import io.devpl.codegen.fxui.model.TableColumnCustomization;
import io.devpl.codegen.fxui.utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.greenrobot.eventbus.Subscribe;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import java.io.File;
import java.net.URL;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController extends FXController {

    @FXML
    public BorderPane root;
    // tool bar buttons
    @FXML
    private Label connectionLabel;
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
    private TextField generateKeysField;    //主键ID
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
    private CheckBox useDAOExtendStyle;
    @FXML
    private CheckBox useSchemaPrefix;
    @FXML
    private CheckBox jsr310Support;
    @FXML
    private TreeView<String> leftDBTree;
    @FXML
    public TextField filterTreeBox;
    // Current selected databaseConfig
    private DatabaseConfiguration selectedDatabaseConfig;
    // Current selected tableName
    private String tableName;

    private List<IgnoredColumn> ignoredColumns;

    private List<ColumnOverride> columnOverrides;

    @FXML
    private ChoiceBox<String> encodingChoice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializePlaceholderValue();
        // 新建数据库连接
        connectionLabel.setGraphic(FXUtils.loadImageView("icons/computer.png", 40, 40));
        connectionLabel.setOnMouseClicked(event -> {
            FXMLHelper.load(FXMLPage.NEW_CONNECTION.getFxml()).ifPresent(parent -> {
                // fix bug: 嵌套弹出时会发生dialogStage被覆盖的情况
                Stage ownerStage = FXUtils.getOwnerStage(root);
                FXUtils.createDialogStage("新建数据库连接", ownerStage, parent).show();
            });
        });
        configsLabel.setGraphic(FXUtils.loadImageView("icons/config-list.png", 40, 40));
        configsLabel.setOnMouseClicked(event -> FXMLHelper.load(FXMLPage.GENERATOR_CONFIG.getFxml()).ifPresent(parent -> {
            // fix bug: 嵌套弹出时会发生dialogStage被覆盖的情况
            Stage ownerStage = FXUtils.getOwnerStage(root);
            FXUtils.createDialogStage("配置", ownerStage, parent).show();
        }));
        useExample.setOnMouseClicked(event -> offsetLimitCheckBox.setDisable(!useExample.isSelected()));
        // selectedProperty().addListener 解决应用配置的时候未触发Clicked事件
        useLombokPlugin.selectedProperty().addListener((observable, oldValue, newValue) -> needToStringHashcodeEquals.setDisable(newValue));

        leftDBTree.setShowRoot(false);
        leftDBTree.setRoot(new TreeItem<>());
        Callback<TreeView<String>, TreeCell<String>> defaultCellFactory = TextFieldTreeCell.forTreeView();

        filterTreeBox.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                ObservableList<TreeItem<String>> schemas = leftDBTree.getRoot().getChildren();
                schemas.filtered(TreeItem::isExpanded).forEach(this::displayTables);
                ev.consume();
            }
        });
        leftDBTree.setCellFactory((TreeView<String> tv) -> {
            TreeCell<String> cell = defaultCellFactory.call(tv);
            // 表列表点击事件
            cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                int level = leftDBTree.getTreeItemLevel(cell.getTreeItem());
                @SuppressWarnings("unchecked")
                TreeCell<String> treeCell = (TreeCell<String>) event.getSource();
                TreeItem<String> treeItem = treeCell.getTreeItem();
                if (level == 1) {
                    final ContextMenu contextMenu = new ContextMenu();
                    MenuItem item1 = new MenuItem("关闭连接");
                    item1.setOnAction(event1 -> treeItem.getChildren().clear());
                    MenuItem item2 = new MenuItem("编辑连接");
                    item2.setOnAction(event1 -> {
                        DatabaseConfiguration selectedConfig = (DatabaseConfiguration) treeItem.getGraphic().getUserData();
                        // 将配置填充进去
                        FXMLHelper.load(FXMLPage.NEW_CONNECTION.getFxml()).ifPresent(parent -> {
                            // fix bug: 嵌套弹出时会发生dialogStage被覆盖的情况
                            Node source = (Node) event.getSource();
                            Stage ownerStage = (Stage) source.getScene().getWindow();
                            FXUtils.createDialogStage("新建数据库连接", ownerStage, parent).show();
                        });
                    });
                    MenuItem item3 = new MenuItem("删除连接");
                    item3.setOnAction(event1 -> {
                        DatabaseConfiguration selectedConfig = (DatabaseConfiguration) treeItem.getGraphic().getUserData();
                        try {
                            ConfigHelper.deleteDatabaseConfig(selectedConfig);
                            this.loadDatabaseConnectionTree(List.of(selectedConfig));
                        } catch (Exception e) {
                            Alerts.error("Delete connection failed! Reason: " + e.getMessage());
                        }
                    });
                    contextMenu.getItems().addAll(item1, item2, item3);
                    cell.setContextMenu(contextMenu);
                }
                if (event.getClickCount() == 2) {
                    if (treeItem == null) {
                        return;
                    }
                    treeItem.setExpanded(true);
                    if (level == 1) {
                        displayTables(treeItem);
                    } else if (level == 2) { // left DB tree level3
                        String tableName = treeCell.getTreeItem().getValue();
                        selectedDatabaseConfig = (DatabaseConfiguration) treeItem.getParent().getGraphic().getUserData();
                        this.tableName = tableName;
                        tableNameField.setText(tableName);
                        domainObjectNameField.setText(Validator.dbStringToCamelStyle(tableName));
                        mapperName.setText(domainObjectNameField.getText().concat("DAO"));
                    }
                }
            });
            return cell;
        });
        // 初始化时加载数据库信息
        // loadDatabaseConnectionTree();
        setTooltip();
        //默认选中第一个，否则如果忘记选择，没有对应错误提示
        encodingChoice.getSelectionModel().selectFirst();
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

    /**
     * 展示所有表
     * @param treeItem
     */
    private void displayTables(TreeItem<String> treeItem) {
        if (treeItem == null) {
            return;
        }
        if (!treeItem.isExpanded()) {
            return;
        }
        DatabaseConfiguration selectedConfig = (DatabaseConfiguration) treeItem.getGraphic().getUserData();
        try {
            String filter = filterTreeBox.getText();
            List<String> tables = DBUtils.getTableNames(selectedConfig, filter);
            if (tables.size() > 0) {
                ObservableList<TreeItem<String>> children = treeItem.getChildren();
                children.clear();
                for (String tableName : tables) {
                    TreeItem<String> newTreeItem = new TreeItem<>();
                    newTreeItem.setGraphic(FXUtils.loadImageView("icons/table.png", 16, 16));
                    newTreeItem.setValue(tableName);
                    children.add(newTreeItem);
                }
            } else if (StringUtils.isNotBlank(filter)) {
                treeItem.getChildren().clear();
            }
            Object userData = treeItem.getGraphic().getUserData();
            if (StringUtils.isNotBlank(filter)) {
                treeItem.setGraphic(FXUtils.loadImageView("icons/filter.png", 16, 16, userData));
            } else {
                treeItem.setGraphic(FXUtils.loadImageView("icons/computer.png", 16, 16, userData));
            }
        } catch (SQLRecoverableException e) {
            LOG.error(e.getMessage(), e);
            Alerts.error("连接超时");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            Alerts.error(e.getMessage());
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
        useLombokPlugin.setTooltip(new Tooltip("实体类使用Lombok @Data简化代码"));
    }

    /**
     * 加载数据库连接
     */
    @Subscribe
    public void loadDatabaseConnectionTree(List<DatabaseConfiguration> dbConfigs) {
        TreeItem<String> rootTreeItem = leftDBTree.getRoot();
        rootTreeItem.getChildren().clear();
        try {
            // List<DatabaseConfiguration> dbConfigs = ConfigHelper.loadDatabaseConfig();
            for (DatabaseConfiguration dbConfig : dbConfigs) {
                TreeItem<String> treeItem = new TreeItem<>();
                treeItem.setValue(dbConfig.getName());
                treeItem.setGraphic(FXUtils.loadImageView("icons/computer.png", 16, 16, dbConfig));
                rootTreeItem.getChildren().add(treeItem);
            }
        } catch (Exception e) {
            Alerts.error(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
        }
    }

    @FXML
    public void chooseProjectFolder(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedFolder = directoryChooser.showDialog(((Node) event.getSource()).getScene().getWindow());
        if (selectedFolder != null) {
            projectFolderField.setText(selectedFolder.getAbsolutePath());
        }
    }

    @FXML
    public void generateCode() {
        if (tableName == null) {
            Alerts.warn("请先在左侧选择数据库表");
            return;
        }
        String result = validateConfig();
        if (result != null) {
            Alerts.error(result);
            return;
        }
        CodeGenConfiguration generatorConfig = getGeneratorConfigFromUI();
        if (!checkDirs(generatorConfig)) {
            return;
        }

        MyBatisCodeGenerator generator = new MyBatisCodeGenerator();
        generator.setGeneratorConfiguration(generatorConfig);
        generator.setDatabaseConfiguration(selectedDatabaseConfig);
        generator.setIgnoredColumns(ignoredColumns);
        generator.setColumnOverrides(columnOverrides);
        UIProgressCallback alert = new UIProgressCallback(Alert.AlertType.INFORMATION);
        generator.setProgressCallback(alert);
        alert.show();
        PictureProcessStateController pictureProcessStateController = null;
        try {
            //Engage PortForwarding
            Session sshSession = SSHHelper.openSession(selectedDatabaseConfig);
            DBUtils.engagePortForwarding(sshSession, selectedDatabaseConfig);
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
                generator.generate();
            } catch (Exception exception) {
                Alerts.error("生成失败: " + exception.getMessage());
            }

            if (pictureProcessStateController != null) {
                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        Thread.sleep(3000);
                        return null;
                    }
                };
                PictureProcessStateController finalPictureProcessStateController = pictureProcessStateController;
                task.setOnSucceeded(event -> finalPictureProcessStateController.close());
                task.setOnFailed(event -> finalPictureProcessStateController.close());
                new Thread(task).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error(e.getMessage());
            if (pictureProcessStateController != null) {
                pictureProcessStateController.close();
                pictureProcessStateController.playFailState(e.getMessage(), true);
            }
        }
    }

    private String validateConfig() {
        String projectFolder = projectFolderField.getText();
        if (StringUtils.isEmpty(projectFolder)) {
            return "项目目录不能为空";
        }
        if (StringUtils.isEmpty(domainObjectNameField.getText())) {
            return "类名不能为空";
        }
        if (StringUtils.isAnyEmpty(modelTargetPackage.getText(), mapperTargetPackage.getText(), daoTargetPackage.getText())) {
            return "包名不能为空";
        }
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
            if (StringUtils.isEmpty(name)) {
                Alerts.error("名称不能为空");
                return;
            }
            LOG.info("user choose name: {}", name);
            try {
                CodeGenConfiguration generatorConfig = getGeneratorConfigFromUI();
                generatorConfig.setName(name);
                ConfigHelper.deleteGeneratorConfig(name);
                ConfigHelper.saveGeneratorConfig(generatorConfig);
            } catch (Exception e) {
                LOG.error("保存配置失败", e);
                Alerts.error("保存配置失败");
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
        return generatorConfig;
    }

    public void setGeneratorConfigIntoUI(CodeGenConfiguration generatorConfig) {
        projectFolderField.setText(generatorConfig.getProjectFolder());
        modelTargetPackage.setText(generatorConfig.getModelPackage());
        generateKeysField.setText(generatorConfig.getGenerateKeys());
        modelTargetProject.setText(generatorConfig.getModelPackageTargetFolder());
        daoTargetPackage.setText(generatorConfig.getDaoPackage());
        daoTargetProject.setText(generatorConfig.getDaoTargetFolder());
        mapperTargetPackage.setText(generatorConfig.getMappingXMLPackage());
        mappingTargetProject.setText(generatorConfig.getMappingXMLTargetFolder());
        if (StringUtils.isBlank(tableNameField.getText())) {
            tableNameField.setText(generatorConfig.getTableName());
            mapperName.setText(generatorConfig.getMapperName());
            domainObjectNameField.setText(generatorConfig.getDomainObjectName());
        }
        offsetLimitCheckBox.setSelected(generatorConfig.isOffsetLimit());
        commentCheckBox.setSelected(generatorConfig.isComment());
        overrideXML.setSelected(generatorConfig.isOverrideXML());
        needToStringHashcodeEquals.setSelected(generatorConfig.isNeedToStringHashcodeEquals());
        useLombokPlugin.setSelected(generatorConfig.isUseLombokPlugin());
        useTableNameAliasCheckbox.setSelected(generatorConfig.isUseTableNameAlias());
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
     * 自定义列
     */
    @FXML
    public void openCustomizeTableColumn(MouseEvent event) {
        if (tableName == null) {
            Alerts.warn("请先在左侧选择数据库表");
            return;
        }
        SelectTableColumnController controller = (SelectTableColumnController) loadFXMLPage("定制列", FXMLPage.SELECT_TABLE_COLUMN, true);
        controller.setMainUIController(this);
        try {
            // If select same schema and another table, update table data
            if (!tableName.equals(controller.getTableName())) {
                List<TableColumnCustomization> tableColumns = DBUtils.getTableColumns(selectedDatabaseConfig, tableName);
                controller.setColumnList(FXCollections.observableList(tableColumns));
                controller.setTableName(tableName);
            }
            controller.showDialogStage();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            Alerts.error(e.getMessage());
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
     * @return
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
            alert.setContentText(Messages.getString("Confirmation.12"));
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.isPresent()) {
                if (ButtonType.OK == optional.get()) {
                    try {
                        for (String dir : dirs) {
                            FileUtils.forceMkdir(new File(dir));
                        }
                        return true;
                    } catch (Exception e) {
                        Alerts.error("创建目录失败，请检查目录是否是文件而非目录");
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
            Alerts.error("打开目录失败，请检查目录是否填写正确" + e.getMessage());
        }
    }
}
