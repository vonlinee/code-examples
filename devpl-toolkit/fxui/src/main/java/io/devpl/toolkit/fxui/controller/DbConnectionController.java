package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.JFX;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.toolkit.fxui.common.Constants;
import io.devpl.toolkit.fxui.common.JDBCDriver;
import io.devpl.toolkit.fxui.model.props.ConnectionInfo;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

/**
 * 数据库连接控制器
 * 负责从界面的配置连接数据库
 */
@FxmlLocation(location = "static/fxml/newConnection.fxml")
public class DbConnectionController extends FxmlView {

    @FXML
    public CheckBox savePwdCheckBox;
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
    protected ComboBox<String> schemaField; // 数据库schema，MySQL中就是数据库名
    @FXML
    protected ChoiceBox<String> encodingChoice; // 编码
    @FXML
    protected ChoiceBox<String> dbTypeChoice;  // 数据库类型选择

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbTypeChoice.setItems(JFX.arrayOf(JDBCDriver.supportedDbNames()));
        dbTypeChoice.setValue(JDBCDriver.MYSQL5.name());
        encodingChoice.setItems(JFX.arrayOf(Constants.SUPPORTED_ENCODING));
        encodingChoice.setValue(Constants.DEFAULT_ENCODING);
        hostField.setText(Constants.LOCALHOST);
        userNameField.setText(Constants.MYSQL_ROOT_USERNAME);
        portField.setText(String.valueOf(Constants.DEFAULT_MYSQL_SERVER_PORT));
    }

    /**
     * 初始化数据绑定
     * @param connectionInfo 事件
     */
    @Subscribe(name = "init-binder", threadMode = ThreadMode.BACKGROUND)
    public void initBinder(ConnectionInfo connectionInfo) {
        connectionInfo.nameProperty().bindBidirectional(nameField.textProperty());
        connectionInfo.hostProperty().bindBidirectional(hostField.textProperty());
        connectionInfo.portProperty().bindBidirectional(portField.textProperty());
        connectionInfo.dbTypeProperty().bindBidirectional(dbTypeChoice.valueProperty());
        connectionInfo.schemaProperty().bindBidirectional(schemaField.valueProperty());
        connectionInfo.usernameProperty().bindBidirectional(userNameField.textProperty());
        connectionInfo.passwordProperty().bindBidirectional(passwordField.textProperty());
        connectionInfo.encodingProperty().bindBidirectional(encodingChoice.valueProperty());
    }

    /**
     * 测试连接
     * @param connectionInfo 数据库连接信息
     */
    @Subscribe(name = "TestConnection")
    public void testConnection(ConnectionInfo connectionInfo) {
        try (Connection connection = connectionInfo.getConnection()) {
            Alerts.info("连接成功", connection).show();
        } catch (Exception exception) {
            log.info("连接失败", exception);
            Alerts.exception("连接失败", exception).show();
        }
    }
}
