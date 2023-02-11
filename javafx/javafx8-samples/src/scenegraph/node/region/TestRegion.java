package scenegraph.node.region;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class TestRegion extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        HBox region = new HBox();
        region.setStyle("-fx-background-color: #66d366");

        region.setPadding(new Insets(5));

        HBox childRegion = new HBox();
        childRegion.setPrefSize(50.0, 50.0);
        childRegion.setStyle("-fx-background-color: #d9a2a2");

        region.getChildren().add(childRegion);

        region.setPadding(new Insets(50));

        Scene scene = new Scene(region, 400.0, 400.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
