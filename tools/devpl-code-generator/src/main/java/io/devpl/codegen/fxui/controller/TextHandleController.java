package io.devpl.codegen.fxui.controller;

import io.devpl.codegen.fxui.framework.mvc.FXController;
import io.devpl.codegen.fxui.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 文本处理工具
 */
public class TextHandleController extends FXController {

    @FXML
    public BorderPane bopRoot;
    @FXML
    public SplitPane sppTxtArea;
    @FXML
    public Group grpLeft;
    @FXML
    public Group grpRight;
    @FXML
    public TextArea txtaLeft;
    @FXML
    public TextArea txtaRight;
    @FXML
    public FlowPane flpBottom;
    @FXML
    public Button btnMaven;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sppTxtArea.prefWidthProperty().bind(bopRoot.widthProperty());
        sppTxtArea.setDividerPositions(0.5);
        txtaLeft.prefWidthProperty().bind(bopRoot.widthProperty().divide(2));
        txtaRight.prefWidthProperty().bind(bopRoot.widthProperty().divide(2));
    }

    @FXML
    public void mavenConvert(ActionEvent actionEvent) {
        String mavenText = txtaLeft.getText();
        String gradleText = txtaRight.getText();
        boolean lEmpty = StringUtils.isEmpty(mavenText);
        boolean rEmpty = StringUtils.isEmpty(gradleText);
        if (lEmpty & rEmpty) return;
        if (lEmpty) {
            // gradle 转 maven
            return;
        }
        if (rEmpty) {
            return;
        }
    }
}
