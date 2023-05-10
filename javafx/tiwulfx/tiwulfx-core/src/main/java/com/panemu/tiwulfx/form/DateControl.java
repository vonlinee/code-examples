/*
 * License GNU LGPL
 * Copyright (C) 2012 Amrullah <amrullah@panemu.com>.
 */
package com.panemu.tiwulfx.form;

import com.panemu.tiwulfx.control.DateField;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;

/**
 *
 * @author Amrullah
 */
public class DateControl extends BaseControl<Date, DateField> {

	private DateField dateField;

	public DateControl() {
		this("");
	}

	public DateControl(String propertyName) {
		super(propertyName, new DateField());
		dateField = getInputComponent();
	}

	@Override
	protected void bindValuePropertyWithControl(DateField inputControl) {
		value.bind(inputControl.selectedDateProperty());
	}

	@Override
	public void setValue(Date value) {
		dateField.setSelectedDate(value);
	}

	public ObjectProperty<Locale> localeProperty() {
		return dateField.localeProperty();
	}

	public void setPromptText(String promptText) {
		dateField.setPromptText(promptText);
	}

	public ObjectProperty<DateFormat> dateFormatProperty() {
		return dateField.dateFormatProperty();
	}

	/**
	 * set Date Format. This method name doesn't conform Java Bean naming because there is a bug in SceneBuilder. This kind of naming will make this property read only in
	 * SceneBuilder. The problem doesn't exist if there is no default value for dateFormat property. However, since we want to make defaultFormat follows what is defined in
	 * TiwulFXUtils, we need to make SceneBuilder thinks that this property is read only.
	 *
	 * @param dateFormat
	 */
	public void setDateFormat_(DateFormat dateFormat) {
		dateField.setDateFormat_(dateFormat);
	}

	public DateFormat getDateFormat() {
		return dateField.getDateFormat();
	}

	public void setLocale(Locale locale) {
		dateField.setLocale(locale);
	}

	public Locale getLocale() {
		return dateField.getLocale();
	}

	public ReadOnlyBooleanProperty showingCalendarProperty() {
		return dateField.showingCalendarProperty();
	}

	public void showCalendar() {
		dateField.showCalendar();
	}

	public BooleanProperty showTodayButtonProperty() {
		return dateField.showTodayButtonProperty();
	}

	public boolean isShowTodayButton() {
		return dateField.isShowTodayButton();
	}

	public void setShowTodayButton(boolean showTodayButton) {
		dateField.setShowTodayButton(showTodayButton);
	}
}
