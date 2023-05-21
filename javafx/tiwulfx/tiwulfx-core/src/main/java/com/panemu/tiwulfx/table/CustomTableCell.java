package com.panemu.tiwulfx.table;

import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

/**
 * 单元格
 * @param <R> 行数据类型
 * @param <C> 列数据类型
 */
public abstract class CustomTableCell<R, C> extends TableCell<R, C> {

    private final Logger logger = Logger.getLogger(CustomTableCell.class.getName());

    /**
     * 编辑视图
     */
    private Node editView;
    private final StringConverter<C> stringConverter;
    private static final PseudoClass PSEUDO_CLASS_INVALID = PseudoClass.getPseudoClass("invalid");
    private final CustomTableColumn<R, C> column;
    private boolean focusListenerAttached = false;

    public CustomTableCell(final CustomTableColumn<R, C> column) {
        this.stringConverter = column.getStringConverter();
        contentDisplayProperty().addListener((observable, oldValue, newValue) -> {
            getEditableView();
            attachFocusListener();
            if (newValue == ContentDisplay.GRAPHIC_ONLY) {
                updateCellValue(getItem());
            }
        });

        this.setOnMouseExited(new CellMouseEventHandler());

        this.column = column;
        InvalidationListener invalidRecordListener = observable -> {
            pseudoClassStateChanged(PSEUDO_CLASS_INVALID, column.getInvalidRecordMap()
                    .containsKey(CustomTableCell.this.getTableRow().getItem()));
        };
        column.getInvalidRecordMap().addListener(new WeakInvalidationListener(invalidRecordListener));
        itemProperty().addListener(invalidRecordListener);
        setAlignment(column.getAlignment());
    }

    class CellMouseEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            CustomTableCell<?, ?> tableCell = (CustomTableCell<?, ?>) event.getSource();
            TableColumn<?, ?> clm = tableCell.getTableColumn();
            if (clm instanceof CustomTableColumn) {
                TableRow<?> tableRow = tableCell.getTableRow();
                CustomTableColumn column = (CustomTableColumn) clm;
                if (!column.isValid(tableRow.getItem())) {
                    CustomTableColumn<?, ?> baseColumn = (CustomTableColumn<?, ?>) clm;
                    PopupControl popup = baseColumn.getPopup(tableRow.getItem());
                    if (event.getEventType() == MouseEvent.MOUSE_MOVED && !popup.isShowing()) {
                        Point2D p = CustomTableCell.this.localToScene(0.0, 0.0);
                        tableCell.showPopup(popup, CustomTableCell.this, p);
                    } else if (event.getEventType() == MouseEvent.MOUSE_EXITED && popup.isShowing()) {
                        popup.hide();
                    }
                }
            }
        }
    }

    private void showPopup(PopupControl popup, Region owner, Point2D p) {
        Scene scene = owner.getScene();
        popup.show(owner, p.getX() + scene.getX() + scene.getWindow()
                .getX(), p.getY() + scene.getY() + scene.getWindow()
                .getY() + owner.getHeight() - 1);
    }

    /**
     * For the case of TypeAhead, Date and Lookup, the focusable control is the textfield, not the control itself.
     * This method is to be overridden by TypeAheadTableCell, DateTableCell and LookupTableCell
     * @return the Node of table cell
     */
    @SuppressWarnings("unchecked")
    protected <T extends Node> T getFocusableNode() {
        return (T) editView;
    }

    private void attachFocusListener() {
        if (focusListenerAttached) {
            return;
        }
        /**
         * Set cell mode to edit if the editor control receives focus. This is intended to deal with mouse click.
         * This way, commitEdit() will be called if the cell is no longer focused
         */
        final Node focusableControl = getFocusableNode();
        if (focusableControl != null) {
            focusableControl.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!CustomTableCell.this.isSelected() && newValue) {
                    getTableView().getSelectionModel().select(getIndex(), getTableColumn());
                }
                if (!isEditing() && newValue) {
                    programmaticallyEdited = true;
                    // 实现获取焦点时编辑单元格
                    getTableView().edit(getIndex(), getTableColumn());
                    programmaticallyEdited = false;
                }
            });
            focusListenerAttached = true;
        }
    }

    /**
     * 通过代码方式触发编辑事件
     */
    private boolean programmaticallyEdited = false;

    /**
     * 双击触发编辑
     */
    @Override
    public void startEdit() {
        /**
         * If a row is added, new cells are created. The old cells are not disposed automatically. They still respond to user event's. Fortunately, the "should-be-discarded" cells
         * have invisible row, so we can recognize them and prevent them to interact with user's event.
         */
        if (!this.getTableRow().isVisible() || !getTableRow().isEditable()) {
            return;
        }
        super.startEdit();
        if (!programmaticallyEdited) {
            Node editView = getEditableView();
            setGraphic(editView);
            editView.requestFocus();
            updateCellValue(getItem());
            // 触发表格的编辑事件
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

    private Node getEditableView() {
        if (editView == null) {
            editView = getEditView();
            attachEnterEscapeEventHandler();
            attachFocusListener();
        }
        return editView;
    }

    /**
     * 更新单元格的值
     * @param value 单元格的值
     */
    protected abstract void updateCellValue(C value);

    /**
     * 获取单元格编辑后的值
     * @return 单元格编辑后的值
     */
    @Nullable
    protected abstract C getEditedValue();

    /**
     * 获取处于编辑状态的节点
     * @return 处于编辑状态的节点
     */
    @Nullable
    protected abstract Node getEditView();

    @Override
    public void cancelEdit() {
        if (!isFocused() && isEditing()) {
            /**
             * The only way to commit edit in tableCell was by pressing enter. If user select another cell by clicking it or pressing tab than cell's value is reverted back. We want
             * to change this behavior. Now if user move to another cell, it's value is committed. The only way to revert the value is by pressing escape. Check {@link Cell}
             */
            logger.fine("about to commitEdit in cancelEdit() method");
            commitEdit(getEditedValue());
            return;
        }
        updateCellValue(getItem());
        super.cancelEdit();

    }

    @Override
    public void updateItem(C item, boolean empty) {
        boolean emptyRow = getTableView().getItems().size() < getIndex() + 1;
        /**
         * don't call super.updateItem() because it will trigger cancelEdit() if the cell is being edited. It causes calling commitEdit() ALWAYS call cancelEdit as well which is
         * undesired.
         */
        if (!isEditing()) {
            super.updateItem(item, empty && emptyRow);
        }
        if (empty && isSelected()) {
            updateSelected(false);
        }
        if (empty && emptyRow) {
            setText(null);
            //do not nullify graphic here. Let the TableRow to control cell dislay
        } else if (!isEditing()) {
            setText(getString(item));
            if (getContentDisplay() == ContentDisplay.GRAPHIC_ONLY) {
                updateCellValue(item);
            }
        }
    }

    protected final String getString(C value) {
        try {
            return stringConverter.toString(value);
        } catch (ClassCastException ex) {
            String propertyName = getTableColumn() instanceof CustomTableColumn ? ((CustomTableColumn<?, ?>) getTableColumn()).getPropertyName() : "unknown";
            if (getTableRow() != null && getTableRow().getItem() != null) {
                propertyName = getTableRow().getItem().getClass().getName() + "." + propertyName;
            }
            String msg = "Invalid column type for \"" + propertyName + "\"";
            throw new RuntimeException(msg, ex);
        }
    }

    protected void attachEnterEscapeEventHandler() {
        editView.setOnKeyPressed(new KeyEventHandler<C>(this));
    }

    static class KeyEventHandler<C> implements EventHandler<KeyEvent> {

        CustomTableCell<?, C> tableCell;

        public KeyEventHandler(CustomTableCell<?, C> tableCell) {
            this.tableCell = tableCell;
        }

        @Override
        public void handle(KeyEvent event) {
            // Enter提交编辑
            if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
                tableCell.commitEdit(tableCell.getEditedValue());
            } else if (event.getCode() == KeyCode.ESCAPE) {
                tableCell.cancelEdit();
            }
        }
    }
}
