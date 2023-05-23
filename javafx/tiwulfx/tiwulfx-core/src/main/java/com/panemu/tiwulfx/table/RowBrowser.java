/*
 * Copyright (C) 2018 Panemu.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author amrullah
 */
public class RowBrowser extends VBox {

	private TableView<Record> tblView;
	private ContextMenu cm;

	public RowBrowser() {
		tblView = new TableView<>();
		VBox.setVgrow(tblView, Priority.ALWAYS);
		TableColumn<Record, String> clmLabel = new TableColumn<>(TiwulFXUtil.getLiteral("column"));
		clmLabel.setPrefWidth(150);
		clmLabel.setCellValueFactory((TableColumn.CellDataFeatures<Record, String> param) -> param.getValue().label);
		TableColumn<Record, String> clmValue = new TableColumn<>(TiwulFXUtil.getLiteral("value"));
		clmValue.setCellValueFactory((TableColumn.CellDataFeatures<Record, String> param) -> param.getValue().value);
		clmValue.setPrefWidth(400);
		clmValue.setCellFactory((TableColumn<Record, String> param) -> {
			TableCell<Record, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(clmValue.widthProperty().add(-5));
			text.textProperty().bind(cell.itemProperty());
			text.getStyleClass().add("row-browser-text-cell");
			return cell;
		});
		tblView.getColumns().addAll(clmLabel, clmValue);
//		tblView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tblView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tblView.getSelectionModel().setCellSelectionEnabled(true);
		this.setPrefWidth(600);
		this.setSpacing(10);
		this.setPadding(new Insets(0, 0, 10, 0));
		Button btnClose = new Button(TiwulFXUtil.getLiteral("close"));
		
		btnClose.setOnAction((event) -> {
			getScene().getWindow().hide();
		});
		HBox hbox = new HBox(btnClose);
		hbox.setAlignment(Pos.CENTER);
		this.getChildren().setAll(tblView, hbox);

		cm = new ContextMenu();
		createCopyCellMenuItem();
		cm.setAutoHide(true);

		tblView.setOnMouseReleased(tableRightClickListener);
		tblView.addEventFilter(KeyEvent.KEY_PRESSED, tableKeyListener);
	}

	private EventHandler<KeyEvent> tableKeyListener = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.C && event.isControlDown()) {
				copyCell();
				event.consume();
			}
			return;

		}
	};

	public void setRecords(List<Record> lst) {
		tblView.getItems().setAll(lst);
	}

	public void show(Window parent) {
		Stage dialogStage = new Stage();
		if (parent instanceof Stage) {
			dialogStage.initOwner(parent);
			dialogStage.setResizable(true);
			final Scene scene = new Scene(this);
			scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
				if (event.getCode() == KeyCode.ESCAPE && getScene() != null && getScene().getWindow() != null) {
					getScene().getWindow().hide();
				}
			});
			dialogStage.setScene(scene);
			dialogStage.setTitle(TiwulFXUtil.getLiteral("browse.row"));
			dialogStage.getIcons().setAll(((Stage) parent).getIcons());
			dialogStage.getScene().getStylesheets().addAll(parent.getScene().getStylesheets());
		}
		dialogStage.show();
	}
	
	public void close() {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> close());
			return;
		}
		if (getScene() != null && getScene().getWindow() != null) {
			getScene().getWindow().hide();
		}
	}

	private EventHandler<MouseEvent> tableRightClickListener = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (cm.isShowing()) {
				cm.hide();
			}
			if (event.getButton().equals(MouseButton.SECONDARY)) {

				if (tblView.getSelectionModel().getSelectedCells().isEmpty()) {
					return;
				}

				cm.show(tblView, event.getScreenX(), event.getScreenY());
			}
		}
	};

	private void createCopyCellMenuItem() {
		MenuItem mi = new MenuItem(TiwulFXUtil.getLiteral("copy"));
		mi.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
		mi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				copyCell();
			}
		});
		cm.getItems().add(mi);

	}

	private void copyCell() {
		TablePosition pos = tblView.getSelectionModel().getSelectedCells().get(0);
		TableColumn column = null;
		if (pos != null) {
			column = pos.getTableColumn();
		}
		Object cellValue = column.getCellData(pos.getRow());
		String textToCopy = String.valueOf(cellValue);
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();
		content.putString(textToCopy);
		clipboard.setContent(content);
	}

	public static class Record {

		public StringProperty label = new SimpleStringProperty();
		public StringProperty value = new SimpleStringProperty();

		public Record(String label, String value) {
			this.label.set(label);
			this.value.set(value);
		}

	}
}
