package com.panemu.tiwulfx.table;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 单元格
 * @param <R> 行数据类型
 * @param <C> 列数据类型
 *            For the case of TypeAhead, Date and Lookup, the focusable control is the textfield, not the control itself.
 *            This method is to be overridden by TypeAheadTableCell, DateTableCell and LookupTableCell
 */
public abstract class CustomTableCell<R, C> extends TableCell<R, C> {

    private final StringConverter<C> stringConverter;

    public CustomTableCell(final CustomTableColumn<R, C> column) {
        this.setAlignment(column.getAlignment());
        this.stringConverter = column.getStringConverter();
        setFocusTraversable(true);
    }

    class CellMouseEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            CustomTableCell<?, ?> tableCell = (CustomTableCell<?, ?>) event.getSource();
            TableColumn<?, ?> clm = tableCell.getTableColumn();
            if (clm instanceof CustomTableColumn) {
                TableRow<?> tableRow = tableCell.getTableRow();
                CustomTableColumn column = (CustomTableColumn<?, ?>) clm;
                if (!column.isValid(tableRow.getItem())) {
                    CustomTableColumn<?, ?> baseColumn = (CustomTableColumn<?, ?>) clm;
                    PopupControl popup = baseColumn.getPopupControl(tableRow.getItem());
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
        popup.show(owner, p.getX() + scene.getX() + scene.getWindow().getX(), p.getY() + scene.getY() + scene
                .getWindow().getY() + owner.getHeight() - 1);
    }

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

        Node editableView = getEditableView();

        editableView.setFocusTraversable(true);

        editableView.requestFocus();

        setGraphic(editableView);
        updateCellValue(getItem());
    }

    /**
     * 编辑状态要展示的节点
     * @return 节点
     */
    private Node getEditableView() {
        Node node = getEditView();
        initEditView(node);
        return node;
    }

    /**
     * 将单元格的值更新到编辑视图的组件中进行显示
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
    @NotNull
    protected abstract Node getEditView();

    @Override
    public void cancelEdit() {
        if (!isFocused() && isEditing()) {
            /**
             * 默认的 {@link Cell} 仅在Enter键按下时提交编辑，如果用户此时点击了另一个单元格，那么值将会恢复
             */
            C editedValue = getEditedValue();
            if (editedValue != null) {
                commitEdit(editedValue);
            }
            updateCellValue(getItem());
            return;
        }
        super.cancelEdit();
    }

    @Override
    public void commitEdit(C newValue) {
        super.commitEdit(newValue);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(C item, boolean empty) {
        boolean emptyRow = getTableView().getItems().size() < getIndex() + 1;
        /**
         * don't call super.updateItem() because it will trigger cancelEdit() if the cell is being edited.
         * It causes calling commitEdit() ALWAYS call cancelEdit as well which is undesired.
         */
        if (!isEditing()) {
            super.updateItem(item, empty && emptyRow);
        }
        if (empty) {
            if (isSelected()) updateSelected(false);
        }
        if (empty && emptyRow) {
            this.setText(null);
            //do not nullify graphic here. Let the TableRow to control cell dislay
        } else if (!isEditing()) {
            this.setText(getString(item));
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

    protected void initEditView(Node editNode) {
        editNode.setOnKeyReleased(new KeyEventHandler<>(this));
    }

    /**
     * Enter提交编辑
     * @param <C>
     */
    static class KeyEventHandler<C> implements EventHandler<KeyEvent> {

        CustomTableCell<?, C> tableCell;

        public KeyEventHandler(CustomTableCell<?, C> tableCell) {
            this.tableCell = tableCell;
        }

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {

                C editedValue = tableCell.getEditedValue();
                System.out.println(editedValue);
                tableCell.commitEdit(editedValue);
            } else if (event.getCode() == KeyCode.ESCAPE) {
                tableCell.cancelEdit();
            }
        }
    }
}
