package io.devpl.tookit.fxui.app;

import io.devpl.fxtras.controls.TaggedRegion;
import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.tookit.fxui.controller.Json;
import io.devpl.tookit.fxui.view.json.JSONTreeView;
import javafx.application.Application;
import javafx.scene.Parent;
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

        final Parent root = ViewLoader.load(Json.class).getRoot();

        
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
