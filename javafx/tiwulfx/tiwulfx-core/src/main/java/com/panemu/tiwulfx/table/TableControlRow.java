package com.panemu.tiwulfx.table;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * the row of TableControl
 * @param <R> record class
 */
public class TableControlRow<R> extends TableRow<R> {

    private final TableControl<R> tableControl;

    public TableControlRow(final TableControl<R> tableControl) {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        this.tableControl = tableControl;
    }

    /**
     * 展示一行所有单元格的内容，单元格包含文本和一个Node
     * @param contentDisplay 内容展示
     */
    private void setCellContentDisplay(ContentDisplay contentDisplay) {
        if (this.getChildrenUnmodifiable().isEmpty()) {
            Platform.runLater(() -> setCellContentDisplay(contentDisplay));
        } else {
            for (Node node : getChildrenUnmodifiable()) {
                // 所有节点都是TableCell，注意不一定是CustomTableCell
                @SuppressWarnings("unchecked")
                TableCell<R, ?> cell = (TableCell<R, ?>) node;
                if (cell.getTableColumn().isEditable() || cell.getContentDisplay() == ContentDisplay.GRAPHIC_ONLY) {
                    // 触发CutomTableCell的contentDisplay监听
                    cell.setContentDisplay(contentDisplay);
                }
            }
        }
    }

    public void refreshLookupSiblings(String propertyName) {
        for (Node node : getChildrenUnmodifiable()) {
            TableCell cell = (TableCell) node;
            if (cell.getTableColumn() instanceof CustomTableColumn) {
                CustomTableColumn baseColumn = (CustomTableColumn) cell.getTableColumn();
                if (baseColumn.getPropertyName().startsWith(propertyName) && !baseColumn.getPropertyName()
                        .equals(propertyName)) {
                    ObservableValue currentObservableValue = baseColumn.getCellObservableValue(cell.getIndex());
                    ((CustomTableCell) cell).updateItem(currentObservableValue.getValue(), false);
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
                TableCell cell = (TableCell) node;
                if (cell.getTableColumn() == null) {
                    continue;
                }
                TableColumn baseColumn = (TableColumn) cell.getTableColumn();
                ObservableValue currentObservableValue = baseColumn.getCellObservableValue(cell.getIndex());
                if (currentObservableValue == null) {
                    continue;
                }
                if (cell instanceof CustomTableCell) {
                    ((CustomTableCell) cell).updateItem(currentObservableValue.getValue(), false);
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
