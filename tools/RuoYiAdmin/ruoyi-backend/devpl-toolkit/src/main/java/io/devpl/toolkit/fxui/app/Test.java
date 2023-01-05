package io.devpl.toolkit.fxui.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        Scene scene = new Scene(root, 500, 400);

        HBox hBox = new HBox();

        Button btn = new Button("Button");

        hBox.getChildren().add(btn);

        root.setTop(hBox);

        btn.setOnAction(event -> {

        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
