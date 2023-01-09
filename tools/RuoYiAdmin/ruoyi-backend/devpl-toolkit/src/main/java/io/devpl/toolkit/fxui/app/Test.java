package io.devpl.toolkit.fxui.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        BorderPane root = new BorderPane();

        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();

        File file = new File("C:\\Users\\vonline\\Downloads\\ace-builds-1.5.0\\index.html");

        if (file.exists()) {
            System.out.println("文件存在");
        }

        URL url = file.toURI().toURL();


        engine.load(url.toExternalForm());

        Scene scene = new Scene(webView, 1200, 800);

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
