package com.panemu.tiwulfx.table;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;

public class ButtonColumn<R> extends CustomTableColumn<R, String> {

    public ButtonColumn(String propertyName) {
        this(propertyName, 100);
    }

    public ButtonColumn(String propertyName, double preferredWidth) {
        super(propertyName, preferredWidth);
        setCellFactory(p -> new ButtonCell());
        setStringConverter(new StringConverter<>() {
            @Override
            public String toString(String t) {
                if (t == null) {
                    return getNullLabel();
                }
                return t;
            }

            @Override
            public String fromString(String string) {
                if (string == null || string.equals(getNullLabel())) {
                    return null;
                }
                return string;
            }
        });
    }

    private ButtonColumnController<R> helper;

    public ButtonColumnController<R> getHelper() {
        return helper;
    }

    public void setHelper(ButtonColumnController<R> helper) {
        this.helper = helper;
    }

    private class ButtonCell extends TableCell<R, String> {

        private final Button button = new Button();

        public ButtonCell() {
            super();
            setGraphic(button);
            button.setAlignment(Pos.CENTER);
            setText(null);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            button.setMaxWidth(Double.MAX_VALUE);
            helper.initButton(button, this);
            contentDisplayProperty().addListener(new ChangeListener<ContentDisplay>() {
                private boolean suspendEvent = false;

                @Override
                public void changed(ObservableValue<? extends ContentDisplay> observable, ContentDisplay oldValue, ContentDisplay newValue) {
                    if (suspendEvent) {
                        return;
                    }
                    if (newValue != ContentDisplay.GRAPHIC_ONLY) {
                        suspendEvent = true;
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        suspendEvent = false;
                    }
                }
            });

        }

        @Override
        protected void updateItem(String item, boolean empty) {
            boolean emptyRow = getTableView().getItems().size() < getIndex() + 1;
            super.updateItem(item, empty && emptyRow);
            if (getTableRow() != null && getTableRow().getItem() != null && !emptyRow) {
                setGraphic(button);
                helper.redrawButton(button, (R) getTableRow().getItem(), item);
            } else {
                setGraphic(null);
            }
        }
    }
}
