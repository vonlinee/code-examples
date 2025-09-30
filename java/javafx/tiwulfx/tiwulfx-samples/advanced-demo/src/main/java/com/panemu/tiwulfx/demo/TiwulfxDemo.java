/*
 * License BSD License
 * Copyright (C) 2013 Amrullah <amrullah@panemu.com>.
 */
package com.panemu.tiwulfx.demo;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.demo.ui.FrmMain;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Amrullah <amrullah@panemu.com>
 */
public class TiwulfxDemo extends Application {

	public static EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiwulfx-demoPU");
	public static Stage mainStage;

	@Override
	public void start(Stage primaryStage) {
//        generateData();
		TiwulFXUtil.DEFAULT_NULL_LABEL = "-";
//		TiwulFXUtil.DEFAULT_EMPTY_STRING_AS_NULL = true;
		TiwulFXUtil.setApplicationId("tiwulfx-demo");
		TiwulFXUtil.addLiteralBundle("demo-literal");
		TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_DELETE = true;
		TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_SAVE = true;
		TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_LOAD = true;

		Scene scene = new Scene(new FrmMain());
		primaryStage.setTitle("TiwulFX Demo 3.2");
		TiwulFXUtil.setTiwulFXStyleSheet(scene);
		scene.getStylesheets().add("tiwulfx-demo.css");
		primaryStage.setScene(scene);
		primaryStage.show();
		mainStage = primaryStage;
	}

	/**
	 * The main() method is ignored in correctly deployed JavaFX application.
	 * main() serves only as fallback in case the application can not be launched
	 * through deployment artifacts, e.g., in IDEs with limited FX support.
	 * NetBeans ignores main().
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
