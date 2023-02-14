package io.devpl.toolkit.fxui.app;

import io.devpl.fxtras.controls.TaggedRegion;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * 测试单个界面
 */
public class UnitTestApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // final Parent root = ViewLoader.load(MetaFieldManageController.class)
        //         .getRoot();

        TaggedRegion region = new TaggedRegion("Response");


        Button btn = new Button("Button");

        region.setContent(btn);

        Scene scene = new Scene(region, 400.0, 400.0);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
