package io.devpl.toolkit.fxui.controller;

import com.jcraft.jsch.Session;
import io.devpl.toolkit.framework.Alerts;
import io.devpl.toolkit.framework.mvc.AbstractViewController;
import io.devpl.toolkit.framework.mvc.FxmlView;
import io.devpl.toolkit.fxui.common.JdbcDriver;
import io.devpl.toolkit.fxui.model.DatabaseInfo;
import io.devpl.toolkit.fxui.model.props.ConnectionInfo;
import io.devpl.toolkit.fxui.utils.Assert;
import io.devpl.toolkit.fxui.utils.DBUtils;
import io.devpl.toolkit.fxui.utils.StringUtils;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

import java.io.EOFException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 新建数据库连接控制器
 */
@FxmlView(location = "static/fxml/newConnection.fxml")
public class NewConnectionController extends AbstractViewController {

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
        String msg = Assert.target(connectionInfo)
                           .hasText(ConnectionInfo::getUsername, "用户名不能为空")
                           .hasText(ConnectionInfo::getPassword, "密码不能为空")
                           .hasText(ConnectionInfo::getHost, "连接地址不能为空")
                           .getErrorMessages();
        if (StringUtils.hasText(msg)) {
            Alerts.error(msg).show();
            return;
        }
        publish("save-connection", connectionInfo);

        getStage(event).close();
    }

    /**
     * 测试数据库连接
     * @param actionEvent ActionEvent
     */
    @FXML
    public void testConnection(ActionEvent actionEvent) {
        String msg = Assert.target(connectionInfo)
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

    // 暂时不用SSH连接
    private void testSshConnection(DatabaseInfo config) {
        Session sshSession = DBUtils.getSSHSession(config);
        PictureProcessStateController pictureProcessState = new PictureProcessStateController();
        pictureProcessState.startPlay();
        // 如果不用异步，则视图会等方法返回才会显示
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                DBUtils.engagePortForwarding(sshSession, config);
                // DBUtils.getConnection(config);
                return null;
            }
        };
        task.setOnFailed(event -> {
            Throwable e = task.getException();
            log.error("task Failed", e);
            if (e instanceof RuntimeException) {
                if (e.getMessage().equals("Address already in use: JVM_Bind")) {
                    // tabControlBController.setLPortLabelText(config.getLport() + "已经被占用，请换其他端口");
                }
                // 端口转发一定不成功，导致数据库连接不上
                pictureProcessState.playFailState("连接失败:" + e.getMessage(), true);
                return;
            }
            if (e.getCause() instanceof EOFException) {
                pictureProcessState.playFailState("连接失败, 请检查数据库的主机名，并且检查端口和目标端口是否一致", true);
                // 端口转发已经成功，但是数据库连接不上，故需要释放连接
                DBUtils.shutdownPortForwarding(sshSession);
                return;
            }
            pictureProcessState.playFailState("连接失败:" + e.getMessage(), true);
            // 可能是端口转发已经成功，但是数据库连接不上，故需要释放连接
            DBUtils.shutdownPortForwarding(sshSession);
        });
        task.setOnSucceeded(event -> {
            try {
                pictureProcessState.playSuccessState("连接成功", true);
                DBUtils.shutdownPortForwarding(sshSession);
            } catch (Exception e) {
                log.error("", e);
            }
        });
        new Thread(task).start();
    }

    /**
     * 填充默认值
     * @param actionEvent 事件
     */
    @FXML
    public void fillDefaultConnectionInfo(ActionEvent actionEvent) {
        connectionInfo.setDbType(JdbcDriver.MySQL5.name());
        connectionInfo.setEncoding("utf8");
        connectionInfo.setHost("127.0.0.1");
        connectionInfo.setPort("3306");
        connectionInfo.setUsername("root");
        connectionInfo.setPassword("123456");
    }
}
