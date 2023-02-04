package io.devpl.fxsdk;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainViewController implements ControlledStage {

    private StageController controller;

    @Override
    public void setStageController(StageController stageController) {
        this.controller = stageController;
    }

    private Stage stage;

    @FXML
    public void showStageState(MouseEvent mouseEvent) {
        System.out.println(stage.isShowing());
        System.out.println(stage.isAlwaysOnTop());
        System.out.println(stage.isIconified());
        System.out.println(stage.isFocused());
    }

    @FXML
    public void openStage(MouseEvent mouseEvent) {
        stage = new Stage();
        BorderPane borderPane = new BorderPane();
        Button button = new Button();
        VBox vBox = new VBox(button);
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane, 400.0, 400.0);
        stage.setScene(scene);
        stage.show();
        stage.showingProperty().addListener((observableValue, aBoolean, t1) -> {
            System.out.println(observableValue);
            System.out.println(aBoolean);
            System.out.println(t1);
        });
    }
}
