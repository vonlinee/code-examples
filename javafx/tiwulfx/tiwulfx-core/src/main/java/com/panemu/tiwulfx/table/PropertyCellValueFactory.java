package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.utils.ClassUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

@SuppressWarnings("unchecked")
public class PropertyCellValueFactory<S, T> implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> {

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
        // This code is adapted from {@link PropertyValueFactory#getCellDataReflectively(T)
        try {
            TableColumn<S, T> tableColumn = param.getTableColumn();
            /**
             * 兼容 {@link BaseColumn} 和 {@link TableColumn}
             */
            if (tableColumn instanceof BaseColumn<S, T> baseColumn) {
                return new SimpleObjectProperty<>(ClassUtils.getProperty(param.getValue(), baseColumn.getPropertyName()));
            } else {
                Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> cellValueFactory = tableColumn.getCellValueFactory();
                if (cellValueFactory == null) {
                    return (ObservableValue<T>) new SimpleObjectProperty<>("Unknown");
                }
                return cellValueFactory.call(param);
            }
        } catch (Exception ex) {
            /**
             * Ideally it catches org.apache.commons.beanutils.NestedNullException. However,
             * we need to import apachec bean utils library in FXML file
             * to be able to display it in Scene Builder. So, I decided
             * to catch Exception to avoid the import.
             */
            return new SimpleObjectProperty<>(null);
        }
    }
}
