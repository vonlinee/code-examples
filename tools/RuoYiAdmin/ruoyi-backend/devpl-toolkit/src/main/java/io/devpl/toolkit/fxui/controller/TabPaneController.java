package io.devpl.toolkit.fxui.controller;

import com.jcraft.jsch.Session;

import io.devpl.toolkit.fxui.config.DatabaseConfig;
import io.devpl.toolkit.fxui.framework.JFX;
import io.devpl.toolkit.fxui.utils.DbUtils;
import io.devpl.toolkit.fxui.framework.Alerts;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.lang3.StringUtils;

import java.io.EOFException;
import java.net.URL;
import java.util.ResourceBundle;

public class TabPaneController extends FXControllerBase {

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
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            isOverssh = observable.getValue().getText().equals("SSH");
            final Pane content = (Pane) tabPane.getSelectionModel().getSelectedItem().getContent();
            tabPane.prefHeightProperty().bind(content.prefHeightProperty());
            final Stage stage = getStage(tabPane);
            stage.close();
            stage.show();
        });
    }

    public void setConfig(DatabaseConfig selectedConfig) {
        tabControlAController.setConfig(selectedConfig);
        tabControlBController.setDbConnectionConfig(selectedConfig);
        if (StringUtils.isNoneBlank(selectedConfig.getSshHost(), selectedConfig.getSshPassword(), selectedConfig.getSshPort(), selectedConfig.getSshUser(), selectedConfig.getLport())) {
            log.info("Found SSH based Config");
            tabPane.getSelectionModel().selectLast();
        }
    }

    private DatabaseConfig extractConfigForUI() {
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

    @FXML
    public void testConnection(ActionEvent actionEvent) {
        DatabaseConfig config = extractConfigForUI();
        if (config == null) {
            return;
        }
        if (StringUtils.isAnyEmpty(config.getName(), config.getHost(), config.getPort(), config.getUsername(), config.getEncoding(), config.getDbType(), config.getSchema())) {
            Alerts.showWarnAlert("密码以外其他字段必填");
            return;
        }
        Session sshSession = DbUtils.getSSHSession(config);
        if (isOverssh && sshSession != null) {
            PictureProcessStateController pictureProcessState = new PictureProcessStateController();
            pictureProcessState.startPlay();
            // 如果不用异步，则视图会等方法返回才会显示
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    DbUtils.engagePortForwarding(sshSession, config);
                    DbUtils.getConnection(config);
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
                    DbUtils.shutdownPortForwarding(sshSession);
                    return;
                }
                pictureProcessState.playFailState("连接失败:" + e.getMessage(), true);
                // 可能是端口转发已经成功，但是数据库连接不上，故需要释放连接
                DbUtils.shutdownPortForwarding(sshSession);
            });
            task.setOnSucceeded(event -> {
                try {
                    pictureProcessState.playSuccessState("连接成功", true);
                    DbUtils.shutdownPortForwarding(sshSession);
                    tabControlBController.recoverNotice();
                } catch (Exception e) {
                    log.error("", e);
                }
            });
            new Thread(task).start();
        } else {
            try {
                DbUtils.getConnection(config);
                Alerts.showInfoAlert("连接成功");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                Alerts.showWarnAlert("连接失败: " + e.getMessage());
            }
        }
    }

    @FXML
    public void cancel(ActionEvent event) {
        getStage(event).close();
    }
}
