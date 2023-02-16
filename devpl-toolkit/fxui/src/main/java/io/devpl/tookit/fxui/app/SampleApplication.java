package io.devpl.tookit.fxui.app;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class SampleApplication extends Application {

    protected double initialWidth = 500.0;
    protected double initialHeight = 500.0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createRoot(), initialWidth, initialHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public abstract Parent createRoot();

    public static void main(String[] args) {
        Application.launch(SampleApplication.class, args);
    }
}
