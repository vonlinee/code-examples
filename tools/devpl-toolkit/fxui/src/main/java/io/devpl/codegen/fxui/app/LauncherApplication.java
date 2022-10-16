package io.devpl.codegen.fxui.app;

import io.devpl.codegen.fxui.utils.Alerts;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * 用于启动其他的应用，相当于启动器
 */
public class LauncherApplication extends Application {

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("启动器");
        BorderPane root = new BorderPane();
        ObservableList<String> appClassNames = FXCollections.observableArrayList(MainApplication.class.getName(), SnippetApplication.class.getName());

        ChoiceBox<String> appChoiceBox = new ChoiceBox<>(appClassNames);

        Button btn = new Button("启动");
        btn.setOnMouseClicked(event -> {
            String value = appChoiceBox.getValue();
            launchApplication(value);
        });

        HBox hBox = new HBox(appChoiceBox, btn);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        root.setCenter(hBox);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void launchApplication(String appClassName) {
        Class<?> clazz;
        try {
            clazz = Class.forName(appClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        if (Application.class.isAssignableFrom(clazz)) {
            @SuppressWarnings("unchecked")
            Class<? extends Application> appClass = (Class<? extends Application>) clazz;
            Application.launch(appClass);
        } else {
            Alerts.error("选择的启动类: " + clazz.getName()).showAndWait();
        }
    }
}
