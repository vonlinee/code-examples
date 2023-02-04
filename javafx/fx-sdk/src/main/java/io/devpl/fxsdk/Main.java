package io.devpl.fxsdk;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXTabPane;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    Stage stage = new Stage();
    Group group1;
    Group group2;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, 500.0, 400.0);

        Button btn1 = new Button("Button1");
        btn1.setOnAction(event -> {
            stage.setWidth(400.0);
            stage.setHeight(400.0);
            stage.show();
        });
        Button btn2 = new Button("Button2");
        btn2.setOnAction(event -> {
            if (group1 == null) {
                group1 = new Group();
            }
            group1.setStyle("-fx-background-color: red");
            ReadOnlyObjectProperty<Scene> property = group1.sceneProperty();
            group1.getScene();
            stage.setScene(new Scene(group1));
        });

        Button btn3 = new Button("Button3");
        btn3.setOnAction(event -> {
            if (group2 == null) {
                group2 = new Group();
            }
            group2.setStyle("-fx-background-color: green");
            stage.setScene(new Scene(group2));
        });

        HBox hBox = new HBox(btn1, btn2, btn3);
        root.setCenter(hBox);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
