package scenegraph.node.parent.control.treetableview;
 
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
 
public class BasicTreeTableView extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}
 
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		HBox hBox = new HBox();
		
		Button button = new Button("UPDATE");
		
		hBox.getChildren().add(button);
		
		Student student = new Student("root",123,true);
		
		Student student2 = new Student("child",123,true);
		
		TreeTableView<Student> treeView = new TreeTableView<Student>();
		
		TreeItem<Student> rootItem = new TreeItem<Student>(student);
		
		TreeItem<Student> childItem = new TreeItem<Student>(student2);
		
		rootItem.getChildren().add(childItem);
	
		treeView.setRoot(rootItem); // 指定root
		rootItem.setExpanded(true); // 设置折叠
	
		 TreeTableColumn<Student, String> nameColumn = new TreeTableColumn<Student, String>("NAME");
		 
		 nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
		 
		 TreeTableColumn<Student, Number> scoreColumn = new TreeTableColumn<Student, Number>("SCORE");
		 
		 scoreColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Student,Number>, ObservableValue<Number>>() {
			@Override
			public ObservableValue<Number> call(CellDataFeatures<Student, Number> param) {
				// TODO Auto-generated method stub
				return param.getValue().getValue().score;
			}
		
		 });
 
		 
		 scoreColumn.setCellFactory(new Callback<TreeTableColumn<Student,Number>, TreeTableCell<Student,Number>>() {
			@Override
			public TreeTableCell<Student, Number> call(TreeTableColumn<Student, Number> param) {
				// TODO Auto-generated method stub
				TreeTableCell<Student, Number> tableCell = new TreeTableCell<Student, Number>(){
					@Override
					protected void updateItem(Number item, boolean empty) {
						// TODO Auto-generated method stub
						super.updateItem(item, empty);
						if(empty) {this.setGraphic(null);return;} //折叠问题
						HBox hBox = new HBox();
						hBox.setStyle("-fx-background-color:#FFFF55");
						hBox.setAlignment(Pos.CENTER);
						Button label = new Button(item.toString());
						label.setOnAction((e)->{
							System.out.println("ingo update");
							hBox.setStyle("-fx-background-color:#AABBCC");
						});
						hBox.getChildren().add(label);
					
						this.setGraphic(hBox);
						
					}
				};
				
				return tableCell;
			}
			
		
		 });
		
		 treeView.getColumns().add(nameColumn);
		 treeView.getColumns().add(scoreColumn);
		
		
		hBox.getChildren().add(treeView);
		
		Scene scene = new Scene(hBox);
		
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
		
	}
	
 
	public  class Student {
		private SimpleStringProperty name = new SimpleStringProperty();
		private SimpleIntegerProperty score = new SimpleIntegerProperty();
		private SimpleBooleanProperty isCheck = new SimpleBooleanProperty();
		public Student(String name, int score, boolean isCheck) {
			super();
			this.name.set(name);
			this.score.set(score);
			this.isCheck.set(isCheck);
		}
		
		public Student() {
			
			
		}
 
		public String getName() {
			return name.get();
		}
 
		public void setName(String name) {
			this.name.set(name);;
		}
 
		public int getScore() {
			return score.get();
		}
 
		public void setScore(int score) {
			this.score.set(score);
		}
 
		public boolean isCheck() {
			return isCheck.get();
		}
 
		public void setCheck(boolean isCheck) {
			this.isCheck.set(isCheck);
		}
	}
}