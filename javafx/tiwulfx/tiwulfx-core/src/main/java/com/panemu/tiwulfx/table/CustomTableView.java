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

    /**
     * 得到选择列
     * @param index 索引
     * @return {@link TableColumn}<{@link R}, {@link C}>
     */
    public final <C> TableColumn<R, C> getSelectedColumn(int index) {
        return getSelectionModel().getSelectedCells().get(index).getTableColumn();
    }

    /**
     * 得到获得焦点的列
     * @return {@link TableColumn}<{@link R}, {@link C}>
     */
    public final <C> TableColumn<R, C> getFocusedColumn() {
        return getFocusModel().getFocusedCell().getTableColumn();
    }

    /**
     * 是否存在选择的单元格
     * @return boolean 是否存在选择的单元格
     */
    public final boolean hasSelectedCells() {
        return !getSelectionModel().getSelectedCells().isEmpty();
    }

    /**
     * 选择某个单元格
     * @param row    行
     * @param column 列对象
     * @param <C>    列数据类型
     */
    public final <C> void selectCell(int row, TableColumn<R, C> column) {
        getSelectionModel().select(row, column);
    }

    /**
     * 选择某个单元格
     * @param position 单元格位置
     */
    public final void selectCell(TablePosition position) {
        getSelectionModel().select(position.getRow(), position.getTableColumn());
    }
}
