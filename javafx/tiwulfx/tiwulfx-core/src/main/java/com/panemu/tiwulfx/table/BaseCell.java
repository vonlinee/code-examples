package com.panemu.tiwulfx.table;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.util.logging.Logger;

public abstract class BaseCell<R, C> extends TableCell<R, C> {

    private static final Logger logger = Logger.getLogger(BaseCell.class.getName());
    private Control control;
    private final StringConverter<C> stringConverter;
    private static final PseudoClass PSEUDO_CLASS_INVALID = PseudoClass.getPseudoClass("invalid");
    private boolean focusListenerAttached = false;
    private boolean programmaticallyEdited = false;

    public BaseCell(final BaseColumn<R, C> column) {
        this.stringConverter = column.getStringConverter();
        this.setAlignment(column.getAlignment());
        /**
         * 当Cell首次显示时，肯定是ContentDisplay.GRAPHIC_ONLY状态，此监听会在单元格首次出现时就会触发
         */
        this.contentDisplayProperty().addListener((observable, oldValue, newValue) -> {
            initGraphic();
            if (newValue == ContentDisplay.GRAPHIC_ONLY) {
                updateValue(getItem());
            } else if (newValue == ContentDisplay.TEXT_ONLY) { // ContentDisplay.TEXT_ONLY 表明退出编辑状态
                commitEdit(getEditedValue());
            }
        });

        EventHandler<MouseEvent> mouseEventEventHandler = event -> {
            TableColumn<R, C> clm = getTableColumn();
            if (clm instanceof BaseColumn<R, C> baseColumn && !baseColumn.isValid(getTableRow().getItem())) {
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
        InvalidationListener invalidRecordListener = observable -> pseudoClassStateChanged(PSEUDO_CLASS_INVALID, column.isRecordInvalid(getRowItem()));
        column.getInvalidRecordMap().addListener(new WeakInvalidationListener(invalidRecordListener));
        itemProperty().addListener(invalidRecordListener);
    }

    /**
     * For the case of TypeAhead, Date and Lookup, the focusable control is the textfield, not the control itself.
     * This method is to be overridden by TypeAheadTableCell, DateTableCell and LookupTableCell
     * @return Control
     */
    protected Control getFocusableControl() {
        return control;
    }

    protected void attachSkinListener() {
        if (control != null) {
            control.skinProperty().addListener((ov, oldValue, newValue) -> {
                if (oldValue == null && newValue != null) {
                    attachFocusListener();
                }
            });
        }
    }

    /**
     * this method will only be executed once
     */
    private void attachFocusListener() {
        if (focusListenerAttached) {
            return;
        }
        /**
         * Set cell mode to edit if the editor control receives focus. This is intended to deal
         * with mouse click. This way, commitEdit() will be called if the cell is no longer focused
         */
        Control focusableControl = getFocusableControl();
        if (focusableControl == null) {
            if (isEditable()) {
                attachSkinListener();
            }
        } else {
            /**
             * 表格的列选是和表格是否可编辑绑定的
             * 行选模式下，点击单元格选中的是一行 {@code TableRow}，而不是 {@code TableCell}
             * 因此不会触发TableCell#selectedProperty()监听
             * @see javafx.scene.control.TableSelectionModel#cellSelectionEnabledProperty()
             */
            this.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue && !isEditing()) {
                    /**
                     * edit the cell by code instead of UI operation, which called 'programmatically'
                     * 如果切换到编辑状态，会触发{@link BaseCell#startEdit()}方法
                     */
                    logger.info("programmaticallyEdited Row:" + getIndex() + ", Column Name:" + getTableColumn().getText());
                    getTableView().edit(getIndex(), getTableColumn());
                    programmaticallyEdited = true;
                }
            });
            focusListenerAttached = true;
        }
    }

    @Override
    public void startEdit() {
        /**
         * If a row is added, new cells are created. The old cells are not disposed automatically. They still respond to user event's.
         * Fortunately, the "should-be-discarded" cells
         * have invisible row, so we can recognize them and prevent them to interact with user's event.
         */
        if (!this.getTableRow().isVisible() || !getTableRow().isEditable()) {
            return;
        }
        super.startEdit();
        if (!programmaticallyEdited) {
            /**
             * 由代码进入编辑状态，不会更新相关的UI，因此需要手动进行更新
             */
            initGraphic();
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // trigger the listener in Constructor
            updateValue(getItem());
            // put focus on the textfield so user can directly type on it
            if (!control.isFocused()) {
                Platform.runLater(() -> control.requestFocus());
            }
        }
        // reset the programmaticallyEdited flag
        programmaticallyEdited = false;
    }

    /**
     * 每次初始化一行的所有列
     * lazy初始化
     */
    private void initGraphic() {
        if (control == null) {
            control = getEditableControl();
            attachEnterEscapeEventHandler();
            attachFocusListener();
        }
        setGraphic(control);
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
    public void cancelEdit() {
        if (!isFocused() && isEditing()) {
            /**
             * The only way to commit edit in tableCell was by pressing enter.
             * If user select another cell by clicking it or pressing tab than cell's value
             * is reverted back. We want to change this behavior. Now if user move to another cell,
             * it's value is committed. The only way to revert the value is by pressing escape.
             * Check {@link Cell}
             */
            logger.fine("about to commitEdit in cancelEdit() method");
            commitEdit(getEditedValue());
            return;
        }
        updateValue(getItem());
        super.cancelEdit();
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
