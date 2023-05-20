package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.utils.EventUtils;
import javafx.application.Platform;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;

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

    /**
     * row changed
     */
    private static final PseudoClass PSEUDO_CLASS_CHANGED = PseudoClass.getPseudoClass("changed");
    private final Logger logger = Logger.getLogger(TableRow.class.getName());

    public TableControlRow(final TableControl<R> tableControl) {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        this.tableControl = tableControl;
        attachScrollListener();
        this.setOnMouseClicked(event -> {
            if (EventUtils.isPrimaryDoubleClikced(event) && TableControlRow.this.getIndex() < tableControl
                    .getRecords().size()) {
                tableControl.getBehaviour().doubleClick(tableControl.getSelectedItem());
            }
        });
        /**
         * Set content display to TEXT_ONLY in READ mode. This listener is relevant
         * only in agileEditing mode
         */
        tableControl.modeProperty().addListener(new WeakChangeListener<>((observable, oldValue, newValue) -> {
            if (newValue == TableControl.OperationMode.READ && selected) {
                selected = false;
                setCellContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }));
        tableControl.getSelectionModel().selectedIndexProperty()
                .addListener(new WeakChangeListener<>(selectionChangeListener));
        tableControl.editingCellProperty().addListener(new WeakChangeListener<>(editingCellListener));
        tableControl.agileEditingProperty().addListener(new WeakChangeListener<>(agileChangeListener));
        tableControl.getChangedRecords()
                .addListener(new WeakInvalidationListener(observable -> pseudoClassStateChanged(PSEUDO_CLASS_CHANGED, tableControl
                        .getChangedRecords()
                        .contains(TableControlRow.this.getItem()))));
    }

    /**
     * 改行的数据实体是否发生更改
     * @return 是否发生更改
     */
    public final boolean isRecordChanged() {
        return tableControl.getChangedRecords().contains(TableControlRow.this.getItem());
    }

    /**
     * handle row index change caused by scrolling the table. If the table is
     * scrolled, the same TableRow object is reused but the position and the
     * item ara changed. This listener change the display content of the row to
     * prevent displaying cell editor controls in the wrong index.
     */
    private void attachScrollListener() {
        this.itemProperty().addListener((ObservableValue<? extends R> observable, R oldValue, R newValue) -> {
            pseudoClassStateChanged(PSEUDO_CLASS_CHANGED, isRecordChanged());
            if (tableControl.getMode() == TableControl.OperationMode.READ || !tableControl.isAgileEditing()) {
                return;
            }
            // 由于滚动，行索引发生变化，但是改行并没发生变化
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
     * 展示所有单元格的内容，单元格包含文本和一个Node
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

    /**
     * If in normal Editing mode (agileEditing == false), when a particular cell
     * is no longer being edited, the content display should change to TEXT_ONLY
     */
    private ChangeListener<TablePosition<R, ?>> editingCellListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends TablePosition<R, ?>> observable, TablePosition<R, ?> oldValue, TablePosition<R, ?> newValue) {
            if ((newValue == null || newValue.getRow() == -1) && !tableControl.isAgileEditing()) {
                setCellContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    };

    /**
     * Flag the row whether it is editable or not.
     * In Agile mode, display cell editors in selected row, if the record is editable.
     */
    private ChangeListener<Number> selectionChangeListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
            int idx = getIndex();
            if (!tableControl.getTableView().isEditable() || (!t.equals(idx) && !t1.equals(idx))) {
                return; //nothing to do with me (i am a TableRowControl instance)
            }
            /**
             * Set the row not editable if the record is not editable. This is
             * just flag. The actual logic is in BaseCell.startEdit. The
             * tableRowControl.isEditable is called by BaseCell.startEdit to
             * decide whether it is okay to edit the cell or not.
             */
            boolean isRecordEditable = true;
            if (!selected) {
                isRecordEditable = tableControl.isRecordEditable(getItem());
                TableControlRow.this.setEditable(isRecordEditable);
            }
            if (tableControl.isAgileEditing()) {
                if (t.equals(getIndex()) && selected) {
                    setCellContentDisplay(ContentDisplay.TEXT_ONLY);
                    selected = false;
                } else if (t1.equals(getIndex()) && !selected) {
                    if (isRecordEditable) {
                        setCellContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                    selected = true;
                }
            }
        }
    };

    private final ChangeListener<Boolean> agileChangeListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (tableControl.getMode() == TableControl.OperationMode.READ) {
                return;
            }
            if (tableControl.getSelectionModel().getSelectedIndex() == getIndex()) {
                if (newValue) {
                    selected = true;
                    if (tableControl.isRecordEditable(getItem())) {
                        setCellContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                } else {
                    selected = false;
                    setCellContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }
    };
}
