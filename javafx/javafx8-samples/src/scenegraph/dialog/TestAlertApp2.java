package scenegraph.dialog;

import application.SampleApplication;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.ResourceLoader;

public class TestAlertApp2 extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Button btn = new Button("Button");
		
		btn.setOnAction(event -> {
			Stage stage = new Stage();
			DialogPane dialogPane = new DialogPane();
			dialogPane.setHeaderText("HeaderText");
			dialogPane.setContentText("ContentText");

			ImageView imageView = new ImageView(ResourceLoader.load("resources/image/mac.png").toExternalForm());
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);
			dialogPane.setGraphic(imageView);

			Text text1 = new Text("ExpandableContentExpandableContent");
			Text text2 = new Text("ExpandableContentExpandableContent");
			text1.setFill(Paint.valueOf("#ccc"));
			text1.setFont(Font.font(20));
			VBox vBox = new VBox(text1, text2);
			dialogPane.setExpandableContent(vBox);
			dialogPane.setExpanded(true);

			dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.APPLY, ButtonType.CANCEL);
			Button btnOk = (Button) dialogPane.lookupButton(ButtonType.OK);
			Button btnApply = (Button) dialogPane.lookupButton(ButtonType.APPLY);
			Button btnCancel = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
			btnOk.setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			        System.out.println("OK");
			        stage.close();
			    }
			});

			stage.initOwner(primaryStage);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setResizable(false);
			stage.setScene(new Scene(dialogPane));
			stage.show();
		});
		Scene scene = new Scene(btn, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
