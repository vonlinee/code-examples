package scenegraph.node.parent.control.label;

import application.SampleApplication;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TestLabelApp extends SampleApplication {

	@Override
	public Parent createRoot() {

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
