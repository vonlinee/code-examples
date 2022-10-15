package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.fxui.event.MsgDisplayEvent;
import io.devpl.codegen.fxui.frame.FXController;
import io.devpl.codegen.fxui.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.greenrobot.eventbus.Subscribe;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 用于展示提示信息，消息窗口，比如展示异常调用栈
 */
public class MessageDisplayController extends FXController {

    private Stage msgDisplayWindow;

    @FXML
    public Label titleLabel;
    @FXML
    public TextArea msgContentTextArea;
    @FXML
    public HBox operationHBox;
    @FXML
    public Button okBtn;
    @FXML
    public VBox rootVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        msgContentTextArea.prefHeightProperty()
                .bind(rootVBox.heightProperty()
                        .subtract(titleLabel.prefHeightProperty())
                        .subtract(operationHBox.prefHeightProperty()));
    }

    @FXML
    public void closeMsgDisplayWindow(MouseEvent event) {
        if (msgDisplayWindow == null) {
            msgDisplayWindow = (Stage) okBtn.getScene().getWindow();
        }
        msgDisplayWindow.close();
    }

    @Subscribe
    public void displayMessage(MsgDisplayEvent msg) {
        if (msgDisplayWindow == null) {
            msgDisplayWindow = FXUtils.createDialogStage(msg.getOwnerStage());
            msgDisplayWindow.setTitle("消息提示");
        }
        titleLabel.setText(msg.getTitle());
        msgContentTextArea.setText(msg.getContent());
        msgDisplayWindow.show();
    }
}
