package com.panemu.tiwulfx.table;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public abstract class BaseCell<R, C> extends TableCell<R, C> {

    private Control control;
    private final StringConverter<C> stringConverter;

    public BaseCell(final BaseColumn<R, C> column) {
        this.stringConverter = column.getStringConverter();
        this.setAlignment(column.getAlignment());
        EventHandler<MouseEvent> mouseEventEventHandler = event -> {
            TableColumn<R, C> clm = getTableColumn();
            if (clm instanceof BaseColumn<R, C> baseColumn && !baseColumn.isValid(getTableRow().getItem())) {
                System.out.println("展示");
                PopupControl popup = baseColumn.getPopup(getTableRow().getItem());
                if (event.getEventType() == MouseEvent.MOUSE_MOVED && !popup.isShowing()) {
                    Point2D p = BaseCell.this.localToScene(0.0, 0.0);
                    popup.show(BaseCell.this, p.getX() + getScene().getX() + getScene().getWindow()
                            .getX(), p.getY() + getScene().getY() + getScene().getWindow()
                            .getY() + BaseCell.this.getHeight() - 1);
                } else if (event.getEventType() == MouseEvent.MOUSE_EXITED && popup.isShowing()) {
                    popup.hide();
                }
            }
        };

        this.setOnMouseExited(mouseEventEventHandler);
        this.setOnMouseMoved(mouseEventEventHandler);

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    // 失去焦点
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        });
    }

    /**
     * For the case of TypeAhead, Date and Lookup, the focusable control is the textfield, not the control itself.
     * This method is to be overridden by TypeAheadTableCell, DateTableCell and LookupTableCell
     * @return Control
     */
    protected Control getFocusableControl() {
        return control;
    }

    @Override
    public void startEdit() {
        initGraphic();
        super.startEdit();
    }

    /**
     * 每次初始化一行的所有列
     * lazy初始化
     */
    private void initGraphic() {
        if (control == null) {
            control = getEditableControl();
            attachEnterEscapeEventHandler();
        }
        setGraphic(control);
        updateValue(getItem());
    }

    /**
     * 更新此Cell的编辑节点的值
     * @param value 新值
     */
    protected abstract void updateValue(C value);

    /**
     * 获取编辑后的值，此值应是实时更新的
     * 单元格的编辑节点的实际值
     * @return {@link C}
     */
    protected abstract C getEditedValue();

    /**
     * 获取编辑节点，比如TextField
     * @return 编辑节点控件
     */
    protected abstract Control getEditableControl();

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
        if (empty && isSelected()) {
            updateSelected(false);
        }
        if (empty && emptyRow) {
            setText(null);
            //do not nullify graphic here. Let the TableRow to control cell dislay
        } else if (!isEditing()) {
            setText(getStringValue(item));
            if (getContentDisplay() == ContentDisplay.GRAPHIC_ONLY) {
                updateValue(item);
            }
        }
    }

    protected String getStringValue(C value) {
        try {
            return stringConverter.toString(value);
        } catch (ClassCastException ex) {
            String propertyName = getTableColumn() instanceof BaseColumn<R, C> baseColumn ? baseColumn.getPropertyName() : "unknown";
            if (getTableRow() != null && getTableRow().getItem() != null) {
                propertyName = getTableRow().getItem().getClass().getName() + "." + propertyName;
            }
            String msg = "Invalid column type for \"" + propertyName + "\"";
            throw new RuntimeException(msg, ex);
        }
    }

    protected void attachEnterEscapeEventHandler() {
        control.setOnKeyPressed(t -> {
            // 按 Enter 提交单元格的修改
            if (t.getCode() == KeyCode.ENTER && !t.isShiftDown()) {
                commitEdit(getEditedValue());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        });
    }

    /**
     * 获取Cell所在行的数据
     * @return Cell所在行的数据
     */
    public final R getRowItem() {
        return getTableRow().getItem();
    }
}
