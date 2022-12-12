package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.fxui.config.Constants;
import io.devpl.codegen.fxui.config.DBDriver;
import io.devpl.codegen.fxui.config.DatabaseConfig;
import io.devpl.codegen.fxui.framework.Alerts;
import io.devpl.codegen.fxui.framework.JFX;
import io.devpl.codegen.fxui.utils.ConfigHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * 数据库连接控制器
 * 负责从界面的配置连接数据库
 */
public class DbConnectionController extends FXControllerBase {

    private static final Logger _LOG = LoggerFactory.getLogger(DbConnectionController.class);

    @FXML
    protected TextField nameField; // 数据库名称
    @FXML
    protected TextField hostField; // 主机地址
    @FXML
    protected TextField portField; // 端口
    @FXML
    protected TextField userNameField; // 用户名
    @FXML
    protected TextField passwordField; // 密码
    @FXML
    protected TextField schemaField; // 数据库schema，MySQL中就是数据库名
    @FXML
    protected ChoiceBox<String> encodingChoice; // 编码
    @FXML
    protected ChoiceBox<String> dbTypeChoice;  // 数据库类型选择

    protected MainUIController mainUIController;
    protected TabPaneController tabPaneController;
    protected boolean isUpdate = false;
    protected Integer primayKey;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbTypeChoice.setItems(JFX.arrayOf(DBDriver.supportedDbNames()));
        dbTypeChoice.setValue(DBDriver.DEFAULT_DRIVER.name());
        encodingChoice.setItems(JFX.arrayOf(Constants.SUPPORTED_ENCODING));
        encodingChoice.setValue(Constants.DEFAULT_ENCODING);
    }

    final void saveConnection() {
        DatabaseConfig config = extractConfigForUI();
        if (config == null) {
            return;
        }
        try {
            ConfigHelper.saveDatabaseConfig(this.isUpdate, primayKey, config);
            this.tabPaneController.getDialogStage().close();
            mainUIController.loadLeftDBTree();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Alerts.error(e.getMessage()).show();
        }
    }

    void setMainUIController(MainUIController controller) {
        this.mainUIController = controller;
        super.setDialogStage(mainUIController.getDialogStage());
    }

    public void setTabPaneController(TabPaneController tabPaneController) {
        this.tabPaneController = tabPaneController;
    }

    public DatabaseConfig extractConfigForUI() {
        String name = nameField.getText();
        String host = hostField.getText();
        String port = portField.getText();
        String userName = userNameField.getText();
        String password = passwordField.getText();
        String encoding = encodingChoice.getValue();
        String dbType = dbTypeChoice.getValue();
        String schema = schemaField.getText();
        DatabaseConfig config = new DatabaseConfig();
        config.setName(name);
        config.setDbType(dbType);
        config.setHost(host);
        config.setPort(port);
        config.setUsername(userName);
        config.setPassword(password);
        config.setSchema(schema);
        config.setEncoding(encoding);
        if (StringUtils.isAnyEmpty(name, host, port, userName, encoding, dbType, schema)) {
            Alerts.showWarnAlert("密码以外其他字段必填");
            return null;
        }
        return config;
    }

    public void setConfig(DatabaseConfig config) {
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
