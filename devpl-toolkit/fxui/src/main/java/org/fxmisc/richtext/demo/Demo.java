package org.fxmisc.richtext.demo;

import io.devpl.tookit.fxui.view.TextEditor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Demo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        TextEditor textEditor = new TextEditor();

        Scene scene = new Scene(textEditor);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
