package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.utils.EventUtils;
import javafx.application.Platform;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.css.PseudoClass;
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
 * @param <R> 行数据模型
 * @see TableControl
 */
public class TableControlRow<R> extends TableRow<R> {

    private boolean selected;
    private final TableControl<R> tblView;
    private static final PseudoClass PSEUDO_CLASS_CHANGED = PseudoClass.getPseudoClass("changed");

    public TableControlRow(final TableControl<R> tblView) {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        this.tblView = tblView;
        attachScrollListener();
        this.setOnMouseClicked(event -> {
            if (EventUtils.isPrimaryKeyDoubleClicked(event) && !isEmpty()) {
                // Table未显示的行为空，即空白行
                tblView.getBehavior().doubleClick(tblView.getSelectionModel().getSelectedItem());
            }
        });
        /*
         * Set content display to TEXT_ONLY in READ mode. This listener is relevant
         * only in agileEditing mode
         */
        tblView.modeProperty().addListener(new WeakChangeListener<>((observable, oldValue, newValue) -> {
            if (newValue == TableControl.Mode.READ && selected) {
                selected = false;
                setChildrenCellContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }));

        /*
         * Flag the row whether it is editable or not.
         * In Agile mode, display cell editors in selected row, if the record is editable.
         */
        tblView.getSelectionModel().selectedIndexProperty()
                .addListener(new WeakChangeListener<>((ov, t, t1) -> {
                    int idx = getIndex();
                    if (!tblView.isEditable() || (!t.equals(idx) && !t1.equals(idx))) {
                        return; //nothing to do with me (I am a TableRowControl instance)
                    }
                    /*
                     * Set the row not editable if the record is not editable. This is
                     * just flag. The actual logic is in BaseCell.startEdit. The
                     * tableRowControl.isEditable is called by BaseCell.startEdit to
                     * decide whether it is okay to edit the cell or not.
                     */
                    boolean isRecordEditable = true;
                    System.out.println(selected);
                    if (!selected) {
                        isRecordEditable = tblView.isRecordEditable(getItem());
                        TableControlRow.this.setEditable(isRecordEditable);
                    }
                    if (tblView.isAgileEditing()) {
                        System.out.println("快速编辑状态");
                        if (t.equals(getIndex()) && selected) {
                            setChildrenCellContentDisplay(ContentDisplay.TEXT_ONLY);
                            selected = false;
                        } else if (t1.equals(getIndex()) && !selected) {
                            if (isRecordEditable) {
                                setChildrenCellContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                            }
                            selected = true;
                        }
                    }
                }));
        /*
         * If in normalEditing mode (agileEditing == false), when a particular cell
         * is no longer being edited, the content display should change to TEXT_ONLY
         */
        tblView.editingCellProperty().addListener(new WeakChangeListener<>((observable, oldValue, newValue) -> {
            if ((newValue == null || newValue.getRow() == -1) && !tblView.isAgileEditing()) {
                setChildrenCellContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }));
        tblView.agileEditingProperty().addListener(new WeakChangeListener<>((observable, oldValue, newValue) -> {
            if (tblView.isReadMode()) {
                return;
            }
            if (tblView.getSelectionModel().getSelectedIndex() == getIndex()) {
                if (newValue) {
                    selected = true;
                    if (tblView.isRecordEditable(getItem())) {
                        setChildrenCellContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                } else {
                    selected = false;
                    setChildrenCellContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }));

        tblView.getChangedRecords()
                .addListener(new WeakInvalidationListener(observable -> pseudoClassStateChanged(PSEUDO_CLASS_CHANGED, tblView
                        .getChangedRecords().contains(TableControlRow.this.getItem()))));
    }

    /**
     * Handle row index change caused by scrolling the table. If the table is
     * scrolled, the same TableRow object is reused but the position and the
     * item ara changed. This listener change the display content of the row to
     * prevent displaying cell editor controls in the wrong index.
     */
    private void attachScrollListener() {
        this.itemProperty().addListener((ObservableValue<? extends R> observable, R oldValue, R newValue) -> {
            pseudoClassStateChanged(PSEUDO_CLASS_CHANGED, tblView.getChangedRecords()
                    .contains(TableControlRow.this.getItem()));
            if (tblView.isReadMode() || !tblView.isAgileEditing()) {
                return;
            }
            // The previously selected row might have different row Index
            // after it is scrolled back.
            if (selected) {
                selected = newValue == tblView.getSelectedItem();
                if (selected && tblView.isRecordEditable(getItem())) {
                    setChildrenCellContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setChildrenCellContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            } else if (newValue == tblView.getSelectionModel().getSelectedItem()) {
                selected = true;
                if (tblView.isRecordEditable(getItem())) {
                    setChildrenCellContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            }
        });
    }

    private void setChildrenCellContentDisplay(ContentDisplay contentDisplay) {
        if (this.getChildrenUnmodifiable() == null || this.getChildrenUnmodifiable().size() == 0) {
            Platform.runLater(() -> setChildrenCellContentDisplay(contentDisplay));
        } else {
            for (Node node : getChildrenUnmodifiable()) {
                @SuppressWarnings("unchecked")
                TableCell<R, Object> cell = (TableCell<R, Object>) node;
                if (cell.getTableColumn().isEditable() || cell.getContentDisplay() == ContentDisplay.GRAPHIC_ONLY) {
                    cell.setContentDisplay(contentDisplay);
                }
            }
        }
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
