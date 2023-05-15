package com.panemu.tiwulfx.table;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;

public class TableControlSelectionModel<R> extends TableView.TableViewSelectionModel<R> {

    TableView.TableViewSelectionModel<R> selectionModel;

    /**
     * Builds a default TableViewSelectionModel instance with the provided
     * TableView.
     * @param tableView The TableView upon which this selection model should
     *                  operate.
     * @throws NullPointerException TableView can not be null.
     */
    TableControlSelectionModel(TableView<R> tableView) {
        super(tableView);
    }

    @Override
    public ObservableList<TablePosition> getSelectedCells() {
        return selectionModel.getSelectedCells();
    }

    @Override
    public boolean isSelected(int row, TableColumn<R, ?> column) {
        return selectionModel.isSelected(row, column);
    }

    @Override
    public void select(int row, TableColumn<R, ?> column) {
        selectionModel.select(row, column);
    }

    @Override
    public void clearAndSelect(int row, TableColumn<R, ?> column) {
        selectionModel.clearAndSelect(row, column);
    }

    @Override
    public void clearSelection(int row, TableColumn<R, ?> column) {
        selectionModel.clearSelection(row, column);
    }

    @Override
    public void selectLeftCell() {
        selectionModel.selectLeftCell();
    }

    @Override
    public void selectRightCell() {
        selectionModel.selectRightCell();
    }

    @Override
    public void selectAboveCell() {
        selectionModel.selectAboveCell();
    }

    @Override
    public void selectBelowCell() {
        selectionModel.selectBelowCell();
    }
}
