package library.jfoenix;

import application.TestApplication;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Test1 extends TestApplication {
    @Override
    public Parent createRoot(Stage primaryStage) throws Exception {

        final AnchorPane anchorPane = new AnchorPane();

        final JFXTextField jfxTextField = new JFXTextField();
        final JFXTextArea jfxTextArea = new JFXTextArea();

        anchorPane.getChildren().add(jfxTextField);
        anchorPane.getChildren().add(jfxTextArea);

        return anchorPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
