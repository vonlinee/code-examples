package io.devpl.codegen.mbg.fx.view;

import io.devpl.codegen.mbg.fx.controller.MainPageController;
import io.devpl.codegen.mbg.utils.ResourceLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author blinderjay
 */
public class FXEditor extends Application {
    Parent root;
    static Path path = null;

    @Override
    public void start(Stage stage) throws Exception {
        // stage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(ResourceLoader.load("fxml/MainPage.fxml"));
        try (InputStream in = ResourceLoader.loadStream("fxml/MainPage.fxml")) {
            root = loader.load(in);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        MainPageController mainpage = loader.getController();
        mainpage.setEditor(this, stage);
        mainpage.setPath(path);

        // Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene scene = new Scene(root);
        mainpage.setScene(scene);
        scene.getStylesheets().add("/css/AppleView.css");
        // scene.setFill(Color.TRANSPARENT);
//        stage.setWidth(1200);
//        stage.setHeight(900);
        stage.setTitle("Jeditor : an open, free editor highly customizable");

        stage.setWidth(500);
        stage.setHeight(500);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Jeditor.png"))));
        stage.setScene(scene);
        stage.show();
    }
}
