package com.panemu.tiwulfx.table;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

class CustomTableView<R> extends TableView<R> {

    private TableColumn<R, ?> selectedColumn;

    @SuppressWarnings("unchecked")
    public <C> TableColumn<R, C> getSelectedColumn() {
        return (TableColumn<R, C>) selectedColumn;
    }

    public <C> void setSelectedColumn(TableColumn<R, C> selectedColumn) {
        this.selectedColumn = selectedColumn;
    }
}
