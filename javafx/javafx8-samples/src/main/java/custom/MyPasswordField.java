package custom;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MyPasswordField extends BorderPane {
	HBox hBox;
	Label eye = new Label();
	PasswordField passwordField = new PasswordField();

	TextField textField = new TextField();
	BorderPane passwordMainField = new BorderPane();
	boolean isVisible = false;

	public MyPasswordField() {
		passwordMainField.setMaxSize(280, 40);
		passwordMainField.setId("username-field");

		Font font = Font.loadFont(getClass().getResourceAsStream("/Icons/icomoon.ttf"), 28);

		eye.setFont(Font.font(font.getFamily(), 20));
		eye.setText("\ue905");
		eye.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if (!isVisible) {
				eye.setText("\ue900");
				textField.setText(passwordField.getText());
				textField.setPrefHeight(40);
				textField.setId("text-field");
				// textField.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/Login/Login.css")));
				hBox.getChildren().set(2, textField);
				isVisible = true;
				textField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
					if (newValue) {
						passwordField.setPromptText("");
						passwordMainField.setStyle("-fx-border-color: #0D90EE");
					} else {
						passwordField.setPromptText("Press your password");
						passwordField.setStyle("-fx-prompt-text-fill: #767676");
						passwordMainField.setStyle("-fx-border-color: #767676");
					}
				}));
				passwordField.textProperty().bind(textField.textProperty());
				textField.requestFocus();
				textField.positionCaret(passwordField.getText().length());
			} else {
				eye.setText("\ue905");
				hBox.getChildren().set(2, passwordField);
				isVisible = false;
				passwordField.requestFocus();
				passwordField.positionCaret(textField.getText().length());
			}
		});
		passwordField.setPrefHeight(40);

		// 透明背景
		BackgroundFill bgFill = new BackgroundFill(Color.TRANSPARENT, null, new Insets(0));
		Background bg = new Background(bgFill);
		passwordField.setBackground(bg);
		passwordField.setId("text-field");
		passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				passwordMainField.setStyle("-fx-border-color: #0D90EE");
			} else {
				passwordField.setStyle("-fx-prompt-text-fill: #767676");
				passwordMainField.setStyle("-fx-border-color: #767676");
			}
		});
		hBox = new HBox(passwordField, eye);
		
		hBox.setAlignment(Pos.CENTER_LEFT);
		passwordMainField.setCenter(hBox);
		this.setMaxSize(280, 50);
		this.setCenter(passwordMainField);
	}
}
