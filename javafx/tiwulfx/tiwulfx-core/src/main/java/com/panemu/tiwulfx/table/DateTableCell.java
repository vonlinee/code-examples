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

import com.panemu.tiwulfx.common.LocalDateConverterWithDateFormat;
import com.panemu.tiwulfx.control.DateField;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Amrullah
 */
public class DateTableCell<R> extends CustomTableCell<R, Date> {

    private DateField datePicker;
    private DateColumn<R> column;

    public DateTableCell(DateColumn<R> column) {
        super(column);
        this.column = column;
        datePickerConverter = new LocalDateConverterWithDateFormat(column.getDateFormat(), column.getNullLabel());
    }

    @Override
    protected void updateCellValue(Date value) {
        datePicker.setSelectedDate(value);
    }

    @Override
    protected Date getEditedValue() {
        return datePicker.getSelectedDate();
    }

    @Override
    protected @NotNull Control getEditView() {
        if (datePicker == null) {
            datePicker = new DateField();
            datePicker.setController(column.getController());
            /**
             * Disable traversing focus using LEFT and RIGHT.
             */
            datePicker.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if ((event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) && isEditing()) {
                    event.consume();
                }
            });

            datePicker.selectedDateProperty().addListener((ov, t, newValue) -> {
                for (CellEditorListener<R, Date> svl : column.getCellEditorListeners()) {
                    svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), (R) getTableRow().getItem(), newValue);
                }
            });
            /**
             * Use event filter instead on onKeyPressed because Enter and Escape have
             * been consumed by Combobox itself
             */
            datePicker.addEventFilter(KeyEvent.KEY_PRESSED, t -> {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(getEditedValue());
                    t.consume();
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                    /**
                     * Propagate ESCAPE key press to cell to go to Browsing mode on
                     * Agile editing only
                     */
                    DateTableCell.this.fireEvent(t);
                }
            });
        }
        return datePicker;
    }

    private StringConverter<LocalDate> datePickerConverter;
}
