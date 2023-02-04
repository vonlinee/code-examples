package io.devpl.toolkit.fxui.controller;

import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.mvc.FxmlLocation;
import io.devpl.fxtras.mvc.FxmlView;
import io.devpl.toolkit.fxui.dao.ConnectionConfigurationDao;
import io.devpl.toolkit.fxui.event.Events;
import io.devpl.toolkit.fxui.event.FillDefaultValueEvent;
import io.devpl.toolkit.fxui.model.ConnectionRegistry;
import io.devpl.toolkit.fxui.model.props.ConnectionConfig;
import io.devpl.toolkit.fxui.utils.SingletonFactory;
import io.devpl.toolkit.fxui.utils.Validator;
import io.devpl.toolkit.fxui.utils.StringUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;

import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * 新建数据库连接控制器
 */
@FxmlLocation(location = "static/fxml/newConnection.fxml", title = "新建数据库连接")
public class NewConnectionController extends FxmlView {

    @FXML
    public Tab tabTcpIpConnection;
    @FXML
    public Tab tabSshConnection;

    /**
     * 与界面绑定的连接信息配置
     */
    private final ConnectionConfig connConfig = new ConnectionConfig();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        publish(Events.FILL_CONNECTION_INFO, connConfig);
    }

    /**
     * 保存数据库连接信息
     * @param event 事件
     */
    @FXML
    public void saveConnection(ActionEvent event) {
        String msg = Validator.target(connConfig)
                .hasText(ConnectionConfig::getUsername, "用户名不能为空")
                .hasText(ConnectionConfig::getPassword, "密码不能为空")
                .hasText(ConnectionConfig::getHost, "连接地址不能为空")
                .getErrorMessages();
        if (StringUtils.hasText(msg)) {
            Alerts.error(msg).show();
            return;
        }
        if (ConnectionRegistry.contains(connConfig.getConnectionName())) {
            Optional<ButtonType> choice = Alerts.confirm("项目中已存在具有相同连接名称的连接信息，是否忽略?")
                    .showAndWait();
            choice.ifPresent(buttonType -> {
                if (buttonType == ButtonType.APPLY) {
                    getStage(event).close();
                }
            });
            return;
        }
        publish(Events.ADD_NEW_CONNECTION, connConfig);
        getStage(event).close();
        Platform.runLater(() -> SingletonFactory.getWeakInstance(ConnectionConfigurationDao.class).save(connConfig));
    }

    /**
     * 测试数据库连接
     * @param actionEvent ActionEvent
     */
    @FXML
    public void testConnection(ActionEvent actionEvent) {
        String msg = Validator.target(connConfig)
                .hasText(ConnectionConfig::getUsername, "用户名不能为空")
                .hasText(ConnectionConfig::getPassword, "密码不能为空")
                .hasText(ConnectionConfig::getHost, "连接地址不能为空")
                .getErrorMessages();
        if (StringUtils.hasText(msg)) {
            Alerts.error(msg).show();
            return;
        }
        try (Connection connection = connConfig.getConnection()) {
            Alerts.info("连接成功", getConnectionInfo(connection)).show();
        } catch (Exception exception) {
            log.info("连接失败", exception);
            Alerts.exception("连接失败", exception).show();
        }
    }

    /**
     * 获取数据库连接信息
     * @param connection 数据库连接
     * @return
     */
    private String getConnectionInfo(Connection connection) {
        StringBuilder sb = new StringBuilder();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            sb.append("DatabaseProductName:").append(metaData.getDatabaseProductName());
            sb.append("DriverName:").append(metaData.getDriverName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 填充默认值
     * @param actionEvent 事件
     */
    @FXML
    public void fillDefaultConnectionInfo(ActionEvent actionEvent) {
        publish(new FillDefaultValueEvent());
    }
}
