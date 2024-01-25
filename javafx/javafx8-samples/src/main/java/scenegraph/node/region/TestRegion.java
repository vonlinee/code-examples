package scenegraph.node.region;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TestRegion extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Region region = new Region();
        region.setPrefWidth(200.0);
        region.setPrefHeight(200.0);

        VBox vBox = new VBox();
        vBox.getContentBias();
        region.setShape(new Circle(200.0, 200.0, 20));

        BackgroundFill bgFill = new BackgroundFill(Color.GREEN, new CornerRadii(50), new Insets(50));
        BackgroundFill bgFill1 = new BackgroundFill(Color.BLUE, new CornerRadii(0), new Insets(0));
        Background bg = new Background(bgFill1, bgFill);
        region.setBackground(bg);
        AnchorPane.setLeftAnchor(region, 50.0);
        AnchorPane.setTopAnchor(region, 60.0);
        anchorPane.getChildren().add(region);
        Scene scene = new Scene(anchorPane, 400.0, 400.0);
        primaryStage.setScene(scene);
        primaryStage.show();

        region.setOnMouseClicked(event -> {
            final double layoutX = region.getLayoutX();
            System.out.println(layoutX);
            region.setLayoutX( + 10);
        });
    }
}
