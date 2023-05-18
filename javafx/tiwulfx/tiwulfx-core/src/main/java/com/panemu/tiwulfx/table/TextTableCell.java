package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TextInputConstraint;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class TextTableCell<R> extends CustomTableCell<R, String> {

    private TextField textField;
    private TextColumn<R> column;

    public TextTableCell(TextColumn<R> column) {
        super(column);
        this.column = column;
    }

    @Override
    protected void updateCellValue(String value) {
        textField.setText(value);
    }

    @Override
    protected String getEditedValue() {
        if (textField.getText() == null
                || (column.isEmptyStringAsNull() && textField.getText().trim().isEmpty())) {
            return null;
        }
        return textField.getText();
    }

    @Override
    protected Control getEditView() {
        if (textField == null) {
            textField = new TextField();
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            if (column.isCapitalize() || column.getMaxLength() > 0) {
                textField.textProperty()
                        .addListener(new TextInputConstraint(textField, column.getMaxLength(), column.isCapitalize()));
            }
            textField.textProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String newValue) {
                    for (CellEditorListener<R, String> svl : column.getCellEditorListeners()) {
                        svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), (R) getTableRow().getItem(), newValue);
                    }
                }
            });

        }
        return textField;
    }
}
