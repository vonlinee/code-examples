package io.devpl.toolkit.fxui.controller;

import com.jcraft.jsch.Session;
import io.devpl.toolkit.framework.mvc.FxmlView;
import io.devpl.toolkit.fxui.model.DatabaseInfo;
import io.devpl.toolkit.framework.Alerts;
import io.devpl.toolkit.framework.mvc.FXControllerBase;
import io.devpl.toolkit.fxui.utils.DBUtils;
import io.devpl.toolkit.fxui.utils.StringUtils;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.greenrobot.eventbus.Subscribe;

import java.io.EOFException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 新建数据库连接控制器
 */
@FxmlView(location = "static/fxml/newConnection.fxml")
public class NewConnectionController extends FXControllerBase {

    @FXML
    public Tab tabTcpIpConnection;
    @FXML
    public Tab tabSshConnection;
    @FXML
    private TabPane tabPane;

    // fx:include可以自动注入Controller，只要名称为${fx:id}Controller
    @FXML
    private DbConnectionController tabControlAController;
    @FXML
    private OverSshController tabControlBController;
    private boolean isOverssh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.setPrefHeight(((AnchorPane) tabPane
                .getSelectionModel()
                .getSelectedItem()
                .getContent()).getPrefHeight());
        // 监听tab切换
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            isOverssh = observable.getValue().getText().equals("SSH");
            final Pane content = (Pane) tabPane.getSelectionModel().getSelectedItem().getContent();
            tabPane.prefHeightProperty().bind(content.prefHeightProperty());
            final Stage stage = getStage(tabPane);
            stage.close();
            stage.show();
        });
    }

    @Subscribe
    public void setConfig(DatabaseInfo selectedConfig) {
        tabControlAController.setConfig(selectedConfig);
        tabControlBController.setDbConnectionConfig(selectedConfig);
        if (StringUtils.isNoneBlank(selectedConfig.getSshHost(), selectedConfig.getSshPassword(), selectedConfig.getSshPort(), selectedConfig.getSshUser(), selectedConfig.getLport())) {
            log.info("Found SSH based Config");
            tabPane.getSelectionModel().selectLast();
        }
    }

    private DatabaseInfo extractConfigForUI() {
        if (isOverssh) {
            return tabControlBController.extractConfigFromUi();
        } else {
            return tabControlAController.extractConfigForUI();
        }
    }

    @FXML
    private void saveConnection(ActionEvent event) {
        if (isOverssh) {
            tabControlBController.saveConfig(event);
        } else {
            tabControlAController.saveConnection(event);
        }
    }

    /**
     * 测试链接
     * @param actionEvent
     */
    @FXML
    public void testConnection(ActionEvent actionEvent) {
        DatabaseInfo config = extractConfigForUI();
        if (config == null) {
            return;
        }
        if (StringUtils.isAnyEmpty(config.getName(), config.getHost(), config.getPort(), config.getUsername(), config.getEncoding(), config.getDbType(), config.getSchema())) {
            Alerts.warn("密码以外其他字段必填").showAndWait();
            return;
        }
        Session sshSession = DBUtils.getSSHSession(config);
        if (isOverssh && sshSession != null) {
            PictureProcessStateController pictureProcessState = new PictureProcessStateController();
            pictureProcessState.startPlay();
            // 如果不用异步，则视图会等方法返回才会显示
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    DBUtils.engagePortForwarding(sshSession, config);
                    DBUtils.getConnection(config);
                    return null;
                }
            };
            task.setOnFailed(event -> {
                Throwable e = task.getException();
                log.error("task Failed", e);
                if (e instanceof RuntimeException) {
                    if (e.getMessage().equals("Address already in use: JVM_Bind")) {
                        tabControlBController.setLPortLabelText(config.getLport() + "已经被占用，请换其他端口");
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
                    tabControlBController.recoverNotice();
                } catch (Exception e) {
                    log.error("", e);
                }
            });
            new Thread(task).start();
        } else {
            try {
                DBUtils.getConnection(config);
                Alerts.info("连接成功").showAndWait();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                Alerts.warn("连接失败: " + e.getMessage()).showAndWait();
            }
        }
    }

    @FXML
    public void cancel(ActionEvent event) {
        getStage(event).close();
    }
}
