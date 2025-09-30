package scenegraph.node.region;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class TestRegion1 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Region region = new HBox();



        Scene scene = new Scene(region, 400.0, 400.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
