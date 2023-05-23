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

import com.panemu.tiwulfx.common.TextInputConstraint;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

/**
 * @author Amrullah
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
            // textField.setPrefHeight(this.getHeight());
            // textField.setFocusTraversable(true);
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
