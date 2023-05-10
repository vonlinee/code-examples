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

package com.panemu.tiwulfx.common;

import com.panemu.tiwulfx.control.*;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import java.util.Date;

/**
 * This class is a controller that will decide which dates are enabled and
 * selectable. Set this class to {@link DateField#setController(com.panemu.tiwulfx.control.DateFieldController) DateField.setController()}.
 * If user type-in a disabled date, an error message will is shown when the DateField
 * lost focus. By default, the value will be reverted. To avoid this behavior,
 * override {@link #onDisabledDateSelected(com.panemu.tiwulfx.control.DateField, java.util.Date, java.util.Date) onDisabledDateSelected()}
 * @author Amrullah
 */
public abstract class DateFieldController {
	
	/**
	 * This method is called if user type in disabled date then move the focus
	 * away from the DateField. It will display an error message and then revert
	 * the value back.
	 * @param dateField the datefield we are talking about :)
	 * @param oldDate date of dateField when it received focus
	 * @param selectedDisabledDate a disabled date that user selected
	 */
	public void onDisabledDateSelected(DateField dateField, Date oldDate, Date selectedDisabledDate) {
		MessageDialogBuilder.error().message("Selected date is disabled. The value is reverted back.").show(dateField.getScene().getWindow());
		dateField.setSelectedDate(oldDate);
		dateField.requestFocus();
	}

	/**
	 * This abstract method will decide whether passed date is enabled.
	 * @param date 
	 * @return true to disable the date
	 */
	public abstract boolean isEnabled(Date date);
}
