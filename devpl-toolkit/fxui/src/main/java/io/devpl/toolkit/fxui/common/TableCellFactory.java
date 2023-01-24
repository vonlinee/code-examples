package io.devpl.toolkit.fxui.common;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public interface TableCellFactory<S, T> extends Callback<TableColumn<S, T>, TableCell<S, T>> {

    @Override
    default TableCell<S, T> call(TableColumn<S, T> param) {
        return createTableCell(param);
    }

    TableCell<S, T> createTableCell(TableColumn<S, T> param);
}
