package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TextInputConstraint;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;

public class ObjectTableCell<R, C> extends CustomTableCell<R, C> {

	private TextField textField;
	private ObjectColumn<R, C> column;
	private StringConverter<C> converter;
	public ObjectTableCell(ObjectColumn<R, C> column) {

		super(column);
		this.column = column;
		this.converter = column.getStringConverter();
	}

	@Override
	protected void updateCellValue(C value) {
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
	protected @NotNull Control getEditView() {
		if (textField == null) {
			textField = new TextField();
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			if (column.isCapitalize() || column.getMaxLength() > 0) {
				textField.textProperty().addListener(new TextInputConstraint(textField, column.getMaxLength(), column.isCapitalize()));
			}
			textField.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> ov, String t, String newValue) {
					for (CellEditorListener<R, C> svl : column.getCellEditorListeners()) {
						svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), (R) getTableRow().getItem(), converter.fromString(newValue));
					}
					throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
				}
			});
		}
		return textField;
	}
}
