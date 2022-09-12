package io.devpl.codegen.mbg.fxui;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.devpl.codegen.mbg.controller.MainUIController;
import io.devpl.codegen.mbg.utils.ConfigHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 这是本软件的主入口,要运行本软件请直接运行本类就可以了,不用传入任何参数
 * 本软件要求jkd版本大于1.8.0.40
 */
public class MainUI extends Application {

    private static final Logger _LOG = LoggerFactory.getLogger(MainUI.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigHelper.createEmptyFiles();
        URL url = Thread.currentThread().getContextClassLoader().getResource("fxml/MainUI.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Mybatis Generator GUI");
        Image imageIcon = new Image("icons/mybatis-logo.png");
        primaryStage.getIcons().add(imageIcon);
        primaryStage.show();

        MainUIController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
    }

    // JavaFX 11 运行此类不行，会提示缺少JavaFX运行时组件
    public static void main(String[] args) {
        launch(args);
    }
}
