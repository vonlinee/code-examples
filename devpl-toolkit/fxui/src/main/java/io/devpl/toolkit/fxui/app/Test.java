package io.devpl.toolkit.fxui.app;

import io.devpl.toolkit.fxui.model.props.ConnectionInfo;
import io.devpl.toolkit.fxui.view.navigation.DatabaseNavigationTreeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
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

        DatabaseNavigationTreeView view = new DatabaseNavigationTreeView();

        ScrollPane leftDbTree = new ScrollPane();
        leftDbTree.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        view.prefWidthProperty().bind(leftDbTree.widthProperty());

        leftDbTree.setContent(view);
        ConnectionInfo connectionInfo = new ConnectionInfo();

        view.addConnection(connectionInfo);

        root.setCenter(leftDbTree);
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
