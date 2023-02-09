package scenegraph.node.parent.control.treeview;

import com.jfoenix.controls.JFXTreeCell;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TreeViewSample extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Tree View Sample");

		TreeItem<String> rootItem = new TreeItem<String>("Inbox");
		rootItem.setExpanded(true);
		for (int i = 1; i < 6; i++) {
			TreeItem<String> item = new TreeItem<String>("Message " + i);
			
			if (i % 2 == 1) {
				for (int j = 0; j < 4; j++) {
					item.getChildren().add(new TreeItem<String>("Child Message " + j));
				}
			}
			
			rootItem.getChildren().add(item);
		}
		
		
		
		TreeView<String> tree = new TreeView<String>(rootItem);
		
		
		tree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
			@Override
			public TreeCell<String> call(TreeView<String> param) {
				TreeCell<String> cell = new JFXTreeCell<>();
				cell.setOnMouseClicked(event -> {
					
				});
				System.out.println(param);
				return cell;
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		StackPane root = new StackPane();
		root.getChildren().add(tree);
		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
	}
}