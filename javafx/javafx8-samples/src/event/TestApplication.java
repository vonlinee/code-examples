package event;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TestApplication extends Application {

	EventType<Event> EVENT_TYPE = new EventType<>("my-event");

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		Button btn = new Button("Button");
		Button btnFire = new Button("Fire");
		HBox hBox = new HBox(btn, btnFire);
		root.setTop(hBox);
		root.setOnMouseClicked(event -> {
			System.out.println("BorderPane " + event);
		});
		btnFire.setOnMouseClicked(event -> {
			Event.fireEvent(btn, new Event(EVENT_TYPE));
		});
		primaryStage.addEventHandler(EVENT_TYPE, event -> {
			System.out.println("Stage " + event);
		});
		hBox.addEventHandler(EVENT_TYPE, event -> {
			System.out.println("HBox " + event);
		});
		btn.addEventHandler(EVENT_TYPE, event -> {
			System.out.println("Button " + event);
		});
		Scene scene = new Scene(root, 600.0, 500.0);
		scene.addEventHandler(EVENT_TYPE, event -> {
			System.out.println("Scene " + event);
		});
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}