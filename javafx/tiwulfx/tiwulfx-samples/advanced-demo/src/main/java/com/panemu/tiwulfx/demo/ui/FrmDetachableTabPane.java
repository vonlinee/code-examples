/*
 * License BSD License
 * Copyright (C) 2013 Amrullah <amrullah@panemu.com>.
 */
package com.panemu.tiwulfx.demo.ui;

import com.panemu.tiwulfx.control.DetachableTabPane;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author amrullah
 */
public class FrmDetachableTabPane extends VBox {

	@FXML
	private DetachableTabPane tabpane;

	public FrmDetachableTabPane() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FrmDetachableTabPane.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		tabpane.setSceneFactory(new Callback<DetachableTabPane, Scene>() {
			@Override
			public Scene call(DetachableTabPane p) {
				Label lbl = new Label("TiwulFX Demo 2.0");
				lbl.setId("app-title");
				p.setPrefSize(400, 343);
				VBox vbox = new VBox();
				HBox hbox = new HBox();
				hbox.setAlignment(Pos.CENTER_RIGHT);
				hbox.setHgrow(p, Priority.ALWAYS);
				hbox.setPrefHeight(-1.0);
				hbox.setPrefWidth(-1.0);
				hbox.setSpacing(10.0);
				hbox.setPadding(new Insets(10));
				hbox.getStyleClass().add("top-panel");
				hbox.getChildren().add(lbl);

				vbox.getChildren().add(hbox);
				vbox.getChildren().add(p);
				VBox.setVgrow(p, Priority.ALWAYS);
				Scene scene = new Scene(vbox);
				return scene;
			}
		});
	}

}
