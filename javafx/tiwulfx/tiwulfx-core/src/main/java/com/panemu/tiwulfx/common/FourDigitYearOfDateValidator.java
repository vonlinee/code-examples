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

import java.util.Calendar;
import java.util.Date;

/**
 * Restrict year of the date. It should fall between 0 - 9999
 * @author amrullah
 */
public class FourDigitYearOfDateValidator implements Validator<Date> {

	@Override
	public String validate(Date date) {
		if (date == null) {
			 return null;
		 }
		 Calendar cal = Calendar.getInstance(TiwulFXUtil.getLocale());
		 cal.setTime(date);
		 int year = cal.get(Calendar.YEAR);
		 if (year > 9999 || year < 0) {
			 return TiwulFXUtil.getString("invalid.date");
		 }
		 
		 return null;
	}
	
}
