package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.utils.EventUtils;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
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

    private boolean selected;
    private final TableControl<R> tableControl;

    public TableControlRow(final TableControl<R> tableControl) {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        this.tableControl = tableControl;
        /**
         * handle row index change caused by scrolling the table. If the table is
         * scrolled, the same TableRow object is reused but the position and the
         * item ara changed. This listener change the display content of the row to
         * prevent displaying cell editor controls in the wrong index.
         */
        this.itemProperty().addListener((ObservableValue<? extends R> observable, R oldValue, R newValue) -> {
            if (tableControl.isReadMode() || !tableControl.isAgileEditing()) {
                return;
            }
            // 由于滚动，行索引发生变化，但是该行并没发生变化
            // The previously selected row might have different row Index after it is scrolled back.
            if (selected) {
                selected = newValue == tableControl.getSelectionModel().getSelectedItem();
                if (selected && tableControl.isRecordEditable(getItem())) {
                    setCellContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setCellContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            } else if (newValue == tableControl.getSelectionModel().getSelectedItem()) {
                selected = true;
                if (tableControl.isRecordEditable(getItem())) {
                    setCellContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            }
        });
    }

    /**
     * 改行的数据实体是否发生更改
     * @return 是否发生更改
     */
    public final boolean isRecordChanged() {
        return tableControl.getChangedRecords().contains(TableControlRow.this.getItem());
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
