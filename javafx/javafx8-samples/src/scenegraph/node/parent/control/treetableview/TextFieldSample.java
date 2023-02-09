package scenegraph.node.parent.control.treetableview;

import application.SampleApplication;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

public class TextFieldSample extends SampleApplication {

	@Override
	public Parent createRoot() {
		
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
