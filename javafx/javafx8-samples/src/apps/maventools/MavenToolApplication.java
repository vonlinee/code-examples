package apps.maventools;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import utils.ResourceLoader;

import java.io.IOException;

public class MavenToolApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene = new Scene(createRoot(), 600.0, 400.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 创建根节点
     */
    public Parent createRoot() throws IOException {
        return FXMLLoader.load(ResourceLoader.load(getClass(), "main.scenegraph.fxml"));
    }
}
