package scenegraph.node.parent.control.listview;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXListView;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TestApp extends Application {

	protected double initialWidth = 600.0;
	protected double initialHeight = 600.0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createRoot(), initialWidth, initialHeight);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		Application.launch(TestApp.class, args);
	}
	
	
	public Parent createRoot() throws Exception {
		
		ListView<String> listView = new ListView<>();
		
		listView.getItems().add("A");
		listView.getItems().add("B");
		listView.getItems().add("C");
		listView.getItems().add("D");
		
		listView.setEditable(true);
		
		listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			
			@Override
			public ListCell<String> call(ListView<String> param) {
				
				TextFieldListCell<String> cell = new TextFieldListCell<>();
				
				return cell;
			}
		});
		
		JFXListView<String> lJfxListView = new JFXListView<>();
		
		lJfxListView.getItems().addAll(getDataList());
		
		VBox root = new VBox(listView, lJfxListView);
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		return root;
	}
	
	public static List<String> getDataList() {
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		return list;
	}
}
