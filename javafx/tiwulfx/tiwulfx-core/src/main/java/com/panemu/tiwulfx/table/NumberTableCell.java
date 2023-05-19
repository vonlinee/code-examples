package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.control.NumberField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;

public class NumberTableCell<R, C extends Number> extends CustomTableCell<R, C> {

	private NumberField<C> textField;
	private final NumberColumn<R, C> column;

	public NumberTableCell(NumberColumn<R, C> column) {
		super(column);
		this.column = column;
	}

	@Override
	protected void updateCellValue(C value) {
		textField.setValue(value);
	}

	@Override
	protected C getEditedValue() {
		return textField.getValue();
	}

	@Override
	protected Control getEditView() {
		if (textField == null) {
			textField = new NumberField<>(column.getNumberType());
			textField.setMaxLength(column.getMaxLength());
			textField.setDigitBehindDecimal(column.getDigitBehindDecimal());
			textField.setNegativeAllowed(column.isNegativeAllowed());
			textField.valueProperty().addListener(new ChangeListener<C>() {
				@Override
				public void changed(ObservableValue<? extends C> ov, C t, C newValue) {
					for (CellEditorListener<R, C> svl : column.getCellEditorListeners()) {
						svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), (R) getTableRow().getItem(), newValue);
					}
				}
			});
		}
		return textField;
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
	}
}
