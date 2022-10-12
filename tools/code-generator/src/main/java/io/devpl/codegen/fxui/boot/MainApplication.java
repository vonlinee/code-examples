package io.devpl.codegen.fxui.boot;

import io.devpl.codegen.common.h2.EmbedH2ServiceThread;
import io.devpl.codegen.common.utils.ConfigHelper;
import io.devpl.codegen.fxui.controller.MainController;
import io.devpl.codegen.fxui.utils.FXMLHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX 11 直接运行此类不行，会提示缺少JavaFX运行时组件
 */
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
        try {
            primaryStage.setScene(new Scene(fxmlLoader.load()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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
