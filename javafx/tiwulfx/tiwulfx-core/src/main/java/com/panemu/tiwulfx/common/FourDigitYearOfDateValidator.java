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
