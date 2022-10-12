package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.common.DbType;
import io.devpl.codegen.common.utils.ConfigHelper;
import io.devpl.codegen.fxui.framework.ControllerEvent;
import io.devpl.codegen.fxui.model.DatabaseConfiguration;
import io.devpl.codegen.fxui.utils.AlertDialog;
import io.devpl.codegen.fxui.utils.FXUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TcpIPConnConfigController extends FXControllerBase {

    private static final Logger _LOG = LoggerFactory.getLogger(TcpIPConnConfigController.class);

    @FXML
    protected TextField nameField;
    @FXML
    protected TextField hostField;
    @FXML
    protected TextField portField;
    @FXML
    protected TextField userNameField;
    @FXML
    protected TextField passwordField;
    @FXML
    protected TextField schemaField;
    @FXML
    protected ChoiceBox<String> encodingChoice;
    @FXML
    protected ChoiceBox<String> dbTypeChoice;

    protected MainController mainUIController;
    protected DbConnConfigController tabPaneController;
    protected boolean isUpdate = false;
    protected Integer primayKey;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbTypeChoice.setItems(FXCollections.observableArrayList(DbType.supportedDatabaseProductNames()));
        // 默认选择MySQL5
        dbTypeChoice.getSelectionModel().select(DbType.MYSQL5.getProductName());
    }


    /**
     * 保存连接配置信息
     */
    void saveConnection() {
        try {
            // TODO 配置持久化
            ConfigHelper.saveDatabaseConfig(this.isUpdate, primayKey, assembleDbConnInfoConfiguration());
            if (FXUtils.closeOwnerStage(nameField)) {
                // 加载数据库连接，获取所有的表信息
                mainUIController.loadDatabaseConnectionTree();
                // fireEvent(new ControllerEvent(ControllerEvent.RECEIVE_DATA));
            }
        } catch (Exception e) {
            _LOG.error(e.getMessage(), e);
            AlertDialog.showError(e.getMessage());
        }
    }

    void setMainUIController(MainController controller) {
        this.mainUIController = controller;
        super.setDialogStage(mainUIController.getDialogStage());
    }

    public void setTabPaneController(DbConnConfigController tabPaneController) {
        this.tabPaneController = tabPaneController;
    }

    /**
     * 提取界面所填的参数
     * @return
     */
    public DatabaseConfiguration assembleDbConnInfoConfiguration() {
        String name = nameField.getText();
        String host = hostField.getText();
        String port = portField.getText();
        String userName = userNameField.getText();
        String password = passwordField.getText();
        String encoding = encodingChoice.getValue();
        String dbType = dbTypeChoice.getValue();
        String schema = schemaField.getText();
        DatabaseConfiguration config = new DatabaseConfiguration();
        if (StringUtils.isAnyEmpty(name, host, port, userName, encoding, dbType)) {
            Alert confirm = AlertDialog.buildConfirmation("是否填充默认值(MySQL5)？");
            Optional<ButtonType> buttonType = confirm.showAndWait();
            // 常用MySQL5版本的配置
            if (buttonType.isPresent()) {
                if (buttonType.get() == ButtonType.OK) {
                    initDefaultMySQLConnectionInfo();
                    // 直接返回，再次点击测试连接按钮
                    return null;
                }
            }
        }
        config.setName(name);
        config.setDbType(dbType);
        config.setHost(host);
        config.setPort(port);
        config.setUsername(userName);
        config.setPassword(password);
        config.setSchema(schema);
        config.setEncoding(encoding);
        return config;
    }

    // 默认填充MySQL的常用配置
    private void initDefaultMySQLConnectionInfo() {
        FXUtils.setTextIfEmpty(hostField, "127.0.0.1");
        FXUtils.setTextIfEmpty(portField, "3306");
        FXUtils.setTextIfEmpty(userNameField, "root");
        FXUtils.setTextIfEmpty(passwordField, "123456");
        FXUtils.setValueIfEmpty(encodingChoice, "utf8");
        FXUtils.setValueIfEmpty(dbTypeChoice, DbType.MYSQL5.name());
        FXUtils.setTextIfEmpty(nameField, hostField.getText() + ":" + portField.getText() + ":" + userNameField.getText());
    }

    public void setConfig(DatabaseConfiguration config) {
        isUpdate = true;
        primayKey = config.getId(); // save id for update config
        nameField.setText(config.getName());
        hostField.setText(config.getHost());
        portField.setText(config.getPort());
        userNameField.setText(config.getUsername());
        passwordField.setText(config.getPassword());
        encodingChoice.setValue(config.getEncoding());
        dbTypeChoice.setValue(config.getDbType());
        schemaField.setText(config.getSchema());
    }
}
