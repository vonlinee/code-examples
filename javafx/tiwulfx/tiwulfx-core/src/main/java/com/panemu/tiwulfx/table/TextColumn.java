/*
 * License GNU LGPL
 * Copyright (C) 2012 Amrullah .
 */
package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TableCriteria.Condition;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amrullah
 */
public class TextColumn<R> extends BaseColumn<R, String> {

    private final TextField searchInputControl = new TextField();
    private boolean emptyStringAsNull = TiwulFXUtil.DEFAULT_EMPTY_STRING_AS_NULL;
    private int maxLength = 0;
    private boolean capitalize = false;

    private final SearchMenuItemBase<String> searchMenuItem = new SearchMenuItemBase<String>(this) {
        @Override
        protected Node getInputControl() {
            searchInputControl.setPromptText("kata kunci");
            return searchInputControl;
        }

        @Override
        protected List<Condition> getOperators() {
            List<Condition> lst = new ArrayList<>();
            lst.add(Condition.eq);
            lst.add(Condition.ne);
            lst.add(Condition.ilike_begin);
            lst.add(Condition.ilike_anywhere);
            lst.add(Condition.ilike_end);
            lst.add(Condition.lt);
            lst.add(Condition.le);
            lst.add(Condition.gt);
            lst.add(Condition.ge);
            lst.add(Condition.is_null);
            lst.add(Condition.is_not_null);
            return lst;
        }

        @Override
        protected String getValue() {
            return searchInputControl.getText();
        }
    };

    public TextColumn() {
        this("");
    }

    public final boolean isEmptyStringAsNull() {
        return emptyStringAsNull;
    }

    public void setEmptyStringAsNull(boolean emptyStringAsNull) {
        this.emptyStringAsNull = emptyStringAsNull;
    }

    public TextColumn(String propertyName) {
        this(propertyName, 100);
    }

    public TextColumn(String propertyName, double preferredWidth) {
        super(propertyName, preferredWidth);
        setCellFactory(p -> new TextTableCell<R>(TextColumn.this));
        setStringConverter(stringConverter);
    }

    @Override
    MenuItem getSearchMenuItem() {
        if (getDefaultSearchValue() != null) {
            searchInputControl.setText(getDefaultSearchValue());
        }
        return searchMenuItem;
    }

    /**
     * Get max character that could be entered when editing a cell. If 0 then
     * there is no limitation.
     * @return max len
     */
    public final int getMaxLength() {
        return maxLength;
    }

    /**
     * Set maximum character that could be entered when editing a cell. Set it to
     * 0 to disable this limitation.
     * @param maxLength maxLength
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * @return true if the cell editor automatically convert entered text to capital
     * letters
     */
    public boolean isCapitalize() {
        return capitalize;
    }

    /**
     * Set whether text entered to a cell editor will be automatically converted to capital
     * letters.
     * @param capitalize set to true to make the text always capital.
     */
    public void setCapitalize(boolean capitalize) {
        this.capitalize = capitalize;
    }

    private StringConverter<String> stringConverter = new StringConverter<String>() {
        @Override
        public String toString(String t) {
            if (t == null || (isEmptyStringAsNull() && t.trim().isEmpty())) {
                return getNullLabel();
            }
            return t;
        }

        @Override
        public String fromString(String string) {
            if (string == null || string.equals(getNullLabel()) || (isEmptyStringAsNull() && string.isEmpty())) {
                return null;
            }
            return string;
        }
    };
}
