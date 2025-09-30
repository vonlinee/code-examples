package tiwulfx.samples.messagedialog;

import com.panemu.tiwulfx.dialog.MessageDialog;
import com.panemu.tiwulfx.dialog.MessageDialog.Answer;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author amrullah
 */
public class MainApp extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		Button btn = new Button();
		btn.setText("Information Dialog");
		btn.setOnAction((ActionEvent event) -> {
			MessageDialogBuilder.info().message("This is an information dialog.").show(primaryStage);
		});
		
		Button btn2 = new Button();
		btn2.setText("Info Dialog Custom Buttons");
		btn2.setOnAction((ActionEvent event) -> {
			Answer answer = MessageDialogBuilder
					  .info()
					  .message("This is an information dialog with custom buttons.")
					  .buttonType(MessageDialog.ButtonType.YES_NO_CANCEL)
					  .yesOkButtonText("Button 1")
					  .noButtonText("Button 2")
					  .cancelButtonText("Button 3")
					  .show(primaryStage);
			System.out.println("Answer: " + answer);
		});
		
		Button btnError = new Button();
		btnError.setText("Error Dialog");
		btnError.setOnAction((ActionEvent event) -> {
			MessageDialogBuilder.error().message("Error dialog without stacktrace.").show(primaryStage);
		});
		
		Button btnError2 = new Button();
		btnError2.setText("Error Dialog With Stacktrace");
		btnError2.setOnAction((ActionEvent event) -> {
			String invalidNumber = "abc";
			try {
				int number = Integer.parseInt(invalidNumber);
			} catch (Exception ex) {
				Answer answer = MessageDialogBuilder
						  .error(ex)
						  .message("Error dialog without stacktrace.")
						  .buttonType(MessageDialog.ButtonType.YES_NO_CANCEL)
						  .yesOkButtonText("Report")
						  .noButtonText("Ignore")
						  .cancelButtonText("Restart App")
						  .defaultAnswer(Answer.CANCEL)
						  .escapeAnswer(Answer.NO)
						  .show(primaryStage);
				
				if (answer == Answer.CANCEL) {
					MessageDialogBuilder.info().message("Restarting...").show(primaryStage);
				} else if (answer == Answer.YES_OK) {
					MessageDialogBuilder.info().message("Report sent!").show(primaryStage);
				}
			}
		});
		
		Button btnConfirm = new Button("Confirmation Dialog");
		btnConfirm.setOnAction((t) -> {
			Answer answer = MessageDialogBuilder
					  .confirmation()
					  .message("Are you sure to do this?")
					  .title("Custom Title")
					  .show(primaryStage);
			System.out.println("Answer: " + answer);
		});
		
		
		VBox root = new VBox();
		root.getChildren().addAll(btn, btn2, btnError, btnError2, btnConfirm);
		root.setSpacing(10);
		root.setPadding(new Insets(10));
		Scene scene = new Scene(root, 300, 250);
		
		primaryStage.setTitle("Message Dialogs");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
