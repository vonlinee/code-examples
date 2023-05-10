/*
 * License GNU LGPL
 * Copyright (C) 2012 Amrullah <amrullah@panemu.com>.
 */
package com.panemu.tiwulfx.control;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;

/**
 * Based on Christian Schudt's DatePicker http://myjavafx.blogspot.com/2012/01/javafx-calendar-control.html
 *
 * @author Amrullah
 */
public class DateField extends Control {

	private StringProperty promptText = new SimpleStringProperty();

	public DateField() {
		this(TiwulFXUtil.getLocale());
	}

	private BooleanProperty markInvalid = new SimpleBooleanProperty(false);

	/**
	 * This property is intended for communication with LookupField's skin. markInvalidProperty() is set to true if user changes textfield's text. When
	 * {@link #selectedDateProperty()}.get is called and markInvalidProperty is true, validation will be executed to ensure user's input is valid.
	 *
	 * Developer should not need to use this property.
	 *
	 * @return
	 */
	public BooleanProperty markInvalidProperty() {
		return markInvalid;
	}

	public DateField(Locale locale) {
		getStyleClass().add("date-field");
		setFocusTraversable(false);
		this.locale.set(locale);
	}

	@Override
	public String getUserAgentStylesheet() {
		return DateField.class.getResource("/com/panemu/tiwulfx/res/tiwulfx.css").toExternalForm();
	}

	/**
	 * The locale.
	 *
	 * @return The property.
	 */
	public ObjectProperty<Locale> localeProperty() {
		return locale;
	}

	private ObjectProperty<Locale> locale = new SimpleObjectProperty<>();

	public void setLocale(Locale locale) {
		this.locale.set(locale);
	}

	public Locale getLocale() {
		return locale.get();
	}

	/**
	 * The selected date.
	 *
	 * @return The property.
	 */
	public ObjectProperty<Date> selectedDateProperty() {
		return selectedDate;
	}
	private ObjectProperty<Date> selectedDate = new SimpleObjectProperty<Date>() {
		@Override
		public Date get() {
			if (markInvalid.get()) {
				//it will trigger validation in LookupFieldSkin
				markInvalid.set(false);
			}
			return super.get();
		}
	};

	public void setSelectedDate(Date date) {
		this.selectedDate.set(date);
	}

	public Date getSelectedDate() {
		return selectedDate.get();
	}

	private ReadOnlyBooleanWrapper showingPropertyImpl() {
		if (showing == null) {
			showing = new ReadOnlyBooleanWrapper(false);
		}
		return showing;
	}

	public ReadOnlyBooleanProperty showingCalendarProperty() {
		return showingPropertyImpl().getReadOnlyProperty();
	}

	public void showCalendar() {
		showingPropertyImpl().set(true);
	}

	public void hideCalendar() {
		showingPropertyImpl().set(false);
	}

	public final boolean isShowing() {
		return showingPropertyImpl().get();
	}

	/**
	 * The prompt text for the text field. By default, the prompt text is taken from the date format pattern.
	 *
	 * @return The property.
	 */
	public StringProperty promptTextProperty() {
		return promptText;
	}

	public void setPromptText(String promptText) {
		this.promptText.set(promptText);
	}

	public String getPromptText() {
		return promptText.get();
	}

	/**
	 * Gets the date format.
	 *
	 * @return The date format.
	 */
	public ObjectProperty<DateFormat> dateFormatProperty() {
		return dateFormat;
	}
	private ObjectProperty<DateFormat> dateFormat = new SimpleObjectProperty<>(TiwulFXUtil.getDateFormatForJavaUtilDate());

	/**
	 * set Date Format. This method name doesn't conform Java Bean naming because there is a bug in SceneBuilder. This kind of naming will make this property read only in
	 * SceneBuilder. The problem doesn't exist if there is no default value for dateFormat property. However, since we want to make defaultFormat follows what is defined in
	 * TiwulFXUtils, we need to make SceneBuilder thinks that this property is read only.
	 *
	 * @param dateFormat
	 */
	public void setDateFormat_(DateFormat dateFormat) {
		this.dateFormat.set(dateFormat);
	}

	public DateFormat getDateFormat() {
		return dateFormat.get();
	}
	private ReadOnlyBooleanWrapper showing;

	/**
	 * Indicates, whether the today button should be shown.
	 *
	 * @return The property.
	 */
	public BooleanProperty showTodayButtonProperty() {
		return showTodayButton;
	}

	private BooleanProperty showTodayButton = new SimpleBooleanProperty(true);

	public boolean isShowTodayButton() {
		return showTodayButton.get();
	}

	public void setShowTodayButton(boolean showTodayButton) {
		this.showTodayButton.set(showTodayButton);
	}

	private ObjectProperty<DateFieldController> controllerProperty = new SimpleObjectProperty<>();

	public ObjectProperty<DateFieldController> controllerProperty() {
		return controllerProperty;
	}

	public DateFieldController getController() {
		return controllerProperty.get();
	}

	/**
	 * This method will set a controller that will decide which dates are enabled. A disabled date is not selectable neither using calendar popup or shortcut (up/down arrow,
	 * Ctrl+up/down arrow). If user type-in a disable date, by default the controller will display an error message and revert the value back. To change this behavior, override {@link DateFieldController#onDisabledDateSelected(com.panemu.tiwulfx.control.DateField, java.util.Date, java.util.Date)
	 * DateFieldController.onDisabledDateSelected}
	 *
	 * @param dateFieldController
	 */
	public void setController(DateFieldController dateFieldController) {
		this.controllerProperty.set(dateFieldController);
	}
}
