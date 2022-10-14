package io.devpl.codegen.ui.fxui.boot;

import io.devpl.codegen.common.h2.EmbedH2ServiceThread;
import io.devpl.codegen.common.utils.ConfigHelper;
import io.devpl.codegen.ui.fxui.utils.FXMLHelper;
import javafx.application.Application;
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
        FXMLHelper.load("fxml/main.fxml").ifPresent(parent -> {
            primaryStage.setScene(new Scene(parent));
        });
        primaryStage.setResizable(true);
        primaryStage.setTitle("Mybatis Generator GUI");
        primaryStage.getIcons().add(new Image("icons/mybatis-logo.png"));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
