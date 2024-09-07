package org.example.workx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    TextArea inputArea;
    TextArea outputArea;

    @Override
    public void start(Stage stage) throws IOException {

        inputArea = new TextArea();
        outputArea = new TextArea();

        SplitPane splitPane = new SplitPane();

        splitPane.setDividerPositions(0.5);
        splitPane.getItems().addAll(inputArea, outputArea);

        BorderPane root = new BorderPane();

        root.setCenter(splitPane);

        FlowPane flowPane = new FlowPane();
        root.setBottom(flowPane);

        Button btnTrimAll = new Button("trimAll");

        btnTrimAll.setOnAction(e -> {

            String inputAreaText = inputArea.getText();
            if (inputAreaText != null) {
                StringBuilder sb = new StringBuilder();
                char[] charArray = inputAreaText.toCharArray();
                for (char c : charArray) {
                    if (c == '\n' || c == '\t' || c == ' ') {
                        continue;
                    }
                    sb.append(c);
                }
                outputArea.setText(sb.toString());
            }
        });

        Button btnCopyToClipboard = new Button("Copy");
        btnCopyToClipboard.setOnAction(e -> {
            String text = outputArea.getText();
            if (text == null) {
                return;
            }
            copyToClipboard(text);
        });
        flowPane.getChildren().add(btnTrimAll);
        flowPane.getChildren().add(btnCopyToClipboard);

        stage.setScene(new Scene(root, 600, 500));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
    }

    public static void copyToClipboard(String text) {
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.setContent(content);
    }
}