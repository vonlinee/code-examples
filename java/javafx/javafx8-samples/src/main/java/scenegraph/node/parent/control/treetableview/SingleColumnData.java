package scenegraph.node.parent.control.treetableview;
 
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;
 
public class SingleColumnData  extends Application{
 
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	
		primaryStage.setTitle("Basic TreeTable example demo");
		Group group = new Group();
		
		final TreeItem<String> childNode1 = new TreeItem<>("ChildNode1");
		final TreeItem<String> childNode2 = new TreeItem<>("ChildNode2");
		final TreeItem<String> childNode3 = new TreeItem<>("ChildNode3");
		
		final TreeItem<String> rootItem = new TreeItem<String>("rootNode");
		rootItem.setExpanded(true);
		rootItem.getChildren().addAll(childNode1,childNode2,childNode3);
		
		
		TreeTableColumn<String, String> column = new TreeTableColumn<>("Column");
		
		column.setPrefWidth(150);
		
		
		column.setCellValueFactory((CellDataFeatures<String, String> p) -> new ReadOnlyStringWrapper(p.getValue().getValue()));  
		
		final TreeTableView<String> treeTableView = new TreeTableView<>(rootItem);
        treeTableView.getColumns().add(column);
        treeTableView.setPrefWidth(152);
        treeTableView.setShowRoot(true);             
        group.getChildren().add(treeTableView);
		
		Scene scene = new Scene(group);
		
		
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}