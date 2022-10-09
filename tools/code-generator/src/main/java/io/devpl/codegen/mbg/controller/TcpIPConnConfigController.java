package io.devpl.codegen.mbg.controller;

import io.devpl.codegen.mbg.model.DatabaseConfiguration;
import io.devpl.codegen.mbg.model.DbType;
import io.devpl.codegen.mbg.utils.ConfigHelper;
import io.devpl.codegen.mbg.view.AlertDialog;
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
import java.util.UUID;

public class TcpIPConnConfigController extends BaseFXController {

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

    }

    final void saveConnection() {
        try {
            ConfigHelper.saveDatabaseConfig(this.isUpdate, primayKey, assembleDbConnInfoConfiguration());
            this.tabPaneController.getDialogStage().close();
            mainUIController.loadLeftDBTree();
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
        if (StringUtils.isAnyEmpty(name, host, port, userName, encoding, dbType, schema)) {
            Alert confirm = AlertDialog.buildConfirmation("是否填充默认值？");
            Optional<ButtonType> buttonType = confirm.showAndWait();
            buttonType.ifPresent(type -> {
                if (type == ButtonType.OK) {
                    // 默认填充MySQL的常用配置
                    if (name == null || name.isEmpty()) nameField.setText(UUID.randomUUID().toString());
                    if (host == null || host.isEmpty()) hostField.setText("127.0.0.1");
                    if (port == null || port.isEmpty()) portField.setText("3306");
                    if (userName == null || userName.isEmpty()) userNameField.setText("root");
                    if (password == null || password.isEmpty()) passwordField.setText("root");
                    if (encoding == null || encoding.isEmpty()) encodingChoice.setValue("utf8");
                    if (dbType == null || dbType.isEmpty()) dbTypeChoice.setValue(DbType.MySQL.name());
                } else {
                    AlertDialog.showError("除密码外所有选项必填！");
                }
            });
            return config;
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
