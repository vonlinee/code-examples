package scenegraph.node.parent;

import application.SampleApplication;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Position extends SampleApplication {

	@Override
	public Parent createRoot() {
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
