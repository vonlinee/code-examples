package scenegraph.dialog.alert;

import application.TestApplication;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TestAlertApp extends TestApplication {

	@Override
	public Parent createRoot(Stage stage) {
		Button btn = new Button("Alert");
		btn.setOnAction(event -> {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			// Alert弹窗默认是模态窗口 APPLICATION_MODAL
			// 默认不可改变大小
			alert.initModality(Modality.NONE);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Look, an Information Dialog");
			alert.setContentText(alert.toString());
			alert.setGraphic(new Button("图形"));
			alert.showAndWait();
		});
		
		Button btn1 = new Button("Dialog");
		btn1.setOnAction(event -> {
			Dialog<Object> dialog = new Dialog<>();
			
			dialog.show();
		});
		HBox root = new HBox(btn, btn1);
		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
