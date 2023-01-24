package io.devpl.toolkit.fxui.common;

import javafx.scene.control.TableColumn;

public class DefaultTableColumn<S, T> extends TableColumn<S, T> {

    public DefaultTableColumn(CellValueFactory<S, T> cellValueFactory, TableCellFactory<S, T> cellFactory) {
        this.setCellValueFactory(cellValueFactory);
        this.setCellFactory(cellFactory);
    }
}
