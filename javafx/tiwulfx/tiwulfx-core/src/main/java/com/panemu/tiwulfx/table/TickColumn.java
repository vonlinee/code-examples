/*
 * License GNU LGPL
 * Copyright (C) 2013 Amrullah .
 */
package com.panemu.tiwulfx.table;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

/**
 * CheckBoxColumn
 * @author Amrullah
 */
public class TickColumn<R> extends TableColumn<R, Boolean> {

    private ReadOnlyListWrapper<R> tickedRecords = new ReadOnlyListWrapper<R>(FXCollections.<R>observableArrayList());
    private CheckBox chkHeader = new CheckBox();

    public TickColumn() {
        super();
        setSortable(false);
        setGraphic(chkHeader);
        chkHeader.setSelected(defaultTicked.get());
        defaultTicked.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                chkHeader.setSelected(newValue);
            }
        });
        setText(null);
        setCellFactory((TableColumn<R, Boolean> param) -> new TickCell());
        setCellValueFactory((CellDataFeatures<R, Boolean> param) -> {
            boolean val = tickedRecords.contains(param.getValue());
            return new SimpleBooleanProperty(val);
        });

        tableViewProperty().addListener((ObservableValue<? extends TableView<R>> observable, TableView<R> oldValue, TableView<R> newValue) -> {
            if (newValue != null) {
                /**
                 * The content of tickedRecords + untickedRecords should always
                 * equal with TableView's items
                 */
                getTableView().getItems().addListener((Change<? extends R> change) -> {
                    while (change.next()) {
                        if (change.wasRemoved()) {
                            tickedRecords.removeAll(change.getRemoved());
                        } else if (change.wasAdded()) {
                            if (defaultTicked.get()) {
                                change.getAddedSubList().forEach(item -> {
                                    if (!tickedRecords.contains(item)) {
                                        tickedRecords.add(item);
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });

        chkHeader.setOnAction((ActionEvent event) -> {
            if (chkHeader.isSelected()) {
                tickedRecords.setAll(getTableView().getItems());
            } else {
                tickedRecords.clear();
            }
            getTableView().refresh();
        });
        chkHeader.disableProperty().bind(this.editableProperty().not());
    }

    private BooleanProperty defaultTicked = new SimpleBooleanProperty(false);

    public boolean isDefaultTicked() {
        return defaultTicked.get();
    }

    /**
     * Sets whether the row is by default ticked or not
     * @param ticked default false
     */
    public void setDefaultTicked(boolean ticked) {
        defaultTicked.set(ticked);
    }

    /**
     * Gets property of defaultTicked
     * @return
     */
    public BooleanProperty defaultTickedProperty() {
        return defaultTicked;
    }

    private void setHeaderSelected(boolean selected) {
        chkHeader.setSelected(selected);
    }

    /**
     * Check if passed item is ticked
     * @param item
     * @return
     */
    public Boolean isTicked(R item) {
        if (tickedRecords.contains(item)) {
            return true;
        }
        return false;
    }

    /**
     * Set passed item to be ticked or unticked
     * @param item
     * @param value
     */
    public void setTicked(R item, boolean value) {
        if (value) {
            if (!tickedRecords.contains(item) && getTableView().getItems().contains(item)) {
                tickedRecords.add(item);
            }
        } else {
            tickedRecords.remove(item);
        }
    }

    /**
     * Gets tickedRecords property.
     * @return
     */
    public ReadOnlyListProperty<R> tickedRecordsProperty() {
        return tickedRecords.getReadOnlyProperty();
    }

    public List<R> getTickedRecords() {
        final List<R> lst = new ArrayList<>(tickedRecords);
        return lst;
    }

    public List<R> getUntickedRecords() {
        List<R> lst = new ArrayList<>(getTableView().getItems());
        lst.removeAll(tickedRecords.get());
        return lst;
    }

    private class TickCell extends TableCell<R, Boolean> {

        private CheckBox checkbox = new CheckBox();

        public TickCell() {
            super();
            checkbox.setDisable(!TickColumn.this.isEditable());
            TickColumn.this.editableProperty().addListener(new WeakChangeListener<>(editableListener));
            setGraphic(checkbox);
            setAlignment(Pos.BASELINE_CENTER);
            checkbox.setAlignment(Pos.CENTER);
            setText(null);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            checkbox.setMaxWidth(Double.MAX_VALUE);
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

            checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    setTicked((R) getTableRow().getItem(), newValue);
                    if (!newValue) {
                        setHeaderSelected(false);
                    } else {
                        setHeaderSelected(tickedRecords.size() == getTableView().getItems().size());
                    }
                }
            });
        }

        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty && getTableRow() != null && getTableRow().getItem() != null) {
                setGraphic(checkbox);
                if (getTableRow() != null) {
                    checkbox.setSelected(isTicked((R) getTableRow().getItem()));
                }
            } else {
                setGraphic(null);
            }
        }

        private ChangeListener<Boolean> editableListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue != null) {
                    checkbox.setDisable(!newValue);
                }
            }
        };
    }
}
