package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.*;
import com.panemu.tiwulfx.dialog.MessageDialog;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import com.panemu.tiwulfx.table.annotation.TableViewColumn;
import com.panemu.tiwulfx.utils.ClassUtils;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
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

import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO 将Table操作独立出来作为事件处理
 * @param <R>
 */
@SuppressWarnings("unchecked")
public class TableControl<R> extends VBox {

    /**
     * JavaFX TableView
     */
    private final CustomTableView<R> tableView;
    private PaginationControl paginationControl;

    private TableToolBar tableToolBar;
    private TableBehaviourBase<R> controller;

    /**
     * start index
     */
    private final ChangeListener<Number> startIndexChangeListener = (ov, t, t1) -> reload();
    private final InvalidationListener sortTypeChangeListener = new SortTypeChangeListener();
    private final ReadOnlyObjectWrapper<Mode> mode = new ReadOnlyObjectWrapper<>(null);

    /**
     * 记录发生改变的数据行
     */
    private final ObservableList<R> changedRows = FXCollections.observableArrayList();
    private final List<TableCriteria<?>> lstCriteria = new ArrayList<>();

    private Class<R> recordClass;

    /**
     * 编辑模式
     */
    private boolean directEdit = false;
    private boolean fitColumnAfterReload = false;
    private boolean reloadOnCriteriaChange = true;

    /**
     * TODO 明确此变量的含义
     */
    private boolean resettingRecords = false;

    private int lastColumnIndex = 0;
    private long totalRows = 0;
    private Integer page = 0;
    private final SimpleIntegerProperty startIndex = new SimpleIntegerProperty(0);
    private final IntegerProperty pageSize = new SimpleIntegerProperty(100);

    /**
     * 接管JavaFX TableView的列，后续所有操作直接操作此值，不通过
     * tblView.getColumns操作
     * {@code tableView.getColumns()}
     */
    private final ObservableList<TableColumn<R, ?>> columns;
    public final ObservableList<R> items;

    private final TableControlService service = new TableControlService();

    private MenuItem resetItem;
    private String configurationID;

    private final Logger logger = Logger.getLogger(TableControl.class.getName());
    private final List<RowBrowser> lstRowBrowser = new ArrayList<>();

    private boolean stageShown = false;
    private boolean suppressSortConfigListener = false;
    private boolean suppressWidthConfigListener = false;
    private final boolean closeRowBrowserOnReload = TiwulFXUtil.DEFAULT_CLOSE_ROW_BROWSER_ON_RELOAD;
    private boolean useBackgroundTaskToLoad = TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_LOAD;
    private boolean useBackgroundTaskToSave = TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_SAVE;
    private boolean useBackgroundTaskToDelete = TiwulFXUtil.DEFAULT_USE_BACKGROUND_TASK_TO_DELETE;
    private ExportMode exportMode = TiwulFXUtil.DEFAULT_EXPORT_MODE;

    /**
     * Table Operation Mode
     */
    public enum Mode {
        INSERT,
        EDIT,
        READ
    }

    public final boolean isEditing() {
        return Mode.EDIT == getMode();
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

        this.tableView = new CustomTableView<>();
        this.columns = tableView.getColumns();
        this.items = tableView.getItems();

        this.recordClass = recordClass;
        this.getStyleClass().add("table-control");

        initControls();

        initTableView();

        mode.addListener((ov, t, t1) -> {
            if (t1 == Mode.READ) {
                directEdit = false;
            }
        });

        cm = new ContextMenu();
        createCopyCellMenuItem();
        cm.setAutoHide(true);

        columns.addListener((ListChangeListener<TableColumn<R, ?>>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (TableColumn<R, ?> column : change.getAddedSubList()) {
                        initTableColumn(column);
                    }
                }
                lastColumnIndex = getLeafColumns().size() - 1;
            }
        });
        attachWindowVisibilityListener();

        if (this.controller == null) {
            this.controller = new TableBehaviourBase<>() {
            };
        }

        if (recordClass != null) {
            initColumns(recordClass);
        }
    }

    /**
     * TODO 可以自定义获取列定义的方式
     * @param recordClass 实体类
     */
    private void initColumns(Class<R> recordClass) {
        final Field[] declaredFields = recordClass.getDeclaredFields();
        final List<CustomTableColumn<R, ?>> columnsToBeAdd = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            final TableViewColumn tvc = declaredField.getAnnotation(TableViewColumn.class);
            if (tvc == null) {
                continue;
            }
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            // 根据数据类型推断选择使用什么列
            final TextColumn<R> column = new TextColumn<>(declaredField.getName());
            final double prefWidth = tvc.prefWidth();
            if (prefWidth != -1) {
                column.setPrefWidth(prefWidth);
            }
            column.setText(tvc.name());
            columnsToBeAdd.add(column);
        }
        columns.addAll(columnsToBeAdd);
    }

    private void initTableView() {
        // 表格任一列的顺序改变后重新加载数据
        tableView.getSortOrder().addListener((ListChangeListener<TableColumn<R, ?>>) change -> {
            if (stageShown) {
                reload();
                resetColumnSortConfig();
            }
        });

        tableView.editableProperty().bind(mode.isNotEqualTo(Mode.READ));
        tableView.getSelectionModel().cellSelectionEnabledProperty().bind(tableView.editableProperty());

        // 更新行号
        tableView.getSelectionModel().selectedIndexProperty()
                .addListener((ov, t, t1) -> footer.updateRowNum(page * pageSize.get() + t1.intValue() + 1));

        /* 点击单元格进行编辑 */
        tableView.getFocusModel().focusedCellProperty().addListener((observable, oldValue, newValue) -> {
            if (!resettingRecords && tableView.isEditable() && directEdit && agileEditing.get()) {
                tableView.edit(newValue);
            }
        });

        tableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                directEdit = false;
            } else if (event.getCode() == KeyCode.ENTER && mode.get() == Mode.READ) {
                getController().doubleClick(TableViewHelper.getSelectedItem(tableView));
            }
        });
        // Define policy for TAB key press
        tableView.addEventFilter(KeyEvent.KEY_PRESSED, tableKeyListener);
        /*
          In INSERT mode, only inserted row that is focusable
          Prevent moving focus to not-inserted-row in INSERT mode
         */
        tableView.getFocusModel().focusedCellProperty().addListener((observable, oldValue, newValue) -> {
            if (!Mode.INSERT.equals(mode.get()) || newValue.getRow() == -1 || oldValue.getRow() == -1) {
                return;
            }
            Platform.runLater(() -> {
                R oldRow = items.get(oldValue.getRow());
                R newRow = items.get(newValue.getRow());
                if (changedRows.contains(oldRow) && !changedRows.contains(newRow)) {
                    tableView.getFocusModel().focus(oldValue);
                    tableView.getSelectionModel().select(oldValue.getRow(), oldValue.getTableColumn());
                }
            });
        });
        tableView.setOnMouseReleased(event -> {
            if (cm.isShowing()) {
                cm.hide();
            }
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                if (!tableView.hasSelectedCells()) {
                    return;
                }
                final TablePosition<R, ?> pos = TableViewHelper.getSelectedPosition(tableView, 0);
                TableColumn<R, ?> column = null;
                if (pos != null) {
                    column = pos.getTableColumn();
                }
                if (column == null) {
                    column = tableView.getSelectedColumn();
                }
                if (searchMenuItem != null) {
                    cm.getItems().remove(searchMenuItem);
                }
                cm.getItems().remove(getPasteMenuItem());
                if (column instanceof CustomTableColumn) {
                    CustomTableColumn<R, Object> clm = (CustomTableColumn<R, Object>) column;
                    int row = tableView.getSelectionModel().getSelectedIndex();
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
                cm.show(tableView, event.getScreenX(), event.getScreenY());
            }
        });
        // create custom row factory that can intercept double click on grid row
        tableView.setRowFactory(param -> new TableControlRow<>(TableControl.this));
    }

    private TablePaneFooter footer;

    /**
     * FXML中会使用此方法
     * @return 所有列，一般是CustomTableColumn及其子类
     */
    public final ObservableList<TableColumn<R, ?>> getColumns() {
        return columns;
    }

    public ObservableList<R> getChangedRecords() {
        return changedRows;
    }

    /**
     * 初始化操作控件：比如工具栏，底部状态信息等
     */
    private void initControls() {
        // 分页控件
        paginationControl = new PaginationControl(this);
        // 工具栏
        tableToolBar = new TableToolBar(this, paginationControl);
        tableToolBar.disableProperty().bind(service.runningProperty());
        // 状态栏
        footer = new TablePaneFooter(this);
        footer.init(service);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        getChildren().addAll(tableToolBar, tableView, footer);
    }

    /**
     * 表格面板底部状态栏
     */
    static class TablePaneFooter extends StackPane {

        private final Label lblRowIndex;
        private final Label lblTotalRow;
        private final MenuButton menuButton;
        private final ProgressBar progressIndicator;

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
         * @param service service
         */
        public final void init(TableControl<?>.TableControlService service) {
            menuButton.disableProperty().bind(service.runningProperty());
            progressIndicator.visibleProperty().bind(service.runningProperty());
        }

        /**
         * 更新行号
         * @param rowNum 行号
         */
        public void updateRowNum(int rowNum) {
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
        final TableControl<?> tableControl;
        public Button btnAdd;
        public Button btnEdit;
        public Button btnDelete;
        public Button btnReload;
        public Button btnSave;
        public Button btnExport;
        public Region spacer;
        // 分页
        private final PaginationControl paginationControl;

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

            TiwulFXUtil.setToolTip(btnAdd, "add.record");
            TiwulFXUtil.setToolTip(btnDelete, "delete.record");
            TiwulFXUtil.setToolTip(btnEdit, "edit.record");
            TiwulFXUtil.setToolTip(btnReload, "reload.records");
            TiwulFXUtil.setToolTip(btnExport, "export.records");
            TiwulFXUtil.setToolTip(btnSave, "save.record");

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
         * @param parent  父节点
         * @param control Control
         * @param visible 是否可见
         */
        public void setOrNot(ToolBar parent, Node control, boolean visible) {
            if (!visible) {
                parent.getItems().remove(control);
            } else if (!parent.getItems().contains(control)) {
                parent.getItems().add(control);
            }
        }

        public void setOrNot(Pane parent, Node control, boolean visible) {
            if (!visible) {
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
        private final Button btnFirstPage;
        private final Button btnLastPage;
        private final Button btnNextPage;
        private final Button btnPrevPage;
        /**
         * pageNum input
         */
        private final ComboBox<Integer> cmbPage;

        public PaginationControl(TableControl<?> tableControl) {
            this.setAlignment(Pos.CENTER);

            btnFirstPage = new Button();
            btnFirstPage.setGraphic(TiwulFXUtil.getGraphicFactory().createPageFirstGraphic());
            final EventHandler<ActionEvent> paginationHandler = new EventHandler<>() {
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

            TiwulFXUtil.setToolTip(btnFirstPage, "go.to.first.page");
            TiwulFXUtil.setToolTip(btnLastPage, "go.to.last.page");
            TiwulFXUtil.setToolTip(btnNextPage, "go.to.next.page");
            TiwulFXUtil.setToolTip(btnPrevPage, "go.to.prev.page");

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
    private final EventHandler<KeyEvent> tableKeyListener = new EventHandler<>() {
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
                    if (tableView.getSelectionModel().getSelectedCells().get(0).getColumn() == 0) {
                        List<TableColumn<R, ?>> leafColumns = getLeafColumns();
                        showRow(tableView.getSelectionModel().getSelectedIndex() - 1);
                        tableView.getSelectionModel().select(tableView.getSelectionModel()
                                .getSelectedIndex() - 1, leafColumns.get(leafColumns.size() - 1));
                    } else {
                        tableView.getSelectionModel().selectLeftCell();
                    }
                } else {
                    if (tableView.getSelectionModel().getSelectedCells().get(0).getColumn() == lastColumnIndex) {
                        showRow(tableView.getSelectionModel().getSelectedIndex() + 1);
                        tableView.getSelectionModel()
                                .select(tableView.getSelectionModel().getSelectedIndex() + 1, columns.get(0));
                    } else {
                        tableView.getSelectionModel().selectRightCell();
                    }
                }
                horizontalScroller.run();
                event.consume();
            } else if (event.getCode() == KeyCode.ENTER && !event.isControlDown() && !event.isAltDown() && !event.isShiftDown()) {
                // 编辑模式下，Enter可从移动的单元格向下移
                if (agileEditing.get()) {
                    if (directEdit) {
                        showRow(tableView.getSelectionModel().getSelectedIndex() + 1);
                        // 末尾
                        if (tableView.getSelectionModel().getSelectedIndex() == items.size() - 1) {
                            //it will trigger cell's commit edit for the most bottom row
                            tableView.getSelectionModel().selectAboveCell();
                        }
                        tableView.getSelectionModel().selectBelowCell();
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
        return tableView.getEditingCell() != null && tableView.getEditingCell().getRow() > -1;
    }

    /**
     * 展示index对应的行
     * @param index 行索引
     */
    public void showRow(int index) {
        if (index < 0 || index >= getRecords().size()) {
            return;
        }
        final Node node = tableView.lookup("VirtualFlow");
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
     * @param record 单条记录
     */
    public void markAsChanged(R record) {
        if (!changedRows.contains(record)) {
            changedRows.add(record);
        }
    }

    /**
     * Paste text on clipboard. Doesn't work on READ mode.
     */
    @SuppressWarnings("rawtypes")
    public void paste() {
        if (mode.get() == Mode.READ) {
            return;
        }
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasString()) {
            final String text = clipboard.getString();
            if (text != null) {

                List<TablePosition> cells = tableView.getSelectionModel().getSelectedCells();
                if (cells.isEmpty()) {
                    return;
                }
                TablePosition cell = cells.get(0);
                List<TableColumn<R, ?>> lstColumn = getLeafColumns();
                TableColumn startColumn = null;
                for (TableColumn clm : lstColumn) {
                    if (clm instanceof CustomTableColumn && clm == cell.getTableColumn()) {
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
                    if (rowIndex < items.size()) {
                        item = items.get(rowIndex);
                    } else if (mode.get() == Mode.EDIT) {
                        /*
                          Will ensure the content display to TEXT_ONLY because
                          there is no way to update cell editors value (in
                          agile editing mode)
                         */
                        tableView.getSelectionModel().clearSelection();
                        return;//stop pasting as it already touched last row
                    }

                    if (!changedRows.contains(item)) {
                        if (mode.get() == Mode.INSERT) {
                            //means that selected row is not new row. Let's create new row
                            createNewRow(rowIndex);
                            item = items.get(rowIndex);
                        } else {
                            changedRows.add(item);
                        }
                    }

                    showRow(rowIndex);
                    // Handle multi column paste
                    String[] stringCellValues = line.split("\t");
                    TableColumn toFillColumn = startColumn;
                    tableView.getSelectionModel().select(rowIndex, toFillColumn);
                    for (String stringCellValue : stringCellValues) {
                        if (toFillColumn == null) {
                            break;
                        }
                        if (toFillColumn instanceof CustomTableColumn && toFillColumn.isEditable() && toFillColumn.isVisible()) {
                            try {
                                Object oldValue = toFillColumn.getCellData(item);
                                Object newValue = ((CustomTableColumn) toFillColumn).convertFromString(stringCellValue);
                                ClassUtils.setSimpleProperty(item, ((CustomTableColumn) toFillColumn).getPropertyName(), newValue);
                                if (mode.get() == Mode.EDIT) {
                                    ((CustomTableColumn) toFillColumn).addRecordChange(item, oldValue, newValue);
                                }
                            } catch (Exception ex) {
                                MessageDialog.Answer answer = MessageDialogBuilder.error(ex)
                                        .message("msg.paste.error", stringCellValue, toFillColumn.getText())
                                        .buttonType(MessageDialog.ButtonType.YES_NO)
                                        .yesOkButtonText("continue.pasting")
                                        .noButtonText("stop").show(getScene().getWindow());
                                if (answer == MessageDialog.Answer.NO) {
                                    stopPasting = true;
                                    break;
                                }
                            }
                        }
                        tableView.getSelectionModel().selectRightCell();

                        TablePosition<R, ?> nextCell = TableViewHelper.getSelectedPosition(tableView, 0);
                        if (nextCell.getTableColumn() instanceof CustomTableColumn && nextCell.getTableColumn() != toFillColumn) {
                            toFillColumn = nextCell.getTableColumn();
                        } else {
                            toFillColumn = null;
                        }
                    }
                    rowIndex++;
                }

                refresh();

                /*
                  Will ensure the content display to TEXT_ONLY because there is
                  no way to update cell editors value (in agile editing mode)
                 */
                tableView.clearSelection();
            }
        }
    }


    /**
     * It calls {@link TableView#refresh()}
     */
    public void refresh() {
        tableView.refresh();
    }

    /**
     * Force the table to repaint specified row.It propagates the call to {@link TableControlRow#refresh()}.
     * @param record specified record to refresh.
     */
    public void refresh(R record) {
        Set<Node> nodes = tableView.lookupAll(".table-row-cell");
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
            TableColumn<R, ?> col = TableViewHelper.getFocusedColumn(tableView);
            if (col == null) {
                return;
            }

            if (scrollBar == null) {
                for (Node n : tableView.lookupAll(".scroll-bar")) {
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
            for (TableColumn<R, ?> c : tableView.getVisibleLeafColumns()) {
                if (c.equals(col)) {
                    break;
                }
                start += c.getWidth();
            }
            double end = start + col.getWidth();

            // determine the width of the table
            double headerWidth = tableView.getWidth() - tableView.snappedLeftInset() - tableView.snappedRightInset();

            // determine by how much we need to translate the table to ensure that
            // the start position of this column lines up with the left edge of the
            // tableview, and also that the columns don't become detached from the
            // right edge of the table
            double pos = scrollBar.getValue();
            double max = scrollBar.getMax();
            double newPos;
            if (start < pos && start >= 0) {
                newPos = start;
            } else {
                double delta = start < 0 || end > headerWidth ? start - pos : 0;
                newPos = Math.min(pos + delta, max);
            }
            // FIXME we should add API in VirtualFlow so we don't end up going
            // direct to the bar.
            // actually shift the flow - this will result in the header moving
            // as well
            scrollBar.setValue(newPos);
        }
    };

    /**
     * Get single selected record property. If multiple records are selected, it
     * returns the last one
     */
    public final ReadOnlyObjectProperty<R> selectedItemProperty() {
        return tableView.getSelectionModel().selectedItemProperty();
    }

    /**
     * @see #selectedItemProperty()
     */
    public final R getSelectedItem() {
        return tableView.getSelectionModel().selectedItemProperty().get();
    }

    /**
     * @see TableView#getSelectionModel()
     */
    public final TableView.TableViewSelectionModel<R> getSelectionModel() {
        return tableView.getSelectionModel();
    }

    // 菜单
    private final ContextMenu cm;
    private MenuItem searchMenuItem;

    private void copyCell() {
        R selectedRow = getSelectedItem();
        if (selectedRow == null) {
            return;
        }
        String textToCopy;
        TablePosition<R, Object> pos = TableViewHelper.getSelectedPosition(tableView, 0);
        TableColumn<R, Object> column = null;
        if (pos != null) {
            column = pos.getTableColumn();
        }
        if (column == null) {
            column = tableView.getSelectedColumn();
        }
        if (column instanceof CustomTableColumn) {
            CustomTableColumn<R, Object> bc = (CustomTableColumn<R, Object>) column;
            Object cellData = bc.getCellData(selectedRow);
            textToCopy = bc.convertToString(cellData);
        } else if (column != null) {
            Object cellValue = column.getCellData(selectedRow);
            textToCopy = String.valueOf(cellValue);
        } else {
            return;
        }
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(textToCopy);
        clipboard.setContent(content);
    }

    /**
     * copy row
     */
    @SuppressWarnings("rawtypes")
    private void copyRow() {
        R selectedRow = getSelectedItem();
        if (selectedRow == null) {
            return;
        }
        StringBuilder textToCopy = new StringBuilder();
        for (TableColumn clm : getLeafColumns()) {
            if (clm instanceof CustomTableColumn) {
                CustomTableColumn bc = (CustomTableColumn) clm;
                Object cellData = bc.getCellData(selectedRow);
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
            if (tableColumn instanceof CustomTableColumn) {
                stringVal = ((CustomTableColumn<R, ?>) tableColumn).getCellDataAsString(selectedRow);
            } else {
                stringVal = String.valueOf(tableColumn.getCellData(selectedRow));
            }
            rcd = new RowBrowser.Record(tableColumn.getText(), stringVal);
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
     * @param menuItem menuItem
     * @see #removeContextMenuItem(javafx.scene.control.MenuItem)
     */
    public final void addContextMenuItem(MenuItem menuItem) {
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

    /**
     * @param col TableColumn
     */
    protected void resizeToFit(TableColumn<R, ?> col) {
        if (items == null || items.isEmpty()) {
            return;
        }
        TableCell<R, ?> cell = TableViewHelper.callCellFactory(col);
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
        final int rows = items.size();
        double maxWidth = 0;
        for (int row = 0; row < rows; row++) {
            cell.updateTableColumn(col);
            cell.updateTableView(tableView);
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
     * {@link #setController(TableBehaviourBase)}
     */
    public TableBehaviourBase<R> getController() {
        return controller;
    }

    /**
     * Set object responsible to fetch, insert, delete and update data
     * @param controller controller
     */
    public final void setController(TableBehaviourBase<R> controller) {
        this.controller = controller;
    }

    /**
     * 初始化表的一列
     * @param clm 列
     */
    @SuppressWarnings("unchecked")
    private void initTableColumn(TableColumn<R, ?> clm) {
        List<TableColumn<R, ?>> lstColumn = new ArrayList<>();
        lstColumn.add(clm);
        lstColumn = getColumnsRecursively(lstColumn);
        for (TableColumn<R, ?> column : lstColumn) {
            if (column instanceof CustomTableColumn) {
                final CustomTableColumn<R, Object> baseColumn = (CustomTableColumn<R, Object>) column;
                baseColumn.tableCriteriaProperty().addListener(observable -> {
                    if (reloadOnCriteriaChange) {
                        reloadFirstPage();
                    }
                });
                // 监听排序
                baseColumn.sortTypeProperty().addListener(sortTypeChangeListener);
                // 编辑提交事件
                baseColumn.addEventHandler(TableColumn.editCommitEvent(), (EventHandler<CellEditEvent<R, Object>>) t -> {
                    if (!TableViewHelper.isCellEditPositionValid(t) || !TableViewHelper.isCellValueUpdated(t)) {
                        System.out.println("编辑无效");
                        return;
                    }
                    R persistentObj = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    if (getMode() == Mode.EDIT) {
                        if (!changedRows.contains(persistentObj)) {
                            changedRows.add(persistentObj);
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
     * @param columns columns
     */
    @SafeVarargs
    public final void addColumn(TableColumn<R, ?>... columns) {
        this.columns.addAll(columns);
    }

    /**
     * Get list of columns including the nested ones.
     * @param lstColumn columns
     * @return columns
     */
    private List<TableColumn<R, ?>> getColumnsRecursively(List<TableColumn<R, ?>> lstColumn) {
        List<TableColumn<R, ?>> newColumns = new ArrayList<>();
        for (TableColumn<R, ?> column : lstColumn) {
            getColumns(column, newColumns);
        }
        return newColumns;
    }

    private void getColumns(TableColumn<R, ?> column, List<TableColumn<R, ?>> columns) {
        if (column.getColumns().isEmpty()) {
            columns.add(column);
        } else {
            for (TableColumn<R, ?> oneColumn : column.getColumns()) {
                getColumns(oneColumn, columns);
            }
        }
    }

    /**
     * Get list of columns that is hold cell. It excludes columns that are
     * containers of nested columns.
     */
    public List<TableColumn<R, ?>> getLeafColumns() {
        List<TableColumn<R, ?>> result = new ArrayList<>();
        for (TableColumn<R, ?> clm : tableView.getColumns()) {
            getColumns(clm, result);
        }
        return result;
    }

    /**
     * Clear all criteria/filters applied to columns then reload the first page.
     */
    public void clearTableCriteria() {
        setReloadOnCriteriaChange(false);
        for (TableColumn<R, ?> clm : getLeafColumns()) {
            if (clm instanceof CustomTableColumn) {
                ((CustomTableColumn<R, ?>) clm).setTableCriteria(null);
            }
        }
        setReloadOnCriteriaChange(true);
        reloadFirstPage();
    }

    /**
     * Reload data on current page. This method is called when pressing reload button.
     * @see #reloadFirstPage()
     */
    @SuppressWarnings("rawtypes")
    public void reload() {
        if (!changedRows.isEmpty()) {
            if (!controller.revertConfirmation(this, changedRows.size())) {
                return;
            }
        }

        lstCriteria.clear();

        List<TableCriteria<Object>> list = new ArrayList<>();

        // Should be in new arraylist to avoid
        // java.lang.IllegalArgumentException: Children: duplicate children added
        List<TableColumn<R, ?>> lstColumns = new ArrayList<>(tableView.getColumns());
        lstColumns = getColumnsRecursively(lstColumns);
        for (TableColumn<R, ?> clm : lstColumns) {
            if (clm instanceof CustomTableColumn) {
                CustomTableColumn<R, ?> customColumn = (CustomTableColumn<R, ?>) clm;
                if (customColumn.getTableCriteria() != null) {
                    final TableCriteria<?> tableCriteria = customColumn.getTableCriteria();
                    lstCriteria.add(tableCriteria);
                }
            }
        }

        // 排序的列
        List<String> lstSortedColumn = new ArrayList<>();
        List<SortType> lstSortedType = new ArrayList<>();
        for (TableColumn<R, ?> tc : tableView.getSortOrder()) {
            if (tc instanceof CustomTableColumn) {
                lstSortedColumn.add(((CustomTableColumn<R, ?>) tc).getPropertyName());
                lstSortedType.add(tc.getSortType());
            } else if (tc.getCellValueFactory() instanceof PropertyValueFactory) {
                PropertyValueFactory valFactory = (PropertyValueFactory) tc.getCellValueFactory();
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
            TableData<R> vol = controller.loadData(startIndex.get(), list, lstSortedColumn, lstSortedType, pageSize.get());
            postLoadAction(vol);
        }
    }

    private void clearChange() {
        changedRows.clear();
        for (TableColumn<R, ?> clm : getLeafColumns()) {
            if (clm instanceof CustomTableColumn) {
                ((CustomTableColumn<R, ?>) clm).clearRecordChange();
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
            if (column instanceof CustomTableColumn) {
                CustomTableColumn<R, ?> baseColumn = (CustomTableColumn<R, ?>) column;
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
        if (event.isConsumed()) {
            return;
        }
        // since the combobox is editable, it might have String value
        page = Integer.valueOf(String.valueOf(pageNum));
        page = page - 1;
        startIndex.set(page * pageSize.get());
    }

    /**
     * Return false if the insertion is canceled because the controller return
     * null object. It is controller's way to abort insertion.
     * @param rowIndex 行号
     */
    private void createNewRow(int rowIndex) {
        R newRecord = ClassUtils.newInstance(recordClass);
        if (items.size() == 0) {
            rowIndex = 0;
        }
        items.add(rowIndex, newRecord);
        changedRows.add(newRecord);
    }

    /**
     * Add new row under selected row or in the first row if there is no row
     * selected. This method is called when pressing insert button
     */
    public void insert() {
        R newRecord = controller.newItem(recordClass);
        newRecord = controller.preInsert(newRecord);
        if (newRecord == null) {
            return;
        }
        int selectedRowIndex = tableView.getSelectedIndex() + 1;
        if (items.size() == 0) {
            selectedRowIndex = 0;
        }
        items.add(selectedRowIndex, newRecord);
        changedRows.add(newRecord);

        this.setOperationMode(Mode.INSERT);

        /*
         Force the table to layout before selecting the newly added row. Without this call, the selection
         will land on existing row at specified index because the new row is not yet actually added to the
         table. It makes the editor controls are not displayed in agileEditing mode.
         */
        tableView.layout();
        tableView.requestFocus();

        ObservableList<R> records = getRecords();

        final int row = selectedRowIndex;
        showRow(row);
        tableView.getSelectionModel().select(row, columns.get(0));
    }

    /**
     * Save changes. This method is called when pressing save button
     */
    public void save() {
        // In case there is a cell being edited, call clearSelection() to trigger
        // commitEdit() in the edited cell.
        tableView.getSelectionModel().clearSelection();
        try {
            if (changedRows.isEmpty()) {
                setOperationMode(Mode.READ);
                return;
            }
            if (!controller.validate(this, changedRows)) {
                return;
            }
            Mode prevMode = mode.get();
            if (useBackgroundTaskToSave) {
                service.runSaveInBackground(prevMode);
            } else {
                List<R> lstResult = new ArrayList<>();
                if (mode.get().equals(Mode.EDIT)) {
                    lstResult = controller.update(changedRows);
                } else if (mode.get().equals(Mode.INSERT)) {
                    lstResult = controller.insert(changedRows);
                }
                postSaveAction(lstResult, prevMode);
            }
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    public final void setOperationMode(Mode operationMode) {
        mode.set(operationMode);
    }

    /**
     * Edit table. This method is called when pressing edit button.
     */
    public void edit() {
        if (controller.canEdit(tableView.getSelectionModel().getSelectedItem())) {
            setOperationMode(Mode.EDIT);
        }
    }

    /**
     * Delete selected row. This method is called when pressing delete button.
     * It will delete selected record(s)
     */
    public void delete() {
        // Delete row that is not yet persisted in database.
        if (mode.get() == Mode.INSERT) {
            TablePosition<R, ?> selectedCell = tableView.getSelectionModel().getSelectedCells().get(0);
            int selectedRow = selectedCell.getRow();
            changedRows.removeAll(tableView.getSelectionModel().getSelectedItems());
            tableView.getSelectionModel()
                    .clearSelection();// it is needed if agile editing is enabled to trigger content display change later
            items.remove(selectedRow);
            tableView.layout();//relayout first before set selection. Without this, cell contend display won't be set property
            tableView.requestFocus();
            if (selectedRow == items.size()) {
                selectedRow--;
            }
            if (changedRows.contains(items.get(selectedRow))) {
                tableView.getSelectionModel().select(selectedRow, selectedCell.getTableColumn());
            } else {
                tableView.getSelectionModel().select(selectedRow - 1, selectedCell.getTableColumn());
            }
            return;
        }

        // Delete persistence record.
        try {
            if (!controller.canDelete(this)) {
                return;
            }
            int selectedRow = tableView.getSelectionModel().getSelectedIndex();
            List<R> lstToDelete = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());

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
                controller.exportToExcel("Override TableController.exportToExcel to reset the title.", pageSize.get(), TableControl.this, genericMove(lstCriteria));
            } else {
                controller.exportToExcelCurrentPage("Override TableController.exportToExcelCurrentPage to reset the title.", TableControl.this);
            }
        }
    }

    /**
     * 嵌套两层的泛型无法强制转换
     * TODO 找更好的解决办法
     * @param list List<TableCriteria<?>>
     * @param <C>  @return List<TableCriteria<C>>
     */
    private <C> List<TableCriteria<C>> genericMove(List<TableCriteria<?>> list) {
        List<TableCriteria<C>> result = new ArrayList<>();
        for (TableCriteria<?> tableCriteria : list) {
            result.add((TableCriteria<C>) tableCriteria);
        }
        return result;
    }

    private class SortTypeChangeListener implements InvalidationListener {

        @Override
        public void invalidated(Observable o) {
            /*
              If the column is not in sortOrder list, just ignore. It avoids
              intermittent duplicate reload() calling
             */
            TableColumn<R, Object> col = (TableColumn<R, Object>) ((SimpleObjectProperty<Object>) o).getBean();
            if (!tableView.getSortOrder().contains(col) || !stageShown) {
                return;
            }
            reload();
            resetColumnSortConfig();
        }
    }

    /**
     * Set max record per retrieval. It will be the parameter in {@link TableBehaviourBase#loadData(int, java.util.List, java.util.List, java.util.List, int) loadData} maxResult parameter
     * @param maxRecord maxRecord
     */
    public void setMaxRecord(int maxRecord) {
        this.pageSize.set(maxRecord);
    }

    /**
     * Get max number of records per-retrieval.
     * @return maxRecord
     */
    public int getMaxRecord() {
        return pageSize.get();
    }

    public IntegerProperty maxRecordProperty() {
        return pageSize;
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
    public final ObservableList<R> getRecords() {
        return items;
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

    public void setFooterVisibility(boolean visible) {
        tableToolBar.setOrNot(this, footer, visible);
    }

    public final Mode getMode() {
        return mode.get();
    }

    public final ReadOnlyObjectProperty<Mode> modeProperty() {
        return mode.getReadOnlyProperty();
    }

    public final TableView<R> getTableView() {
        return tableView;
    }

    public final ReadOnlyObjectProperty<TablePosition<R, ?>> editingCellProperty() {
        return tableView.editingCellProperty();
    }

    /**
     * Check if a record is editable. After ensure that the item is not null and
     * the mode is not {@link Mode#INSERT} it will propagate the call to
     * {@link TableBehaviourBase#isRecordEditable}.
     * @param item item
     * @return false if item == null. True if mode is INSERT. otherwise depends
     * on the logic in {@link TableBehaviourBase#isRecordEditable}
     * @see TableBehaviourBase#isRecordEditable(java.lang.Object)
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

    /**
     * 数据加载
     * @param vol 表数据
     */
    private void postLoadAction(TableData<R> vol) {
        if (vol.getRows() == null) {
            vol.setRows(new ArrayList<>());
        }
        totalRows = vol.getTotalRows();
        //keep track of previous selected row
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        TableColumn<R, ?> selectedColumn = null;
        if (!tableView.getSelectionModel().getSelectedCells().isEmpty()) {
            selectedColumn = TableViewHelper.getSelectedColumn(tableView, 0);
        }

        if (hasEditingCell()) {
            /*
               Trigger cancelEdit if there is cell being edited. Otherwise,
               ArrayIndexOutOfBound exception happens since tblView items are
               cleared (see next lines) but setOnEditCommit listener is executed.
             */
            tableView.edit(-1, tableView.getColumns().get(0));
        }

        //clear items and add with objects that has just been retrieved
        resettingRecords = true;
        items.setAll(vol.getRows());
        if (selectedIndex < vol.getRows().size()) {
            tableView.getSelectionModel().select(selectedIndex, selectedColumn);
        } else {
            tableView.getSelectionModel().select(vol.getRows().size() - 1, selectedColumn);
        }
        resettingRecords = false;

        long page = vol.getTotalRows() / pageSize.get();
        if (vol.getTotalRows() % pageSize.get() != 0) {
            page++;
        }
        startIndex.removeListener(startIndexChangeListener);
        startIndex.addListener(startIndexChangeListener);

        paginationControl.refreshPageNums(page);
        paginationControl.select(startIndex.get() / pageSize.get());

        toggleButtons(vol.isMoreRows());
        mode.set(Mode.READ);
        clearChange();

        // 自适应列
        if (fitColumnAfterReload) {
            for (TableColumn<R, ?> clm : tableView.getColumns()) {
                resizeToFit(clm);
            }
        }

        footer.updateTotalRow(totalRows);

        for (TableColumn<R, ?> clm : getLeafColumns()) {
            if (clm instanceof CustomTableColumn) {
                ((CustomTableColumn<R, ?>) clm).getInvalidRecordMap().clear();
            }
        }
        controller.postLoadData();
    }

    private void postSaveAction(List<R> lstResult, Mode prevMode) {
        mode.set(Mode.READ);
        /*
            In case objects in lstResult differ with original object. Ex: In SOA
            architecture, sent objects always differ with received object due to
            serialization.
         */
        int i = 0;
        for (R row : changedRows) {
            int index = items.indexOf(row);
            items.remove(index);
            items.add(index, lstResult.get(i));
            i++;
        }

        /*
         Refresh cells. They won't refresh automatically if the entity's
         properties bound to the cells are not javaFX property object.
         */
        clearChange();
        controller.postSave(prevMode);
    }

    private void postDeleteAction(List<R> lstDeleted, int selectedRow) {
        items.removeAll(lstDeleted);
        /* select a row */
        if (!items.isEmpty()) {
            if (selectedRow >= items.size()) {
                tableView.getSelectionModel().select(items.size() - 1);
            } else {
                tableView.getSelectionModel().select(selectedRow);
            }
        }
        totalRows = totalRows - lstDeleted.size();

        footer.updateTotalRow(totalRows);

        tableView.requestFocus();
    }

    private void saveColumnPosition() {
        if (configurationID == null || configurationID.trim().length() == 0) {
            return;
        }
        Runnable runnable = () -> {
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
        };
        new Thread(runnable).start();
        configureResetMenuItem();
    }

    private void configureResetMenuItem() {
        if (resetItem == null) {
            resetItem = new MenuItem(TiwulFXUtil.getString("reset.columns"));
            resetItem.setOnAction(t -> resetColumnPosition());
        }
        footer.addMenuItem(resetItem);
    }

    private void attachWindowVisibilityListener() {
        this.sceneProperty().addListener((ov, t, scene) -> {
            if (scene != null) {
                /*
                  This method could be executed again if this table is inside a detachable tab pane.
                 */
                if (stageShown) return;
                stageShown = true;
                /*
                  TODO Once this code is executed, the scene listener need to be removed
                  to avoid potential memory leak.
                  This code need to be initialized once only.
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
                                TiwulFXUtil.writeProperties(configurationID + "." + clmIdx + ".width", String.valueOf(newValue));
                            } catch (Exception ex) {
                                logger.log(Level.WARNING, "Unable to save column width information. Column index: " + clmIdx, ex);
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

                for (int i = 0; i < tableView.getSortOrder().size(); i++) {
                    TableColumn<R, ?> t = tableView.getSortOrder().get(i);
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
                        lstTableColumnsOriginalOrder.get(i).setPrefWidth(Double.parseDouble(stringWidth));
                    } catch (Exception ex) {
                        logger.warning("Invalid column width configuration: " + configurationID + "." + i + ".width");
                    }
                }
            }
            List<TableColumn<R, ?>> lstSorted = new ArrayList<>();
            for (TableColumn<R, ?> clm : arrColumn) {
                if (clm != null) lstSorted.add(clm);
            }
            if (!lstSorted.isEmpty()) {
                tableView.getSortOrder().clear();
                for (TableColumn<R, ?> tableColumn : lstSorted) {
                    tableView.getSortOrder().add(tableColumn);
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
                    // Either the configuration file is corrupted or new column is added. Reset column position.
                    resetColumnPosition();
                } else {
                    columns.clear();
                    for (int i = 0; i <= maxIndex; i++) {
                        TableColumn<R, ?> tc = map.get(i);
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
     * If it is set, the columns position, width and sorting information will be saved to a configuration file
     * located in a folder inside user's home directory. Call {@link TiwulFXUtil#setApplicationId(java.lang.String, java.lang.String)}
     * to set the folder name. The configurationID must be unique across all TableControl in an application, but it is
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
     * {@link TableBehaviourBase} will be executed in background task so developer
     * need to avoid updating UI in those methods. Default value for this
     * property is taken from {@link TiwulFXUtil#DEFAULT_USE_BACKGROUND_TASK_TO_LOAD}. Default is FALSE
     * @param useBackgroundTaskToLoad useBackgroundTaskToLoad
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
     * {@link TableBehaviourBase} will be executed in background task so developer
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
     * {@link TableBehaviourBase} will be executed in background task so developer
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
        @SuppressWarnings("rawtypes")
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

        private final List<String> lstSortedColumn;
        private final List<SortType> sortingOrders;

        public DataLoadTask(List<String> sortedColumns, List<SortType> sortingOrders) {
            this.lstSortedColumn = sortedColumns;
            this.sortingOrders = sortingOrders;
            setOnFailed((WorkerStateEvent event) -> handleException(getException()));
            setOnSucceeded((WorkerStateEvent event) -> postLoadAction(getValue()));
        }

        @Override
        protected TableData<R> call() {
            return controller.loadData(startIndex.get(), genericMove(lstCriteria), lstSortedColumn, sortingOrders, pageSize.get());
        }
    }

    private class SaveTask extends Task<List<R>> {

        public SaveTask(Mode prevMode) {
            setOnFailed((WorkerStateEvent event) -> handleException(getException()));
            setOnSucceeded((WorkerStateEvent event) -> postSaveAction(getValue(), prevMode));
        }

        @Override
        protected List<R> call() {
            List<R> lstResult = new ArrayList<>();
            if (mode.get().equals(Mode.EDIT)) {
                lstResult = controller.update(changedRows);
            } else if (mode.get().equals(Mode.INSERT)) {
                lstResult = controller.insert(changedRows);
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
            controller.delete(lstToDelete);
            return null;
        }

    }

    private class ExportTask extends Task<Void> {

        public ExportTask() {
            setOnFailed((WorkerStateEvent event) -> handleException(getException()));
        }

        @Override
        protected Void call() {
            if (exportMode == ExportMode.ALL_PAGES) {
                controller.exportToExcel("Override TableController.exportToExcel to reset the title.", pageSize.get(), TableControl.this, genericMove(lstCriteria));
            } else {
                controller.exportToExcelCurrentPage("Override TableController.exportToExcelCurrentPage to reset the title.", TableControl.this);
            }
            return null;
        }
    }
}
