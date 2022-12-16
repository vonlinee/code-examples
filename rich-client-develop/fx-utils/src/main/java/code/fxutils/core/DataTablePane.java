package code.fxutils.core;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Map;

/**
 * 数据表格布局类
 */
public class DataTablePane extends BorderPane {

    private TableView<Map> dataTable = new TableView<>();
    private HBox operationBox = new HBox();

    private Map<String, String> columnNameMap;

    Stage insertPopup;

    Scene scene;

    public DataTablePane(Map<String, String> columnNameMap) {
        this.columnNameMap = columnNameMap;
        Button btnAddRow = new Button("新增");
        Button btnDeleteRow = new Button("删除");
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(btnAddRow, btnDeleteRow);
        operationBox.getChildren().addAll(buttonBar);
        operationBox.setAlignment(Pos.BASELINE_RIGHT);
        topProperty().set(operationBox);
        setMargin(operationBox, new Insets(5, 2, 5, 2));
        // 设置每列自动均分整个表格的宽度
        dataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        for (Map.Entry<String, String> columnInfo : columnNameMap.entrySet()) {
            TableColumn<Map, Object> column = new TableColumn<>(columnInfo.getValue());
            // Key
            column.setCellValueFactory(new MapValueFactory<>(columnInfo.getKey()));
            this.dataTable.getColumns().add(column);
        }
        setCenter(this.dataTable);

        btnAddRow.setOnMouseClicked(event -> {
            if (insertPopup == null) {
                insertPopup = new Stage();
                insertPopup.initModality(Modality.APPLICATION_MODAL);
            }
            if (scene == null) {
                scene = new Scene(createInsertDataPane(), 250, 100);
            }
            if (insertPopup.getScene() == null) {
                insertPopup.setScene(scene);
            }
            insertPopup.show();
        });
    }

    public final void addRow(Map<String, Object> rowData) {
        dataTable.getItems().add(rowData);
    }

    private GridPane createInsertDataPane() {
        GridPane insertForm = new GridPane();
        insertForm.setAlignment(Pos.CENTER);
        insertForm.setHgap(20);
        insertForm.setVgap(10);
        insertForm.setPadding(new Insets(5, 5, 5, 5));
        int rowNum = 0;
        for (Map.Entry<String, String> entry : columnNameMap.entrySet()) {
            Label label = new Label(entry.getValue());
            TextField textField = new TextField();
            textField.setMinWidth(entry.getValue().length());
            insertForm.add(label, 0, rowNum);
            insertForm.add(textField, 1, rowNum);
            rowNum++;
        }
        Button btn = new Button("确定");
        HBox hBox = new HBox(btn);
        insertForm.add(hBox, 1, rowNum);
        return insertForm;
    }

    @Override
    public ObservableList<Node> getChildren() {
        // 不可修改
        return super.getChildren();
    }
}
