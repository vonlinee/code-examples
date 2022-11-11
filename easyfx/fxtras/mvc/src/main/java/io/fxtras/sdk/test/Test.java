package io.fxtras.sdk.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.bushe.swing.event.EventBus;

import java.net.URL;

public class Test extends Application {

    public static Stage stage;
    public static Scene scene1;




    @Override
    public void start(Stage primaryStage) throws Exception {

        URL resource = Test.class.getResource("/main.fxml");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(resource);


        //
        // System.out.println(builderFactory);
        // BorderPane borderPane = new BorderPane();
        // Scene scene = new Scene(borderPane, 500, 400);
        // primaryStage.setScene(scene);
        //
        // Button btn = new Button();
        // btn.setOnMouseClicked(event -> {
        //     primaryStage.setScene(primaryStage.getScene());
        // });
        //
        // Scene scene1 = new Scene(root, 400, 300);
        //
        try {
            Pane root = loader.load();
            Scene scene = new Scene(root, 200, 100);
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
