/*
 * Copyright (C) 2014 Panemu.
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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Amrullah 
 */
public class ComboBoxTableCell<R, C> extends BaseCell<R, C> {

	private ComboBox<C> combobox;
	private final ComboBoxColumn<R, C> column;

	public ComboBoxTableCell(ComboBoxColumn<R, C> column) {
		super(column);
		this.column = column;
	}

	@Override
	protected void updateValue(C value) {
		combobox.setValue(value);
	}

	@Override
	protected Control getEditableControl() {
		if (combobox == null) {
			combobox = new ComboBox<>();
			if (!column.isRequired()) {
				combobox.getItems().add(null);
			}
			column.requiredProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
					if (t1) {
						combobox.getItems().remove(null);
					} else {
						combobox.getItems().add(0, null);
					}
				}
			});
			combobox.getItems().addAll(column.getItemMap().values());
			//TODO this listener is subject to memory leak
			column.getItemMap().addListener((MapChangeListener<String, C>) change -> {
				if (change.wasAdded()) {
					combobox.getItems().add(change.getValueAdded());
				} else {
					combobox.getItems().remove(change.getValueRemoved());
				}
			});

			combobox.setConverter(column.getStringConverter());

			/**
			 * Disable traversing focus using LEFT and RIGHT.
			 */
			combobox.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if ((event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT)
							  && isEditing()) {
						event.consume();
					}
				}
			});

			combobox.valueProperty().addListener((ov, t, newValue) -> {
				for (CellEditorListener<R, C> svl : column.getCellEditorListeners()) {
					svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), (R) getTableRow().getItem(), newValue);
				}
			});
		}
		return combobox;
	}

	@Override
	protected C getEditedValue() {
		return combobox.getValue();
	}

	@Override
	protected void attachEnterEscapeEventHandler() {
		/**
		 * Use event filter instead on onKeyPressed because Enter and Escape have
		 * been consumed by Combobox itself
		 */
		combobox.addEventFilter(KeyEvent.KEY_PRESSED, t -> {
			if (t.getCode() == KeyCode.ENTER) {
				commitEdit(combobox.getValue());
				t.consume();
			} else if (t.getCode() == KeyCode.ESCAPE) {
				cancelEdit();
				/**
				 * Propagate ESCAPE key press to cell
				 */
				ComboBoxTableCell.this.fireEvent(t);
			}
		});
	}
}
