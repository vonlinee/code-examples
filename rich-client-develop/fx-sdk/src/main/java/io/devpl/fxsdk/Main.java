package io.devpl.fxsdk;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXTabPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, 500.0, 400.0);

        JFXTabPane tabPane = new JFXTabPane();

        Tab tab1 = new Tab("tab1");

        Tab tab2 = new Tab("tab2");

        tabPane.getTabs().addAll(tab1, tab2);

        root.setCenter(tabPane);

        primaryStage.setScene(scene);

        primaryStage.show();

        tab2.setOnClosed(event -> {

        });
    }
}
