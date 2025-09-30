package scenegraph.node.parent.control.treetableview;

import application.TestApplication;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;

public class TextFieldSample extends TestApplication {

	@Override
	public Parent createRoot(Stage stage) {
		
		TreeTableView<String> treeTableView = new TreeTableView<>();
		
		TreeItem<String> item = new TreeItem<>("Root");
		
		for (int i = 0; i < 5; i++) {
			TreeItem<String> childItem = new TreeItem<>("" + i);
			
			item.getChildren().add(childItem);
		}
		

		treeTableView.setRoot(item);
		
		
		return treeTableView;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
