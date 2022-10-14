package io.devpl.codegen.fxui.controller;

import com.jcraft.jsch.Session;
import io.devpl.codegen.common.utils.DBUtils;
import io.devpl.codegen.fxui.model.DatabaseConfiguration;
import io.devpl.codegen.fxui.utils.AlertDialog;
import io.devpl.codegen.fxui.utils.FXUtils;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import org.apache.commons.lang3.StringUtils;

import java.io.EOFException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

/**
 * 数据库连接配置控制器
 */
public class DbConnConfigController extends FXController {

    @FXML
    private TabPane tabPane;

    /**
     * 自动注入
     */
    @FXML
    private TcpIPConnConfigController tcpIPConnConfigController;

    @FXML
    private OverSshConnConfigController sshConnConfigController;

    private boolean isOverssh;

    private MainController mainUIController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Region content = (Region) tabPane.getSelectionModel().getSelectedItem().getContent();
        tabPane.setPrefHeight(content.getPrefHeight());
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            isOverssh = observable.getValue().getText().equals("SSH");
            tabPane.prefHeightProperty().bind(((Region) tabPane.getSelectionModel().getSelectedItem().getContent()).prefHeightProperty());
        });
    }

    public void setMainUIController(MainController mainUIController) {
        this.mainUIController = mainUIController;
        this.tcpIPConnConfigController.setMainUIController(mainUIController);
        this.tcpIPConnConfigController.setTabPaneController(this);
        this.sshConnConfigController.setMainUIController(mainUIController);
        this.sshConnConfigController.setTabPaneController(this);
    }

    public void setConfig(DatabaseConfiguration selectedConfig) {
        tcpIPConnConfigController.setConfig(selectedConfig);
        sshConnConfigController.setDbConnectionConfig(selectedConfig);
        if (StringUtils.isNoneBlank(
                selectedConfig.getSshHost(),
                selectedConfig.getSshPassword(),
                selectedConfig.getSshPort(),
                selectedConfig.getSshUser(),
                selectedConfig.getLport())) {
            tabPane.getSelectionModel().selectLast();
        }
    }

    /**
     * 将界面填的配置聚合
     * 校验配置项，如果有必填项弹窗提示或者填充默认值
     * @return 数据库连接配置, TCP/IP或者SSH连接
     */
    private DatabaseConfiguration extractDatabaseConfiguration() {
        if (isOverssh) {
            return sshConnConfigController.extractConfigFromUi();
        } else {
            return tcpIPConnConfigController.assembleDbConnInfoConfiguration();
        }
    }

    @FXML
    private void saveConnection() {
        if (isOverssh) {
            sshConnConfigController.saveConfig();
        } else {
            tcpIPConnConfigController.saveConnection();
        }
    }

    /**
     * 测试连接按钮
     */
    @FXML
    private void testDbConnection() {
        DatabaseConfiguration config = extractDatabaseConfiguration();
        if (config == null) return;
        if (isOverssh) {
            // SSH连接
            Session sshSession = DBUtils.getSSHSession(config);
            if (sshSession == null) return;
            PictureProcessStateController pictureProcessState = new PictureProcessStateController();
            pictureProcessState.setDialogStage(getDialogStage());
            pictureProcessState.startPlay();
            //如果不用异步，则视图会等方法返回才会显示
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
                LOG.error("task Failed", e);
                if (e instanceof RuntimeException) {
                    if (e.getMessage().equals("Address already in use: JVM_Bind")) {
                        sshConnConfigController.setLPortLabelText(config.getLport() + "已经被占用，请换其他端口");
                    }
                    //端口转发一定不成功，导致数据库连接不上
                    pictureProcessState.playFailState("连接失败:" + e.getMessage(), true);
                    return;
                }
                if (e.getCause() instanceof EOFException) {
                    pictureProcessState.playFailState("连接失败, 请检查数据库的主机名，并且检查端口和目标端口是否一致", true);
                    //端口转发已经成功，但是数据库连接不上，故需要释放连接
                    DBUtils.shutdownPortForwarding(sshSession);
                    return;
                }
                pictureProcessState.playFailState("连接失败:" + e.getMessage(), true);
                //可能是端口转发已经成功，但是数据库连接不上，故需要释放连接
                DBUtils.shutdownPortForwarding(sshSession);
            });
            task.setOnSucceeded(event -> {
                try {
                    pictureProcessState.playSuccessState("连接成功", true);
                    DBUtils.shutdownPortForwarding(sshSession);
                    sshConnConfigController.recoverNotice();
                } catch (Exception e) {

                }
            });
            new Thread(task).start();
        } else {
            try {
                Connection connection = DBUtils.getConnection(config);
                AlertDialog.showInformation("连接成功:" + connection);
            } catch (RuntimeException e) {
                AlertDialog.showWarning("连接失败, " + e.getMessage());
            } catch (Exception e) {
                AlertDialog.showWarning("连接失败");
            }
        }
    }

    @FXML
    void cancel() {
        FXUtils.closeOwnerStage(tabPane);
    }
}
