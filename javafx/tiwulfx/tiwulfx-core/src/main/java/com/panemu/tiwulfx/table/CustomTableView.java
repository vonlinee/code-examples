package com.panemu.tiwulfx.table;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;

/**
 * 自定义TableView，继承自TableView
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class CustomTableView<R> extends TableView<R> {

    private TableColumn<R, ?> selectedColumn;

    public CustomTableView() {
        getColumns().addListener((ListChangeListener<TableColumn<R, ?>>) c -> {
            while (c.next()) {
                if (c.wasReplaced()) {
                    ObservableList<? extends TableColumn<R, ?>> list = c.getList();
                    System.out.println(list.get(0).getText());
                }
            }
        });
    }

    public TableColumn<R, ?> getSelectedColumn() {
        return selectedColumn;
    }

    public void setSelectedColumn(TableColumn<R, ?> selectedColumn) {
        this.selectedColumn = selectedColumn;
    }

    public final R getSelectedItem() {
        return getSelectionModel().getSelectedItem();
    }

    public final void edit(TablePosition position) {
        this.edit(position.getRow(), position.getTableColumn());
    }

    public final <T> TablePosition<R, T> getSelectedCellPosition(int index) {
        return getSelectionModel().getSelectedCells().get(index);
    }

    public final <T> TableColumn<R, T> getColumn(int index) {
        return (TableColumn<R, T>) getColumns().get(index);
    }

    public final boolean hasSelectedCells() {
        return !getSelectionModel().getSelectedCells().isEmpty();
    }
}
