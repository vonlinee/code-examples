package io.devpl.toolkit.fxui.app;

import io.devpl.toolkit.fxui.model.ConnectionInfo;
import io.devpl.toolkit.fxui.view.navigation.impl.DatabaseNavigationView;
import io.devpl.toolkit.fxui.view.navigation.DatabaseNavigationTreeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {

        BorderPane root = new BorderPane();

        DatabaseNavigationView treeView = new DatabaseNavigationView();

        final DatabaseNavigationTreeView view = new DatabaseNavigationTreeView();

        ConnectionInfo connectionInfo = new ConnectionInfo();

        view.addConnection(connectionInfo);

        root.setLeft(view);
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
