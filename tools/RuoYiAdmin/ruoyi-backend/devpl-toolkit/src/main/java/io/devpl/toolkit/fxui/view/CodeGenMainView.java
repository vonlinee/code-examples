package io.devpl.toolkit.fxui.view;

import io.devpl.toolkit.fxui.model.DBTableListModel;
import io.devpl.toolkit.fxui.model.DataSourceProperties;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;

/**
 * 代码生成主窗口
 * 完全用Java代码表现界面
 */
public class CodeGenMainView extends BorderPane {

    private GridPane grpTop;

    public TableView<DBTableListModel> tblvDbTableList;
    public TableColumn<DBTableListModel, Integer> tblcId;
    public TableColumn<DBTableListModel, Boolean> tblcSelected;
    public TableColumn<DBTableListModel, String> tblcTableName;
    public TableColumn<DBTableListModel, String> tblcTableComment;
    public TableColumn<DBTableListModel, String> tblcTableCreatedTime;

    public CodeGenMainView() {

        // 第一行
        final Label labelDs = new Label("数据源");
        labelDs.setMinWidth(60);
        labelDs.setMaxWidth(60);
        labelDs.setAlignment(Pos.CENTER); // 垂直水平居中
        final ChoiceBox<DataSourceProperties> chobDs = new ChoiceBox<>();
        chobDs.getItems().add(new DataSourceProperties());
        chobDs.getItems().add(new DataSourceProperties());
        chobDs.getItems().add(new DataSourceProperties());
        chobDs.setMinWidth(400.0);
        chobDs.setConverter(new StringConverter<>() {
            @Override
            public String toString(DataSourceProperties object) {
                return object.getHost() + ":" + object.getPort();
            }

            @Override
            public DataSourceProperties fromString(String string) {
                // 不可编辑状态下,这里不会被调用
                return null;
            }
        });

        final Button btnManageDs = new Button("数据源管理");
        btnManageDs.setMinWidth(100);
        btnManageDs.setMaxWidth(100);
        final HBox hBox = new HBox(labelDs, chobDs, btnManageDs);
        hBox.setAlignment(Pos.BASELINE_CENTER); // 水平居中
        hBox.setSpacing(5);

        HBox.setHgrow(labelDs, Priority.ALWAYS);
        HBox.setHgrow(chobDs, Priority.ALWAYS);
        HBox.setHgrow(btnManageDs, Priority.ALWAYS);
        GridPane.setRowIndex(hBox, 0);
        GridPane.setColumnIndex(labelDs, 0);
        GridPane.setColumnIndex(chobDs, 1);
        GridPane.setColumnIndex(btnManageDs, 2);

        chobDs
                .prefWidthProperty()
                .bind(hBox.widthProperty().subtract(labelDs.getPrefWidth()).subtract(btnManageDs.getPrefWidth()));

        grpTop = new GridPane();
        grpTop.setVgap(5.0);
        grpTop.getChildren().add(hBox);

        grpTop.setPadding(new Insets(5, 5, 5, 5));

        // 宽高绑定
        hBox.prefWidthProperty().bind(grpTop.widthProperty());

        initSecondRowOfGridPane(grpTop);

        setTop(grpTop);
        initTableView(); // Center
    }

    public void addDbTableInfo(DBTableListModel model) {
        tblvDbTableList.getItems().add(model);
    }

    /**
     * 第2行：数据库选择
     * @param gridPane
     */
    private void initSecondRowOfGridPane(GridPane gridPane) {
        final Label labelDb = new Label("数据库");
        labelDb.setMinWidth(60);
        labelDb.setMaxWidth(60);
        labelDb.setAlignment(Pos.CENTER); // 垂直水平居中

        final ChoiceBox<DataSourceProperties> chobDb = new ChoiceBox<>();
        chobDb.setMinWidth(400.0);
        final Button btnRefreshDb = new Button("刷新");
        btnRefreshDb.setMinWidth(100);
        btnRefreshDb.setMaxWidth(100);

        final HBox hBox = new HBox(labelDb, chobDb, btnRefreshDb);
        hBox.setAlignment(Pos.BASELINE_CENTER); // 水平居中
        hBox.setSpacing(5);
        HBox.setHgrow(labelDb, Priority.ALWAYS);
        HBox.setHgrow(chobDb, Priority.ALWAYS);

        chobDb.prefWidthProperty()
              .bind(hBox.widthProperty().subtract(labelDb.getPrefWidth()).subtract(btnRefreshDb.getPrefWidth()));

        hBox.prefWidthProperty().bind(grpTop.widthProperty());

        gridPane.getChildren().add(hBox);
        GridPane.setRowIndex(hBox, 1); // 第2行
    }

    private void initTableView() {
        setCenter(tblvDbTableList = new TableView<>());
        tblvDbTableList.setEditable(true);
        tblvDbTableList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblcId = new TableColumn<>("序号");
        tblcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblcSelected = new TableColumn<>("是否选中");
        tblcSelected.setCellFactory(CheckBoxTableCell.forTableColumn(tblcSelected));
        tblcSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        tblcTableName = new TableColumn<>("表名称");
        tblcTableName.setCellValueFactory(param -> param.getValue().tableNameProperty());
        tblcTableComment = new TableColumn<>("描述信息");
        tblcTableComment.setCellValueFactory(param -> param.getValue().tableCommentProperty());
        tblcTableCreatedTime = new TableColumn<>("创建时间");
        tblcTableCreatedTime.setCellValueFactory(param -> param.getValue().createTimeProperty().asString());
        tblvDbTableList.getColumns().add(tblcId);
        tblvDbTableList.getColumns().add(tblcSelected);
        tblvDbTableList.getColumns().add(tblcTableName);
        tblvDbTableList.getColumns().add(tblcTableComment);
        tblvDbTableList.getColumns().add(tblcTableCreatedTime);
    }

    public void addRow(DBTableListModel model) {
        tblvDbTableList.getItems().add(model);
    }
}
