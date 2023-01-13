package io.devpl.toolkit.fxui.app;

import io.devpl.toolkit.framework.mvc.ViewLoader;
import io.devpl.toolkit.fxui.controller.MainFrameController;
import io.devpl.toolkit.framework.JavaFXApplication;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

/**
 * 这是本软件的主入口,要运行本软件请直接运行本类就可以了,不用传入任何参数
 * 本软件要求jkd版本大于1.8.0.40
 */
public class MainUI extends JavaFXApplication {

    private final Log log = LogFactory.getLog(MainUI.class);

    private static final String MAIN_WINDOW_TITLE = "代码生成器";

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(true);
        Parent root = ViewLoader.load(MainFrameController.class).getRoot();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle(MAIN_WINDOW_TITLE);
        primaryStage.getIcons().add(new Image("static/icons/mybatis-logo.png"));
        primaryStage.show();
    }

    // JFX 11 运行此类不行，会提示缺少JavaFX运行时组件
    public static void main(String[] args) {
        launch(args);
    }
}
