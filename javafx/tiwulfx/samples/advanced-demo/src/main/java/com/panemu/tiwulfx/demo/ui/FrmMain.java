/*
 * License BSD License
 * Copyright (C) 2013 Amrullah <amrullah@panemu.com>.
 */
package com.panemu.tiwulfx.demo.ui;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.common.WindowEventListener;
import com.panemu.tiwulfx.control.DetachableTabPane;
import com.panemu.tiwulfx.control.NumberField;
import com.panemu.tiwulfx.control.sidemenu.SideMenu;
import com.panemu.tiwulfx.control.sidemenu.SideMenuActionHandler;
import com.panemu.tiwulfx.control.sidemenu.SideMenuCategory;
import com.panemu.tiwulfx.control.sidemenu.SideMenuItem;
import com.panemu.tiwulfx.demo.date.FrmDateColumn;
import com.panemu.tiwulfx.demo.misc.DataGenerator;
import com.panemu.tiwulfx.demo.synch.FrmSynchronizedColumns;
import com.panemu.tiwulfx.demo.text.FrmTextColumn;
import com.panemu.tiwulfx.dialog.MessageDialog.Answer;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 *
 * @author Amrullah <amrullah@panemu.com>
 */
public class FrmMain extends VBox {

	@FXML
	private Button btnGenerate;
	@FXML
	private DetachableTabPane tabpane;
	@FXML
	private NumberField<Integer> txtRecordCount;
	@FXML
	private SideMenu sideMenu;

	public FrmMain() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FrmMain.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
//        tabInForm.setContent(new FrmPersonTable2(new InFormMaintenanceController()));
//        tabMasterDetail.setContent(new FrmMasterDetail());
		final FrmPersonTable frmPersonTable = new FrmPersonTable();
		showAsTab(frmPersonTable, "Edit in Row");
		txtRecordCount.setNumberType(Integer.class);
		txtRecordCount.setOnKeyPressed((KeyEvent ke) -> {
			if (ke.getCode().equals(KeyCode.ENTER)) {
				btnGenerate.fire();
			}
		});

		btnGenerate.setOnAction((ActionEvent event) -> {
			if (txtRecordCount.getValue() != null && txtRecordCount.getValue() > 0) {
				generateData(txtRecordCount.getValue());
			}
		});

		SideMenuCategory smCat = new SideMenuCategory("image-menu-cat1", "Editing");
		SideMenuItem smItem1 = new SideMenuItem("image-menu-item1", "Edit In Row", "showFrmPersonTable");
		SideMenuItem smItem6 = new SideMenuItem("image-menu-item1", "Edit In Row(Odd Editable)", "showFrmOddEditablePersonTable");
		SideMenuItem smItem2 = new SideMenuItem("image-menu-item2", "Edit In Form", "showFrmPersonTable2");
		SideMenuItem smItem3 = new SideMenuItem("image-menu-item3", "Master Detail", "showFrmMasterDetail");
		SideMenuItem smItem4 = new SideMenuItem("image-menu-item4", "Tab Pane", "showFrmDetachableTabPane");
		SideMenuItem smItem5 = new SideMenuItem("image-menu-item2", "History", "showFrmLog");
		SideMenuItem smItemDate = new SideMenuItem("image-menu-item2", "Date Table", "showFrmDateColumn");
		SideMenuItem smItemText = new SideMenuItem("image-menu-item1", "Text Table", "showFrmTextColumn");
		SideMenuItem smItemSynch = new SideMenuItem("image-menu-item1", "Synchronized columns", "showFrmSychronizedColumns");
		smCat.addMainMenuItem(smItem1, smItem6, smItem2, smItemText, smItemDate, smItemSynch, smItem5);

		sideMenu.addMenuItems(smCat, smItem3, smItem4);

		sideMenu.setActionHandler(new SideMenuActionHandler() {

			@Override
			public void executeAction(String actionName) {
				switch (actionName) {
					case "showFrmPersonTable":
						showFrmPersonTable();
						break;
					case "showFrmOddEditablePersonTable":
						showFrmOddEditablePersonTable();
						break;
					case "showFrmPersonTable2":
						showFrmPersonTable2();
						break;
					case "showFrmMasterDetail":
						showFrmMasterDetail();
						break;
					case "showFrmDetachableTabPane":
						showFrmDetachableTabPane();
						break;
					case "showFrmTextColumn":
						showFrmTextColumn();
						break;
					case "showFrmDateColumn":
						showFrmDateColumn();
						break;
					case "showFrmSychronizedColumns":
						showFrmSychronizedColumn();
						break;
					case "showFrmLog":
						showFrmLog();
						break;
				}
			}
		});

		tabpane.setSceneFactory(new Callback<DetachableTabPane, Scene>() {
			@Override
			public Scene call(DetachableTabPane p) {
				//create your scene here
				Label lbl = new Label("TiwulFX Demo 2.0");
				lbl.setId("app-title");
				p.setPrefSize(600, 400);
				VBox vbox = new VBox();
//			hbox
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
		TiwulFXUtil.attachWindowListener(this, new WindowEventListener() {
			@Override
			public void onWindowShown(WindowEvent event) {
				/**
				 * Since the generateData method will show a Stage, it should be executed
				 * after this form is shown (has scene and window).
				 */
				generateData(154);
				frmPersonTable.reload();
			}

			@Override
			public void onWindowCloseRequest(WindowEvent event) {
				Answer answer = MessageDialogBuilder.confirmation().message("Are you sure to exit the application?")
						  .yesOkButtonText("Exit please")
						  .noButtonText("Nevermind")
						  .show(getScene().getWindow());
				if (answer != Answer.YES_OK) {
					event.consume();//cancel close
				}
			}
			
		});
	}

	private void generateData(int count) {
		DataGenerator.createWithTestData(count);
		MessageDialogBuilder.info().message("message.record.has.been.generated", count).show(FrmMain.this.getScene().getWindow());
		txtRecordCount.setValue(null);
	}

	public void showAsTab(Pane frm, String label) {
		final Tab tab = new Tab(label);
		tab.setClosable(true);
		tab.setContent(frm);
		tabpane.getTabs().add(tab);
		tabpane.getSelectionModel().select(tab);

		/**
		 * Workaround for TabPane memory leak
		 */
		tab.setOnClosed(new EventHandler<Event>() {

			@Override
			public void handle(Event t) {
				tab.setContent(null);
			}
		});

	}

	public void showFrmPersonTable() {
		showAsTab(new FrmPersonTable(), "Edit in Row");
	}

	public void showFrmOddEditablePersonTable() {
		showAsTab(new FrmOddEditablePersonTable(), "Edit in Row (Odd Editable)");
	}

	public void showFrmPersonTable2() {
		showAsTab(new FrmPersonTable2(), "Edit in Form");
	}

	public void showFrmMasterDetail() {
		showAsTab(new FrmMasterDetail(), "Master Detail");
	}

	public void showFrmLog() {
		final FrmLog frm = new FrmLog();
		showAsTab(frm, "History");
	}

	public void showFrmDetachableTabPane() {
		showAsTab(new FrmDetachableTabPane(), "Inner Scope");
	}

	public void showFrmTextColumn() {
		FrmTextColumn frm = new FrmTextColumn();
		frm.reload();
		showAsTab(frm, "Text Column");
	}

	public void showFrmDateColumn() {
		FrmDateColumn frm = new FrmDateColumn();
		frm.reload();
		showAsTab(frm, "Date Column");
	}

	public void showFrmSychronizedColumn() {
		FrmSynchronizedColumns frm = new FrmSynchronizedColumns();
		frm.reload();
		showAsTab(frm, "Synchronize Column");
	}
}
