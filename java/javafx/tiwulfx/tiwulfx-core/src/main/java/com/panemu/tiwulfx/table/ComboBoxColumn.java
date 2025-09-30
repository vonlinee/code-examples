package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TableCriteria.Condition;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class ComboBoxColumn<R, C> extends BaseColumn<R, C> {

    private final ObservableMap<String, C> itemMap = FXCollections.observableMap(new LinkedHashMap<>());
    private final ComboBox<C> searchInputControl = new ComboBox<>();
    private final SearchMenuItemBase<C> searchMenuItem = new SearchMenuItemBase<>(this) {
        @Override
        protected Node getInputControl() {
            return searchInputControl;
        }

        @Override
        protected List<Condition> getOperators() {
            List<Condition> lst = new ArrayList<>();
            lst.add(Condition.eq);
            lst.add(Condition.ne);
            lst.add(Condition.is_null);
            lst.add(Condition.is_not_null);
            return lst;
        }

        @Override
        protected C getValue() {
            return searchInputControl.getValue();
        }
    };

    public ComboBoxColumn() {
        this("");
    }

    public ComboBoxColumn(String propertyName) {
        this(propertyName, 100);
    }

    public ComboBoxColumn(String propertyName, double prefWidth) {
        super(propertyName, prefWidth);
        setCellFactory(param -> new ComboBoxTableCell<>(ComboBoxColumn.this));
        StringConverter<C> stringConverter = new StringConverter<>() {
            @Override
            public String toString(C object) {
                if (object == null) {
                    return getNullLabel();
                }
                Set<String> keys = itemMap.keySet();
                for (String label : keys) {
                    C obj = itemMap.get(label);
                    if (obj.equals(object)) {
                        return label;
                    }
                }
                return object.toString();
            }

            @Override
            public C fromString(String string) {
                if (string == null || string.equals(getNullLabel())) {
                    return null;
                }
                return itemMap.get(string);
            }
        };
        searchInputControl.setConverter(stringConverter);
        searchInputControl.setFocusTraversable(false);

        setStringConverter(stringConverter);

        itemMap.addListener((MapChangeListener<String, C>) change -> {
            if (change.wasAdded()) {
                searchInputControl.getItems().add(change.getValueAdded());
            } else if (change.wasRemoved()) {
                searchInputControl.getItems().remove(change.getValueAdded());
            }
        });
    }

    @Override
    MenuItem getSearchMenuItem() {
        if (getDefaultSearchValue() != null) {
            searchInputControl.setValue(getDefaultSearchValue());
        }
        return searchMenuItem;
    }

    /**
     * Add a pair of label and object corresponding to the label
     * @param label
     * @param object
     */
    public void addItem(String label, C object) {
        itemMap.put(label, object);
    }

    /**
     * Remove an item from typeahead popup list. It has the opposite effect of
     * {@link #addItem(java.lang.String, java.lang.Object) addItem}
     * @param label
     */
    public void removeItem(String label) {
        searchInputControl.getItems().remove(itemMap.get(label));
    }

    /**
     * Clear typeahead popup list
     */
    public void clearItems() {
        searchInputControl.getItems().clear();
    }

    /**
     * Get ObservableMap (LinkedHashMap) of typeahead label-value pair popup list
     * @return
     */
    public ObservableMap<String, C> getItemMap() {
        return itemMap;
    }
}
