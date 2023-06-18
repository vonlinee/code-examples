package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.*;
import com.panemu.tiwulfx.control.TableIntializer;
import com.panemu.tiwulfx.dialog.MessageDialog;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import com.panemu.tiwulfx.utils.ClassUtils;
import com.panemu.tiwulfx.utils.ClipboardUtils;
import com.panemu.tiwulfx.utils.EventUtils;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 为了区分API和JavaFX内置的API，很多地方用到强制转换
 * 比如 BaseCell 和 TableControlRow
 * @param <R>
 */
public class TableControl<R> extends VBox {

    private final CustomTableView<R> tblView = new CustomTableView<>();
    private TableControlBehavior<R> behavior;
    private final SimpleIntegerProperty startIndex = new SimpleIntegerProperty(0);
    private final StartIndexChangeListener startIndexChangeListener = new StartIndexChangeListener();
    private final InvalidationListener sortTypeChangeListener = new SortTypeChangeListener();
    private int lastColumnIndex = 0;
    /**
     * 操作模式
     */
    private final SimpleObjectProperty<Mode> mode = new SimpleObjectProperty<>(Mode.READ);

    public final void setMode(Mode mode) {
        this.mode.set(mode);
    }

    private long totalRows = 0;
    private Integer currentPage = 0;

    /**
     * 存放修改的行
     * 编辑模式下：被修改的行高亮显示
     */
    private final ObservableList<R> lstChangedRow = FXCollections.observableArrayList();

    public ObservableList<R> getLstChangedRow() {
        return lstChangedRow;
    }

    private Class<R> recordClass;
    private boolean fitColumnAfterReload = false;
    private final List<TableCriteria> lstCriteria = new ArrayList<>();
    private boolean reloadOnCriteriaChange = true;

    private final ObservableList<TableColumn<R, ?>> columns = tblView.getColumns();
    private final TableControlService service = new TableControlService();

    private String configurationID;
    private boolean suppressSortConfigListener = false;
    private boolean suppressWidthConfigListener = false;
    private static final Logger logger = Logger.getLogger(TableControl.class.getName());
    private final List<RowBrowser> lstRowBrowser = new ArrayList<>();

    private boolean stageShown = false;
    private ExportMode exportMode = ExportMode.CURRENT_PAGE;

    /**
     * TODO 是否用后台加载
     */
    private final boolean useBackgroundTaskToLoad = TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_LOAD;
    private final boolean useBackgroundTaskToSave = TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_SAVE;
    private final boolean useBackgroundTaskToDelete = TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_DELETE;

    public enum Mode {
        INSERT,
        EDIT,
        READ
    }

    /**
     * UI component in TableControl which their visibility could be manipulated
     * @see #setVisibleComponents(boolean,
     * com.panemu.tiwulfx.table.TableControl.Component[])
     */
    public enum Component {

        BUTTON_RELOAD,
        BUTTON_INSERT,
        BUTTON_EDIT,
        BUTTON_SAVE,
        BUTTON_DELETE,
        BUTTON_EXPORT,
        BUTTON_PAGINATION,
        TOOLBAR,
        FOOTER
    }

    static class Footer extends StackPane {

        TableControl<?> tableControl;

        private final Label labelRowIndex;
        private final Label lblTotalRow;
        private final MenuButton menuButton;
        private MenuItem resetItem;

        public Footer(TableControl<?> tableControl) {
            this.tableControl = tableControl;
            this.getStyleClass().add("table-footer");

            this.labelRowIndex = new Label();
            this.lblTotalRow = new Label();
            this.menuButton = new TableControlMenu(tableControl);
            StackPane.setAlignment(labelRowIndex, Pos.CENTER_LEFT);
            StackPane.setAlignment(lblTotalRow, Pos.CENTER);
            StackPane.setAlignment(menuButton, Pos.CENTER_RIGHT);
            ProgressBar progressIndicator = new ProgressBar();
            lblTotalRow.visibleProperty().bind(progressIndicator.visibleProperty().not());
            progressIndicator.setProgress(-1);
            progressIndicator.visibleProperty().bind(tableControl.busyProperty());
            menuButton.disableProperty().bind(tableControl.busyProperty());
            this.getChildren().addAll(labelRowIndex, lblTotalRow, menuButton, progressIndicator);
        }

        public void configureResetMenuItem() {
            if (resetItem == null) {
                resetItem = new MenuItem(TiwulFXUtil.getLiteral("reset.columns"));
                resetItem.setOnAction(t -> tableControl.resetColumnPosition());
            }
            if (!menuButton.getItems().contains(resetItem)) {
                menuButton.getItems().add(resetItem);
            }
        }

        public void removeResetItem() {
            menuButton.getItems().remove(resetItem);
        }

        public void updateTotalRecord(long totalRows) {
            lblTotalRow.setText(TiwulFXUtil.getLiteral("total.record.param", totalRows));
        }

        public void updateRow(int rowNumber) {
            labelRowIndex.setText(TiwulFXUtil.getLiteral("row.param", rowNumber));
        }
    }

    TableIntializer<R> tableIntializer = new TableIntializer<>();

    public TableControl(Class<R> recordClass) {
        this.recordClass = recordClass;
        initControls();

        if (recordClass != null) {
            tableIntializer.initTableView(recordClass, tblView);
        }

        tblView.getSortOrder().addListener((ListChangeListener<TableColumn<R, ?>>) change -> {
            if (stageShown) {
                reload();
                resetColumnSortConfig();
            }
        });

        tblView.editableProperty().bind(mode.isNotEqualTo(Mode.READ));
        // 列选择模式: 单独选择某个单元格，而不是选中一行
        tblView.getSelectionModel().cellSelectionEnabledProperty().bind(tblView.editableProperty());
        // 更新选择行显示标记
        tblView.getSelectionModel().selectedIndexProperty()
                .addListener((ov, oldValue, newValue) -> footer.updateRow(getRowNumber(newValue)));

        /**
         * Define policy for TAB key press
         * Move focus to the next cell if user pressing TAB and the mode is
         * EDIT/INSERT
         */
        tblView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            TableView.TableViewSelectionModel<R> selectionModel = tblView.getSelectionModel();
            if (isReadMode()) {
                if (event.getCode() == KeyCode.C && event.isControlDown()) {
                    if (event.isShiftDown()) copyRow();
                    else copyCell();
                    event.consume();
                } else if (event.getCode() == KeyCode.B && event.isAltDown()) {
                    // Alt + B 浏览选中行
                    browseSelectedRow();
                }
                return;
            }
            if (event.getCode() == KeyCode.TAB) {
                if (event.isShiftDown()) {
                    if (selectionModel.getSelectedCells().get(0).getColumn() == 0) {
                        List<TableColumn<R, ?>> leafColumns = getLeafColumns();
                        showRow(tblView.getSelectionModel().getSelectedIndex() - 1);
                        selectionModel.select(selectionModel.getSelectedIndex() - 1, leafColumns.get(leafColumns.size() - 1));
                    } else {
                        selectionModel.selectLeftCell();
                    }
                } else {
                    TablePosition<R, Object> position = tblView.getSelectedCellPosition(0);
                    if (position.getColumn() == lastColumnIndex) {
                        showRow(tblView.getSelectionModel().getSelectedIndex() + 1);
                        selectionModel.select(selectionModel.getSelectedIndex() + 1, tblView.getColumns().get(0));
                    } else {
                        tblView.getSelectionModel().selectRightCell();
                    }
                }
                horizontalScroller.run();
                event.consume();
            } else if (event.getCode() == KeyCode.V && event.isControlDown()) {
                if (!hasEditingCell()) {
                    paste();
                    event.consume();
                }
            }
        });

        tableContextMenu = new TableContextMenu(this);

        tblView.setOnMouseReleased(event -> {
            tableContextMenu.hide();
            // 右键
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                if (!tblView.hasSelectedCells()) {
                    return;
                }
                TableColumn<R, Object> column = tblView.getSelectedColumn(0);

                tableContextMenu.removeSearchMenuItemIfNull();
                tableContextMenu.removePasteMenuItem();

                if (column instanceof BaseColumn<R, Object> clm) {
                    int row = tblView.getSelectionModel().getSelectedIndex();
                    clm.setDefaultSearchValue(column.getCellData(row));
                    tableContextMenu.addSearchMenuItem(clm.getSearchMenuItem(), clm.isFilterable());
                    if (!isReadMode() && !hasEditingCell() && ClipboardUtils.hasString()) {
                        tableContextMenu.addPasteMenuItemIfNotContains();
                    }
                }
                tableContextMenu.show(tblView, event.getScreenX(), event.getScreenY());
            }
        });
        // create custom row factory that can intercept double click on grid row
        tblView.setRowFactory(param -> {
            TableControlRow<R> row = new TableControlRow<>(TableControl.this);
            row.setOnMouseClicked(event -> {
                @SuppressWarnings("unchecked")
                TableControlRow<R> tableRow = (TableControlRow<R>) event.getSource();
                if (EventUtils.isPrimaryKeyDoubleClicked(event) && !tableRow.isEmpty() && tableRow.isFocused()) {
                    // Table未显示的行为空，即空白行
                    behavior.doubleClick(tableRow.getItem());
                }
            });
            return row;
        });

        // 初始化TableControl的列
        columns.addListener((ListChangeListener<TableColumn<R, ?>>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (TableColumn<R, ?> column : change.getAddedSubList()) {
                        initColumn(column);
                    }
                }
                lastColumnIndex = getLeafColumns().size() - 1;
            }
        });
        attachWindowVisibilityListener();
    }

    /**
     * 获取行号
     * @param rowIndex 行索引
     * @return int
     */
    private int getRowNumber(Number rowIndex) {
        return (currentPage * pageSize.get() + rowIndex.intValue() + 1);
    }

    public final ObservableList<TableColumn<R, ?>> getColumns() {
        return columns;
    }

    public final ObservableList<R> getChangedRecords() {
        return lstChangedRow;
    }

    PaginationControl paginationControl;
    OperationToolBar toolBar;
    Footer footer;

    private void initControls() {
        this.getStyleClass().add("table-control");

        /**
         * 分页控制
         */
        paginationControl = new PaginationControl(this);
        /**
         * 工具栏
         */
        toolBar = new OperationToolBar(this, paginationControl);

        toolBar.getStyleClass().add("table-toolbar");
        toolBar.disableProperty().bind(service.runningProperty());

        footer = new Footer(this);

        getChildren().addAll(toolBar, tblView, footer);

        VBox.setVgrow(tblView, Priority.ALWAYS);
    }

    interface Visiable {

        default void setOrNot(ToolBar parent, Node control, boolean visible) {
            if (!visible) {
                parent.getItems().remove(control);
            } else if (!parent.getItems().contains(control)) {
                parent.getItems().add(control);
            }
        }
    }


    static class OperationToolBar extends ToolBar implements EventHandler<ActionEvent>, Visiable {

        TableControl<?> tableControl;

        private Button buildButton(Node graphic) {
            Button btn = new Button();
            btn.setGraphic(graphic);
            btn.getStyleClass().add("flat-button");
            return btn;
        }

        public OperationToolBar(TableControl<?> tableControl, PaginationControl paginationControl) {
            this.tableControl = tableControl;
            this.paginationControl = paginationControl;

            btnAdd = buildButton(TiwulFXUtil.getGraphicFactory().createAddGraphic());
            btnDelete = buildButton(TiwulFXUtil.getGraphicFactory().createDeleteGraphic());
            btnEdit = buildButton(TiwulFXUtil.getGraphicFactory().createEditGraphic());
            btnExport = buildButton(TiwulFXUtil.getGraphicFactory().createExportGraphic());
            btnReload = buildButton(TiwulFXUtil.getGraphicFactory().createReloadGraphic());
            btnSave = buildButton(TiwulFXUtil.getGraphicFactory().createSaveGraphic());
            TiwulFXUtil.setToolTip(btnAdd, "add.record");
            TiwulFXUtil.setToolTip(btnDelete, "delete.record");
            TiwulFXUtil.setToolTip(btnEdit, "edit.record");
            TiwulFXUtil.setToolTip(btnReload, "reload.records");
            TiwulFXUtil.setToolTip(btnExport, "export.records");
            TiwulFXUtil.setToolTip(btnSave, "save.record");

            SimpleObjectProperty<Mode> modeProperty = tableControl.modeProperty();
            btnAdd.disableProperty().bind(modeProperty.isEqualTo(Mode.EDIT));
            btnEdit.disableProperty().bind(modeProperty.isNotEqualTo(Mode.READ));
            btnSave.disableProperty().bind(modeProperty.isEqualTo(Mode.READ));

            BooleanBinding booleanBinding = new BooleanBinding() {
                {
                    super.bind(modeProperty, tableControl.getTableView().getSelectionModel()
                            .selectedItemProperty(), tableControl.getLstChangedRow());
                }

                @Override
                protected boolean computeValue() {
                    return (tableControl.isInsertMode() && tableControl.getLstChangedRow().size() < 2) || tableControl
                            .getTableView().getSelectedItem() == null || tableControl.isEditMode();
                }
            };

            btnDelete.disableProperty().bind(booleanBinding);

            btnAdd.setOnAction(this);
            btnDelete.setOnAction(this);
            btnEdit.setOnAction(this);
            btnExport.setOnAction(this);
            btnReload.setOnAction(this);
            btnSave.setOnAction(this);

            this.getItems().addAll(btnReload, btnAdd, btnEdit, btnSave, btnDelete, btnExport);

            spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            this.getItems().addAll(spacer, paginationControl);
        }

        public Button btnAdd;
        public Button btnEdit;
        public Button btnDelete;
        public Button btnReload;
        public Button btnSave;
        public Button btnExport;

        public Region spacer;
        PaginationControl paginationControl;

        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() == btnAdd) {
                tableControl.insert();
            } else if (event.getSource() == btnDelete) {
                tableControl.delete();
            } else if (event.getSource() == btnEdit) {
                tableControl.edit();
            } else if (event.getSource() == btnExport) {
                tableControl.export();
            } else if (event.getSource() == btnReload) {
                tableControl.reload();
            } else if (event.getSource() == btnSave) {
                tableControl.save();
            }
        }

        public void setComponentVisible(Component comp, boolean visible) {
            ToolBar toolBar = this;
            switch (comp) {
                case BUTTON_DELETE -> setOrNot(toolBar, btnDelete, visible);
                case BUTTON_EDIT -> setOrNot(toolBar, btnEdit, visible);
                case BUTTON_INSERT -> setOrNot(toolBar, btnAdd, visible);
                case BUTTON_EXPORT -> setOrNot(toolBar, btnExport, visible);
                case BUTTON_PAGINATION -> {
                    setOrNot(toolBar, spacer, visible);
                    setOrNot(toolBar, paginationControl, visible);
                }
                case BUTTON_RELOAD -> setOrNot(toolBar, btnReload, visible);
                case BUTTON_SAVE -> setOrNot(toolBar, btnSave, visible);
            }
        }

        public void addOptionalNode(Node node) {
            ToolBar toolBar = this;
            boolean hasPagination = toolBar.getItems().contains(paginationControl);
            if (hasPagination) {
                toolBar.getItems().remove(spacer);
                toolBar.getItems().remove(paginationControl);
            }
            toolBar.getItems().add(node);
            if (hasPagination) {
                toolBar.getItems().add(spacer);
                toolBar.getItems().add(paginationControl);
            }
        }
    }

    private final BooleanProperty agileEditing = new SimpleBooleanProperty(true);

    public final void setAgileEditing(boolean agileEditing) {
        this.agileEditing.set(agileEditing);
    }

    public final boolean isAgileEditing() {
        return agileEditing.get();
    }

    public final BooleanProperty agileEditingProperty() {
        return agileEditing;
    }

    /**
     * 是否有正处于编辑状态的单元格
     * @return boolean
     */
    public final boolean hasEditingCell() {
        return tblView.getEditingCell() != null && tblView.getEditingCell().getRow() > -1;
    }

    public void showRow(int index) {
        if (index < 0 || index >= getRecords().size()) {
            return;
        }
        Node node = tblView.lookup("VirtualFlow");
        if (node instanceof VirtualFlow<?> virtualFlow) {
            virtualFlow.scrollTo(index);
        }
    }

    /**
     * Mark record as changed. It will only add the record to the changed record
     * list if the record doesn't exist in the list. Avoid adding record to
     * {@link #getChangedRecords()} to avoid adding the same record multiple
     * times.
     * @param record record
     */
    public void markAsChanged(R record) {
        if (!lstChangedRow.contains(record)) {
            lstChangedRow.add(record);
        }
    }

    /**
     * Paste text on clipboard. Doesn't work on READ mode.
     */
    public void paste() {
        if (mode.get() == Mode.READ) {
            return;
        }
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasString()) {
            final String text = clipboard.getString();
            if (text != null) {
                final TablePosition<R, Object> cell = tblView.getSelectedCellPosition(0);
                if (cell == null) {
                    return;
                }
                List<TableColumn<R, ?>> lstColumn = getLeafColumns();
                TableColumn<R, ?> startColumn = null;
                for (TableColumn<R, ?> clm : lstColumn) {
                    if (clm instanceof BaseColumn && clm == cell.getTableColumn()) {
                        startColumn = clm;
                        break;
                    }
                }
                if (startColumn == null) {
                    return;
                }
                int rowIndex = cell.getRow();
                String[] arrString = text.split("\n");
                boolean stopPasting = false;
                for (String line : arrString) {
                    if (stopPasting) {
                        break;
                    }
                    R item = null;
                    if (rowIndex < tblView.getItems().size()) {
                        item = tblView.getItems().get(rowIndex);
                    } else if (isEditMode()) {
                        /**
                         * Will ensure the content display to TEXT_ONLY because
                         * there is no way to update cell editors value (in
                         * agile editing mode)
                         */
                        tblView.getSelectionModel().clearSelection();
                        return;//stop pasting as it already touched last row
                    }

                    if (!lstChangedRow.contains(item)) {
                        if (mode.get() == Mode.INSERT) {
                            //means that selected row is not new row. Let's create new row
                            createNewRow(rowIndex);
                            item = tblView.getItems().get(rowIndex);
                        } else {
                            lstChangedRow.add(item);
                        }
                    }

                    showRow(rowIndex);

                    TableColumn<R, ?> toFillColumn = startColumn;
                    tblView.getSelectionModel().select(rowIndex, toFillColumn);

                    // Handle multicolumn paste
                    String[] stringCellValues = line.split("\t");
                    for (String stringCellValue : stringCellValues) {
                        if (toFillColumn == null) {
                            break;
                        }
                        if (toFillColumn instanceof BaseColumn && toFillColumn.isEditable() && toFillColumn.isVisible()) {
                            try {
                                Object oldValue = toFillColumn.getCellData(item);
                                Object newValue = ((BaseColumn<R, ?>) toFillColumn).convertFromString(stringCellValue);
                                ClassUtils.setSimpleProperty(item, ((BaseColumn<R, ?>) toFillColumn).getPropertyName(), newValue);
                                if (isEditMode()) {
                                    ((BaseColumn<R, ?>) toFillColumn).addRecordChange(item, oldValue, newValue);
                                }
                            } catch (Exception ex) {
                                MessageDialog.Answer answer = MessageDialogBuilder.error(ex)
                                        .message("msg.paste.error", stringCellValue, toFillColumn.getText())
                                        .buttonType(MessageDialog.ButtonType.YES_NO).yesOkButtonText("continue.pasting")
                                        .noButtonText("stop").show(getScene().getWindow());
                                if (answer == MessageDialog.Answer.NO) {
                                    stopPasting = true;
                                    break;
                                }
                            }
                        }
                        tblView.getSelectionModel().selectRightCell();
                        TablePosition<R, ?> nextCell = tblView.getSelectedCellPosition(0);
                        if (nextCell.getTableColumn() instanceof BaseColumn && nextCell.getTableColumn() != toFillColumn) {
                            toFillColumn = nextCell.getTableColumn();
                        } else {
                            toFillColumn = null;
                        }
                    }
                    rowIndex++;
                }
                refresh();
                /**
                 * Will ensure the content display to TEXT_ONLY because there is
                 * no way to update cell editors value (in agile editing mode)
                 */
                tblView.getSelectionModel().clearSelection();
            }
        }
    }

    /**
     * It calls {@link TableView#refresh()}
     */
    public final void refresh() {
        tblView.refresh();
    }

    /**
     * Force the table to repaint specified row.It propagates the call to {@link TableControlRow#refresh()}.
     * @param record specified record to refresh.
     */
    public final void refresh(R record) {
        Set<Node> nodes = tblView.lookupAll(".table-row-cell");
        for (Node node : nodes) {
            if (node instanceof TableControlRow<?> tableRow) {
                if (tableRow.getItem() != null && tableRow.getItem().equals(record)) {
                    tableRow.refresh();
                    break;
                }
            }
        }
    }

    private final Runnable horizontalScroller = new Runnable() {
        private ScrollBar scrollBar = null;

        @Override
        public void run() {
            TableColumn<R, Object> col = tblView.getFocusedColumn();
            if (col == null || !col.isVisible()) {
                return;
            }
            if (scrollBar == null) {
                for (Node n : tblView.lookupAll(".scroll-bar")) {
                    if (n instanceof ScrollBar bar) {
                        if (bar.getOrientation().equals(Orientation.HORIZONTAL) && bar.isVisible()) {
                            scrollBar = bar;
                            break;
                        }
                    }
                }
            }
            if (scrollBar == null) {
                // scrollbar is not visible, meaning all columns are visible. No need to scroll
                return;
            }
            // work out where this column header is, and it's width (start -> end)
            double start = 0;
            for (TableColumn<R, ?> c : tblView.getVisibleLeafColumns()) {
                if (c.equals(col)) {
                    break;
                }
                start += c.getWidth();
            }
            double end = start + col.getWidth();

            // determine the width of the table
            double headerWidth = tblView.getWidth() - tblView.snappedLeftInset() - tblView.snappedRightInset();
            // determine by how much we need to translate the table to ensure that
            // the start position of this column lines up with the left edge of the
            // tableview, and also that the columns don't become detached from the
            // right edge of the table
            double pos = scrollBar.getValue();
            double max = scrollBar.getMax();
            double newPos = pos;
            if (start < pos && start >= 0) {
                newPos = start;
            } else {
                double delta = start < 0 || end > headerWidth ? start - pos : 0;
                newPos = Math.min(pos + delta, max);
            }
            // FIXME we should add API in VirtualFlow so we don't end up going
            // direct to the hbar.
            // actually shift the flow - this will result in the header moving as well
            scrollBar.setValue(newPos);
        }
    };

    /**
     * Get single selected record property. If multiple records are selected, it
     * returns the last one
     * @return table selected item
     */
    public final ReadOnlyObjectProperty<R> selectedItemProperty() {
        return tblView.getSelectionModel().selectedItemProperty();
    }

    /**
     * @return the selected item of the tableview
     * @see #selectedItemProperty()
     */
    public final R getSelectedItem() {
        return tblView.getSelectionModel().selectedItemProperty().get();
    }

    private final TableContextMenu tableContextMenu;

    /**
     * 复制一个单元格
     */
    private void copyCell() {
        System.out.println("复制一个单元格");
    }

    /**
     * Ctrl + C 复制一行
     */
    private void copyRow() {
        System.out.println("复制一行");
    }

    public void browseSelectedRow() {
        R selectedRow = getSelectedItem();
        if (selectedRow == null) {
            return;
        }
        List<TableColumn<R, ?>> lstColumn = getLeafColumns();
        List<Record> lstRecord = new ArrayList<>();
        for (TableColumn<R, ?> tableColumn : lstColumn) {
            Record rcd;
            if (tableColumn instanceof BaseColumn<R, ?> column) {
                rcd = new Record(tableColumn.getText(), column.getStringValueOfRow(selectedRow));
            } else {
                String stringVal = tableColumn.getCellData(selectedRow) == null ? "" : tableColumn
                        .getCellData(selectedRow).toString();
                rcd = new Record(tableColumn.getText(), stringVal);
            }
            lstRecord.add(rcd);
        }
        RowBrowser rb = new RowBrowser();
        lstRowBrowser.add(rb);
        rb.setRecords(lstRecord);
        rb.show(getScene().getWindow());
        rb.getScene().getWindow().addEventHandler(WindowEvent.WINDOW_HIDDEN, (e) -> lstRowBrowser.remove(rb));
    }

    public void closeRowBrowsers() {
        lstRowBrowser.forEach(RowBrowser::close);
    }

    /**
     * Add menu item to context menu. The context menu is displayed when
     * right-clicking a row.
     * @param menuItem MenuItem
     * @see #removeContextMenuItem(javafx.scene.control.MenuItem)
     */
    public void addContextMenuItem(MenuItem menuItem) {
        tableContextMenu.getItems().add(menuItem);
    }

    /**
     * Remove passed menuItem from context menu.
     * @param menuItem MenuItem
     * @see #addContextMenuItem(javafx.scene.control.MenuItem)
     */
    public void removeContextMenuItem(MenuItem menuItem) {
        tableContextMenu.getItems().remove(menuItem);
    }

    protected <T> void resizeToFit(TableColumn<R, T> col) {
        List<?> items = tblView.getItems();
        if (items == null || items.isEmpty()) {
            return;
        }
        Callback<TableColumn<R, T>, TableCell<R, T>> factory = col.getCellFactory();
        if (factory == null) {
            return;
        }
        final TableCell<R, T> cell = factory.call(col);
        if (cell == null) {
            return;
        }
        // set this property to tell the TableCell we want to know its actual
        // preferred width, not the width of the associated TableColumn
        cell.getProperties().put("deferToParentPrefWidth", Boolean.TRUE);

        // determine cell padding
        double padding = 10;
        Node n = cell.getSkin() == null ? null : cell.getSkin().getNode();
        if (n instanceof Region region) {
            padding = region.getInsets().getLeft() + region.getInsets().getRight();
        }

        int rows = items.size();
        double maxWidth = 0;
        for (int row = 0; row < rows; row++) {
            cell.updateTableColumn(col);
            cell.updateTableView(tblView);
            cell.updateIndex(row);
            if ((cell.getText() != null && !cell.getText().isEmpty()) || cell.getGraphic() != null) {
                getChildren().add(cell);
                maxWidth = Math.max(maxWidth, cell.prefWidth(-1));
                getChildren().remove(cell);
            }
        }
    }

    public TableControl() {
        this(null);
    }

    /**
     * @return Object set from
     * {@link #setBehavior(TableControlBehavior)}
     */
    public TableControlBehavior<R> getBehavior() {
        return behavior;
    }

    /**
     * Set object responsible to fetch, insert, delete and update data
     * @param behavior TableControlBehavior
     */
    public void setBehavior(TableControlBehavior<R> behavior) {
        this.behavior = behavior;
    }

    /**
     * 初始化列TableColumn
     * @param clm clm
     */
    private void initColumn(TableColumn<R, ?> clm) {
        List<TableColumn<R, ?>> lstColumn = getColumnsRecursively(List.of(clm));
        for (TableColumn<R, ?> column : lstColumn) {
            if (column instanceof BaseColumn<R, ?> baseColumn) {
                baseColumn.tableCriteriaProperty().addListener(tableCriteriaListener);
                baseColumn.sortTypeProperty().addListener(sortTypeChangeListener);
                baseColumn.setOnEditCommit(event -> {
                    final R persistentObj = TableViewHelper.getEditingItem(event);
                    if (persistentObj == null) {
                        return;
                    }
                    if (isEditMode() && TableViewHelper.isValueChanged(event)) {
                        if (!lstChangedRow.contains(persistentObj)) {
                            lstChangedRow.add(persistentObj);
                        }
                        baseColumn.addRecordChange(persistentObj, event.getOldValue(), event.getNewValue());
                    }
                    ClassUtils.setSimpleProperty(persistentObj, baseColumn.getPropertyName(), event.getNewValue());
                    baseColumn.validate(persistentObj);
                });
            }
        }
    }

    /**
     * Add column to TableView. You can also call {@link TableView#getColumns()}
     * and then add columns to it.
     * @param columns new table columns set
     */
    @SafeVarargs
    public final void addColumn(TableColumn<R, ?>... columns) {
        if (columns != null) {
            tblView.getColumns().addAll(columns);
        }
    }

    /**
     * Get list of columns including the nested ones.
     * @param lstColumn TableColumn列表 只读列表
     * @return 所有的列，包含列的子列
     */
    private List<TableColumn<R, ?>> getColumnsRecursively(List<TableColumn<R, ?>> lstColumn) {
        List<TableColumn<R, ?>> newColumns = new ArrayList<>();
        for (TableColumn<R, ?> column : lstColumn) {
            if (column.getColumns().isEmpty()) {
                newColumns.add(column);
            } else {
                // Should be in new arraylist to avoid java.lang.IllegalArgumentException: Children: duplicate children added
                newColumns.addAll(getColumnsRecursively(new ArrayList<>(column.getColumns())));
            }
        }
        return newColumns;
    }

    /**
     * Get list of columns that is hold cell. It excludes columns that are
     * containers of nested columns.
     * @return all left columns
     */
    public List<TableColumn<R, ?>> getLeafColumns() {
        List<TableColumn<R, ?>> result = new ArrayList<>();
        for (TableColumn<R, ?> clm : tblView.getColumns()) {
            if (clm.getColumns().isEmpty()) {
                result.add(clm);
            } else {
                result.addAll(getColumnsRecursively(clm.getColumns()));
            }
        }
        return result;
    }

    /**
     * Clear all criteria/filters applied to columns then reload the first page.
     */
    public void clearTableCriteria() {
        setReloadOnCriteriaChange(false);
        for (TableColumn<R, ?> clm : getLeafColumns()) {
            if (clm instanceof BaseColumn) {
                ((BaseColumn<R, ?>) clm).setTableCriteria(null);
            }
        }
        setReloadOnCriteriaChange(true);
        reloadFirstPage();
    }

    /**
     * Reload data on current page. This method is called when pressing reload
     * button.
     * @see #reloadFirstPage()
     */
    public void reload() {
        // 改变的行
        if (!lstChangedRow.isEmpty()) {
            if (!behavior.revertConfirmation(this, lstChangedRow.size())) {
                return;
            }
        }
        lstCriteria.clear();
        // Should be in new arraylist to avoid java.lang.IllegalArgumentException: Children: duplicate children added
        List<TableColumn<R, ?>> lstColumns = new ArrayList<>(tblView.getColumns());
        lstColumns = getColumnsRecursively(lstColumns);
        for (TableColumn<R, ?> clm : lstColumns) {
            if (clm instanceof BaseColumn<R, ?> baseColumn) {
                if (baseColumn.getTableCriteria() != null) {
                    lstCriteria.add(baseColumn.getTableCriteria());
                }
            }
        }
        List<String> lstSortedColumn = new ArrayList<>();
        List<SortType> lstSortedType = new ArrayList<>();
        for (TableColumn<R, ?> tc : tblView.getSortOrder()) {
            if (tc instanceof BaseColumn<R, ?> baseColumn) {
                lstSortedColumn.add(baseColumn.getPropertyName());
                lstSortedType.add(tc.getSortType());
            } else if (tc.getCellValueFactory() instanceof PropertyValueFactory) {
                PropertyValueFactory valFactory = (PropertyValueFactory) tc.getCellValueFactory();
                // 获取排序的字段名
                lstSortedColumn.add(valFactory.getProperty());
                lstSortedType.add(tc.getSortType());
            }
        }
        this.closeRowBrowsers();
        if (useBackgroundTaskToLoad) {
            service.runLoadInBackground(lstSortedColumn, lstSortedType);
        } else {
            TableData<R> vol = behavior.loadData(startIndex.get(), lstCriteria, lstSortedColumn, lstSortedType, pageSize.get());
            postLoadAction(vol);
        }
    }

    private void clearChange() {
        lstChangedRow.clear();
        for (TableColumn<R, ?> clm : getLeafColumns()) {
            if (clm instanceof BaseColumn<R, ?> baseColumn) {
                baseColumn.clearRecordChange();
            }
        }
    }

    /**
     * Get list of change happens on cells. It is useful to get detailed
     * information of old and new values of particular record's property
     * @return 改变的记录
     */
    public List<RecordChange<R, ?>> getRecordChangeList() {
        List<RecordChange<R, ?>> lstRecordChange = new ArrayList<>();
        for (TableColumn<R, ?> column : getLeafColumns()) {
            if (column instanceof BaseColumn<R, ?> baseColumn) {
                lstRecordChange.addAll(baseColumn.getRecordChanges());
            }
        }
        return lstRecordChange;
    }

    /**
     * Reload data from the first page.
     */
    public void reloadFirstPage() {
        currentPage = 0;
        if (startIndex.get() != 0) {
            // it will automatically reload data. See StartIndexChangeListener
            startIndex.set(0);
        } else {
            reload();
        }
    }

    /**
     * TODO 以事件发布形式实现
     * the page num changed
     * @param oldPageNum 旧页码
     * @param newPageNum 新页码
     */
    public void pageChangeFired(int oldPageNum, int newPageNum) {
        currentPage = newPageNum - 1;
        startIndex.set(currentPage * getPageSize());
    }

    /**
     * Return false if the insertion is canceled because the controller return
     * null object. It is controller's way to abort insertion.
     * @param rowIndex 行索引
     */
    private void createNewRow(int rowIndex) {
        R newRecord = ClassUtils.newInstance(recordClass);
        if (tblView.getItems().size() == 0) {
            rowIndex = 0;
        }
        tblView.getItems().add(rowIndex, newRecord);
        lstChangedRow.add(newRecord);
    }

    /**
     * Add new row under selected row or in the first row if there is no row
     * selected. This method is called when pressing insert button
     */
    public void insert() {
        if (recordClass == null) {
            throw new RuntimeException("Cannot add new row because the class of the record is undefined.\nPlease call setRecordClass(Class<T> recordClass)");
        }
        R newRecord = ClassUtils.newInstance(recordClass);
        newRecord = behavior.preInsert(newRecord);
        if (newRecord == null) {
            return;
        }
        int selectedRow = tblView.getSelectionModel().getSelectedIndex() + 1;
        if (tblView.getItems().size() == 0) {
            selectedRow = 0;
        }
        tblView.getItems().add(selectedRow, newRecord);
        lstChangedRow.add(newRecord);
        final int row = selectedRow;
        setMode(Mode.INSERT);
        /**
         * Force the table to layout before selecting the newly added row.
         * Without this call, the selection will land on existing row at
         * specified index because the new row is not yet actually added to the
         * table. It makes the editor controls are not displayed in agileEditing
         * mode.
         */
        tblView.layout();
        tblView.requestFocus();
        showRow(row);
        tblView.getSelectionModel().select(row, tblView.getColumns().get(0));
    }

    /**
     * Save changes. This method is called when pressing save button
     */
    public void save() {
        /**
         * In case there is a cell being edited, call clearSelection() to trigger
         * commitEdit() in the edited cell.
         */
        tblView.getSelectionModel().clearSelection();


        try {
            if (lstChangedRow.isEmpty()) {
                setMode(Mode.READ);
                return;
            }
            if (!behavior.validate(this, lstChangedRow)) {
                return;
            }
            Mode prevMode = getMode();
            if (useBackgroundTaskToSave) {
                service.runSaveInBackground(prevMode);
            } else {
                List<R> lstResult = new ArrayList<>();
                if (isEditMode()) {
                    lstResult = behavior.update(lstChangedRow);
                } else if (isInsertMode()) {
                    lstResult = behavior.insert(lstChangedRow);
                }
                postSaveAction(lstResult, prevMode);
            }
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    /**
     * Edit table. This method is called when pressing edit button.
     * 仅修改mode状态
     */
    public final void edit() {
        if (behavior.canEdit(tblView.getSelectedItem())) {
            setMode(Mode.EDIT);
        }
    }

    /**
     * Delete selected row. This method is called when pressing delete button.
     * It will delete selected record(s)
     */
    public void delete() {
        /**
         * Delete row that is not yet persisted in database.
         */
        if (isInsertMode()) {
            TablePosition<R, ?> selectedCell = tblView.getSelectedCellPosition(0);
            int selectedRow = selectedCell.getRow();
            lstChangedRow.removeAll(tblView.getSelectionModel().getSelectedItems());
            tblView.getSelectionModel()
                    .clearSelection();// it is needed if agile editing is enabled to trigger content display change later
            tblView.getItems().remove(selectedRow);
            tblView.layout();//relayout first before set selection. Without this, cell contend display won't be set propertly
            tblView.requestFocus();
            if (selectedRow == tblView.getItems().size()) {
                selectedRow--;
            }
            if (lstChangedRow.contains(tblView.getItems().get(selectedRow))) {
                tblView.getSelectionModel().select(selectedRow, selectedCell.getTableColumn());
            } else {
                tblView.getSelectionModel().select(selectedRow - 1, selectedCell.getTableColumn());
            }
            return;
        }

        /**
         * Delete persistence record.
         */
        try {
            if (!behavior.canDelete(this)) {
                return;
            }
            int selectedRow = tblView.getSelectionModel().getSelectedIndex();
            List<R> lstToDelete = new ArrayList<>(tblView.getSelectionModel().getSelectedItems());

            if (useBackgroundTaskToDelete) {
                service.runDeleteInBackground(lstToDelete, selectedRow);
            } else {
                behavior.delete(lstToDelete);
                postDeleteAction(lstToDelete, selectedRow);
            }
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    /**
     * Export table to Excel. All pages will be exported. The criteria set on
     * columns are taken into account. This method is called by export button.
     */
    public void export() {
        if (useBackgroundTaskToLoad) {
            service.runExportInBackground();
        } else {
            if (exportMode == ExportMode.ALL_PAGES) {
                behavior.exportToExcel("Override TableController.exportToExcel to reset the title.", pageSize.get(), TableControl.this, lstCriteria);
            } else {
                behavior.exportToExcelCurrentPage("Override TableController.exportToExcelCurrentPage to reset the title.", TableControl.this);
            }
        }
    }

    private class StartIndexChangeListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
            reload();
        }
    }

    private final InvalidationListener tableCriteriaListener = observable -> {
        if (reloadOnCriteriaChange) {
            reloadFirstPage();
        }
    };

    private class SortTypeChangeListener implements InvalidationListener {

        @Override
        public void invalidated(Observable o) {
            /**
             * If the column is not in sortOrder list, just ignore. It avoids
             * intermittent duplicate reload() calling
             */
            TableColumn<?, ?> col = (TableColumn<?, ?>) ((SimpleObjectProperty<?>) o).getBean();
            if (!tblView.getSortOrder().contains(col) || !stageShown) {
                return;
            }
            reload();
            resetColumnSortConfig();
        }
    }

    /**
     * 每页记录条数
     */
    private final IntegerProperty pageSize = new SimpleIntegerProperty(20);

    /**
     * Set max record per retrieval. It will be the parameter in {@link TableControlBehavior#loadData(int, java.util.List, java.util.List, java.util.List, int) loadData} maxResult parameter
     * @param maxRecord max number of records per-retrieval
     */
    public final void setPageSize(int maxRecord) {
        this.pageSize.set(maxRecord);
    }

    /**
     * Get max number of records per-retrieval.
     * @return max number of records per-retrieval
     */
    public final int getPageSize() {
        return pageSize.get();
    }

    public final IntegerProperty pageSizeProperty() {
        return pageSize;
    }

    /**
     * Set the class of object that will be displayed in the table.
     * @param recordClass row record class
     */
    public final void setRecordClass(Class<R> recordClass) {
        this.recordClass = recordClass;
    }

    public final void setFitColumnAfterReload(boolean fitColumnAfterReload) {
        this.fitColumnAfterReload = fitColumnAfterReload;
    }

    /**
     * @return @see #setReloadOnCriteriaChange(boolean)
     */
    public final boolean isReloadOnCriteriaChange() {
        return reloadOnCriteriaChange;
    }

    /**
     * Set it too false to prevent auto-reloading when there is table criteria
     * change. It is useful if we want to change tableCriteria of several
     * columns at a time. After that set it to true and call {@link TableControl#reloadFirstPage()
     * }
     * @param reloadOnCriteriaChange reloadOnCriteriaChange
     */
    public final void setReloadOnCriteriaChange(boolean reloadOnCriteriaChange) {
        this.reloadOnCriteriaChange = reloadOnCriteriaChange;
    }

    /**
     * Get displayed record. It is just the same with
     * {@link TableView#getItems()}
     * @return all items in the TableView
     */
    public final ObservableList<R> getRecords() {
        return tblView.getItems();
    }

    /**
     * Add button to toolbar. The button's style is set by this method. Make
     * sure to add image on the button and also define the action method.
     * @param btn Button
     */
    public final void addButton(Button btn) {
        addNode(btn);
    }

    /**
     * Add JavaFX Node to table's toolbar
     * @param node Node
     */
    public void addNode(Node node) {
        if (node instanceof Button) {
            node.getStyleClass().add("flat-button");
            ((Button) node).setMaxHeight(Double.MAX_VALUE);
        }

        toolBar.addOptionalNode(node);
    }

    /**
     * Set UI component visibility.
     * @param visible  是否可见
     * @param controls 控件类型
     */
    public void setVisibleComponents(boolean visible, TableControl.Component... controls) {
        for (Component comp : controls) {

            if (comp == Component.FOOTER) {
                if (!visible) {
                    this.getChildren().remove(footer);
                } else if (!this.getChildren().contains(footer)) {
                    this.getChildren().add(footer);
                }
            } else if (comp == Component.TOOLBAR) {
                if (!visible) {
                    this.getChildren().remove(toolBar);
                } else if (!this.getChildren().contains(toolBar)) {
                    this.getChildren().add(0, toolBar);
                }
            } else {
                toolBar.setComponentVisible(comp, visible);
            }
        }
    }

    /**
     * 获取模式
     * @return {@link Mode}
     */
    public final Mode getMode() {
        return modeProperty().get();
    }

    /**
     * 模式属性
     * @return {@link SimpleObjectProperty}<{@link Mode}>
     */
    public final SimpleObjectProperty<Mode> modeProperty() {
        return mode;
    }

    public final CustomTableView<R> getTableView() {
        return tblView;
    }

    /**
     * Check if a record is editable. After ensure that the item is not null and
     * the mode is not {@link Mode#INSERT} it will propagate the call to
     * {@link TableControlBehavior#isRecordEditable}.
     * @param item 行对象
     * @return false if item == null. True if mode is INSERT. otherwise depends
     * on the logic in {@link TableControlBehavior#isRecordEditable}
     * @see TableControlBehavior#isRecordEditable(java.lang.Object)
     */
    public final boolean isRecordEditable(R item) {
        if (item == null) {
            return false;
        }
        if (isInsertMode()) {
            return true;
        }
        return behavior.isRecordEditable(item);
    }

    /**
     * 加载数据操作，将数据填充到Table上，更新一些状态
     * @param vol 表格分页数据
     */
    private void postLoadAction(TableData<R> vol) {
        if (vol.getRows() == null) {
            vol.setRows(new ArrayList<>());
        }
        totalRows = vol.getTotalRows();
        //keep track of previous selected row
        int selectedIndex = tblView.getSelectionModel().getSelectedIndex();
        TableColumn<R, Object> selectedColumn = null;
        if (tblView.hasSelectedCells()) {
            // 默认选择第一列
            selectedColumn = tblView.getSelectedColumn(0);
        }

        if (hasEditingCell()) {
            /**
             * Trigger cancelEdit if there is cell being edited. Otherwise,
             * ArrayIndexOutOfBound exception happens since tblView items are
             * cleared (see next lines) but setOnEditCommit listener is executed.
             */
            tblView.edit(-1, 0);
        }
        tblView.getItems().setAll(vol.getRows());
        if (selectedIndex < vol.getRows().size()) {
            tblView.getSelectionModel().select(selectedIndex, selectedColumn);
        } else {
            tblView.getSelectionModel().select(vol.getRows().size() - 1, selectedColumn);
        }
        long page = vol.getTotalRows() / pageSize.get();
        if (vol.getTotalRows() % pageSize.get() != 0) {
            page++;
        }
        startIndex.removeListener(startIndexChangeListener);
        paginationControl.updatePage(page, startIndex.get() / pageSize.get());
        startIndex.addListener(startIndexChangeListener);

        // 切换分页按钮的显示状态
        paginationControl.toggleButtons(vol.isMoreRows(), startIndex.get() == 0);

        setMode(Mode.READ);

        clearChange();
        if (fitColumnAfterReload) {
            for (TableColumn<R, ?> clm : tblView.getColumns()) {
                resizeToFit(clm);
            }
        }
        footer.updateTotalRecord(totalRows);
        for (TableColumn<R, ?> clm : getLeafColumns()) {
            if (clm instanceof BaseColumn<R, ?> baseColumn) {
                baseColumn.getInvalidRecordMap().clear();
            }
        }
        behavior.postLoadData();
    }


    private void postSaveAction(List<R> lstResult, Mode prevMode) {
        mode.set(Mode.READ);
        /**
         * In case objects in lstResult differ with original object. Ex: In SOA
         * architecture, sent objects always differ with received object due to
         * serialization.
         */
        int i = 0;
        for (R row : lstChangedRow) {
            int index = tblView.getItems().indexOf(row);
            tblView.getItems().remove((int) index);
            tblView.getItems().add(index, lstResult.get(i));
            i++;
        }

        /**
         * Refresh cells. They won't refresh automatically if the entity's
         * properties bound to the cells are not javaFX property object.
         */
        clearChange();
        behavior.postSave(prevMode);
    }

    private void postDeleteAction(List<R> lstDeleted, int selectedRow) {
        tblView.getItems().removeAll(lstDeleted);
        /**
         * select a row
         */
        if (!tblView.getItems().isEmpty()) {
            if (selectedRow >= tblView.getItems().size()) {
                tblView.getSelectionModel().select(tblView.getItems().size() - 1);
            } else {
                tblView.getSelectionModel().select(selectedRow);
            }
        }
        totalRows = totalRows - lstDeleted.size();

        footer.updateTotalRecord(totalRows);

        tblView.requestFocus();
    }

    private void saveColumnPosition() {
        if (configurationID == null || configurationID.trim().length() == 0) {
            return;
        }
        new Thread(() -> {
            Map<String, String> mapProperties = new HashMap<>();
            for (TableColumn<R, ?> column : columns) {
                int oriIndex = lstTableColumnsOriginalOrder.indexOf(column);
                int newIndex = columns.indexOf(column);
                mapProperties.put(configurationID + "." + oriIndex + ".pos", String.valueOf(newIndex));
            }
            try {
                TiwulFXUtil.writeProperties(mapProperties);
            } catch (Exception ex) {
                handleException(ex);
            }
        }).start();
        footer.configureResetMenuItem();
    }

    private void attachWindowVisibilityListener() {
        this.sceneProperty().addListener((ov, t, scene) -> {
            if (scene != null) {
                /**
                 * This method could be executed again if this table is inside a detachabletabpane.
                 */
                if (stageShown) return;
                stageShown = true;
                /**
                 * TODO Once this code is executed, the scene listener need to be removed
                 * to avoid potential memory leak.
                 * This code need to be initialized once only.
                 */
                readColumnPosition();
                readColumnOrderConfig();
                columns.addListener((ListChangeListener<TableColumn<R, ?>>) change -> {
                    while (change.next()) {
                        if (change.wasReplaced()) {
                            saveColumnPosition();
                        }
                    }
                });
                if (configurationID != null && configurationID.trim().length() != 0) {
                    for (final TableColumn<R, ?> clm : getColumns()) {
                        clm.widthProperty().addListener((observable, oldValue, newValue) -> {
                            if (suppressWidthConfigListener) return;
                            int clmIdx = lstTableColumnsOriginalOrder.indexOf(clm);
                            try {
                                TiwulFXUtil.writeProperties(configurationID + "." + clmIdx + ".width", newValue + "");
                            } catch (Exception ex) {
                                logger.log(Level.WARNING, "Unable to save column width information. Column index: " + clmIdx, ex);
                            }
                        });
                    }
                }
            }
        });
    }

    public void resetColumnPosition() {
        new Thread(() -> {
            List<String> propNames = new ArrayList<>();
            for (int i = 0; i < columns.size(); i++) {
                propNames.add(configurationID + "." + i + ".pos");
                propNames.add(configurationID + "." + i + ".width");
                propNames.add(configurationID + "." + i + ".sort");
            }
            try {
                TiwulFXUtil.deleteProperties(propNames);
            } catch (Exception ex) {
                handleException(ex);
            }
        }).start();
        try {
            suppressWidthConfigListener = true;

            footer.removeResetItem();

            if (lstTableColumnsOriginalOrder.size() == lstOriColumnWidth.size()) {
                for (int i = 0; i < lstTableColumnsOriginalOrder.size(); i++) {
                    final TableColumn<R, ?> clm = lstTableColumnsOriginalOrder.get(i);
                    Double prefWidth = lstOriColumnWidth.get(i);
                    if (prefWidth != null && prefWidth > 0) {
                        clm.setPrefWidth(prefWidth);
                    }
                }
            }
            columns.clear();
            columns.addAll(lstTableColumnsOriginalOrder);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "unexpected error", ex);
        } finally {
            suppressWidthConfigListener = false;
        }
    }

    private void resetColumnSortConfig() {
        if (configurationID == null || configurationID.trim().isEmpty() || suppressSortConfigListener) {
            return;
        }
        List<TableColumn<R, ?>> lstLeafColumns = getLeafColumns();
        Runnable runnable = () -> {
            List<String> propNames = new ArrayList<>();
            for (int i = 0; i < lstLeafColumns.size(); i++) {
                propNames.add(configurationID + "." + i + ".sort");
            }

            try {
                TiwulFXUtil.deleteProperties(propNames);

                Map<String, String> mapProperties = new LinkedHashMap<>();
                for (int i = 0; i < tblView.getSortOrder().size(); i++) {
                    TableColumn<R, ?> t = tblView.getSortOrder().get(i);
                    int oriIndex = lstTableColumnsOriginalOrder.indexOf(t);

                    mapProperties.put(configurationID + "." + oriIndex + ".sort", t.getSortType() + "," + i);
                }
                if (!mapProperties.isEmpty()) {
                    TiwulFXUtil.writeProperties(mapProperties);
                }

            } catch (Exception ex) {
                handleException(ex);
            }
        };
        new Thread(runnable).start();
    }

    private void readColumnOrderConfig() {
        if (configurationID == null || configurationID.trim().isEmpty()) {
            return;
        }
        try {
            suppressSortConfigListener = true;
            TableColumn[] arrColumn = new TableColumn[lstTableColumnsOriginalOrder.size()];
            for (int i = 0; i < lstTableColumnsOriginalOrder.size(); i++) {
                String pos = TiwulFXUtil.readProperty(configurationID + "." + i + ".sort");
                if (pos != null) {
                    String[] infos = pos.split(",");
                    lstTableColumnsOriginalOrder.get(i).setSortType(SortType.valueOf(infos[0]));
                    int sortingIndex = Integer.parseInt(infos[1]);
                    arrColumn[sortingIndex] = lstTableColumnsOriginalOrder.get(i);
                }
                String stringWidth = TiwulFXUtil.readProperty(configurationID + "." + i + ".width");
                if (stringWidth != null && !stringWidth.isBlank()) {
                    try {
                        double dwidth = Double.parseDouble(stringWidth);
                        lstTableColumnsOriginalOrder.get(i).setPrefWidth(dwidth);
                    } catch (Exception ex) {
                        logger.warning("Invalid column width configuration: " + configurationID + "." + i + ".width");
                    }
                }
            }
            List<TableColumn<R, ?>> lstSorted = new ArrayList<>();
            for (TableColumn clm : arrColumn) {
                if (clm != null) lstSorted.add(clm);
            }
            if (!lstSorted.isEmpty()) {
                tblView.getSortOrder().clear();
                for (TableColumn<R, ?> tableColumn : lstSorted) {
                    tblView.getSortOrder().add(tableColumn);
                }
            }
        } catch (Exception ex) {
            handleException(ex);
        } finally {
            suppressSortConfigListener = false;
        }
    }

    private List<TableColumn<R, ?>> lstTableColumnsOriginalOrder;
    private List<Double> lstOriColumnWidth;

    private void readColumnPosition() {
        try {
            lstTableColumnsOriginalOrder = new ArrayList<>(columns);
            if (configurationID == null || configurationID.trim().length() == 0) {
                return;
            }
            lstOriColumnWidth = new ArrayList<>();
            lstTableColumnsOriginalOrder.forEach(item -> lstOriColumnWidth.add(item.getPrefWidth()));
            Map<Integer, TableColumn<R, ?>> map = new HashMap<>();
            int maxIndex = 0;
            for (int i = 0; i < columns.size(); i++) {
                String pos = TiwulFXUtil.readProperty(configurationID + "." + i + ".pos");
                if (pos != null && pos.matches("[0-9]*")) {
                    map.put(Integer.valueOf(pos), columns.get(i));
                    maxIndex = Math.max(maxIndex, Integer.parseInt(pos));
                }
            }

            if (!map.isEmpty()) {
                if (map.size() != lstTableColumnsOriginalOrder.size()) {
                    //Either the configuration file is corrupted or new column is added. Reset column position.
                    resetColumnPosition();
                    return;
                } else {
                    columns.clear();

                    for (int i = 0; i <= maxIndex; i++) {
                        TableColumn<R, ?> tc = map.get(i);
                        if (tc != null) {
                            columns.add(i, tc);
                        }
                    }
                    footer.configureResetMenuItem();
                }
            }
        } catch (Exception ex) {
            this.handleException(ex);
            resetColumnPosition();
        }
    }

    /**
     * Get configuration ID.
     * @return configurationID
     * @see #setConfigurationID(java.lang.String) for detailed explanation
     */
    public String getConfigurationID() {
        return configurationID;
    }

    public final boolean isBusy() {
        return service.isRunning();
    }

    public final ReadOnlyBooleanProperty busyProperty() {
        return service.runningProperty();
    }

    /**
     * If it is set, the columns position, width and sorting information will be saved to a configuration file
     * located in a folder inside user's home directory. Call {@link TiwulFXUtil#setApplicationId(java.lang.String, java.lang.String)}
     * to set the folder name. The configurationID must be unique across all TableControl in an application but it is
     * not enforced.
     * @param configurationID must be unique across all TableControls in an application
     * @see TiwulFXUtil#setApplicationId(java.lang.String, java.lang.String)
     */
    public void setConfigurationID(String configurationID) {
        this.configurationID = configurationID;
    }

    private ExceptionHandler exceptionHandler = TiwulFXUtil.getExceptionHandler();

    /**
     * Get {@link ExceptionHandler}. The default is what returned by
     * {@link TiwulFXUtil#getExceptionHandler()}
     * <p>
     * @return an implementation of ExceptionHandler that is called when
     * uncatched exception happens
     */
    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    /**
     * Set custom {@link ExceptionHandler} to override the default taken from
     * {@link TiwulFXUtil#getExceptionHandler()}. To override application-wide
     * {@link ExceptionHandler} call
     * {@link TiwulFXUtil#setExceptionHandlerFactory(ExceptionHandlerFactory)}
     * <p>
     * @param exceptionHandler custom Exception Handler
     */
    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    private void handleException(Throwable throwable) {
        Window window = null;
        if (getScene() != null) {
            window = getScene().getWindow();
        }
        exceptionHandler.handleException(throwable, window);
    }

    /**
     * Get the export-to-excel mode. Default value is configured in {@link TiwulFXUtil#DEFAULT_EXPORT_MODE}
     * @return
     */
    public ExportMode getExportMode() {
        return exportMode;
    }

    /**
     * Set the export-to-excel mode. Default value is configured in {@link TiwulFXUtil#DEFAULT_EXPORT_MODE}
     * @param exportMode 导出模式
     * @see ExportMode
     */
    public void setExportMode(ExportMode exportMode) {
        this.exportMode = exportMode;
    }

    /**
     * Check if this TableControl use background task to execute Load and Export.
     * Default value for this property is taken from
     * {@link TiwulFXUtil#DEFAULT_USE_BACKGROUND_TASK_TO_LOAD}.
     * @return useBackgroundTaskToLoad
     */
    public boolean isUseBackgroundTaskToLoad() {
        return useBackgroundTaskToLoad;
    }

    /**
     * Check if this TableControl use background task to execute save.
     * Default value for this property is taken from
     * {@link TiwulFXUtil#DEFAULT_USE_BACKGROUND_TASK_TO_SAVE}.
     * @return useBackgroundTaskToSave
     */
    public boolean isUseBackgroundTaskToSave() {
        return useBackgroundTaskToSave;
    }

    /**
     * Check if this TableControl use background task to execute delete.
     * Default value for this property is taken from
     * {@link TiwulFXUtil#DEFAULT_USE_BACKGROUND_TASK_TO_DELETE}.
     * @return useBackgroundTaskToDelete
     */
    public boolean isUseBackgroundTaskToDelete() {
        return useBackgroundTaskToDelete;
    }

    private class TableControlService extends Service {

        private List<String> lstSortedColumn = new ArrayList<>();
        private List<SortType> sortingOrders = new ArrayList<>();
        private Mode prevMode;
        private int actionCode;
        private List<R> lstToDelete;
        private int selectedRow;

        public void runLoadInBackground(List<String> lstSortedColumn, List<SortType> sortingOrders) {
            this.lstSortedColumn = lstSortedColumn;
            this.sortingOrders = sortingOrders;
            actionCode = 0;
            this.restart();
        }

        public void runSaveInBackground(Mode prevMode) {
            this.prevMode = prevMode;
            actionCode = 1;
            this.restart();
        }

        public void runDeleteInBackground(List<R> lstToDelete, int selectedRow) {
            this.lstToDelete = lstToDelete;
            this.selectedRow = selectedRow;
            actionCode = 2;
            this.restart();
        }

        public void runExportInBackground() {
            actionCode = 3;
            this.restart();
        }

        @Override
        protected Task createTask() {
            if (actionCode == 0) {
                return new LoadDataTask(lstSortedColumn, sortingOrders);
            } else if (actionCode == 1) {
                return new SaveTask(prevMode);
            } else if (actionCode == 2) {
                return new DeleteTask(lstToDelete, selectedRow);
            } else if (actionCode == 3) {
                return new ExportTask();
            }
            return null;
        }

    }

    private class LoadDataTask extends Task<TableData<R>> {

        private List<String> lstSortedColumn;
        private List<SortType> sortingOrders;

        public LoadDataTask(List<String> sortedColumns, List<SortType> sortingOrders) {
            this.lstSortedColumn = sortedColumns;
            this.sortingOrders = sortingOrders;
            setOnFailed((WorkerStateEvent event) -> handleException(getException()));
            setOnSucceeded((WorkerStateEvent event) -> postLoadAction(getValue()));
        }

        @Override
        protected TableData<R> call() {
            return behavior.loadData(startIndex.get(), lstCriteria, lstSortedColumn, sortingOrders, pageSize.get());
        }
    }

    private class SaveTask extends Task<List<R>> {

        public SaveTask(Mode prevMode) {
            setOnFailed((WorkerStateEvent event) -> handleException(getException()));
            setOnSucceeded((WorkerStateEvent event) -> postSaveAction(getValue(), prevMode));
        }

        @Override
        protected List<R> call() throws Exception {
            List<R> lstResult = new ArrayList<>();
            if (mode.get().equals(Mode.EDIT)) {
                lstResult = behavior.update(lstChangedRow);
            } else if (mode.get().equals(Mode.INSERT)) {
                lstResult = behavior.insert(lstChangedRow);
            }
            return lstResult;
        }
    }

    private class DeleteTask extends Task<Void> {
        private final List<R> lstToDelete;

        public DeleteTask(List<R> lstToDelete, int selectedRow) {
            this.lstToDelete = lstToDelete;
            setOnFailed((WorkerStateEvent event) -> handleException(getException()));
            setOnSucceeded((WorkerStateEvent event) -> postDeleteAction(lstToDelete, selectedRow));
        }

        @Override
        protected Void call() {
            behavior.delete(lstToDelete);
            return null;
        }
    }

    private class ExportTask extends Task<Void> {

        public ExportTask() {
            setOnFailed((WorkerStateEvent event) -> handleException(getException()));
        }

        @Override
        protected Void call() throws Exception {
            if (exportMode == ExportMode.ALL_PAGES) {
                behavior.exportToExcel("Override TableController.exportToExcel to reset the title.", pageSize.get(), TableControl.this, lstCriteria);
            } else {
                behavior.exportToExcelCurrentPage("Override TableController.exportToExcelCurrentPage to reset the title.", TableControl.this);
            }
            return null;
        }
    }

    /****************************************************************************************
     * Public API
     *****************************************************************************************/

    /**
     * 是否插入模式
     * @return boolean
     */
    public final boolean isInsertMode() {
        return getMode() == Mode.INSERT;
    }

    /**
     * 是否编辑模式
     * @return boolean
     */
    public final boolean isEditMode() {
        return getMode() == Mode.EDIT;
    }

    /**
     * 是否读模式
     * @return boolean
     */
    public final boolean isReadMode() {
        return getMode() == Mode.READ;
    }

    public final boolean isEditable() {
        return tblView.isEditable();
    }

    /**
     * 表格右键菜单
     */
    static class TableContextMenu extends ContextMenu {

        TableControl<?> tableControl;

        /**
         * 粘贴菜单项
         */
        private MenuItem miPaste;

        private MenuItem getPasteMenuItem() {
            if (miPaste == null) {
                miPaste = new MenuItem(TiwulFXUtil.getLiteral("paste"));
                miPaste.setOnAction(event -> tableControl.paste());
            }
            return miPaste;
        }

        public void removePasteMenuItem() {
            this.getItems().remove(getPasteMenuItem());
        }

        public TableContextMenu(TableControl<?> tableControl) {
            this.tableControl = tableControl;
            this.setAutoHide(true);
            MenuItem copyCellMenu = new MenuItem(TiwulFXUtil.getLiteral("copy.cell"));
            copyCellMenu.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
            copyCellMenu.setOnAction(e -> tableControl.copyCell());
            this.getItems().add(copyCellMenu);
            MenuItem copyRowMenu = new MenuItem(TiwulFXUtil.getLiteral("copy.row"));
            copyRowMenu.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
            copyRowMenu.setOnAction(e -> tableControl.copyRow());
            this.getItems().add(copyRowMenu);
            MenuItem browseRowMenu = new MenuItem(TiwulFXUtil.getLiteral("browse.row"));
            browseRowMenu.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.ALT_DOWN));
            browseRowMenu.setOnAction(e -> tableControl.browseSelectedRow());
            this.getItems().add(browseRowMenu);
        }

        public void removeSearchMenuItemIfNull() {
            if (searchMenuItem != null) {
                this.getItems().remove(searchMenuItem);
            }
        }

        public void addPasteMenuItemIfNotContains() {
            if (!this.getItems().contains(getPasteMenuItem())) {
                this.getItems().add(getPasteMenuItem());
            }
        }

        /**
         * 搜索菜单
         */
        private MenuItem searchMenuItem;

        public void setSearchMenuItem(MenuItem searchMenuItem) {
            this.searchMenuItem = searchMenuItem;
        }

        public void addSearchMenuItem(MenuItem searchMenuItem, boolean filterable) {
            setSearchMenuItem(searchMenuItem);
            if (searchMenuItem != null && filterable) {
                this.getItems().add(0, searchMenuItem);
            }
        }
    }
}
