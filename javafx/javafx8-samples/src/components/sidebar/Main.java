package components.sidebar;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        SideBar root = new SideBar();

        Scene scene = new Scene(root);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("treeview.css")).toExternalForm());

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Genuine Coder");
        stage.show();
    }
}
