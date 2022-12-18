package io.devpl.toolkit.fxui.controller;

import io.devpl.toolkit.fxui.config.Constants;
import io.devpl.toolkit.fxui.config.DBDriver;
import io.devpl.toolkit.fxui.config.DatabaseConfig;
import io.devpl.toolkit.fxui.event.LoadDbTreeEvent;
import io.devpl.toolkit.fxui.framework.Alerts;
import io.devpl.toolkit.fxui.framework.JFX;
import io.devpl.toolkit.fxui.utils.ConfigHelper;
import io.devpl.toolkit.fxui.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 数据库连接控制器
 * 负责从界面的配置连接数据库
 */
public class DbConnectionController extends FXControllerBase {

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

    protected boolean isUpdate = false;
    protected Integer primayKey;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbTypeChoice.setItems(JFX.arrayOf(DBDriver.supportedDbNames()));
        dbTypeChoice.setValue(DBDriver.DEFAULT_DRIVER.name());
        encodingChoice.setItems(JFX.arrayOf(Constants.SUPPORTED_ENCODING));
        encodingChoice.setValue(Constants.DEFAULT_ENCODING);
        hostField.setText(Constants.LOCALHOST);
        userNameField.setText(Constants.MYSQL_ROOT_USERNAME);
        portField.setText(String.valueOf(Constants.DEFAULT_MYSQL_SERVER_PORT));
    }

    final void saveConnection(ActionEvent event) {
        DatabaseConfig config = extractConfigForUI();
        if (config == null) {
            return;
        }
        try {
            ConfigHelper.saveDatabaseConfig(this.isUpdate, primayKey, config);
            JFX.getStage(event).close();
            this.post(new LoadDbTreeEvent());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Alerts.error(e.getMessage()).show();
        }
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
            Alerts.warn("密码以外其他字段必填").showAndWait();
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
