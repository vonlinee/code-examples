package event.mouse.drag;

import application.SampleApplication;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

public class TestApp extends SampleApplication {

	@Override
	public Parent createRoot() {
		Button node = new Button("Button");
        // 提示用户该结点可点击
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> node.setCursor(Cursor.HAND));
        node.addEventHandler(MouseEvent.MOUSE_EXITED, event -> node.setCursor(Cursor.DEFAULT));
        // 提示用户该结点可拖拽
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> node.setCursor(Cursor.MOVE));
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> node.setCursor(Cursor.DEFAULT));
        
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().add(node);
        
		return flowPane;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
