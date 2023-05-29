package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.LocalDateConverterWithDateFormat;
import com.panemu.tiwulfx.control.DateField;
import java.time.LocalDate;
import java.util.Date;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

public class DateTableCell<R> extends BaseCell<R, Date> {

	private DateField datePicker;
	private DateColumn<R> column;

	public DateTableCell(DateColumn<R> column) {
		super(column);
		this.column = column;
		datePickerConverter = new LocalDateConverterWithDateFormat(column.getDateFormat(), column.getNullLabel());
	}

	@Override
	protected void updateValue(Date value) {
		datePicker.setSelectedDate(value);
	}

	@Override
	protected Date getEditedValue() {
		return datePicker.getSelectedDate();
	}

	@Override
	protected Control getEditableControl() {
		if (datePicker == null) {
			datePicker = new DateField();
			datePicker.setController(column.getController());
			/**
			 * Disable traversing focus using LEFT and RIGHT.
			 */
			datePicker.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				if ((event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT)
						  && isEditing()) {
					event.consume();
				}
			});
			
			datePicker.selectedDateProperty().addListener((ov, t, newValue) -> {
				for (CellEditorListener<R, Date> svl : column.getCellEditorListeners()) {
					svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), (R) getTableRow().getItem(), newValue);
				}
			});
		}
		return datePicker;
	}

	@Override
	protected void attachEnterEscapeEventHandler() {
		/**
		 * Use event filter instead on onKeyPressed because Enter and Escape have
		 * been consumed by Combobox itself
		 */
		datePicker.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
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
			}
		});
	}

	private StringConverter<LocalDate> datePickerConverter;

}
