package com.panemu.tiwulfx.control.skin;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.common.Validator;
import com.panemu.tiwulfx.control.DateField;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import eu.schudt.javafx.controls.calendar.CalendarView;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Based on Christian Schudt's DatePicker
 * http://myjavafx.blogspot.com/2012/01/javafx-calendar-control.html
 */
public class DateFieldSkin extends SkinBase<DateField> {

    private TextField textField;
    private Button button;
    private Popup popup;
    private CalendarView calendarView;
    private BooleanProperty invalid = new SimpleBooleanProperty();
    private DateField dateField;
    private Date onFocusDate = null;
    private Validator<Date> globalValidator = TiwulFXUtil.getDateValidator();

    public DateFieldSkin(DateField control) {
        super(control);
        this.dateField = control;
        initCalendarView();
        // move focus in to the textfield if the comboBox is editable
        dateField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean hasFocus) {
                if (hasFocus) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            textField.requestFocus();
                        }
                    });
                }
            }
        });
        initialize();

        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean hasFocus) {
                if (!hasFocus && invalid.get()) {
                    tryParse(false);
                } else {
                    onFocusDate = dateField.getSelectedDate();
                }
            }
        });

        textField.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent ke) {
                if ((ke.getCode() == KeyCode.DOWN || ke.getCode() == KeyCode.UP) && !ke.isControlDown()) {
                    dateField.fireEvent(ke);
                    //prevent moving caret position to the start/end
                    ke.consume();
                }
            }
        });
        dateField.addEventFilter(InputEvent.ANY, new EventHandler<InputEvent>() {
            @Override
            public void handle(InputEvent t) {
                if (textField == null) {
                    return;
                }

                // When the user hits the enter or F4 keys, we respond before 
                // ever giving the event to the TextField.
                if (t instanceof KeyEvent) {
                    KeyEvent ke = (KeyEvent) t;

                    if (ke.getCode() == KeyCode.F10 || ke.getCode() == KeyCode.ESCAPE || ke.getCode() == KeyCode.ENTER) {
                        // RT-23275: The TextField fires F10 and ESCAPE key events
                        // up to the parent, which are then fired back at the
                        // TextField, and this ends up in an infinite loop until
                        // the stack overflows. So, here we consume these two
                        // events and stop them from going any further.
                        t.consume();
                        return;
                    } else if (ke.getCode() == KeyCode.DOWN && ke.getEventType() == KeyEvent.KEY_RELEASED) {
                        if (ke.isControlDown() && ke.isShiftDown()) {
                            decreaseYear();
                        } else if (ke.isControlDown()) {
                            decreaseMonth();
                        } else {
                            decreaseDay();
                        }
                        t.consume();
                        return;
                    } else if (ke.getCode() == KeyCode.UP && ke.getEventType() == KeyEvent.KEY_RELEASED) {
                        if (ke.isControlDown() && ke.isShiftDown()) {
                            increaseYear();
                        } else if (ke.isControlDown()) {
                            increaseMonth();
                        } else {
                            increaseDay();
                        }
                        t.consume();
                        return;
                    }
                }
            }
        });

        getSkinnable().requestLayout();
    }

    /**
     * Get the reference to the underlying textfield. This method is used by DateTableCell.
     * @return TextField
     */
    public TextField getTextField() {
        return textField;
    }

    private void initialize() {
        textField = new TextField();
        textField.setFocusTraversable(true);
        // Let the prompt text property listen to locale or date format changes.
        textField.promptTextProperty().bind(new StringBinding() {
            {
                super.bind(getSkinnable().localeProperty(), getSkinnable().promptTextProperty(), getSkinnable().dateFormatProperty());
            }

            @Override
            protected String computeValue() {
                // First check, if there is a custom prompt text.
                if (getSkinnable().promptTextProperty().get() != null) {
                    return getSkinnable().promptTextProperty().get();
                }

                // If not, use the the date format's pattern.
                DateFormat dateFormat = getActualDateFormat();
                if (dateFormat instanceof SimpleDateFormat) {
                    return TiwulFXUtil.DEFAULT_DATE_PROMPTEXT;
                }

                return "";
            }
        });

        dateField.markInvalidProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue && !newValue && invalid.get() && !textSetProgrammatically) {
                    tryParse(false);
                }
            }
        });

        // Listen to user input.
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                // Only evaluate the input, it it wasn't set programmatically.
                if (textSetProgrammatically) {
                    return;
                }
                dateField.markInvalidProperty().set(true);
                invalid.set(true);
                // If the user clears the text field, set the date to null and the field to valid.
                if (s1.equals("")) {
                    dateField.selectedDateProperty().set(null);
                    invalid.set(false);
                }
            }
        });

        if (dateField.selectedDateProperty().get() != null) {
            updateTextField();
        }
        dateField.selectedDateProperty().addListener(new ChangeListener<Date>() {
            @Override
            public void changed(ObservableValue<? extends Date> ov, Date t, Date t1) {
                updateTextField();
                invalid.set(false);
            }
        });

        dateField.localeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateTextField();
            }
        });

        button = new Button();
        button.setFocusTraversable(false);
        button.setGraphic(TiwulFXUtil.getGraphicFactory().createCalendarGraphic());
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (!dateField.isFocused()) {
                    /**
                     * Need to make this control become focused. Otherwise changing
                     * value in DateColumn while the DateField cell editor is not
                     * focused before, won't trigger commitEdit()
                     */
                    dateField.requestFocus();
                }
                dateField.showCalendar();
            }
        });

        dateField.showingCalendarProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    showPopup();
                } else {
                    hidePopup();
                }
            }
        });

        StackPane.setAlignment(textField, Pos.CENTER_LEFT);
        StackPane.setAlignment(button, Pos.CENTER_RIGHT);
        this.getChildren().addAll(textField, button);
    }

    private boolean isParsing = false;

    /**
     * Tries to parse the text field for a valid date.
     * @param setDateToNullOnException True, if the date should be set to null,
     *                                 when a {@link ParseException} occurs. This is the case, when the text
     *                                 field loses focus.
     */
    private void tryParse(boolean setDateToNullOnException) {
        if (isParsing) {
            return;
        }
        isParsing = true;
        try {
            // Double parse the date here, since e.g. 01.01.1 is parsed as year 1, and then formatted as 01.01.01 and then parsed as year 2001.
            // This might lead to an undesired date.
            DateFormat dateFormat = getActualDateFormat();
            Date parsedDate = dateFormat.parse(textField.getText());
            parsedDate = dateFormat.parse(dateFormat.format(parsedDate));
            if (validateDate(parsedDate)) {
                if (getSkinnable().selectedDateProperty().get() == null || getSkinnable().selectedDateProperty()
                        .get() != null && parsedDate.getTime() != getSkinnable().selectedDateProperty().get()
                        .getTime()) {

                    if (dateField.getController() != null && !dateField.getController().isEnabled(parsedDate)) {
                        dateField.getController().onDisabledDateSelected(dateField, onFocusDate, parsedDate);
                    } else {
                        dateField.setSelectedDate(parsedDate);
                    }
                }
            } else {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        dateField.requestFocus();
                    }
                });
            }
            invalid.set(false);
        } catch (ParseException e) {
            if (textField.getText() != null && textField.getText().trim().length() > 0) {
                if (textField.getText().matches("[0-9]*")) {
                    secondAttemptParsing();
                } else {
                    if (getSkinnable().getScene() != null) {
                        MessageDialogBuilder.error().message("invalid.date")
                                .show(getSkinnable().getScene().getWindow());
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                dateField.requestFocus();
                            }
                        });
                    }
                    invalid.set(true);
                    if (setDateToNullOnException) {
                        getSkinnable().selectedDateProperty().set(null);
                    }
                }
            }
        } finally {
            isParsing = false;
        }
        updateTextField();
    }

    private boolean validateDate(Date date) {
        if (globalValidator != null) {
            String errMsg = globalValidator.validate(date);
            if (errMsg != null && errMsg.trim().length() > 0) {
                MessageDialogBuilder.error().message(errMsg).show(getSkinnable().getScene().getWindow());
                return false;
            }
        }
        return true;
    }

    private void secondAttemptParsing() {
        //TODO This parsing only handle MM/DD/YYYY format. It should handle DD/MM/YYYY too. Fix this when time allows.
        try {
            DateFormat mmddyyDateFormat = null;
            if (textField.getText().length() == 6) {
                mmddyyDateFormat = new SimpleDateFormat("MMddyy");
            } else {
                mmddyyDateFormat = new SimpleDateFormat("MMddyyyy");
            }
            mmddyyDateFormat.setLenient(false);
            Date parsedDate = mmddyyDateFormat.parse(textField.getText());
            if (validateDate(parsedDate)) {
                if (getSkinnable().selectedDateProperty().get() == null || getSkinnable().selectedDateProperty()
                        .get() != null && parsedDate.getTime() != getSkinnable().selectedDateProperty().get()
                        .getTime()) {
                    if (dateField.getController() != null && !dateField.getController().isEnabled(parsedDate)) {
                        dateField.getController().onDisabledDateSelected(dateField, onFocusDate, parsedDate);
                    } else {
                        dateField.setSelectedDate(parsedDate);
                    }
                }
            }
            invalid.set(false);
        } catch (ParseException ex) {
            invalid.set(true);
            if (getSkinnable().getScene() != null) {
                MessageDialogBuilder.error().message("invalid.date").show(getSkinnable().getScene().getWindow());
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        dateField.requestFocus();
                    }
                });
            }
        }
    }

    /**
     * Gets the actual date format. If {@link #dateFormatProperty()} is set, take
     * it, otherwise get a default format for the current locale.
     * @return The date format.
     */
    private DateFormat getActualDateFormat() {
        if (getSkinnable().dateFormatProperty().get() != null) {
            return getSkinnable().dateFormatProperty().get();
        }

        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, getSkinnable().localeProperty().get());
        format.setCalendar(calendarView.getCalendar());
        format.setLenient(false);

        return format;
    }

    private boolean textSetProgrammatically;

    /**
     * Updates the text field.
     */
    private void updateTextField() {
        // Mark the we update the text field (and not the user), so that it can be ignored, by textField.textProperty()
        textSetProgrammatically = true;
        if (getSkinnable().selectedDateProperty().get() != null) {
            String date = getActualDateFormat().format(getSkinnable().selectedDateProperty().get());
            if (!textField.getText().equals(date)) {
                textField.setText(date);
            }
        } else {
            textField.setText("");
        }
        textSetProgrammatically = false;
    }

    @Override
    protected void layoutChildren(final double x, final double y, final double w, final double h) {

        double obw = button.prefWidth(-1);

        double displayWidth = getSkinnable().getWidth() - (getSkinnable().getInsets().getLeft() + getSkinnable()
                .getInsets().getRight() + obw);

        textField.resizeRelocate(x, y, w, h);
        button.resizeRelocate(x + displayWidth, y, obw, h);
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return getSkinnable().prefHeight(width);
    }

    private void initCalendarView() {
        calendarView = new CalendarView(getSkinnable().localeProperty().get());

        calendarView.setEffect(new DropShadow());

        // Use the same locale.
        calendarView.localeProperty().bind(getSkinnable().localeProperty());

        // Bind the current date of the calendar view with the selected date, so that the calendar shows up with the same month as in the text field.
        calendarView.currentDateProperty().bind(getSkinnable().selectedDateProperty());

        // When the user selects a date in the calendar view, hide it.
        calendarView.selectedDateProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                dateField.selectedDateProperty().set(calendarView.selectedDateProperty().get());
//                hidePopup();
                getSkinnable().hideCalendar();
            }
        });

        calendarView.showTodayButtonProperty().bind(getSkinnable().showTodayButtonProperty());

        calendarView.controllerProperty().bind(dateField.controllerProperty());
    }

    private void showPopup() {

        if (popup == null) {
            popup = new Popup();
            popup.setAutoHide(true);
            popup.setHideOnEscape(true);
            popup.setAutoFix(true);
            popup.getContent().add(calendarView);
            popup.setOnAutoHide(new EventHandler<Event>() {
                @Override
                public void handle(Event e) {
                    getSkinnable().hideCalendar();
                }
            });
        }

        Point2D p = getSkinnable().localToScene(0.0, 0.0);
        Point2D p2 = new Point2D(p.getX() + getSkinnable().getScene().getX() + getSkinnable().getScene().getWindow()
                .getX(), p.getY() + getSkinnable().getScene().getY() + getSkinnable().getScene().getWindow()
                .getY() + getSkinnable().getHeight());

        Scene scene = getSkinnable().getScene();
        if (scene != null) {
            popup.show(scene.getWindow(), p2.getX(), p2.getY());
        }


    }

    private void hidePopup() {
        if (popup != null && popup.isShowing()) {
            popup.hide();
        }
    }

    private void increaseMonth() {
        changeDate(Calendar.MONTH, 1);
    }

    private void decreaseMonth() {
        changeDate(Calendar.MONTH, -1);
    }

    private void increaseDay() {
        changeDate(Calendar.DATE, 1);
    }

    private void decreaseDay() {
        changeDate(Calendar.DATE, -1);
    }

    private void increaseYear() {
        changeDate(Calendar.YEAR, 1);
    }

    private void decreaseYear() {
        changeDate(Calendar.YEAR, -1);
    }

    private void changeDate(int dayOrMonthOrYear, int offset) {
        Date date = getSkinnable().getSelectedDate();
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(dayOrMonthOrYear, offset);
        Date candidateDate = calendar.getTime();
        if (getSkinnable().getController() == null || getSkinnable().getController().isEnabled(candidateDate)) {
            getSkinnable().setSelectedDate(candidateDate);
        }

    }
}
