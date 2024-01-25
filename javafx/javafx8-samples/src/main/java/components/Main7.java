package components;
 
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
 
public class Main7 extends Application{
	
	public static void main(String[] args) {
		
		launch(args);
	}
 
	@Override
	public void start(Stage stage) throws Exception {
		
		AnchorPane anchorPane = new AnchorPane();
		
		MenuBar menuBar = new MenuBar();
		
		Menu menu1 = new Menu("File1");
		Menu menu2 = new Menu("File2");
		menuBar.getMenus().addAll(menu1,menu2);
		
		MenuItem menuItem1 = new MenuItem("Item1");
		MenuItem menuItem2 = new MenuItem("Item2");
		MenuItem menuItem3 = new MenuItem("Item3");
		
		menu1.getItems().addAll(menuItem1,menuItem2,menuItem3);
		
		/**
		 * ToggleGroup,RadioMenuItem,CheckMenuItem
		 * */
		//单选框
		ToggleGroup group = new ToggleGroup();
		
		RadioMenuItem radioMenuItem1 = new RadioMenuItem("radioMenuItem1");
		RadioMenuItem radioMenuItem2 = new RadioMenuItem("radioMenuItem2");
		RadioMenuItem radioMenuItem3 = new RadioMenuItem("radioMenuItem3");
		//将三个单选框加入到同一个组里
		group.getToggles().add(radioMenuItem1);
		group.getToggles().add(radioMenuItem2);
		group.getToggles().add(radioMenuItem3);
		
		//设置radioMenuItem3为默认选择
		radioMenuItem3.setSelected(true);
		
		menu2.getItems().addAll(radioMenuItem1,radioMenuItem2,radioMenuItem3);
		
		anchorPane.getChildren().add(menuBar);
		Scene scene = new Scene(anchorPane);
		
		stage.setScene(scene);
		stage.setHeight(500);
		stage.setWidth(600);
		stage.setTitle("选择框学习");
		stage.show();
	}
}