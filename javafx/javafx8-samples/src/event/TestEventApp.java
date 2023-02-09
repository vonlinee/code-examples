package event;

import application.SampleApplication;import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class TestEventApp extends SampleApplication {

	@Override
	public Parent createRoot() {
		
		Button btn = new Button("Button");
		
		btn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("EventFilter 鼠标点击");
			event.consume();
		});
		btn.setOnMouseClicked(event -> {
			System.out.println("鼠标点击");
		});
		
		return btn;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
