package io.devpl.tookit.fxui.app;

import io.devpl.fxtras.JavaFXApplication;
import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.tookit.fxui.controller.mbg.MyBatisCodeGenerationView;
import io.devpl.tookit.fxui.model.ConnectionRegistry;
import io.devpl.tookit.fxui.model.props.ConnectionInfo;
import io.devpl.tookit.fxui.view.navigation.impl.DatabaseNavigationView;
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

        DatabaseNavigationView view = new DatabaseNavigationView();

        for (ConnectionInfo connectionInfo : ConnectionRegistry.getConnectionConfigurations()) {
            view.addConnection(connectionInfo);
        }


        Parent root = ViewLoader.load(MyBatisCodeGenerationView.class).getRoot();
        primaryStage.setScene(new Scene(view));
        primaryStage.show();
    }
}
