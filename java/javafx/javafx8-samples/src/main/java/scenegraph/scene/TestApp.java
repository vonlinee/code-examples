package scenegraph.scene;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TestApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button btn1 = new Button("Create Scene");
		btn1.setOnAction(event -> {
			Button button = new Button("New Button");
			Scene newScene = new Scene(button);
			primaryStage.setScene(newScene);
		});
		HBox hBox = new HBox(btn1);
		Scene scene = new Scene(hBox, 400, 400);
		primaryStage.setScene(scene);

	}

	public static void main(String[] args) {
		launch(args);
	}
}
