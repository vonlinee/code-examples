package com.panemu.tiwulfx.table;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.Objects;

/**
 * TableView工具类
 * JavaFX 的 TableView API使用过程中很多地方使用类型强制转换，使用此工具类来集中这些转换操作
 */
@SuppressWarnings(value = {"rawtypes", "unchecked"})
public class TableViewHelper {

    public static <S> void edit(TableView<S> tableView, int row, TableColumn column) {
        tableView.edit(row, column);
    }

    /**
     * TableView.TableViewFocusModel.focusedCellProperty()
     */
    public static void edit(TablePosition position) {
        position.getTableView().edit(position.getRow(), position.getTableColumn());
    }

    /**
     * 获取获得焦点的列
     * @param tableView TableView
     * @param <S>       行数据类型
     * @param <T>       列数据类型
     * @return TableColumn
     */
    public static <S, T> TableColumn<S, T> getFocusedColumn(TableView<S> tableView) {
        TableView.TableViewFocusModel<S> focusModel = tableView.getFocusModel();
        if (focusModel == null) {
            return null;
        }
        TablePosition<S, T> focusedCell = (TablePosition<S, T>) focusModel.getFocusedCell();
        if (focusedCell == null) {
            return null;
        }
        return focusedCell.getTableColumn();
    }

    public static <S, T> TableColumn<S, T> getSelectedColumn(TableView<S> tblView, int index) {
        return tblView.getSelectionModel().getSelectedCells().get(index).getTableColumn();
    }

    public static <S, T> TablePosition<S, T> getSelectedPosition(TableView<S> tblView, int index) {
        return tblView.getSelectionModel().getSelectedCells().get(index);
    }

    public static <R> R getSelectedItem(TableView<R> tblView) {
        return tblView.getSelectionModel().getSelectedItem();
    }

    public static <R, T> TableCell<R, T> callCellFactory(TableColumn<R, ?> column) {
        Callback cellFactory = column.getCellFactory();
        if (cellFactory == null) {
            return null;
        }
        return (TableCell<R, T>) cellFactory.call(column);
    }

    public static <S> boolean hasSelectedCells(TableView<S> tableView) {
        return !tableView.getSelectionModel().getSelectedCells().isEmpty();
    }

    /**
     * 获取TablePosition
     * @param index 索引
     * @param <T>   单元格数据类型
     * @return TablePosition
     */
    public static <S, T> TablePosition<S, T> getSelectedTablePosition(TableView<S> tableView, int index) {
        return tableView.getSelectionModel().getSelectedCells().get(index);
    }

    /**
     * @see TableView#getSelectionModel() getSelectionItems()
     */
    public static <R> ObservableList<R> getSelectedItems(TableView<R> tableView) {
        return tableView.getSelectionModel().getSelectedItems();
    }

    /**
     * 单元格的值的值是否被更新
     * @param cellEditEvent 单元格编辑事件
     * @param <R>           行数据类型
     * @param <C>           列数据类型
     * @return 单元格的值是否更改
     */
    public static <R, C> boolean isCellValueUpdated(TableColumn.CellEditEvent<R, C> cellEditEvent) {
        C oldValue = cellEditEvent.getOldValue();
        C newValue = cellEditEvent.getNewValue();
        if (oldValue == null && newValue == null) {
            return false;
        }
        return !Objects.equals(oldValue, newValue);
    }

    /**
     * 单元格编辑位置是否有效
     * 测试是否会出现返回false的情况
     * @param t   CellEditEvent
     * @param <R> 行数据类型
     * @param <C> 列数据类型
     * @return 单元格编辑位置是否有效
     */
    public static <R, C> boolean isCellEditPositionValid(TableColumn.CellEditEvent<R, C> t) {
        if (t.getTablePosition() == null || t.getTableView() == null) {
            return false;
        }
        return t.getTablePosition().getRow() < t.getTableView().getItems().size();
    }
}