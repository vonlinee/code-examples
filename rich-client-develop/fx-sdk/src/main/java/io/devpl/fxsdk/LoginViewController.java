package io.devpl.fxsdk;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by CatScan on 2016/6/21.
 */
public class LoginViewController implements ControlledStage, Initializable {
    StageController myController;

    public void setStageController(StageController stageController) {
        this.myController = stageController;
    }

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goToMain() {
        myController.setStage(MainApp.mainViewID);
    }

    @FXML
    public void login(MouseEvent mouseEvent) {
        Stage stage = WindowManager.getInstance().createChildStage();
        stage.setWidth(400.0);
        stage.setHeight(200.0);
        stage.setTitle("子模态窗口");
        stage.show();
    }
}