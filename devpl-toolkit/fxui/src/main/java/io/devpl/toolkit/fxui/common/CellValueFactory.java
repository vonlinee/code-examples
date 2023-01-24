package io.devpl.toolkit.fxui.common;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public interface CellValueFactory<S, T> extends Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {
}
