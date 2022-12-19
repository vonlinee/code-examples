package io.devpl.toolkit.fxui.common;

import io.devpl.toolkit.fxui.controller.MainUIController;
import io.devpl.toolkit.fxui.framework.JFX;
import io.devpl.toolkit.fxui.framework.JavaFXApplication;
import io.devpl.toolkit.fxui.framework.fxml.FXMLCache;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 这是本软件的主入口,要运行本软件请直接运行本类就可以了,不用传入任何参数
 * 本软件要求jkd版本大于1.8.0.40
 */
public class MainUI extends JavaFXApplication {

    private static final String MAIN_WINDOW_TITLE = "代码生成器";

    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLCache cache = applicationContext.getFXMLCache("static/fxml/MainUI.fxml");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(cache.getRoot()));
        primaryStage.setTitle(MAIN_WINDOW_TITLE);
        primaryStage.getIcons().add(new Image("static/icons/mybatis-logo.png"));
        primaryStage.show();
        MainUIController controller = (MainUIController) cache.getController();
        controller.setPrimaryStage(primaryStage);
    }

    // JFX 11 运行此类不行，会提示缺少JavaFX运行时组件
    public static void main(String[] args) {
        launch(args);
    }
}
