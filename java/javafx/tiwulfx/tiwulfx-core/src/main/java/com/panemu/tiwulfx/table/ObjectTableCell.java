package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TextInputConstraint;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class ObjectTableCell<R, C> extends BaseCell<R, C> {

	private TextField textField;
	private ObjectColumn<R, C> column;
	private StringConverter<C> converter;
	public ObjectTableCell(ObjectColumn<R, C> column) {

		super(column);
		this.column = column;
		this.converter = column.getStringConverter();
	}

	@Override
	protected void updateValue(C value) {
		textField.setText(converter.toString(value));
	}

	@Override
	protected C getEditedValue() {
		if (textField.getText() == null
				  || (column.isEmptyStringAsNull() && textField.getText().trim().isEmpty())) {
			return null;
		}
		return converter.fromString(textField.getText());
	}

	@Override
	protected Control getEditableControl() {
		if (textField == null) {
			textField = new TextField();
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			if (column.isCapitalize() || column.getMaxLength() > 0) {
				textField.textProperty().addListener(new TextInputConstraint(textField, column.getMaxLength(), column.isCapitalize()));
			}
			
			textField.textProperty().addListener((ov, t, newValue) -> {
				for (CellEditorListener<R, C> svl : column.getCellEditorListeners()) {
					svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), (R) getTableRow().getItem(), converter.fromString(newValue));
				}
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			});
		}
		return textField;
	}
}
