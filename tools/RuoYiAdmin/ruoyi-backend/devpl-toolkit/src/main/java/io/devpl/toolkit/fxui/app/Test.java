package io.devpl.toolkit.fxui.app;

import com.jfoenix.controls.JFXTreeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {

        JFXTreeView<String> treeView = new JFXTreeView<>();

        treeView.setRoot(new TreeItem<>());

        for (int i = 0; i < 10; i++) {
            treeView.getRoot().getChildren().add(new TreeItem<>("" + i));
        }

        Scene scene = new Scene(treeView, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
