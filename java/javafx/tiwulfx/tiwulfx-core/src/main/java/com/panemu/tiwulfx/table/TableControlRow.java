package com.panemu.tiwulfx.table;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * the row of TableControl
 * @param <R> 行数据模型
 * @see TableControl
 */
public class TableControlRow<R> extends TableRow<R> {

    private final TableControl<R> tblView;

    public TableControlRow(final TableControl<R> tblView) {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        this.tblView = tblView;

        setOnScrollStarted(event -> {
            
        });
    }

    @SuppressWarnings("unchecked")
    public void refreshLookupSiblings(String propertyName) {
        for (Node node : getChildrenUnmodifiable()) {
            TableCell<R, Object> cell = (TableCell<R, Object>) node;
            if (cell.getTableColumn() instanceof BaseColumn<R, Object> baseColumn) {
                if (baseColumn.getPropertyName().startsWith(propertyName) && !baseColumn.getPropertyName()
                        .equals(propertyName)) {
                    ObservableValue<Object> currentObservableValue = baseColumn.getCellObservableValue(cell.getIndex());
                    if (currentObservableValue == null) {
                        continue;
                    }
                    if (cell instanceof BaseCell<R, ?>) {
                        Object value = currentObservableValue.getValue();
                        ((BaseCell<R, Object>) cell).updateItem(value, false);
                    }
                }
            }
        }
    }

    /**
     * Refresh the value of every cell with values taken from Row's object.
     */
    public void refresh() {
        for (Node node : getChildrenUnmodifiable()) {
            if (node instanceof TableCell) {
                @SuppressWarnings("unchecked")
                TableCell<R, Object> cell = (TableCell<R, Object>) node;
                if (cell.getTableColumn() == null) {
                    continue;
                }
                TableColumn<R, Object> baseColumn = cell.getTableColumn();
                ObservableValue<Object> currentObservableValue = baseColumn.getCellObservableValue(cell.getIndex());
                if (currentObservableValue == null) {
                    continue;
                }
                if (cell instanceof BaseCell) {
                    ((BaseCell<R, Object>) cell).updateItem(currentObservableValue.getValue(), false);
                } else {
                    try {
                        Method m = cell.getClass().getDeclaredMethod("updateItem", Object.class, boolean.class);
                        m.setAccessible(true);
                        m.invoke(cell, currentObservableValue.getValue(), false);
                    } catch (Exception ex) {
                        Logger.getLogger(TableControlRow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
