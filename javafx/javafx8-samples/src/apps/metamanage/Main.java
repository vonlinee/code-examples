package apps.metamanage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        final URL resource = Thread.currentThread()
                .getContextClassLoader()
                .getResource("apps/metamanage/metadata_manage.fxml");
        assert resource != null;
        final Pane root = FXMLLoader.load(resource);

        final Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
