package scenegraph.node.parent.pane.anchorpane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestAnchorPane extends Application {
	public static void main(String[] args) {
		launch();
	}

	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = new AnchorPane();
		root.setPrefSize(400, 300);
		Scene scene = new Scene(root);
		Button btn = new Button("Button");
		root.getChildren().add(btn);


		primaryStage.setScene(scene);
		primaryStage.setTitle("TestAnchorPane");
		primaryStage.show();
	}
}
