package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.control.LookupFieldController;
import com.panemu.tiwulfx.utils.ClassUtils;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LookupColumn<R, C> extends BaseColumn<R, C> {

    private TextField searchInputControl;
    private SearchMenuItemBase<C> searchMenuItem;
    private String lookupPropertyName;
    private LookupFieldController<C> lookupController;
    private boolean disableLookupManualInput = false;

    public LookupColumn() {
        this("", "");
    }

    public LookupColumn(String propertyName, String lookupPropertyName) {
        this(propertyName, lookupPropertyName, 100);
    }

    public LookupColumn(String propertyName, String lookupPropertyName, double prefWidth) {
        super(propertyName, prefWidth);
        this.lookupPropertyName = lookupPropertyName;
        setCellFactory(p -> new LookupTableCell<>(LookupColumn.this));
        setStringConverter(stringConverter);
    }

    public final String getLookupPropertyName() {
        return lookupPropertyName;
    }

    public final void setLookupPropertyName(String lookupPropertyName) {
        this.lookupPropertyName = lookupPropertyName;
    }

    @Override
    MenuItem getSearchMenuItem() {
        return getLookupMenuItem();
    }

    public LookupFieldController<C> getLookupController() {
        return lookupController;
    }

    public void setLookupController(LookupFieldController<C> lookupController) {
        this.lookupController = lookupController;
    }

    private final Map<String, C> mapValue = new HashMap<>();
    private final StringConverter<C> stringConverter = new StringConverter<C>() {
        @Override
        public String toString(C value) {
            Object string;
            try {
                string = ClassUtils.getSimpleProperty(value, lookupPropertyName);
            } catch (Exception ex) {
                return null;
            }
            return string != null ? string.toString() : getNullLabel();
        }

        @Override
        public C fromString(String stringValue) {
            if (stringValue == null || stringValue.equals(getNullLabel())) {
                return null;
            }
            C result = mapValue.get(stringValue);
            if (result == null && !stringValue.isEmpty()) {
                List<C> data = lookupController.loadDataForPopup(getLookupPropertyName(), stringValue, TableCriteria.Condition.eq);
                if (data.size() == 1) {
                    result = data.get(0);
                    mapValue.put(stringValue, result);
                } else {
                    result = getLookupController().show(getTableView().getScene()
                            .getWindow(), null, getLookupPropertyName(), stringValue);
                }
            }
            return result;
        }
    };

    private SearchMenuItemBase<C> getLookupMenuItem() {
        if (searchMenuItem == null) {
            searchInputControl = new TextField();
            searchMenuItem = new SearchMenuItemBase<>(this) {
                @Override
                protected Node getInputControl() {
                    return searchInputControl;
                }

                @Override
                protected List<TableCriteria.Condition> getOperators() {
                    List<TableCriteria.Condition> lst = new ArrayList<>();
                    lst.add(TableCriteria.Condition.eq);
                    lst.add(TableCriteria.Condition.ne);
                    lst.add(TableCriteria.Condition.ilike_begin);
                    lst.add(TableCriteria.Condition.ilike_anywhere);
                    lst.add(TableCriteria.Condition.ilike_end);
                    lst.add(TableCriteria.Condition.lt);
                    lst.add(TableCriteria.Condition.le);
                    lst.add(TableCriteria.Condition.gt);
                    lst.add(TableCriteria.Condition.ge);
                    lst.add(TableCriteria.Condition.is_null);
                    lst.add(TableCriteria.Condition.is_not_null);
                    return lst;
                }

                @Override
                @SuppressWarnings("unchecked")
                protected C getValue() {
                    return (C) searchInputControl.getText();
                }
            };
        }
        C defaultSearch = getDefaultSearchValue();
        if (defaultSearch != null) {
            searchInputControl.setText(stringConverter.toString(defaultSearch));
        }
        return searchMenuItem;
    }

    private int waitTime = TiwulFXUtil.DEFAULT_LOOKUP_SUGGESTION_WAIT_TIMES;

    /**
     * Set wait time in millisecond for showing suggestion list. Set it to -1 to
     * disable suggestion list feature.
     * @param waitTime Default is 500 millisecond
     */
    public final void setShowSuggestionWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    TableCriteria<C> createSearchCriteria(TableCriteria.Condition operator, C value) {
        return new TableCriteria<>(this.getPropertyName() + "." + lookupPropertyName, operator, value);
    }

    public int getShowSuggestionWaitTime() {
        return this.waitTime;
    }

    /**
     * Set it TRUE to restrict input only from lookupWindow and disable user
     * manual input.
     * @param disableLookupManualInput disableLookupManualInput
     */
    public void setDisableLookupManualInput(boolean disableLookupManualInput) {
        this.disableLookupManualInput = disableLookupManualInput;
    }

    public boolean getDisableLookupManualInput() {
        return disableLookupManualInput;
    }
}
