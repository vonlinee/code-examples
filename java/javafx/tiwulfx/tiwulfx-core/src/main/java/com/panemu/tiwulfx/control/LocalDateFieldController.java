package com.panemu.tiwulfx.control;

import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;

public abstract class LocalDateFieldController {
	
	/**
	 * This method is called if user type in disabled date then move the focus
	 * away from the DateField. It will display an error message and then revert
	 * the value back.
	 * @param dateField the datefield we are talking about :)
	 * @param oldDate date of dateField when it received focus
	 */
	public void onDisabledDateSelected(DatePicker dateField, LocalDate oldDate) {
		MessageDialogBuilder.error().message("date.disabled").show(dateField.getScene().getWindow());
		dateField.setValue(oldDate);
	}

	/**
	 * This abstract method will decide whether passed date is enabled.
	 * @param date 
	 * @return true to enable the date
	 */
	public abstract boolean isEnabled(LocalDate date);
}

