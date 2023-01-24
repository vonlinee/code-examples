package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.toolkit.fxui.common.JDBCDriver;
import io.devpl.toolkit.fxui.model.props.ConnectionInfo;
import io.devpl.toolkit.fxui.utils.Validator;
import io.devpl.toolkit.fxui.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 新建数据库连接控制器
 */
@FxmlLocation(location = "static/fxml/newConnection.fxml")
public class NewConnectionController extends FxmlView {

    @FXML
    public Tab tabTcpIpConnection;
    @FXML
    public Tab tabSshConnection;

    // 连接信息配置
    private final ConnectionInfo connectionInfo = new ConnectionInfo();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 父FXML的initialize方法晚于子FXML的initialize方法执行
        publish("init-binder", this.connectionInfo);
    }

    /**
     * 保存数据库连接
     * @param event
     */
    @FXML
    void saveConnection(ActionEvent event) {
        String msg = Validator.target(connectionInfo)
                .hasText(ConnectionInfo::getUsername, "用户名不能为空")
                .hasText(ConnectionInfo::getPassword, "密码不能为空")
                .hasText(ConnectionInfo::getHost, "连接地址不能为空")
                .getErrorMessages();
        if (StringUtils.hasText(msg)) {
            Alerts.error(msg).show();
            return;
        }
        publish("add-new-connection", connectionInfo);
        getStage(event).close();
    }

    /**
     * 测试数据库连接
     * @param actionEvent ActionEvent
     */
    @FXML
    public void testConnection(ActionEvent actionEvent) {
        String msg = Validator.target(connectionInfo)
                .hasText(ConnectionInfo::getUsername, "用户名不能为空")
                .hasText(ConnectionInfo::getPassword, "密码不能为空")
                .hasText(ConnectionInfo::getHost, "连接地址不能为空")
                .getErrorMessages();
        if (StringUtils.hasText(msg)) {
            Alerts.error(msg).show();
            return;
        }
        publish("TestConnection", connectionInfo);
    }

    /**
     * 填充默认值
     * @param actionEvent 事件
     */
    @FXML
    public void fillDefaultConnectionInfo(ActionEvent actionEvent) {
        connectionInfo.setDbType(JDBCDriver.MYSQL5.name());
        connectionInfo.setEncoding("utf8");
        connectionInfo.setHost("127.0.0.1");
        connectionInfo.setPort("3306");
        connectionInfo.setUsername("root");
        connectionInfo.setPassword("123456");
    }
}
