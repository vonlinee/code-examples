package scenegraph.scene;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scenegraph.scene.scene1.Scene1Controller;
import utils.FxmlLoader;

/**
 * JavaFX Scene Communication
 * @author Genuine Coder
 * www.genuinecoder.com 
 */
public class JavaFXControllerCommunication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FxmlLoader.load(Scene1Controller.class, "scene1.scenegraph.fxml");

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("First Window");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
