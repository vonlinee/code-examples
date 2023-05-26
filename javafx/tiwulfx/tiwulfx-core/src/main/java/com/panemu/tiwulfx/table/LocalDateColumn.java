package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TableCriteria.Condition;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.control.LocalDateFieldController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LocalDateColumn<R> extends BaseColumn<R, LocalDate> {

    private final DatePicker searchInputControl = new DatePicker();
    private final SearchMenuItemBase<LocalDate> searchMenuItem = new SearchMenuItemBase<>(this) {

        @Override
        protected Node getInputControl() {
            return searchInputControl;
        }

        @Override
        protected List<Condition> getOperators() {
            List<Condition> lst = new ArrayList<>();
            lst.add(Condition.eq);
            lst.add(Condition.ne);
            lst.add(Condition.lt);
            lst.add(Condition.le);
            lst.add(Condition.gt);
            lst.add(Condition.ge);
            lst.add(Condition.is_null);
            lst.add(Condition.is_not_null);
            return lst;
        }

        @Override
        protected LocalDate getValue() {
            return searchInputControl.getValue();
        }
    };

    public LocalDateColumn() {
        this("");
    }

    public LocalDateColumn(String propertyName) {
        this(propertyName, 100);
    }

    public LocalDateColumn(String propertyName, double preferredWidth) {
        super(propertyName, preferredWidth);
        setCellFactory(p -> new LocalDateTableCell<>(LocalDateColumn.this));
        StringConverter<LocalDate> stringConverter = new StringConverter<>() {

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormat.get().format(date);
                } else {
                    return getNullLabel();
                }

            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.trim().isEmpty() && !string.equals(getNullLabel())) {
                    return LocalDate.parse(string, dateFormat.get());
                } else {
                    return null;
                }

            }
        };
        setStringConverter(stringConverter);
        TiwulFXUtil.attachShortcut(searchInputControl, getController());
    }

    @Override
    public MenuItem getSearchMenuItem() {
        searchInputControl.setValue(getDefaultSearchValue());
        return searchMenuItem;
    }

    /**
     * Gets the date format.
     * @return The date format.
     */
    public final ObjectProperty<DateTimeFormatter> dateFormatProperty() {
        return dateFormat;
    }

    private ObjectProperty<DateTimeFormatter> dateFormat = new SimpleObjectProperty<>(TiwulFXUtil.getDateFormatForLocalDate());

    public void setDateFormat(DateTimeFormatter dateFormat) {
        this.dateFormat.set(dateFormat);
    }

    public DateTimeFormatter getDateFormat() {
        return dateFormat.get();
    }

    private final ObjectProperty<LocalDateFieldController> controllerProperty = new SimpleObjectProperty<>();

    public final ObjectProperty<LocalDateFieldController> controllerProperty() {
        return controllerProperty;
    }

    public final LocalDateFieldController getController() {
        return controllerProperty.get();
    }

    /**
     * This method will set a controller that will decide which dates are
     * enabled. A disabled date is not selectable neither using calendar popup
     * or shortcut (up/down arrow, Ctrl+up/down arrow). If user type-in a
     * disable date, by default the controller will display an error message and
     * revert the value back. To change this behavior, override {@link LocalDateFieldController#onDisabledDateSelected(javafx.scene.control.DatePicker, java.time.LocalDate)
     * DateFieldController.onDisabledDateSelected}
     * @param dateFieldController
     */
    public void setController(LocalDateFieldController dateFieldController) {
        this.controllerProperty.set(dateFieldController);
    }
}
