package com.panemu.tiwulfx.form;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.SingleSelectionModel;
import javafx.util.StringConverter;

public class ComboBoxControl<R> extends BaseControl<R, ComboBox<R>> {

    private final HashMap<String, R> itemMap = new LinkedHashMap<>();
    private LabelConverter lblConverter = new LabelConverter();
    private final ComboBox<R> combobox;
    private final ListCell<R> buttoncell = new ListCell<>();

    public ComboBoxControl() {
        super(new ComboBox<R>());
        combobox = getInputComponent();
        /**
         * Workaround for
         * http://javafx-jira.kenai.com/browse/RT-24412?focusedCommentId=323719&page=com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel#comment-323719
         */
        combobox.setButtonCell(buttoncell);
        combobox.valueProperty().addListener((observable, oldValue, newValue) -> buttoncell.setText(getLabel(newValue)));
        
        combobox.setConverter(lblConverter);
        if (!isRequired()) {
            combobox.getItems().add(null);
        }
        requiredProperty().addListener((ov, t, t1) -> {
            if (t1) {
                combobox.getItems().remove(null);
            } else {
                combobox.getItems().add(0, null);
            }
        });
		
		getItems().addListener((ListChangeListener<R>) change -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    for (R t : change.getRemoved()){
                        for (String key : itemMap.keySet()) {
                            if (t!= null && t.equals(itemMap.get(key))) {
                                itemMap.remove(key);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    private String getLabel(R object) {
        if (object == null) {
            return "";
        }
        Set<String> keys = itemMap.keySet();
        for (String label : keys) {
            R obj = (R) itemMap.get(label);
            if (obj.equals(object)) {
                return label;
            }
        }
        return null;
    }

    @Override
    public void setValue(final R value) {
        combobox.setValue(value);
    }

    @Override
    protected void bindValuePropertyWithControl(ComboBox<R> inputControl) {
        value.bind(inputControl.valueProperty());
    }

    public class LabelConverter extends StringConverter<R> {

        @Override
        public String toString(Object value) {
            return getLabel((R) value);
        }

        @Override
        public R fromString(String value) {
            return itemMap.get(value);
        }
    }

    public void addItem(String Label, R value) {
        itemMap.put(Label, value);
        combobox.getItems().add(value);
    }

    public final ObservableList<R> getItems() {
        return combobox.getItems();
    }

    public final void setOnAction(EventHandler<ActionEvent> eh) {
        combobox.setOnAction(eh);
    }

    public final SingleSelectionModel<R> getSelectionModel() {
        return combobox.getSelectionModel();
    }

    public BooleanProperty editableComboBoxProperty() {
        return combobox.editableProperty();
    }

    public void setEditableComboBox(boolean editable) {
        combobox.setEditable(editable);
    }

    public boolean isEditableCombobox() {
        return combobox.isEditable();
    }
	
	public String getPromptText() {
		return combobox.getPromptText();
	}

	public final StringProperty promptTextProperty() {
		return combobox.promptTextProperty();
	}

	public final void setPromptText(String string) {
		combobox.setPromptText(string);
	}
}
