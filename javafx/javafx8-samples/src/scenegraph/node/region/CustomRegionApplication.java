package scenegraph.node.region;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CustomRegionApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        CustomRegion region = new CustomRegion();

        Button btn1 = new Button("Button");
        Button btn2 = new Button("Button");

        region.getChildren().add(btn1);

        btn1.setOnAction(event -> {
            final double width = region.getWidth();
            final double height = region.getHeight();
            final double prefWidth = region.getPrefWidth();
            final double prefHeight = region.getPrefHeight();

            System.out.println(width);
            System.out.println(height);
            System.out.println(prefWidth);
            System.out.println(prefHeight);
        });

        Scene scene = new Scene(region, 400.0, 400.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
