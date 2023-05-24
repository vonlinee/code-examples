package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.*;
import com.panemu.tiwulfx.control.TableIntializer;
import com.panemu.tiwulfx.dialog.MessageDialog;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import com.panemu.tiwulfx.utils.ClassUtils;
import com.panemu.tiwulfx.utils.ClipboardUtils;
import javafx.application.Platform;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.apache.commons.beanutils.PropertyUtils;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableControl<R> extends VBox {

    private Button btnAdd;
    private Button btnEdit;
    private Button btnDelete;
    private Button btnFirstPage;
    private Button btnLastPage;
    private Button btnNextPage;
    private Button btnPrevPage;
    private Button btnReload;
    private Button btnSave;
    private Button btnExport;
    private ComboBox<Integer> cmbPage;
    private Label lblRowIndex;
    private Label lblTotalRow;
    private final CustomTableView<R> tblView = new CustomTableView<>();
    private Region spacer;
    private HBox paginationBox;
    private ToolBar toolbar;
    private StackPane footer;
    private TableControlBehavior<R> behavior;
    private final SimpleIntegerProperty startIndex = new SimpleIntegerProperty(0);
    private final StartIndexChangeListener startIndexChangeListener = new StartIndexChangeListener();
    private final InvalidationListener sortTypeChangeListener = new SortTypeChangeListener();
    private final ReadOnlyObjectWrapper<Mode> mode = new ReadOnlyObjectWrapper<>(null);
    private long totalRows = 0;
    private Integer currentPage = 0;

    public final int getStartIndex() {
        return startIndex.get();
    }

    public final SimpleIntegerProperty startIndexProperty() {
        return startIndex;
    }

    /**
     * 存放修改的行
     * 编辑模式下：被修改的行高亮显示
     */
    private final ObservableList<R> lstChangedRow = FXCollections.observableArrayList();
    private Class<R> recordClass;
    private boolean fitColumnAfterReload = false;
    private final List<TableCriteria> lstCriteria = new ArrayList<>();
    private boolean reloadOnCriteriaChange = true;
    private boolean directEdit = false;
    private final ObservableList<TableColumn<R, ?>> columns = tblView.getColumns();
    private final ProgressBar progressIndicator = new ProgressBar();
    private final TableControlService service = new TableControlService();
    private MenuButton menuButton;
    private MenuItem resetItem;
    private String configurationID;
    private boolean suppressSortConfigListener = false;
    private boolean suppressWidthConfigListener = false;
    private static final Logger logger = Logger.getLogger(TableControl.class.getName());
    private final List<RowBrowser> lstRowBrowser = new ArrayList<>();

    private boolean stageShown = false;
    private ExportMode exportMode = ExportMode.CURRENT_PAGE;
    private boolean useBackgroundTaskToLoad = TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_LOAD;
    private boolean useBackgroundTaskToSave = TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_SAVE;
    private boolean useBackgroundTaskToDelete = TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_DELETE;

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

        btnAdd.disableProperty().bind(mode.isEqualTo(Mode.EDIT));
        btnEdit.disableProperty().bind(mode.isNotEqualTo(Mode.READ));
        btnSave.disableProperty().bind(mode.isEqualTo(Mode.READ));
        btnDelete.disableProperty().bind(new BooleanBinding() {
            {
                super.bind(mode, tblView.getSelectionModel().selectedItemProperty(), lstChangedRow);
            }

            @Override
            protected boolean computeValue() {
                return (isInsertMode() && lstChangedRow.size() < 2) || tblView.getSelectedItem() == null || isEditMode();
            }
        });
        mode.addListener((ov, t, t1) -> {
            if (t1 == Mode.READ) {
                directEdit = false;
            }
        });

        tblView.editableProperty().bind(mode.isNotEqualTo(Mode.READ));
        // 列选择模式
        tblView.getSelectionModel().cellSelectionEnabledProperty().bind(tblView.editableProperty());
        tblView.getSelectionModel().selectedIndexProperty()
                .addListener((ov, t, t1) -> lblRowIndex.setText(TiwulFXUtil.getLiteral("row.param", (currentPage * maxResult.get() + t1.intValue() + 1))));

        tblView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                directEdit = false;
            } else if (event.getCode() == KeyCode.ENTER && isReadMode()) {
                getBehavior().doubleClick((getSelectedItem()));
            }
        });
        // Define policy for TAB key press
        tblView.addEventFilter(KeyEvent.KEY_PRESSED, tableKeyListener);
        /**
         * In INSERT mode, only inserted row that is focusable
         * Prevent moving focus to not-inserted-row in INSERT mode
         */
        tblView.getFocusModel().focusedCellProperty().addListener((observable, oldValue, newValue) -> {
            if (!resettingRecords && tblView.isEditable() && directEdit && isAgileEditing()) {
                tblView.edit(newValue);
            }
            if (!isInsertMode() || newValue.getRow() == -1 || oldValue.getRow() == -1) {
                return;
            }
            Platform.runLater(() -> {
                R oldRow = tblView.getItems().get(oldValue.getRow());
                R newRow = tblView.getItems().get(newValue.getRow());
                System.out.println("oldRow => " + oldRow);
                System.out.println("newRow => " + newRow);
                if (lstChangedRow.contains(oldRow) && !lstChangedRow.contains(newRow)) {
                    tblView.getFocusModel().focus(oldValue);
                    tblView.getSelectionModel().select(oldValue.getRow(), oldValue.getTableColumn());
                }
            });
        });

        tblView.setOnMouseReleased(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (cm.isShowing()) {
                    cm.hide();
                }
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    if (tblView.getSelectionModel().getSelectedCells().isEmpty()) {
                        return;
                    }
                    TablePosition<R, ?> pos = tblView.getSelectedCellPosition(0);
                    TableColumn<R, ?> column = null;
                    if (pos != null) {
                        column = pos.getTableColumn();
                    }
                    if (column == null) {
                        column = tblView.getSelectedColumn();
                    }
                    if (searchMenuItem != null) {
                        cm.getItems().remove(searchMenuItem);
                    }
                    cm.getItems().remove(getPasteMenuItem());
                    if (column instanceof BaseColumn) {
                        BaseColumn clm = (BaseColumn) column;
                        int row = tblView.getSelectionModel().getSelectedIndex();
                        clm.setDefaultSearchValue(column.getCellData(row));
                        searchMenuItem = clm.getSearchMenuItem();
                        if (searchMenuItem != null && clm.isFilterable()) {
                            cm.getItems().add(0, searchMenuItem);
                        }
                        if (!isReadMode() && !hasEditingCell() && ClipboardUtils.hasString()) {
                            if (!cm.getItems().contains(getPasteMenuItem())) {
                                cm.getItems().add(getPasteMenuItem());
                            }
                        }
                    }
                    cm.show(tblView, event.getScreenX(), event.getScreenY());
                }
            }
        });

        cm = createContextMenu();
        setToolTips();
        // create custom row factory that can intercept double click on grid row
        tblView.setRowFactory(param -> new TableRowControl<>(TableControl.this));

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

    private int lastColumnIndex = 0;

    public final ObservableList<TableColumn<R, ?>> getColumns() {
        return columns;
    }

    public ObservableList<R> getChangedRecords() {
        return lstChangedRow;
    }

    public Button getButtonAdd() {
        return btnAdd;
    }

    private void initControls() {
        this.getStyleClass().add("table-control");
        btnAdd = buildButton(TiwulFXUtil.getGraphicFactory().createAddGraphic());
        btnDelete = buildButton(TiwulFXUtil.getGraphicFactory().createDeleteGraphic());
        btnEdit = buildButton(TiwulFXUtil.getGraphicFactory().createEditGraphic());
        btnExport = buildButton(TiwulFXUtil.getGraphicFactory().createExportGraphic());
        btnReload = buildButton(TiwulFXUtil.getGraphicFactory().createReloadGraphic());
        btnSave = buildButton(TiwulFXUtil.getGraphicFactory().createSaveGraphic());

        btnFirstPage = new Button();
        btnFirstPage.setGraphic(TiwulFXUtil.getGraphicFactory().createPageFirstGraphic());
        btnFirstPage.setOnAction(paginationHandler);
        btnFirstPage.setDisable(true);
        btnFirstPage.setFocusTraversable(false);
        btnFirstPage.getStyleClass().addAll("pill-button", "pill-button-left");

        btnPrevPage = new Button();
        btnPrevPage.setGraphic(TiwulFXUtil.getGraphicFactory().createPagePrevGraphic());
        btnPrevPage.setOnAction(paginationHandler);
        btnPrevPage.setDisable(true);
        btnPrevPage.setFocusTraversable(false);
        btnPrevPage.getStyleClass().addAll("pill-button", "pill-button-center");

        btnNextPage = new Button();
        btnNextPage.setGraphic(TiwulFXUtil.getGraphicFactory().createPageNextGraphic());
        btnNextPage.setOnAction(paginationHandler);
        btnNextPage.setDisable(true);
        btnNextPage.setFocusTraversable(false);
        btnNextPage.getStyleClass().addAll("pill-button", "pill-button-center");

        btnLastPage = new Button();
        btnLastPage.setGraphic(TiwulFXUtil.getGraphicFactory().createPageLastGraphic());
        btnLastPage.setOnAction(paginationHandler);
        btnLastPage.setDisable(true);
        btnLastPage.setFocusTraversable(false);
        btnLastPage.getStyleClass().addAll("pill-button", "pill-button-right");

        cmbPage = new ComboBox<>();
        cmbPage.setEditable(true);
        cmbPage.setOnAction(paginationHandler);
        cmbPage.setFocusTraversable(false);
        cmbPage.setDisable(true);
        cmbPage.getStyleClass().addAll("combo-page");
        cmbPage.setPrefWidth(75);

        paginationBox = new HBox();
        paginationBox.setAlignment(Pos.CENTER);
        paginationBox.getChildren().addAll(btnFirstPage, btnPrevPage, cmbPage, btnNextPage, btnLastPage);

        spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolbar = new ToolBar(btnReload, btnAdd, btnEdit, btnSave, btnDelete, btnExport, spacer, paginationBox);
        toolbar.getStyleClass().add("table-toolbar");

        footer = new StackPane();
        footer.getStyleClass().add("table-footer");
        lblRowIndex = new Label();
        lblTotalRow = new Label();
        menuButton = new TableControlMenu(this);
        StackPane.setAlignment(lblRowIndex, Pos.CENTER_LEFT);
        StackPane.setAlignment(lblTotalRow, Pos.CENTER);
        StackPane.setAlignment(menuButton, Pos.CENTER_RIGHT);

        lblTotalRow.visibleProperty().bind(progressIndicator.visibleProperty().not());

        progressIndicator.setProgress(-1);
        progressIndicator.visibleProperty().bind(service.runningProperty());
        toolbar.disableProperty().bind(service.runningProperty());
        menuButton.disableProperty().bind(service.runningProperty());

        footer.getChildren().addAll(lblRowIndex, lblTotalRow, menuButton, progressIndicator);
        VBox.setVgrow(tblView, Priority.ALWAYS);
        getChildren().addAll(toolbar, tblView, footer);

    }

    private Button buildButton(Node graphic) {
        Button btn = new Button();
        btn.setGraphic(graphic);
        btn.getStyleClass().add("flat-button");
        btn.setOnAction(buttonHandler);
        return btn;
    }

    private final EventHandler<ActionEvent> buttonHandler = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() == btnAdd) {
                insert();
            } else if (event.getSource() == btnDelete) {
                delete();
            } else if (event.getSource() == btnEdit) {
                edit();
            } else if (event.getSource() == btnExport) {
                export();
            } else if (event.getSource() == btnReload) {
                reload();
            } else if (event.getSource() == btnSave) {
                save();
            }
        }
    };

    /**
     * 分页控制
     */
    private final EventHandler<ActionEvent> paginationHandler = event -> {
        if (event.getSource() == btnFirstPage) {
            reloadFirstPage();
        } else if (event.getSource() == btnPrevPage) {
            prevPageFired(event);
        } else if (event.getSource() == btnNextPage) {
            nextPageFired(event);
        } else if (event.getSource() == btnLastPage) {
            lastPageFired(event);
        } else if (event.getSource() == cmbPage) {
            pageChangeFired(event);
        }
    };

    /**
     * Set selection mode
     * @param mode SelectionMode
     * @see javafx.scene.control.SelectionMode
     */
    public final void setSelectionMode(SelectionMode mode) {
        tblView.getSelectionModel().setSelectionMode(mode);
    }

    /**
     * 设置工具提示
     */
    private void setToolTips() {
        TiwulFXUtil.setToolTip(btnAdd, "add.record");
        TiwulFXUtil.setToolTip(btnDelete, "delete.record");
        TiwulFXUtil.setToolTip(btnEdit, "edit.record");
        TiwulFXUtil.setToolTip(btnFirstPage, "go.to.first.page");
        TiwulFXUtil.setToolTip(btnLastPage, "go.to.last.page");
        TiwulFXUtil.setToolTip(btnNextPage, "go.to.next.page");
        TiwulFXUtil.setToolTip(btnPrevPage, "go.to.prev.page");
        TiwulFXUtil.setToolTip(btnReload, "reload.records");
        TiwulFXUtil.setToolTip(btnExport, "export.records");
        TiwulFXUtil.setToolTip(btnSave, "save.record");
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
     * Move focus to the next cell if user pressing TAB and the mode is
     * EDIT/INSERT
     */
    private EventHandler<KeyEvent> tableKeyListener = new EventHandler<>() {
        @Override
        public void handle(KeyEvent event) {
            TableView.TableViewSelectionModel<R> selectionModel = tblView.getSelectionModel();
            if (isReadMode()) {
                if (event.getCode() == KeyCode.C && event.isControlDown()) {
                    if (event.isShiftDown()) {
                        copyRow();
                    } else {
                        copyCell();
                    }
                    event.consume();
                } else if (event.getCode() == KeyCode.B && event.isAltDown()) {
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
                    if (tblView.getSelectionModel().getSelectedCells().get(0).getColumn() == lastColumnIndex) {
                        showRow(tblView.getSelectionModel().getSelectedIndex() + 1);
                        selectionModel.select(selectionModel.getSelectedIndex() + 1, tblView.getColumns().get(0));
                    } else {
                        tblView.getSelectionModel().selectRightCell();
                    }
                }
                horizontalScroller.run();
                event.consume();
            } else if (event.getCode() == KeyCode.ENTER && !event.isControlDown() && !event.isAltDown() && !event.isShiftDown()) {
                if (agileEditing.get()) {
                    if (directEdit) {
                        showRow(tblView.getSelectionModel().getSelectedIndex() + 1);
                        if (tblView.getSelectionModel().getSelectedIndex() == tblView.getItems().size() - 1) {
                            //it will trigger cell's commit edit for the most bottom row
                            tblView.getSelectionModel().selectAboveCell();
                        }
                        tblView.getSelectionModel().selectBelowCell();
                        event.consume();
                    } else {
                        directEdit = true;
                    }
                }
            } else if (event.getCode() == KeyCode.V && event.isControlDown()) {
                if (!hasEditingCell()) {
                    paste();
                    event.consume();
                }
            }
        }
    };

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

                    TableColumn toFillColumn = startColumn;
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
                                Object newValue = ((BaseColumn) toFillColumn).convertFromString(stringCellValue);
                                PropertyUtils.setSimpleProperty(item, ((BaseColumn) toFillColumn).getPropertyName(), newValue);
                                if (mode.get() == Mode.EDIT) {
                                    ((BaseColumn) toFillColumn).addRecordChange(item, oldValue, newValue);
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
     * Force the table to repaint specified row.It propagates the call to {@link TableRowControl#refresh()}.
     * @param record specified record to refresh.
     */
    public final void refresh(R record) {
        Set<Node> nodes = tblView.lookupAll(".table-row-cell");
        for (Node node : nodes) {
            if (node instanceof TableRowControl<?> tableRow) {
                if (tableRow.getItem() != null && tableRow.getItem().equals(record)) {
                    tableRow.refresh();
                    break;
                }
            }
        }
    }

    private Runnable horizontalScroller = new Runnable() {
        private ScrollBar scrollBar = null;

        @Override
        public void run() {
            TableView.TableViewFocusModel<R> fm = tblView.getFocusModel();
            if (fm == null) {
                return;
            }
            TableColumn col = fm.getFocusedCell().getTableColumn();
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
                // scrollbar is not visible, meaning all columns are visible.
                // No need to scroll
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
     * @return
     * @see #selectedItemProperty()
     */
    public final R getSelectedItem() {
        return tblView.getSelectionModel().selectedItemProperty().get();
    }

    /**
     * @return
     * @see TableView#getSelectionModel() getSelectionItems()
     */
    public final ObservableList<R> getSelectedItems() {
        return tblView.getSelectionModel().getSelectedItems();
    }

    /**
     * @see TableView#getSelectionModel()
     */
    public final TableView.TableViewSelectionModel<R> getSelectionModel() {
        return tblView.getSelectionModel();
    }

    private ContextMenu cm;
    private MenuItem searchMenuItem;

    private void copyCell() {
        R selectedRow = getSelectedItem();
        if (selectedRow == null) {
            return;
        }
        String textToCopy = "";
        TablePosition<R, ?> pos = tblView.getSelectedCellPosition(0);
        TableColumn<R, ?> column = null;
        if (pos != null) {
            column = pos.getTableColumn();
        }
        if (column == null) {
            column = tblView.getSelectedColumn();
        }
        if (column instanceof BaseColumn) {
            BaseColumn bc = (BaseColumn) column;
            textToCopy = bc.convertToString(bc.getCellData(selectedRow));
        } else if (column != null) {
            Object cellValue = column.getCellData(selectedRow);
            textToCopy = String.valueOf(cellValue);
        } else {
            logger.log(Level.SEVERE, "Failed to detect column to copy from");
            return;
        }
        ClipboardUtils.putString(textToCopy);
    }

    private void copyRow() {
        R selectedRow = getSelectedItem();
        if (selectedRow == null) {
            return;
        }
        StringBuilder textToCopy = new StringBuilder();
        for (TableColumn clm : getLeafColumns()) {
            if (clm instanceof BaseColumn) {
                BaseColumn bc = (BaseColumn) clm;
                Object cellData = bc.getCellData(selectedRow);
                System.out.println("单元格数据 " + cellData);
                textToCopy.append(bc.convertToString(cellData)).append("\t");
            } else {
                Object cellValue = clm.getCellData(selectedRow);
                textToCopy.append(cellValue).append("\t");
            }
        }
        if (textToCopy.toString().endsWith("\t")) {
            textToCopy = new StringBuilder(textToCopy.substring(0, textToCopy.length() - 1));
        }
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(textToCopy.toString());
        clipboard.setContent(content);
    }

    private MenuItem miPaste;

    private MenuItem getPasteMenuItem() {
        if (miPaste == null) {
            miPaste = new MenuItem(TiwulFXUtil.getLiteral("paste"));
            miPaste.setOnAction(event -> paste());
        }
        return miPaste;
    }

    public void browseSelectedRow() {
        R selectedRow = getSelectedItem();
        if (selectedRow == null) {
            return;
        }
        List<TableColumn<R, ?>> lstColumn = getLeafColumns();
        List<RowBrowser.Record> lstRecord = new ArrayList<>();
        for (TableColumn<R, ?> tableColumn : lstColumn) {
            RowBrowser.Record rcd;
            if (tableColumn instanceof BaseColumn) {
                String stringVal = ((BaseColumn) tableColumn).getStringConverter()
                        .toString(((BaseColumn) tableColumn).getCellData(selectedRow));
                rcd = new RowBrowser.Record(tableColumn.getText(), stringVal);
            } else {
                String stringVal = tableColumn.getCellData(selectedRow) == null ? "" : tableColumn
                        .getCellData(selectedRow).toString();
                rcd = new RowBrowser.Record(tableColumn.getText(), stringVal);
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
        List<RowBrowser> lst = new ArrayList<>(lstRowBrowser);
        lst.forEach(RowBrowser::close);
    }

    /**
     * Add menu item to context menu. The context menu is displayed when
     * right-clicking a row.
     * @param menuItem MenuItem
     * @see #removeContextMenuItem(javafx.scene.control.MenuItem)
     */
    public void addContextMenuItem(MenuItem menuItem) {
        cm.getItems().add(menuItem);
    }

    private ContextMenu createContextMenu() {
        ContextMenu cm = new ContextMenu();
        cm.setAutoHide(true);
        MenuItem copyCellMenu = new MenuItem(TiwulFXUtil.getLiteral("copy.cell"));
        copyCellMenu.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        copyCellMenu.setOnAction(e -> copyCell());
        cm.getItems().add(copyCellMenu);
        MenuItem copyRowMenu = new MenuItem(TiwulFXUtil.getLiteral("copy.row"));
        copyRowMenu.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        copyRowMenu.setOnAction(e -> copyRow());
        cm.getItems().add(copyRowMenu);
        MenuItem browseRowMenu = new MenuItem(TiwulFXUtil.getLiteral("browse.row"));
        browseRowMenu.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.ALT_DOWN));
        browseRowMenu.setOnAction(e -> browseSelectedRow());
        cm.getItems().add(browseRowMenu);
        return cm;
    }

    /**
     * Remove passed menuItem from context menu.
     * @param menuItem MenuItem
     * @see #addContextMenuItem(javafx.scene.control.MenuItem)
     */
    public void removeContextMenuItem(MenuItem menuItem) {
        cm.getItems().remove(menuItem);
    }

    protected <T> void resizeToFit(TableColumn<R, T> col, int maxRows) {
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

        int rows = maxRows == -1 ? items.size() : Math.min(items.size(), maxRows);
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
     * @param behavior
     */
    public void setBehavior(TableControlBehavior<R> behavior) {
        this.behavior = behavior;
    }


    /**
     * TableView 工具类
     */
    static class TableViewHelper {

        /**
         * 根据值发生改变的单元格获取正在编辑的行对应的数据项
         * @param cellEditEvent 单元格编辑事件
         * @return {@link S} 为null表示编辑事件无效，不满足判断条件
         */
        @Nullable
        public static <S, T> S getEditingItem(CellEditEvent<S, T> cellEditEvent) {
            TablePosition<S, T> editPosition = cellEditEvent.getTablePosition();
            TableView<S> tableView = cellEditEvent.getTableView();
            if (editPosition == null || tableView == null) {
                return null;
            }
            final int editRow = editPosition.getRow();
            if (editRow >= tableView.getItems().size()) {
                return null;
            }
            final T newValue = cellEditEvent.getNewValue();
            final T oldValue = cellEditEvent.getOldValue();
            if ((newValue == null && oldValue == null) || (newValue != null && newValue.equals(oldValue))) {
                return null;
            }
            return tableView.getItems().get(editRow);
        }

        /**
         * 单元格编辑事件是否值发生改变
         * @param cellEditEvent 单元格编辑事件
         * @param <S>           表格数据类型
         * @param <T>           单元格数据类型
         * @return 是否值发生改变
         */
        public static <S, T> boolean isValueChanged(CellEditEvent<S, T> cellEditEvent) {
            final T newValue = cellEditEvent.getNewValue();
            final T oldValue = cellEditEvent.getOldValue();
            return (newValue != null || oldValue != null) && (newValue == null || !newValue.equals(oldValue));
        }
    }

    /**
     * 初始化列TableColumn
     * @param clm clm
     */
    private void initColumn(TableColumn<R, ?> clm) {
        List<TableColumn<R, ?>> lstColumn = getColumnsRecursively(List.of(clm));
        for (TableColumn<R, ?> column : lstColumn) {
            if (column instanceof BaseColumn) {
                final BaseColumn baseColumn = (BaseColumn) column;
                baseColumn.tableCriteriaProperty().addListener(tableCriteriaListener);
                baseColumn.sortTypeProperty().addListener(sortTypeChangeListener);
                baseColumn.addEventHandler(TableColumn.editCommitEvent(), (EventHandler<CellEditEvent<R, ?>>) t -> {
                    final R persistentObj = TableViewHelper.getEditingItem(t);
                    if (persistentObj == null) {
                        return;
                    }
                    if (isEditMode() && TableViewHelper.isValueChanged(t)) {
                        if (!lstChangedRow.contains(persistentObj)) {
                            lstChangedRow.add(persistentObj);
                        }
                        baseColumn.addRecordChange(persistentObj, t.getOldValue(), t.getNewValue());
                    }
                    ClassUtils.setSimpleProperty(persistentObj, baseColumn.getPropertyName(), t.getNewValue());
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
            TableData<R> vol = behavior.loadData(startIndex.get(), lstCriteria, lstSortedColumn, lstSortedType, maxResult.get());
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
                Map<R, ?> map = baseColumn.getRecordChangeMap();
                lstRecordChange.addAll((Collection<? extends RecordChange<R, ?>>) map.values());
            }
        }
        return lstRecordChange;
    }

    private void toggleButtons(boolean moreRows) {
        boolean firstPage = startIndex.get() == 0;
        btnFirstPage.setDisable(firstPage);
        btnPrevPage.setDisable(firstPage);
        btnNextPage.setDisable(!moreRows);
        btnLastPage.setDisable(!moreRows);
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

    private void lastPageFired(ActionEvent event) {
        cmbPage.getSelectionModel().selectLast();
    }

    private void prevPageFired(ActionEvent event) {
        cmbPage.getSelectionModel().selectPrevious();
    }

    /**
     * the next page
     * @param event ActionEvent
     */
    private void nextPageFired(ActionEvent event) {
        cmbPage.getSelectionModel().selectNext();
    }

    /**
     * the page num changed
     * @param event ActionEvent
     */
    private void pageChangeFired(ActionEvent event) {
        if (cmbPage.getValue() != null) {
            // since the combobox is editable, it might have String value
            // enen though the generic is Integer, it still can be a String
            currentPage = Integer.parseInt(String.valueOf(cmbPage.getValue()));
            currentPage = currentPage - 1;
            startIndex.set(currentPage * getMaxRecord());
        }
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
        mode.set(Mode.INSERT);

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
                mode.set(Mode.READ);
                return;
            }
            if (!behavior.validate(this, lstChangedRow)) {
                return;
            }
            Mode prevMode = mode.get();
            if (useBackgroundTaskToSave) {
                service.runSaveInBackground(prevMode);
            } else {
                List<R> lstResult = new ArrayList<>();
                if (mode.get().equals(Mode.EDIT)) {
                    lstResult = behavior.update(lstChangedRow);
                } else if (mode.get().equals(Mode.INSERT)) {
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
     */
    public void edit() {
        if (behavior.canEdit(tblView.getSelectionModel().getSelectedItem())) {
            mode.set(Mode.EDIT);
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
                behavior.exportToExcel("Override TableController.exportToExcel to reset the title.", maxResult.get(), TableControl.this, lstCriteria);
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

    private final IntegerProperty maxResult = new SimpleIntegerProperty(20);

    /**
     * Set max record per retrieval. It will be the parameter in {@link TableControlBehavior#loadData(int, java.util.List, java.util.List, java.util.List, int) loadData} maxResult parameter
     * @param maxRecord max number of records per-retrieval
     */
    public final void setMaxRecord(int maxRecord) {
        this.maxResult.set(maxRecord);
    }

    /**
     * Get max number of records per-retrieval.
     * @return max number of records per-retrieval
     */
    public final int getMaxRecord() {
        return maxResult.get();
    }

    public final IntegerProperty maxRecordProperty() {
        return maxResult;
    }

    /**
     * @return Class object set on {@link #setRecordClass(java.lang.Class) }
     * @see #setRecordClass(java.lang.Class)
     */
    public Class<R> getRecordClass() {
        return recordClass;
    }

    /**
     * Set the class of object that will be displayed in the table.
     * @param recordClass row record class
     */
    public void setRecordClass(Class<R> recordClass) {
        this.recordClass = recordClass;
    }

    public void setFitColumnAfterReload(boolean fitColumnAfterReload) {
        this.fitColumnAfterReload = fitColumnAfterReload;
    }

    /**
     * @return @see #setReloadOnCriteriaChange(boolean)
     */
    public boolean isReloadOnCriteriaChange() {
        return reloadOnCriteriaChange;
    }

    /**
     * Set it to false to prevent auto-reloading when there is table criteria
     * change. It is useful if we want to change tableCriteria of several
     * columns at a time. After that set it to true and call {@link TableControl#reloadFirstPage()
     * }
     * @param reloadOnCriteriaChange
     */
    public void setReloadOnCriteriaChange(boolean reloadOnCriteriaChange) {
        this.reloadOnCriteriaChange = reloadOnCriteriaChange;
    }

    /**
     * Get displayed record. It is just the same with
     * {@link TableView#getItems()}
     * @return
     */
    public ObservableList<R> getRecords() {
        return tblView.getItems();
    }

    private void setOrNot(ToolBar parent, Node control, boolean visible) {
        if (!visible) {
            parent.getItems().remove(control);
        } else if (!parent.getItems().contains(control)) {
            parent.getItems().add(control);
        }
    }

    /**
     * Add button to toolbar. The button's style is set by this method. Make
     * sure to add image on the button and also define the action method.
     * @param btn
     */
    public void addButton(Button btn) {
        addNode(btn);
    }

    /**
     * Add JavaFX Node to table's toolbar
     * @param node
     */
    public void addNode(Node node) {
        if (node instanceof Button) {
            node.getStyleClass().add("flat-button");
            ((Button) node).setMaxHeight(Double.MAX_VALUE);
        }
        boolean hasPagination = toolbar.getItems().contains(paginationBox);
        if (hasPagination) {
            toolbar.getItems().remove(spacer);
            toolbar.getItems().remove(paginationBox);
        }
        toolbar.getItems().add(node);
        if (hasPagination) {
            toolbar.getItems().add(spacer);
            toolbar.getItems().add(paginationBox);
        }
    }

    /**
     * Set UI component visibility.
     * @param visible
     * @param controls
     */
    public void setVisibleComponents(boolean visible, TableControl.Component... controls) {
        for (Component comp : controls) {
            switch (comp) {
                case BUTTON_DELETE:
                    setOrNot(toolbar, btnDelete, visible);
                    break;
                case BUTTON_EDIT:
                    setOrNot(toolbar, btnEdit, visible);
                    break;
                case BUTTON_INSERT:
                    setOrNot(toolbar, btnAdd, visible);
                    break;
                case BUTTON_EXPORT:
                    setOrNot(toolbar, btnExport, visible);
                    break;
                case BUTTON_PAGINATION:
                    setOrNot(toolbar, spacer, visible);
                    setOrNot(toolbar, paginationBox, visible);
                    break;
                case BUTTON_RELOAD:
                    setOrNot(toolbar, btnReload, visible);
                    break;
                case BUTTON_SAVE:
                    setOrNot(toolbar, btnSave, visible);
                    break;
                case FOOTER:
                    if (!visible) {
                        this.getChildren().remove(footer);
                    } else if (!this.getChildren().contains(footer)) {
                        this.getChildren().add(footer);
                    }
                    break;
                case TOOLBAR:
                    if (!visible) {
                        this.getChildren().remove(toolbar);
                    } else if (!this.getChildren().contains(toolbar)) {
                        this.getChildren().add(0, toolbar);
                    }
                    break;
            }
        }
    }

    public Mode getMode() {
        return mode.get();
    }

    public ReadOnlyObjectProperty<Mode> modeProperty() {
        return mode.getReadOnlyProperty();
    }

    public TableView<R> getTableView() {
        return tblView;
    }

    public final ReadOnlyObjectProperty<TablePosition<R, ?>> editingCellProperty() {
        return tblView.editingCellProperty();
    }

    /**
     * Check if a record is editable. After ensure that the item is not null and
     * the mode is not {@link Mode#INSERT} it will propagate the call to
     * {@link TableControlBehavior#isRecordEditable}.
     * @param item
     * @return false if item == null. True if mode is INSERT. otherwise depends
     * on the logic in {@link TableControlBehavior#isRecordEditable}
     * @see TableControlBehavior#isRecordEditable(java.lang.Object)
     */
    public final boolean isRecordEditable(R item) {
        if (item == null) {
            return false;
        }
        if (mode.get() == Mode.INSERT) {
            return true;
        }
        return behavior.isRecordEditable(item);
    }

    private boolean resettingRecords = false;

    private void postLoadAction(TableData<R> vol) {
        if (vol.getRows() == null) {
            vol.setRows(new ArrayList<>());
        }
        totalRows = vol.getTotalRows();
        //keep track of previous selected row
        int selectedIndex = tblView.getSelectionModel().getSelectedIndex();
        TableColumn selectedColumn = null;
        if (!tblView.getSelectionModel().getSelectedCells().isEmpty()) {
            selectedColumn = tblView.getSelectionModel().getSelectedCells().get(0).getTableColumn();
        }

        if (hasEditingCell()) {
            /**
             * Trigger cancelEdit if there is cell being edited. Otherwise,
             * ArrayIndexOutOfBound exception happens since tblView items are
             * cleared (see next lines) but setOnEditCommit listener is executed.
             */
            tblView.edit(-1, tblView.getColumns().get(0));
        }

        // clear items and add with objects that has just been retrieved
        resettingRecords = true;
        tblView.getItems().setAll(vol.getRows());
        if (selectedIndex < vol.getRows().size()) {
            tblView.getSelectionModel().select(selectedIndex, selectedColumn);
        } else {
            tblView.getSelectionModel().select(vol.getRows().size() - 1, selectedColumn);
        }
        resettingRecords = false;

        long page = vol.getTotalRows() / maxResult.get();
        if (vol.getTotalRows() % maxResult.get() != 0) {
            page++;
        }
        cmbPage.setDisable(page == 0);
        startIndex.removeListener(startIndexChangeListener);
        cmbPage.getItems().clear();
        for (int i = 1; i <= page; i++) {
            cmbPage.getItems().add(i);
        }
        cmbPage.getSelectionModel().select(startIndex.get() / maxResult.get());
        startIndex.addListener(startIndexChangeListener);
        toggleButtons(vol.isMoreRows());

        mode.set(Mode.READ);

        clearChange();
        if (fitColumnAfterReload) {
            for (TableColumn<R, ?> clm : tblView.getColumns()) {
                resizeToFit(clm, -1);
            }
        }
        lblTotalRow.setText(TiwulFXUtil.getLiteral("total.record.param", totalRows));

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
        lblTotalRow.setText(TiwulFXUtil.getLiteral("total.record.param", totalRows));
        tblView.requestFocus();
    }

    private void saveColumnPosition() {
        if (configurationID == null || configurationID.trim().length() == 0) {
            return;
        }
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                Map<String, String> mapProperties = new HashMap<>();
                for (TableColumn column : columns) {
                    int oriIndex = lstTableColumnsOriginalOrder.indexOf(column);
                    int newIndex = columns.indexOf(column);
                    mapProperties.put(configurationID + "." + oriIndex + ".pos", newIndex + "");
                }
                try {
                    TiwulFXUtil.writeProperties(mapProperties);
                } catch (Exception ex) {
                    handleException(ex);
                }
            }
        };
        new Thread(runnable).start();
        configureResetMenuItem();
    }

    private void configureResetMenuItem() {
        if (resetItem == null) {
            resetItem = new MenuItem(TiwulFXUtil.getLiteral("reset.columns"));
            resetItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    resetColumnPosition();
                }
            });
        }
        if (!menuButton.getItems().contains(resetItem)) {
            menuButton.getItems().add(resetItem);
        }
    }

    private void attachWindowVisibilityListener() {
        this.sceneProperty().addListener(new ChangeListener<Scene>() {

            @Override
            public void changed(ObservableValue<? extends Scene> ov, Scene t, Scene scene) {
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
            }

        });
    }

    private void resetColumnPosition() {
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
            menuButton.getItems().remove(resetItem);
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
            Map<Integer, TableColumn> map = new HashMap<>();
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
                        TableColumn tc = map.get(i);
                        if (tc != null) {
                            columns.add(i, tc);
                        }
                    }

                    configureResetMenuItem();
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
     * @param exportMode
     */
    public void setExportMode(ExportMode exportMode) {
        this.exportMode = exportMode;
    }

    /**
     * Check if this TableControl use background task to execute Load and Export.
     * Default value for this property is taken from
     * {@link TiwulFXUtil#DEFAULT_USE_BACKGROUND_TASK_TO_LOAD}.
     * @return
     */
    public boolean isUseBackgroundTaskToLoad() {
        return useBackgroundTaskToLoad;
    }

    /**
     * If it is set to true, TableControl will use background task to execute
     * Load and Export actions. In this case, the corresponding methods in
     * {@link TableControlBehavior} will be executed in background task so developer
     * need to avoid updating UI in those methods. Default value for this
     * property is taken from {@link TiwulFXUtil#DEFAULT_USE_BACKGROUND_TASK_TO_LOAD}. Default is FALSE
     * @param useBackgroundTaskToLoad
     */
    public void setUseBackgroundTaskToLoad(boolean useBackgroundTaskToLoad) {
        this.useBackgroundTaskToLoad = useBackgroundTaskToLoad;
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
     * If it is set to true, TableControl will use background task to execute
     * Save action. In this case, the corresponding method in
     * {@link TableControlBehavior} will be executed in background task so developer
     * need to avoid updating UI in it. Default value for this property is taken
     * from {@link TiwulFXUtil#DEFAULT_USE_BACKGROUND_TASK_TO_SAVE}. Default is FALSE.
     * @param useBackgroundTaskToSave useBackgroundTaskToSave
     */
    public void setUseBackgroundTaskToSave(boolean useBackgroundTaskToSave) {
        this.useBackgroundTaskToSave = useBackgroundTaskToSave;
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

    /**
     * If it is set to true, TableControl will use background task to execute
     * Delete action. In this case, the corresponding method in
     * {@link TableControlBehavior} will be executed in background task so developer
     * need to avoid updating UI in it. Default value for this property is taken
     * from {@link TiwulFXUtil#DEFAULT_USE_BACKGROUND_TASK_TO_DELETE}. Default is false
     * @param useBackgroundTaskToDelete useBackgroundTaskToDelete
     */
    public void setUseBackgroundTaskToDelete(boolean useBackgroundTaskToDelete) {
        this.useBackgroundTaskToDelete = useBackgroundTaskToDelete;
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
        protected TableData<R> call() throws Exception {
            return behavior.loadData(startIndex.get(), lstCriteria, lstSortedColumn, sortingOrders, maxResult.get());
        }
    }

    private class SaveTask extends Task<List<R>> {
        private Mode prevMode;

        public SaveTask(Mode prevMode) {
            this.prevMode = prevMode;
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
        protected Void call() throws Exception {
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
                behavior.exportToExcel("Override TableController.exportToExcel to reset the title.", maxResult.get(), TableControl.this, lstCriteria);
            } else {
                behavior.exportToExcelCurrentPage("Override TableController.exportToExcelCurrentPage to reset the title.", TableControl.this);
            }
            return null;
        }
    }

    /*****************************************************************************************
     * Public API
     *****************************************************************************************/

    public final boolean isInsertMode() {
        return getMode() == Mode.INSERT;
    }

    public final boolean isEditMode() {
        return getMode() == Mode.INSERT;
    }

    public final boolean isReadMode() {
        return getMode() == Mode.READ;
    }
}
