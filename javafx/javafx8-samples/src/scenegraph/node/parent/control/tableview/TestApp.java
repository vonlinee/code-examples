package scenegraph.node.parent.control.tableview;

import scenegraph.node.parent.control.tableview.model.Data;
import scenegraph.node.parent.control.tableview.model.PropertyData;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
	
	TableView<Data> tableView1 = new TableView<>();
	TableView<PropertyData> tableView2 = new TableView<>();

	ObservableList<Data> items1 = FXCollections.observableArrayList();
	ObservableList<PropertyData> items2 = FXCollections.observableArrayList();

	public Parent createRoot() throws Exception {

		TableColumn<Data, Integer> idColumn1 = new TableColumn<>("ID");
		TableColumn<Data, String> nameColumn1 = new TableColumn<>("姓名");

		idColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn1.setCellFactory(TextFieldTableCell.forTableColumn());
		tableView1.getColumns().addAll(idColumn1, nameColumn1);

		TableColumn<PropertyData, Integer> idColumn2 = new TableColumn<>("ID");
		TableColumn<PropertyData, String> nameColumn2 = new TableColumn<>("姓名");
		nameColumn2.setCellFactory(TextFieldTableCell.forTableColumn());
		idColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn2.setEditable(true);
		tableView2.getColumns().addAll(idColumn2, nameColumn2);

		tableView1.setEditable(true);
		tableView2.setEditable(true);

		tableView1.setItems(items1);
		tableView2.setItems(items2);

		items1.add(new Data(0, "A"));
		items1.add(new Data(1, "B"));
		items1.add(new Data(2, "C"));

		items2.add(new PropertyData(0, "A"));
		items2.add(new PropertyData(1, "B"));
		items2.add(new PropertyData(2, "C"));

		Button btn1 = new Button("Button1");
		Button btn2 = new Button("Refresh");

		btn2.setOnAction(event -> {
			tableView1.refresh();
		});

		HBox hBox = new HBox(btn1, btn2);
		
		Label label1 = new Label("Data");
		Label label2 = new Label("PropertyData");
		
		TextArea textArea = new TextArea();
		textArea.setWrapText(true);
		textArea.setPrefHeight(200.0);
		btn1.setOnAction(event -> {
			textArea.setText(items1.toString() + "\n" + items2.toString());
		});
		
		VBox root = new VBox(label1, tableView1, label2, tableView2, hBox, textArea);
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		return root;
	}
}
