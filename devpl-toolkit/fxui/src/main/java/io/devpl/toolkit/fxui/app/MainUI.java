package io.devpl.toolkit.fxui.app;

import io.devpl.fxtras.JavaFXApplication;
import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.toolkit.fxui.controller.mbg.MyBatisCodeGenerationView;
import io.devpl.toolkit.fxui.model.ConnectionRegistry;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 这是本软件的主入口,要运行本软件请直接运行本类就可以了,不用传入任何参数
 * 本软件要求jdk版本大于11.0.4
 */
public class MainUI extends JavaFXApplication {

    @Override
    protected void onInit() throws Exception {
        try {
            ConnectionRegistry.getRegisteredConnectionConfigMap();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = ViewLoader.load(MyBatisCodeGenerationView.class).getRoot();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
