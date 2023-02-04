package custom;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TestApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        final TextArea editor = new TextArea();

        HBox hBox = new HBox();
        final Scene scene = new Scene(hBox, 400.0, 400.0);
        primaryStage.setScene(scene);
        primaryStage.show();

        final Text a = new Text("A");
        final Text b = new Text("A");
        final Text c = new Text("A");
        final Text d = new Text("A");
        hBox.getChildren().addAll(a, b, c, d);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
