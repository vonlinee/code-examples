package scenegraph.node.parent.control.label;

import application.TestApplication;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TestLabelApp extends TestApplication {

	@Override
	public Parent createRoot(Stage stage) {

		Label label = new Label("1111111111");
		
		Button btn1 = new Button("Dialog");
		btn1.setOnAction(event -> {
			label.setText("");
		});
		
		HBox root = new HBox(label, btn1);
		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
