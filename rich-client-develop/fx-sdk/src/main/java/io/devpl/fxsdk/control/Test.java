package io.devpl.fxsdk.control;

import com.jfoenix.controls.JFXAlert;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 600.0, 400.0);

        HBox hBox = new HBox();

        Button button = new Button("弹窗");

        hBox.getChildren().addAll(button);

        button.setOnMouseClicked(event -> {
            JFXAlert<Object> alert = new JFXAlert<>(primaryStage);
            alert.setResizable(true);
            alert.setTitle("1111111");
            alert.setSize(400, 400);
            alert.setContent(new Button("AAAA"));
            alert.show();
        });

        root.setTop(hBox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
