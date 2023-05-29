package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.control.NumberField;
import javafx.scene.control.Control;

public class NumberTableCell<R, C extends Number> extends BaseCell<R, C> {

    private NumberField<C> textField;
    private NumberColumn<R, C> column;

    public NumberTableCell(NumberColumn<R, C> column) {
        super(column);
        this.column = column;
    }

    @Override
    protected void updateValue(C value) {
        textField.setValue(value);
    }

    @Override
    protected C getEditedValue() {
        return textField.getValue();
    }

    @Override
    protected Control getEditableControl() {
        if (textField == null) {
            textField = new NumberField<>(column.getNumberType());
            textField.setMaxLength(column.getMaxLength());
            textField.setDigitBehindDecimal(column.getDigitBehindDecimal());
            textField.setNegativeAllowed(column.isNegativeAllowed());
            textField.valueProperty().addListener((ov, t, newValue) -> {
                for (CellEditorListener<R, C> svl : column.getCellEditorListeners()) {
                    svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), (R) getTableRow().getItem(), newValue);
                }
            });
        }
        return textField;
    }
}
