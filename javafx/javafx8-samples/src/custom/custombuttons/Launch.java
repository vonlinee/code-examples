package custom.custombuttons;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * JavaFX Custom Shape Buttons
 * Program starting point
 */
public class Launch extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Round Buttons");
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
