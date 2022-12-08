package io.devpl.codegen.fxui.common;

import io.devpl.codegen.fxui.controller.MainUIController;
import io.devpl.codegen.fxui.framework.JavaFXApplication;
import io.devpl.codegen.fxui.utils.ConfigHelper;
import io.devpl.codegen.fxui.utils.Messages;
import io.devpl.codegen.fxui.utils.Resources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.openjdk.jol.info.ClassLayout;

import java.io.File;
import java.net.URL;

/**
 * 这是本软件的主入口,要运行本软件请直接运行本类就可以了,不用传入任何参数
 * 本软件要求jkd版本大于1.8.0.40
 */
public class MainUI extends JavaFXApplication {

    private static final String MAIN_WINDOW_TITLE = "代码生成器";

    @Override
    public void init() throws Exception {
        super.init();
        final File file = Resources.getResourcesAsFile("message.properties", true);
        Messages.init(file);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigHelper.createEmptyFiles();
        URL url = Thread.currentThread().getContextClassLoader().getResource("static/fxml/MainUI.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle(MAIN_WINDOW_TITLE);
        primaryStage.getIcons().add(new Image("static/icons/mybatis-logo.png"));
        primaryStage.show();
        MainUIController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.setMinWidth(1200.0);
        primaryStage.setMinHeight(800.0);

        primaryStage.setResizable(false);
        primaryStage.setIconified(true);
    }

    // JFX 11 运行此类不行，会提示缺少JavaFX运行时组件
    public static void main(String[] args) {
        launch(args);
    }
}
