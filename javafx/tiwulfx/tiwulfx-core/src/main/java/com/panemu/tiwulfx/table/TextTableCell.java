package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TextInputConstraint;
import com.panemu.tiwulfx.utils.SceneGraph;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * @see javafx.scene.control.cell.TextFieldTableCell
 * @param <R>
 */
public class TextTableCell<R> extends BaseCell<R, String> {

    private TextField textField;
    private final TextColumn<R> column;

    public TextTableCell(TextColumn<R> column) {
        super(column);
        this.column = column;
    }

    @Override
    protected void updateValue(String value) {
        textField.setText(value);
    }

    @Override
    protected String getEditedValue() {
        if (textField.getText() == null || (column.isEmptyStringAsNull() && isTextFieldBlank())) {
            return null;
        }
        return textField.getText();
    }

    private boolean isTextFieldBlank() {
        return textField.getText().trim().isEmpty();
    }

    @Override
    protected Control getEditableControl() {
        if (textField == null) {
            textField = new TextField();
            textField.setPrefHeight(this.getHeight());
            textField.setFocusTraversable(true);
            // textField.setBorder(SceneGraph.border(Color.BLACK, 0, 0));
            // textField.setBackground(SceneGraph.background(Color.WHITE, 0, 0));
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            if (column.isCapitalize() || column.getMaxLength() > 0) {
                textField.textProperty()
                        .addListener(new TextInputConstraint(textField, column.getMaxLength(), column.isCapitalize()));
            }
            textField.textProperty().addListener((ov, t, newValue) -> {
                for (CellEditorListener<R, String> svl : column.getCellEditorListeners()) {
                    svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), getTableRow().getItem(), newValue);
                }
            });
        }
        return textField;
    }
}
