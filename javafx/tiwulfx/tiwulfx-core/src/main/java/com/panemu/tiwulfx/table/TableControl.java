package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.*;
import com.panemu.tiwulfx.dialog.MessageDialog;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import com.panemu.tiwulfx.utils.ClassUtils;
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
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO 将Table操作独立出来作为事件处理
 * @param <R>
 */
public class TableControl<R> extends VBox {

    /**
     * Default number of rows displayed in {@link com.panemu.tiwulfx.table.TableControl TableControl}. Default is 500
     */
    public static int DEFAULT_TABLE_MAX_ROW = 500;


    private final CustomTableView<R> tblView = new CustomTableView<>();
    private PaginationControl paginationControl;

    private TableToolBar tableToolBar;
    private TableOperation<R> controller;
    private final SimpleIntegerProperty startIndex = new SimpleIntegerProperty(0);
    private final StartIndexChangeListener startIndexChangeListener = new StartIndexChangeListener();
    private final InvalidationListener sortTypeChangeListener = new SortTypeChangeListener();
    private final ReadOnlyObjectWrapper<Mode> mode = new ReadOnlyObjectWrapper<>(null);
    private final ObservableList<R> lstChangedRow = FXCollections.observableArrayList();
    private final List<TableCriteria<Object>> lstCriteria = new ArrayList<>();

    private Class<R> recordClass;
    private boolean reloadOnCriteriaChange = true;
    private boolean directEdit = false;
    private boolean fitColumnAfterReload = false;
    private long totalRows = 0;
    private Integer page = 0;
    private int lastColumnIndex = 0;

    /**
     * Table Columns
     */
    private final ObservableList<TableColumn<R, ?>> columns = tblView.getColumns();

    private final TableControlService service = new TableControlService();

    private MenuItem resetItem;
    private String configurationID;
    private boolean suppressSortConfigListener = false;
    private boolean suppressWidthConfigListener = false;
    private final Logger logger = Logger.getLogger(TableControl.class.getName());
    private boolean stageShown = false;
    private final List<RowBrowser> lstRowBrowser = new ArrayList<>();
    private final boolean closeRowBrowserOnReload = TiwulFXUtil.DEFAULT_CLOSE_ROW_BROWSER_ON_RELOAD;
    private ExportMode exportMode = TiwulFXUtil.DEFAULT_EXPORT_MODE;
    private boolean useBackgroundTaskToLoad = TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_LOAD;
    private boolean useBackgroundTaskToSave = TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_SAVE;
    private boolean useBackgroundTaskToDelete = TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_DELETE;

    /**
     * Table Operation Mode
     */
    public enum Mode {
        INSERT,
        EDIT,
        READ
    }

    /**
     * UI component in TableControl which their visibility could be manipulated
     */
    public enum Component {
        BUTTON_RELOAD,
        BUTTON_INSERT,
        BUTTON_EDIT,
        BUTTON_SAVE,
        BUTTON_DELETE,
        BUTTON_EXPORT,
        BUTTON_PAGINATION,
        TOOLBAR
    }

    public TableControl(Class<R> recordClass) {
        this.recordClass = recordClass;
        this.getStyleClass().add("table-control");

        initControls();
        tblView.getSortOrder().addListener((ListChangeListener<TableColumn<R, ?>>) change -> {
            if (stageShown) {
                reload();
                resetColumnSortConfig();
            }
        });

        tblView.editableProperty().bind(mode.isNotEqualTo(Mode.READ));
        tblView.getSelectionModel().cellSelectionEnabledProperty().bind(tblView.editableProperty());
        mode.addListener((ov, t, t1) -> {
            if (t1 == Mode.READ) {
                directEdit = false;
            }
        });

        // 更新行号
        tblView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                int rowNum = page * maxResult.get() + t1.intValue() + 1;
                footer.updateRowIndex(rowNum);
            }
        });

        /**
         * 点击单元格进行编辑
         */
        tblView.getFocusModel().focusedCellProperty().addListener(new ChangeListener<TablePosition>() {
            @Override
            public void changed(ObservableValue<? extends TablePosition> observable, TablePosition oldValue, TablePosition newValue) {
                if (!resettingRecords && tblView.isEditable() && directEdit && agileEditing.get()) {
                    tblView.edit(newValue.getRow(), newValue.getTableColumn());
                }
            }
        });

        tblView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                directEdit = false;
            } else if (event.getCode() == KeyCode.ENTER && mode.get() == Mode.READ) {
                Object selectedItem = getSelectionModel().getSelectedItem();
                getController().doubleClick((R) selectedItem);
            }
        });

        // Define policy for TAB key press
        tblView.addEventFilter(KeyEvent.KEY_PRESSED, tableKeyListener);
        // In INSERT mode, only inserted row that is focusable
        tblView.getFocusModel().focusedCellProperty().addListener(tableFocusListener);

        tblView.setOnMouseReleased(tableRightClickListener);

        cm = new ContextMenu();
        createCopyCellMenuItem();
        cm.setAutoHide(true);
        setToolTips();
        // create custom row factory that can intercept double click on grid row
        tblView.setRowFactory(param -> new TableControlRow<>(TableControl.this));

        columns.addListener(new ListChangeListener<TableColumn<R, ?>>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends TableColumn<R, ?>> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (TableColumn<R, ?> column : change.getAddedSubList()) {
                            initColumn(column);
                        }
                    }
                    lastColumnIndex = getLeafColumns().size() - 1;
                }
            }
        });
        attachWindowVisibilityListener();
    }

    TablePaneFooter footer;

    public final ObservableList<TableColumn<R, ?>> getColumns() {
        return columns;
    }

    public ObservableList<R> getChangedRecords() {
        return lstChangedRow;
    }

    private void initControls() {
        // 分页控件
        paginationControl = new PaginationControl(this);

        // 工具栏
        tableToolBar = new TableToolBar(this, paginationControl);
        tableToolBar.disableProperty().bind(service.runningProperty());

        // 状态栏
        footer = new TablePaneFooter(this);
        footer.init(service);

        VBox.setVgrow(tblView, Priority.ALWAYS);
        getChildren().addAll(tableToolBar, tblView, footer);
    }

    /**
     * 表格面板底部状态栏
     */
    static class TablePaneFooter extends StackPane {

        private Label lblRowIndex;
        private Label lblTotalRow;
        private MenuButton menuButton;
        private ProgressBar progressIndicator;

        public TablePaneFooter(TableControl<?> tableControl) {
            this.getStyleClass().add("table-footer");

            // 菜单
            menuButton = new TableControlMenu(tableControl);
            StackPane.setAlignment(menuButton, Pos.CENTER_RIGHT);

            lblRowIndex = new Label();
            lblTotalRow = new Label();

            StackPane.setAlignment(lblRowIndex, Pos.CENTER_LEFT);
            StackPane.setAlignment(lblTotalRow, Pos.CENTER);
            progressIndicator = new ProgressBar();
            lblTotalRow.visibleProperty().bind(progressIndicator.visibleProperty().not());
            progressIndicator.setProgress(-1);

            this.getChildren().addAll(lblRowIndex, lblTotalRow, menuButton, progressIndicator);
        }

        /**
         * 初始化
         * @param service
         */
        public void init(TableControl.TableControlService service) {
            menuButton.disableProperty().bind(service.runningProperty());
            progressIndicator.visibleProperty().bind(service.runningProperty());
        }

        /**
         * 更新行号
         * @param rowNum 行号
         */
        public void updateRowIndex(int rowNum) {
            lblRowIndex.setText(TiwulFXUtil.getString("row.param", rowNum));
        }

        public void updateTotalRow(long totalRow) {
            lblTotalRow.setText(TiwulFXUtil.getString("total.record.param", totalRow));
        }

        public void removeResetItem(MenuItem menuItem) {
            menuButton.getItems().remove(menuItem);
        }

        public void addMenuItem(MenuItem resetItem) {
            if (!menuButton.getItems().contains(resetItem)) {
                menuButton.getItems().add(resetItem);
            }
        }
    }

    /**
     * 控制工具栏
     * @see TableControl#initControls()
     */
    static class TableToolBar extends ToolBar implements EventHandler<ActionEvent> {
        TableControl<?> tableControl;
        public Button btnAdd;
        public Button btnEdit;
        public Button btnDelete;
        public Button btnReload;
        public Button btnSave;
        public Button btnExport;
        public Region spacer;
        // 分页
        private PaginationControl paginationControl;

        public TableToolBar(TableControl<?> tableControl, PaginationControl paginationControl) {
            this.tableControl = tableControl;
            this.paginationControl = paginationControl;
            this.getStyleClass().add("table-toolbar");

            btnAdd = buildButton(TiwulFXUtil.getGraphicFactory().createAddGraphic());
            btnDelete = buildButton(TiwulFXUtil.getGraphicFactory().createDeleteGraphic());
            btnEdit = buildButton(TiwulFXUtil.getGraphicFactory().createEditGraphic());
            btnExport = buildButton(TiwulFXUtil.getGraphicFactory().createExportGraphic());
            btnReload = buildButton(TiwulFXUtil.getGraphicFactory().createReloadGraphic());
            btnSave = buildButton(TiwulFXUtil.getGraphicFactory().createSaveGraphic());

            // TODO
            btnAdd.setOnAction(this);
            btnDelete.setOnAction(this);
            btnEdit.setOnAction(this);
            btnExport.setOnAction(this);
            btnReload.setOnAction(this);
            btnSave.setOnAction(this);

            ReadOnlyObjectProperty<Mode> tableModeProps = tableControl.modeProperty();
            btnAdd.disableProperty().bind(tableModeProps.isEqualTo(Mode.EDIT));
            btnEdit.disableProperty().bind(tableModeProps.isNotEqualTo(Mode.READ));
            btnSave.disableProperty().bind(tableModeProps.isEqualTo(Mode.READ));
            btnDelete.disableProperty().bind(new BooleanBinding() {
                {
                    TableView<?> tblView = tableControl.getTableView();
                    super.bind(tableModeProps, tblView.getSelectionModel()
                            .selectedItemProperty(), tableControl.getChangedRecords());
                }

                @Override
                protected boolean computeValue() {
                    return (tableModeProps.get() == Mode.INSERT && tableControl.getChangedRecords()
                            .size() < 2) || tableControl.getTableView().getSelectionModel().selectedItemProperty()
                            .get() == null || tableModeProps.get() == Mode.EDIT;
                }
            });

            // 空格区域  工具按钮组和分页控件中间的空格
            spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            this.getItems()
                    .addAll(btnReload, btnAdd, btnEdit, btnSave, btnDelete, btnExport, spacer, paginationControl);
        }

        private Button buildButton(Node graphic) {
            Button btn = new Button();
            btn.setGraphic(graphic);
            btn.getStyleClass().add("flat-button");
            return btn;
        }

        public void addNode(Node node) {
            this.getItems().add(node);
            boolean hasPagination = this.getItems().contains(paginationControl);
            if (hasPagination) {
                this.getItems().removeAll(spacer, paginationControl);
                this.getItems().addAll(spacer, paginationControl);
            }
        }

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

        /**
         * 如果visible为true，则判断是否包含control
         * @param parent
         * @param control
         * @param visible
         */
        public void setOrNot(ToolBar parent, Node control, boolean visible) {
            if (!visible) {
                parent.getItems().remove(control);
            } else if (!parent.getItems().contains(control)) {
                parent.getItems().add(control);
            }
        }

        public void setOrNot(Pane parent, Node control, boolean visiable) {
            if (!visiable) {
                parent.getChildren().remove(control);
            } else if (!parent.getChildren().contains(control)) {
                parent.getChildren().add(control);
            }
        }

        /**
         * Set UI component visibility.
         * @param visible  预期可见状态
         * @param controls 控件列表
         */
        public void setVisibleComponents(boolean visible, TableControl.Component... controls) {
            ToolBar toolbar = this;
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
                        setOrNot(toolbar, paginationControl, visible);
                        break;
                    case BUTTON_RELOAD:
                        setOrNot(toolbar, btnReload, visible);
                        break;
                    case BUTTON_SAVE:
                        setOrNot(toolbar, btnSave, visible);
                        break;
                    case TOOLBAR:
                        setOrNot(tableControl, toolbar, visible);
                        break;
                }
            }
        }
    }

    /**
     * 分页控制面板
     * @see TableControl#paginationControl
     */
    static class PaginationControl extends HBox {

        // pagination buttons
        private Button btnFirstPage;
        private Button btnLastPage;
        private Button btnNextPage;
        private Button btnPrevPage;
        /**
         * pageNum input
         */
        private ComboBox<Integer> cmbPage;
        TableControl<?> tableControl;

        private final EventHandler<ActionEvent> paginationHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == btnFirstPage) {
                    tableControl.reloadFirstPage();
                } else if (event.getSource() == btnPrevPage) {
                    cmbPage.getSelectionModel().selectPrevious();
                } else if (event.getSource() == btnNextPage) {
                    cmbPage.getSelectionModel().selectNext();
                } else if (event.getSource() == btnLastPage) {
                    cmbPage.getSelectionModel().selectLast();
                } else if (event.getSource() == cmbPage) {
                    Integer value = cmbPage.getValue();
                    if (value != null) {
                        tableControl.pageChangeFired(event, value);
                    }
                }
            }
        };

        public PaginationControl(TableControl<?> tableControl) {
            this.tableControl = tableControl;
            this.setAlignment(Pos.CENTER);

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

            this.getChildren().addAll(btnFirstPage, btnPrevPage, cmbPage, btnNextPage, btnLastPage);
        }

        private void toggleButtons(int startIndex, boolean moreRows) {
            boolean firstPage = startIndex == 0;
            btnFirstPage.setDisable(firstPage);
            btnPrevPage.setDisable(firstPage);
            btnNextPage.setDisable(!moreRows);
            btnLastPage.setDisable(!moreRows);
        }

        public void refreshPageNums(long maxPageNum) {
            cmbPage.setDisable(maxPageNum == 0);
            cmbPage.getItems().clear();
            for (int i = 1; i <= maxPageNum; i++) {
                cmbPage.getItems().add(i);
            }
        }

        public void select(int pageNum) {
            cmbPage.getSelectionModel().select(pageNum);
        }
    }

    /**
     * Set selection mode
     * @param mode SelectionMode
     * @see javafx.scene.control.SelectionMode
     */
    public void setSelectionMode(SelectionMode mode) {
        tblView.getSelectionModel().setSelectionMode(mode);
    }

    private void setToolTips() {
//        TiwulFXUtil.setToolTip(btnAdd, "add.record");
//        TiwulFXUtil.setToolTip(btnDelete, "delete.record");
//        TiwulFXUtil.setToolTip(btnEdit, "edit.record");
//        TiwulFXUtil.setToolTip(btnFirstPage, "go.to.first.page");
//        TiwulFXUtil.setToolTip(btnLastPage, "go.to.last.page");
//        TiwulFXUtil.setToolTip(btnNextPage, "go.to.next.page");
//        TiwulFXUtil.setToolTip(btnPrevPage, "go.to.prev.page");
//        TiwulFXUtil.setToolTip(btnReload, "reload.records");
//        TiwulFXUtil.setToolTip(btnExport, "export.records");
//        TiwulFXUtil.setToolTip(btnSave, "save.record");
    }

    /**
     * 快速编辑模式
     */
    private final BooleanProperty agileEditing = new SimpleBooleanProperty(true);

    public void setAgileEditing(boolean agileEditing) {
        this.agileEditing.set(agileEditing);
    }

    public boolean isAgileEditing() {
        return agileEditing.get();
    }

    public BooleanProperty agileEditingProperty() {
        return agileEditing;
    }

    /**
     * Move focus to the next cell if user pressing TAB and the mode is
     * EDIT/INSERT
     */
    private EventHandler<KeyEvent> tableKeyListener = new EventHandler<>() {
        @Override
        public void handle(KeyEvent event) {
            if (mode.get() == Mode.READ) {
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
            } else if (event.getCode() == KeyCode.TAB) {
                if (event.isShiftDown()) {
                    if (tblView.getSelectionModel().getSelectedCells().get(0).getColumn() == 0) {
                        List<TableColumn<R, ?>> leafColumns = getLeafColumns();
                        showRow(tblView.getSelectionModel().getSelectedIndex() - 1);
                        tblView.getSelectionModel().select(tblView.getSelectionModel()
                                .getSelectedIndex() - 1, leafColumns.get(leafColumns.size() - 1));
                    } else {
                        tblView.getSelectionModel().selectLeftCell();
                    }
                } else {
                    if (tblView.getSelectionModel().getSelectedCells().get(0).getColumn() == lastColumnIndex) {
                        showRow(tblView.getSelectionModel().getSelectedIndex() + 1);
                        tblView.getSelectionModel()
                                .select(tblView.getSelectionModel().getSelectedIndex() + 1, tblView.getColumns()
                                        .get(0));
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

    /**
     * 是否有单元格处于编辑状态中
     * @return true/false
     */
    private boolean hasEditingCell() {
        return tblView.getEditingCell() != null && tblView.getEditingCell().getRow() > -1;
    }

    public void showRow(int index) {
        if (index < 0 || index >= getRecords().size()) {
            return;
        }
        final Node node = tblView.lookup("VirtualFlow");
        if (node instanceof VirtualFlow) {
            VirtualFlow<?> virtualFlow = (VirtualFlow<?>) node;
            virtualFlow.scrollTo(index);
        }
    }

    /**
     * Mark record as changed. It will only add the record to the changed record
     * list if the record doesn't exist in the list. Avoid adding record to
     * {@link #getChangedRecords()} to avoid adding the same record multiple
     * times.
     * @param record
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
                List<TablePosition> cells = tblView.getSelectionModel().getSelectedCells();
                if (cells.isEmpty()) {
                    return;
                }
                TablePosition cell = cells.get(0);
                List<TableColumn<R, ?>> lstColumn = getLeafColumns();
                TableColumn startColumn = null;
                for (TableColumn clm : lstColumn) {
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
                    } else if (mode.get() == Mode.EDIT) {
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
                    // Handle multicolumn paste
                    String[] stringCellValues = line.split("\t");
                    TableColumn toFillColumn = startColumn;
                    tblView.getSelectionModel().select(rowIndex, toFillColumn);
                    for (String stringCellValue : stringCellValues) {
                        if (toFillColumn == null) {
                            break;
                        }
                        if (toFillColumn instanceof BaseColumn && toFillColumn.isEditable() && toFillColumn.isVisible()) {
                            try {
                                Object oldValue = toFillColumn.getCellData(item);
                                Object newValue = ((BaseColumn) toFillColumn).convertFromString(stringCellValue);
                                ClassUtils.setSimpleProperty(item, ((BaseColumn) toFillColumn).getPropertyName(), newValue);
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

                        TablePosition<R, ?> nextCell = TableViewHelper.getSelectedPosition(tblView, 0);
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
     * TableView工具类
     */
    static class TableViewHelper {

        /**
         * 获取获得焦点的列
         * @param tableView TableView
         * @param <S>       行数据类型
         * @param <T>       列数据类型
         * @return TableColumn
         */
        public static <S, T> TableColumn<S, T> getFocusedColumn(TableView<S> tableView) {
            TableView.TableViewFocusModel<S> focusModel = tableView.getFocusModel();
            if (focusModel == null) {
                return null;
            }
            @SuppressWarnings("unchecked") TablePosition<S, T> focusedCell = (TablePosition<S, T>) focusModel.getFocusedCell();
            if (focusedCell == null) {
                return null;
            }
            return focusedCell.getTableColumn();
        }

        @SuppressWarnings("unchecked")
        public static <S, T> TableColumn<S, T> getSelectedColumn(TableView<S> tblView, int index) {
            return tblView.getSelectionModel().getSelectedCells().get(index).getTableColumn();
        }

        @SuppressWarnings("unchecked")
        public static <S, T> TablePosition<S, T> getSelectedPosition(TableView<S> tblView, int index) {
            return tblView.getSelectionModel().getSelectedCells().get(index);
        }
    }

    /**
     * It calls {@link TableView#refresh()}
     */
    public void refresh() {
        tblView.refresh();
    }

    /**
     * Force the table to repaint specified row.It propagates the call to {@link TableControlRow#refresh()}.
     * @param record specified record to refresh.
     */
    public void refresh(R record) {
        Set<Node> nodes = tblView.lookupAll(".table-row-cell");
        for (Node node : nodes) {
            if (node instanceof TableControlRow) {
                TableControlRow<R> row = (TableControlRow<R>) node;
                if (row.getItem() != null && row.getItem().equals(record)) {
                    row.refresh();
                    break;
                }
            }
        }
    }

    private final Runnable horizontalScroller = new Runnable() {
        private ScrollBar scrollBar = null;

        @Override
        public void run() {
            TableColumn<R, ?> col = TableViewHelper.getFocusedColumn(tblView);
            if (col == null) {
                return;
            }

            if (scrollBar == null) {
                for (Node n : tblView.lookupAll(".scroll-bar")) {
                    if (n instanceof ScrollBar) {
                        ScrollBar bar = (ScrollBar) n;
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
            // actually shift the flow - this will result in the header moving
            // as well
            scrollBar.setValue(newPos);
        }
    };

    /**
     * Get single selected record property. If multiple records are selected, it
     * returns the last one
     */
    public ReadOnlyObjectProperty<R> selectedItemProperty() {
        return tblView.getSelectionModel().selectedItemProperty();
    }

    /**
     * @see #selectedItemProperty()
     */
    public R getSelectedItem() {
        return tblView.getSelectionModel().selectedItemProperty().get();
    }

    /**
     * @see TableView#getSelectionModel() getSelectionItems()
     */
    public ObservableList<R> getSelectedItems() {
        return tblView.getSelectionModel().getSelectedItems();
    }

    /**
     * @see TableView#getSelectionModel()
     */
    public TableView.TableViewSelectionModel<R> getSelectionModel() {
        return tblView.getSelectionModel();
    }

    /**
     * Prevent moving focus to not-inserted-row in INSERT mode
     */
    private ChangeListener<TablePosition> tableFocusListener = (observable, oldValue, newValue) -> {
        if (!Mode.INSERT.equals(mode.get()) || newValue.getRow() == -1 || oldValue.getRow() == -1) {
            return;
        }
        Platform.runLater(() -> {
            R oldRow = tblView.getItems().get(oldValue.getRow());
            R newRow = tblView.getItems().get(newValue.getRow());
            if (lstChangedRow.contains(oldRow) && !lstChangedRow.contains(newRow)) {
                tblView.getFocusModel().focus(oldValue);
                tblView.getSelectionModel().select(oldValue.getRow(), oldValue.getTableColumn());
            }
        });
    };

    // 菜单
    private final ContextMenu cm;
    private MenuItem searchMenuItem;
    private EventHandler<MouseEvent> tableRightClickListener = new EventHandler<>() {
        @Override
        public void handle(MouseEvent event) {
            if (cm.isShowing()) {
                cm.hide();
            }
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                if (tblView.getSelectionModel().getSelectedCells().isEmpty()) {
                    return;
                }
                TablePosition<R, ?> pos = getSelectedTablePosition(0);
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
                    if (mode.get() != Mode.READ && !hasEditingCell() && Clipboard.getSystemClipboard().hasString()) {
                        if (!cm.getItems().contains(getPasteMenuItem())) {
                            cm.getItems().add(getPasteMenuItem());
                        }
                    }
                }
                cm.show(tblView, event.getScreenX(), event.getScreenY());
            }
        }
    };

    /**
     * 获取TablePosition
     * @param index 索引
     * @param <T>   单元格数据类型
     * @return TablePosition
     */
    @SuppressWarnings("unchecked")
    private <T> TablePosition<R, T> getSelectedTablePosition(int index) {
        return tblView.getSelectionModel().getSelectedCells().get(index);
    }

    private void copyCell() {
        R selectedRow = getSelectedItem();
        if (selectedRow == null) {
            return;
        }
        String textToCopy = "";
        TablePosition<R, Object> pos = getSelectedTablePosition(0);
        TableColumn<R, Object> column = null;
        if (pos != null) {
            column = pos.getTableColumn();
        }
        if (column == null) {
            column = tblView.getSelectedColumn();
        }
        if (column instanceof BaseColumn) {
            BaseColumn<R, Object> bc = (BaseColumn<R, Object>) column;
            Object cellData = bc.getCellData(selectedRow);
            textToCopy = bc.convertToString(cellData);
        } else if (column != null) {
            Object cellValue = column.getCellData(selectedRow);
            textToCopy = String.valueOf(cellValue);
        } else {
            logger.log(Level.SEVERE, "Failed to detect column to copy from");
            return;
        }
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(textToCopy);
        clipboard.setContent(content);
    }

    private void copyRow() {
        R selectedRow = getSelectedItem();
        if (selectedRow == null) {
            return;
        }
        String textToCopy = "";
        for (TableColumn clm : getLeafColumns()) {
            if (clm instanceof BaseColumn) {
                BaseColumn bc = (BaseColumn) clm;
                textToCopy = textToCopy + bc.convertToString(bc.getCellData(selectedRow)) + "\t";
            } else {
                Object cellValue = clm.getCellData(selectedRow);
                textToCopy = textToCopy + String.valueOf(cellValue) + "\t";
            }
        }
        if (textToCopy.endsWith("\t")) {
            textToCopy = textToCopy.substring(0, textToCopy.length() - 1);
        }
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(textToCopy);
        clipboard.setContent(content);
    }

    private MenuItem miPaste;

    private MenuItem getPasteMenuItem() {
        if (miPaste == null) {
            miPaste = new MenuItem(TiwulFXUtil.getString("paste"));
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
            String stringVal;
            if (tableColumn instanceof BaseColumn) {
                stringVal = ((BaseColumn<R, ?>) tableColumn).getCellDataAsString(selectedRow);
                rcd = new RowBrowser.Record(tableColumn.getText(), stringVal);
            } else {
                stringVal = String.valueOf(tableColumn.getCellData(selectedRow));
                rcd = new RowBrowser.Record(tableColumn.getText(), stringVal);
            }
            lstRecord.add(rcd);
        }
        RowBrowser rb = new RowBrowser();
        lstRowBrowser.add(rb);
        rb.setRecords(lstRecord);
        rb.show(getScene().getWindow());
        rb.getScene().getWindow().addEventHandler(WindowEvent.WINDOW_HIDDEN, (e) -> {
            lstRowBrowser.remove(rb);
        });
    }

    public void closeRowBrowsers() {
        List<RowBrowser> lst = new ArrayList<>(lstRowBrowser);
        lst.forEach(RowBrowser::close);
    }

    /**
     * Add menu item to context menu. The context menu is displayed when
     * right-clicking a row.
     * @param menuItem menuItem
     * @see #removeContextMenuItem(javafx.scene.control.MenuItem)
     */
    public void addContextMenuItem(MenuItem menuItem) {
        cm.getItems().add(menuItem);
    }

    private void createCopyCellMenuItem() {
        MenuItem mi = new MenuItem(TiwulFXUtil.getString("copy.cell"));
        mi.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        mi.setOnAction(e -> copyCell());
        cm.getItems().add(mi);
        mi = new MenuItem(TiwulFXUtil.getString("copy.row"));
        mi.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        mi.setOnAction(e -> copyRow());
        cm.getItems().add(mi);
        mi = new MenuItem(TiwulFXUtil.getString("browse.row"));
        mi.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.ALT_DOWN));
        mi.setOnAction(e -> browseSelectedRow());
        cm.getItems().add(mi);
    }

    /**
     * Remove passed menuItem from context menu.
     * @param menuItem menuItem
     * @see #addContextMenuItem(javafx.scene.control.MenuItem)
     */
    public void removeContextMenuItem(MenuItem menuItem) {
        cm.getItems().remove(menuItem);
    }

    protected void resizeToFit(TableColumn<R, ?> col, int maxRows) {
        List<?> items = tblView.getItems();
        if (items == null || items.isEmpty()) {
            return;
        }
        Callback cellFactory = col.getCellFactory();
        if (cellFactory == null) {
            return;
        }
        TableCell cell = (TableCell) cellFactory.call(col);
        if (cell == null) {
            return;
        }
        // set this property to tell the TableCell we want to know its actual
        // preferred width, not the width of the associated TableColumn
        cell.getProperties().put("deferToParentPrefWidth", Boolean.TRUE);
        // determine cell padding
        double padding = 10;
        Node n = cell.getSkin() == null ? null : cell.getSkin().getNode();
        if (n instanceof Region) {
            Region r = (Region) n;
            padding = r.getInsets().getLeft() + r.getInsets().getRight();
        }
        int rows = maxRows == -1 ? items.size() : Math.min(items.size(), maxRows);
        double maxWidth = 0;
        for (int row = 0; row < rows; row++) {
            cell.updateTableColumn(col);
            cell.updateTableView(tblView);
            cell.updateIndex(row);
            cell.setPadding(new Insets(padding));
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
     * {@link #setController(TableOperation)}
     */
    public TableOperation<R> getController() {
        return controller;
    }

    /**
     * Set object responsible to fetch, insert, delete and update data
     * @param controller
     */
    public void setController(TableOperation<R> controller) {
        this.controller = controller;
    }

    /**
     * 初始化表的一列
     * @param clm 列
     */
    @SuppressWarnings("unchecked")
    private void initColumn(TableColumn<R, ?> clm) {
        List<TableColumn<R, ?>> lstColumn = new ArrayList<>();
        lstColumn.add(clm);
        lstColumn = getColumnsRecursively(lstColumn);
        for (TableColumn<R, ?> column : lstColumn) {
            if (column instanceof BaseColumn) {
                final BaseColumn<R, Object> baseColumn = (BaseColumn<R, Object>) column;
                baseColumn.tableCriteriaProperty().addListener(tableCriteriaListener);
                baseColumn.sortTypeProperty().addListener(sortTypeChangeListener);
                baseColumn.addEventHandler(TableColumn.editCommitEvent(), new EventHandler<TableColumn.CellEditEvent<R, Object>>() {
                    @Override
                    public void handle(CellEditEvent<R, Object> t) {
                        if (t.getTablePosition() == null || t.getTableView() == null) {
                            return;
                        }
                        if (!(t.getTablePosition().getRow() < t.getTableView().getItems().size())) {
                            return;
                        }
                        R persistentObj = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        if ((t.getNewValue() == null && t.getOldValue() == null) || (t.getNewValue() != null && t
                                .getNewValue().equals(t.getOldValue()))) {
                            return;
                        }
                        if (mode.get()
                                .equals(Mode.EDIT) && t.getOldValue() != t.getNewValue() && (t.getOldValue() == null || !t
                                .getOldValue().equals(t.getNewValue()))) {
                            if (!lstChangedRow.contains(persistentObj)) {
                                lstChangedRow.add(persistentObj);
                            }
                            baseColumn.addRecordChange(persistentObj, t.getOldValue(), t.getNewValue());
                        }
                        ClassUtils.setSimpleProperty(persistentObj, baseColumn.getPropertyName(), t.getNewValue());
                        baseColumn.validate(persistentObj);
                    }
                });
            }
        }
    }

    /**
     * Add column to TableView. You can also call {@link TableView#getColumns()}
     * and then add columns to it.
     * @param columns columns
     */
    @SafeVarargs
    public final void addColumn(TableColumn<R, ?>... columns) {
        tblView.getColumns().addAll(columns);
    }

    /**
     * Get list of columns including the nested ones.
     * @param lstColumn columns
     * @return columns
     */
    private List<TableColumn<R, ?>> getColumnsRecursively(List<TableColumn<R, ?>> lstColumn) {
        List<TableColumn<R, ?>> newColumns = new ArrayList<>();
        for (TableColumn<R, ?> column : lstColumn) {
            if (column.getColumns().isEmpty()) {
                newColumns.add(column);
            } else {
                /**
                 * Should be in new arraylist to avoid
                 * java.lang.IllegalArgumentException: Children: duplicate
                 * children added
                 */
                newColumns.addAll(getColumnsRecursively(new ArrayList<>(column.getColumns())));
            }
        }
        return newColumns;
    }

    /**
     * Get list of columns that is hold cell. It excludes columns that are
     * containers of nested columns.
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
     * Reload data on current page. This method is called when pressing reload button.
     * @see #reloadFirstPage()
     */
    public void reload() {
        if (!lstChangedRow.isEmpty()) {
            if (!controller.revertConfirmation(this, lstChangedRow.size())) {
                return;
            }
        }
        lstCriteria.clear();
        // Should be in new arraylist to avoid
        // java.lang.IllegalArgumentException: Children: duplicate children added
        List<TableColumn<R, ?>> lstColumns = new ArrayList<>(tblView.getColumns());
        lstColumns = getColumnsRecursively(lstColumns);
        for (TableColumn<R, ?> clm : lstColumns) {
            if (clm instanceof BaseColumn) {
                BaseColumn<R, ?> baseColumn = (BaseColumn<R, ?>) clm;
                if (baseColumn.getTableCriteria() != null) {
                    lstCriteria.add(baseColumn.getTableCriteria());
                }
            }
        }
        List<String> lstSortedColumn = new ArrayList<>();
        List<SortType> lstSortedType = new ArrayList<>();
        for (TableColumn<R, ?> tc : tblView.getSortOrder()) {
            if (tc instanceof BaseColumn) {
                lstSortedColumn.add(((BaseColumn<R, ?>) tc).getPropertyName());
                lstSortedType.add(tc.getSortType());
            } else if (tc.getCellValueFactory() instanceof PropertyValueFactory) {
                Callback<? extends TableColumn.CellDataFeatures<R, ?>, ? extends ObservableValue<?>> cellValueFactory = tc.getCellValueFactory();
                @SuppressWarnings("unchecked") PropertyValueFactory<R, ?> valFactory = (PropertyValueFactory<R, ?>) cellValueFactory;
                lstSortedColumn.add(valFactory.getProperty());
                lstSortedType.add(tc.getSortType());
            }
        }
        if (closeRowBrowserOnReload) {
            this.closeRowBrowsers();
        }
        if (useBackgroundTaskToLoad) {
            service.runLoadInBackground(lstSortedColumn, lstSortedType);
        } else {
            TableData<R> vol = controller.loadData(startIndex.get(), lstCriteria, lstSortedColumn, lstSortedType, maxResult.get());
            postLoadAction(vol);
        }
    }

    private void clearChange() {
        lstChangedRow.clear();
        for (TableColumn<R, ?> clm : getLeafColumns()) {
            if (clm instanceof BaseColumn) {
                ((BaseColumn<R, ?>) clm).clearRecordChange();
            }
        }
    }

    /**
     * Get list of change happens on cells. It is useful to get detailed
     * information of old and new values of particular record's property
     * @return list of RecordChange
     */
    public List<RecordChange<R, ?>> getRecordChangeList() {
        List<RecordChange<R, ?>> lstRecordChange = new ArrayList<>();
        for (TableColumn<R, ?> column : getLeafColumns()) {
            if (column instanceof BaseColumn) {
                BaseColumn<R, ?> baseColumn = (BaseColumn<R, ?>) column;
                Map<R, ? extends RecordChange<R, ?>> map = baseColumn.getRecordChangeMap();
                lstRecordChange.addAll(map.values());
            }
        }
        return lstRecordChange;
    }

    private void toggleButtons(boolean moreRows) {
        paginationControl.toggleButtons(startIndex.get(), moreRows);
    }

    /**
     * Reload data from the first page.
     */
    public void reloadFirstPage() {
        page = 0;
        if (startIndex.get() != 0) {
            // it will automatically reload data.
            // @see StartIndexChangeListener
            startIndex.set(0);
        } else {
            reload();
        }
    }

    private void pageChangeFired(ActionEvent event, int pageNum) {
        // since the combobox is editable, it might have String value
        page = Integer.valueOf(String.valueOf(pageNum));
        page = page - 1;
        startIndex.set(page * maxResult.get());
    }

    /**
     * Return false if the insertion is canceled because the controller return
     * null object. It is controller's way to abort insertion.
     * @param rowIndex 行号
     * @return
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
        newRecord = controller.preInsert(newRecord);
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
            if (!controller.validate(this, lstChangedRow)) {
                return;
            }
            Mode prevMode = mode.get();
            if (useBackgroundTaskToSave) {
                service.runSaveInBackground(prevMode);
            } else {
                List<R> lstResult = new ArrayList<>();
                if (mode.get().equals(Mode.EDIT)) {
                    lstResult = controller.update(lstChangedRow);
                } else if (mode.get().equals(Mode.INSERT)) {
                    lstResult = controller.insert(lstChangedRow);
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
        if (controller.canEdit(tblView.getSelectionModel().getSelectedItem())) {
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
        if (mode.get() == Mode.INSERT) {
            @SuppressWarnings("unchecked") TablePosition<R, ?> selectedCell = tblView.getSelectionModel()
                    .getSelectedCells().get(0);
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
            if (!controller.canDelete(this)) {
                return;
            }
            int selectedRow = tblView.getSelectionModel().getSelectedIndex();
            List<R> lstToDelete = new ArrayList<>(tblView.getSelectionModel().getSelectedItems());

            if (useBackgroundTaskToDelete) {
                service.runDeleteInBackground(lstToDelete, selectedRow);
            } else {
                controller.delete(lstToDelete);
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
                controller.exportToExcel("Override TableController.exportToExcel to reset the title.", maxResult.get(), TableControl.this, lstCriteria);
            } else {
                controller.exportToExcelCurrentPage("Override TableController.exportToExcelCurrentPage to reset the title.", TableControl.this);
            }
        }
    }

    /**
     * start index change
     */
    private class StartIndexChangeListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
            reload();
        }
    }

    private final InvalidationListener tableCriteriaListener = new InvalidationListener() {
        @Override
        public void invalidated(Observable observable) {
            if (reloadOnCriteriaChange) {
                reloadFirstPage();
            }
        }
    };

    private class SortTypeChangeListener implements InvalidationListener {

        @Override
        public void invalidated(Observable o) {
            /**
             * If the column is not in sortOrder list, just ignore. It avoids
             * intermittent duplicate reload() calling
             */
            @SuppressWarnings("unchecked") TableColumn<R, Object> col = (TableColumn<R, Object>) ((SimpleObjectProperty<Object>) o).getBean();
            if (!tblView.getSortOrder().contains(col) || !stageShown) {
                return;
            }
            reload();
            resetColumnSortConfig();
        }
    }

    private final IntegerProperty maxResult = new SimpleIntegerProperty(DEFAULT_TABLE_MAX_ROW);

    /**
     * Set max record per retrieval. It will be the parameter in {@link TableOperation#loadData(int, java.util.List, java.util.List, java.util.List, int) loadData} maxResult parameter
     * @param maxRecord maxRecord
     */
    public void setMaxRecord(int maxRecord) {
        this.maxResult.set(maxRecord);
    }

    /**
     * Get max number of records per-retrieval.
     * @return maxRecord
     */
    public int getMaxRecord() {
        return maxResult.get();
    }

    public IntegerProperty maxRecordProperty() {
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
     * @param recordClass the record class for table row
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
     * Set it too false to prevent auto-reloading when there is table criteria
     * change. It is useful if we want to change tableCriteria of several
     * columns at a time. After that set it to true and call {@link TableControl#reloadFirstPage()
     * }
     * @param reloadOnCriteriaChange reloadOnCriteriaChange
     */
    public void setReloadOnCriteriaChange(boolean reloadOnCriteriaChange) {
        this.reloadOnCriteriaChange = reloadOnCriteriaChange;
    }

    /**
     * Get displayed record. It is just the same with
     * {@link TableView#getItems()}
     */
    public ObservableList<R> getRecords() {
        return tblView.getItems();
    }


    /**
     * Add button to toolbar. The button's style is set by this method. Make
     * sure to add image on the button and also define the action method.
     * @param btn btn
     */
    public void addButton(Button btn) {
        addNode(btn);
    }

    /**
     * Add JavaFX Node to table's toolbar
     * @param node node
     */
    public void addNode(Node node) {
        if (node instanceof Button) {
            node.getStyleClass().add("flat-button");
            ((Button) node).setMaxHeight(Double.MAX_VALUE);
        }
        tableToolBar.addNode(node);
    }

    /**
     * Set UI component visibility.
     * @param visible  预期可见状态
     * @param controls 控件列表
     */
    public void setVisibleComponents(boolean visible, TableControl.Component... controls) {
        tableToolBar.setVisibleComponents(visible, controls);
    }

    public void setFooterVisiablity(boolean visiable) {
        tableToolBar.setOrNot(this, footer, visiable);
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
     * {@link TableOperation#isRecordEditable}.
     * @param item item
     * @return false if item == null. True if mode is INSERT. otherwise depends
     * on the logic in {@link TableOperation#isRecordEditable}
     * @see TableOperation#isRecordEditable(java.lang.Object)
     */
    public final boolean isRecordEditable(R item) {
        if (item == null) {
            return false;
        }
        if (mode.get() == Mode.INSERT) {
            return true;
        }
        return controller.isRecordEditable(item);
    }

    private boolean resettingRecords = false;

    private void postLoadAction(TableData<R> vol) {
        if (vol.getRows() == null) {
            vol.setRows(new ArrayList<>());
        }
        totalRows = vol.getTotalRows();
        //keep track of previous selected row
        int selectedIndex = tblView.getSelectionModel().getSelectedIndex();
        TableColumn<R, ?> selectedColumn = null;
        if (!tblView.getSelectionModel().getSelectedCells().isEmpty()) {
            selectedColumn = TableViewHelper.getSelectedColumn(tblView, 0);
        }

        if (hasEditingCell()) {
            /**
             * Trigger cancelEdit if there is cell being edited. Otherwise,
             * ArrayIndexOutOfBound exception happens since tblView items are
             * cleared (see next lines) but setOnEditCommit listener is executed.
             */
            tblView.edit(-1, tblView.getColumns().get(0));
        }

        //clear items and add with objects that has just been retrieved
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
        startIndex.removeListener(startIndexChangeListener);

        paginationControl.refreshPageNums(page);
        paginationControl.select(startIndex.get() / maxResult.get());

        startIndex.addListener(startIndexChangeListener);
        toggleButtons(vol.isMoreRows());
        mode.set(Mode.READ);
        clearChange();

        // 自适应列
        if (fitColumnAfterReload) {
            for (TableColumn<R, ?> clm : tblView.getColumns()) {
                resizeToFit(clm, -1);
            }
        }

        footer.updateTotalRow(totalRows);

        for (TableColumn<R, ?> clm : getLeafColumns()) {
            if (clm instanceof BaseColumn) {
                ((BaseColumn<R, ?>) clm).getInvalidRecordMap().clear();
            }
        }
        controller.postLoadData();
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
        controller.postSave(prevMode);
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


        footer.updateTotalRow(totalRows);

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
                for (TableColumn<R, ?> column : columns) {
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
            resetItem = new MenuItem(TiwulFXUtil.getString("reset.columns"));
            resetItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    resetColumnPosition();
                }
            });
        }

        footer.addMenuItem(resetItem);
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
                columns.addListener(new ListChangeListener<TableColumn<R, ?>>() {
                    @Override
                    public void onChanged(Change<? extends TableColumn<R, ?>> change) {
                        while (change.next()) {
                            if (change.wasReplaced()) {
                                saveColumnPosition();
                            }
                        }
                    }
                });
                if (configurationID != null && configurationID.trim().length() != 0) {
                    for (final TableColumn<R, ?> clm : getColumns()) {
                        clm.widthProperty().addListener(new ChangeListener<Number>() {
                            @Override
                            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                                if (suppressWidthConfigListener) return;
                                int clmIdx = lstTableColumnsOriginalOrder.indexOf(clm);
                                try {
                                    TiwulFXUtil.writeProperties(configurationID + "." + clmIdx + ".width", newValue + "");
                                } catch (Exception ex) {
                                    logger.log(Level.WARNING, "Unable to save column width information. Column index: " + clmIdx, ex);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void resetColumnPosition() {
        Runnable runnable = () -> {
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
        };
        new Thread(runnable).start();
        try {
            suppressWidthConfigListener = true;

            footer.removeResetItem(resetItem);

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
            TableColumn<R, ?>[] arrColumn = new TableColumn[lstTableColumnsOriginalOrder.size()];
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
                    // Either the configuration file is corrupted or new column is added. Reset column position.
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
     * @see #setConfigurationID(java.lang.String) for detailed explanation
     */
    public String getConfigurationID() {
        return configurationID;
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

    private final ExceptionHandler exceptionHandler = TiwulFXUtil.getExceptionHandler();

    private void handleException(Throwable throwable) {
        Window window = null;
        if (getScene() != null) {
            window = getScene().getWindow();
        }
        exceptionHandler.handleException(throwable, window);
    }

    /**
     * Set the export-to-excel mode. Default value is configured in {@link TiwulFXUtil#DEFAULT_EXPORT_MODE}
     * @param exportMode ExportMode
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
     * If it is set to true, TableControl will use background task to execute
     * Load and Export actions. In this case, the corresponding methods in
     * {@link TableOperation} will be executed in background task so developer
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
     * @return
     */
    public boolean isUseBackgroundTaskToSave() {
        return useBackgroundTaskToSave;
    }

    /**
     * If it is set to true, TableControl will use background task to execute
     * Save action. In this case, the corresponding method in
     * {@link TableOperation} will be executed in background task so developer
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
     * @return isUseBackgroundTaskToDelete
     */
    public boolean isUseBackgroundTaskToDelete() {
        return useBackgroundTaskToDelete;
    }

    /**
     * If it is set to true, TableControl will use background task to execute
     * Delete action. In this case, the corresponding method in
     * {@link TableOperation} will be executed in background task so developer
     * need to avoid updating UI in it. Default value for this property is taken
     * from {@link TiwulFXUtil#DEFAULT_USE_BACKGROUND_TASK_TO_DELETE}. Default is false
     * @param useBackgroundTaskToDelete useBackgroundTaskToDelete
     */
    public void setUseBackgroundTaskToDelete(boolean useBackgroundTaskToDelete) {
        this.useBackgroundTaskToDelete = useBackgroundTaskToDelete;
    }

    /**
     * 表格数据操作
     */
    class TableControlService extends Service<Object> {

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
                return new DataLoadTask(lstSortedColumn, sortingOrders);
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

    /**
     * 数据加载
     */
    private class DataLoadTask extends Task<TableData<R>> {

        private List<String> lstSortedColumn;
        private List<SortType> sortingOrders;

        public DataLoadTask(List<String> sortedColumns, List<SortType> sortingOrders) {
            this.lstSortedColumn = sortedColumns;
            this.sortingOrders = sortingOrders;
            setOnFailed((WorkerStateEvent event) -> handleException(getException()));
            setOnSucceeded((WorkerStateEvent event) -> postLoadAction(getValue()));
        }

        @Override
        protected TableData<R> call() throws Exception {
            return controller.loadData(startIndex.get(), lstCriteria, lstSortedColumn, sortingOrders, maxResult.get());
        }
    }

    private class SaveTask extends Task<List<R>> {
        private Mode prevMode;

        public SaveTask(Mode prevMode) {
            this.prevMode = prevMode;
            setOnFailed((WorkerStateEvent event) -> {
                handleException(getException());
            });

            setOnSucceeded((WorkerStateEvent event) -> {
                postSaveAction(getValue(), prevMode);
            });
        }

        @Override
        protected List<R> call() throws Exception {
            List<R> lstResult = new ArrayList<>();
            if (mode.get().equals(Mode.EDIT)) {
                lstResult = controller.update(lstChangedRow);
            } else if (mode.get().equals(Mode.INSERT)) {
                lstResult = controller.insert(lstChangedRow);
            }
            return lstResult;
        }

    }

    private class DeleteTask extends Task<Void> {
        private final List<R> lstToDelete;

        public DeleteTask(List<R> lstToDelete, int selectedRow) {
            this.lstToDelete = lstToDelete;

            setOnFailed((WorkerStateEvent event) -> {
                handleException(getException());
            });

            setOnSucceeded((WorkerStateEvent event) -> {
                postDeleteAction(lstToDelete, selectedRow);
            });
        }

        @Override
        protected Void call() throws Exception {
            controller.delete(lstToDelete);
            return null;
        }

    }

    private class ExportTask extends Task<Void> {

        public ExportTask() {
            setOnFailed((WorkerStateEvent event) -> {
                handleException(getException());
            });
        }

        @Override
        protected Void call() throws Exception {
            if (exportMode == ExportMode.ALL_PAGES) {
                controller.exportToExcel("Override TableController.exportToExcel to reset the title.", maxResult.get(), TableControl.this, lstCriteria);
            } else {
                controller.exportToExcelCurrentPage("Override TableController.exportToExcelCurrentPage to reset the title.", TableControl.this);
            }
            return null;
        }
    }
}
