package de.saxsys.mvvmfx.samples;

import de.saxsys.mvvmfx.core.ViewComponent;
import de.saxsys.mvvmfx.core.ViewLoader;
import de.saxsys.mvvmfx.samples.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SampleApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        ViewComponent ve = ViewLoader.loadByClass(MainView.class);
        final Scene scene = new Scene(ve);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
