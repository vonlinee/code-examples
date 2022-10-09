package io.devpl.codegen.mbg.fxui;

import io.devpl.codegen.mbg.controller.MainController;
import io.devpl.codegen.mbg.h2.EmbedH2ServiceThread;
import io.devpl.codegen.mbg.utils.ConfigHelper;
import io.devpl.codegen.mbg.utils.FXMLHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void init() throws Exception {
        new EmbedH2ServiceThread().start();
        ConfigHelper.createEmptyFiles();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = FXMLHelper.createFXMLLoader("fxml/main.fxml");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        primaryStage.setTitle("Mybatis Generator GUI");
        primaryStage.getIcons().add(new Image("icons/mybatis-logo.png"));
        primaryStage.show();
        MainController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
