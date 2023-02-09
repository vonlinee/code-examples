package apps.filestructure;

import apps.filestructure.java.JavaFileStrucutreTreeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.girod.javafx.svgimage.SVGLoader;
import utils.ResourceLoader;

/**
 * Jetbrains 图标设计指南
 * <a href="https://jetbrains.design/intellij/principles/icons/">...</a>
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        JavaFileStrucutreTreeView jfsTreeView = new JavaFileStrucutreTreeView();

        Button button = new Button("Button");
        button.setGraphic(SVGLoader.load(ResourceLoader.load(Main.class, "icons/field.svg")));
        final AnchorPane anchorPane = new AnchorPane(button);

        Scene scene = new Scene(anchorPane, 600.0, 500.0);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
