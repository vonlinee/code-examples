package code.fxutils.core.extension;

import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public abstract class RowFactory<T> implements Callback<TableView<T>, TableRow<T>> {

    @Override
    public TableRow<T> call(TableView<T> param) {
        TableRow<T> row = new TableRow<>();
        ObservableList<T> items = param.getItems();
        return row;
    }
}