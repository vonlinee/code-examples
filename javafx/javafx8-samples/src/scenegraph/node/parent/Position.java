package scenegraph.node.parent;

import application.TestApplication;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Position extends TestApplication {

	@Override
	public Parent createRoot(Stage stage) {
		Button btn = new Button("Button");
		btn.setOnAction(event -> {
			System.out.println(btn.getLayoutX() + "\t" + btn.getLayoutY());
			btn.getLayoutBounds();
		});
		HBox hBox = new HBox(btn);
		hBox.setAlignment(Pos.CENTER);
		hBox.setStyle("-fx-background-color: yellow");
		
		btn.layout();
		
		return hBox;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
