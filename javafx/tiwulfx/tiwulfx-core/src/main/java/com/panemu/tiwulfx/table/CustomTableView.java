package com.panemu.tiwulfx.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.Nullable;

/**
 * 封装JavaFX TableView
 * @param <R>
 */
@SuppressWarnings("unchecked")
public class CustomTableView<R> extends TableView<R> {

    private TableColumn<R, ?> selectedColumn;

    public final <C> TableColumn<R, C> getSelectedColumn() {
        return (TableColumn<R, C>) selectedColumn;
    }

    public <C> void setSelectedColumn(TableColumn<R, C> selectedColumn) {
        this.selectedColumn = selectedColumn;
    }

    /**
     * 不通过getColumns()方法进行添加列，不使用TableColumn而是使用CustomTableColumn
     * @param column 列
     * @param <C>    列数据类型
     */
    public final <C> void addColumn(CustomTableColumn<R, C> column) {
        getColumns().add(column);
    }

    /**
     * 避免直接使用原javafx table api
     * @param <C> 列数据类型
     * @return 表的所有列，每次都产生一个新的集合
     */
    @SuppressWarnings("unchecked")
    public final <C> ObservableList<CustomTableColumn<R, C>> getTableColumns() {
        ObservableList<CustomTableColumn<R, C>> columns = FXCollections.observableArrayList();
        for (TableColumn<R, ?> column : getColumns()) {
            columns.add((CustomTableColumn<R, C>) column);
        }
        return columns;
    }

    public final int getSelectedIndex() {
        return getSelectionModel().getSelectedIndex();
    }

    public final void clearSelection() {
        getSelectionModel().clearSelection();
    }

    public final <C> void edit(TablePosition<R, C> newValue) {
        this.edit(newValue.getRow(), newValue.getTableColumn());
    }

    public final boolean hasSelectedCells() {
        return !getSelectionModel().getSelectedCells().isEmpty();
    }

    @Nullable
    public final <C> TablePosition<R, C> getSelectedPosition(int index) {
        return getSelectionModel().getSelectedCells().get(index);
    }

    /**
     * 选择某个单元格
     * @param row 行
     * @param col 列
     */
    public final void select(int row, int col) {
        this.getSelectionModel().select(row, getColumns().get(col));
    }
}
