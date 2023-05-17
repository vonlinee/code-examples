package com.panemu.tiwulfx.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CustomTableView<R> extends TableView<R> {

    private TableColumn<R, ?> selectedColumn;

    @SuppressWarnings("unchecked")
    public <C> TableColumn<R, C> getSelectedColumn() {
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
}
