package editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.nio.file.Path;

public class TextEditor extends Application {
    Parent root;
    static Path path = null;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(TextEditor.class.getResource("MainPage.fxml"));
        root = loader.load();
        MainPageController controller = loader.getController();
        controller.setEditor(this, stage);
        controller.setPath(path);
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("编辑器");
        stage.getIcons().add(new Image("img/Jeditor.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
