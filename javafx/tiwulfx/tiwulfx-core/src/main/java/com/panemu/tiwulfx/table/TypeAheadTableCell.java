package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.control.TypeAheadField;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.jetbrains.annotations.NotNull;

public class TypeAheadTableCell<R, C> extends CustomTableCell<R, C> {

	private TypeAheadField<C> typeAheadField;
	private TypeAheadColumn<R, C> column;
	private Logger logger = Logger.getLogger(TypeAheadTableCell.class.getName());
	public TypeAheadTableCell(TypeAheadColumn<R, C> column) {
		super(column);
		this.column = column;
	}

	@Override
	protected void updateCellValue(C value) {
		typeAheadField.setValue(value);
	}

	@Override
	protected C getEditedValue() {
		typeAheadField.markInvalidProperty().set(true);
		return typeAheadField.getValue();
	}

	@Override
	protected @NotNull Control getEditView() {
		if (typeAheadField == null) {
			typeAheadField = new TypeAheadField<>();
			typeAheadField.setSorted(column.isSorted());
			typeAheadField.valueProperty().addListener((ov, t, newValue) -> {
				for (CellEditorListener<R, C> svl : column.getCellEditorListeners()) {
					svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), (R) getTableRow().getItem(), newValue);
				}
			});
			column.requiredProperty().addListener((ov, t, t1) -> {
				if (t1) {
					typeAheadField.getItems().remove(null);
				} else {
					typeAheadField.getItems().add(0, null);
				}
			});
			typeAheadField.getItems().addAll(column.getItemMap().values());
			column.getItemMap().addListener(new MapChangeListener<String, C>() {
				//TODO this listener is subject to memory leak
				@Override
				public void onChanged(MapChangeListener.Change<? extends String, ? extends C> change) {
					if (change.wasAdded()) {
						typeAheadField.getItems().add(change.getValueAdded());
					} else {
						typeAheadField.getItems().remove(change.getValueRemoved());
					}
				}
			});
			typeAheadField.setConverter(column.getStringConverter());
			/**
			 * Use event filter instead on onKeyPressed because Enter and Escape have
			 * been consumed by TypeAhead itself
			 */
			typeAheadField.addEventFilter(KeyEvent.KEY_PRESSED, t -> {
				if (t.getCode() == KeyCode.ENTER) {
					commitEdit(typeAheadField.getValue());
					t.consume();
				} else if (t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
					/**
					 * Propagate ESCAPE key press to cell
					 */
					TypeAheadTableCell.this.fireEvent(t);
				}
			});
		}
		return typeAheadField;
	}
}
