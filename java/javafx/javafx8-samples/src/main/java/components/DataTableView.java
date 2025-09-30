package components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据表格面板
 * @param <T>
 * @see Pagination
 */
public class DataTableView<T> extends BorderPane {

    private final IntegerProperty pageSize = new SimpleIntegerProperty(10);

    HBox operationPane;
    TableView<T> tableView;

    List<T> data;
    ObservableList<T> items;

    HBox paginationPane = new HBox();

    DataSupplier<T> dataSupplier;

    ToggleGroup group = new ToggleGroup();

    public DataTableView(Class<T> modelClass) {
        tableView = new TableView<>();
        operationPane = init();

        data = new ArrayList<>();
        items = FXCollections.observableArrayList(data);

        items.addListener(new ListChangeListener<T>() {
            @Override
            public void onChanged(Change<? extends T> c) {
                if (items.size() / getPageSize() > 0) {
                    ToggleButton btn = new ToggleButton();
                    btn.setText(String.valueOf(1));
                    paginationPane.getChildren().add(btn);
                    group.getToggles().add(btn);
                }
            }
        });

        tableView.setItems(items);

        setTop(operationPane);
        setCenter(tableView);

        paginationPane.setAlignment(Pos.CENTER);
        setBottom(paginationPane);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Field[] declaredFields = modelClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            TableViewColumn annotation = declaredField.getAnnotation(TableViewColumn.class);
            if (annotation == null) {
                continue;
            }
            TableColumn<T, Object> column = new TableColumn<>(annotation.name());

            final Class<?> type = declaredField.getType();
            if (Property.class.isAssignableFrom(type)) {
                column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<T, Object>, ObservableValue<Object>>() {
                    @Override
                    public ObservableValue<Object> call(TableColumn.CellDataFeatures<T, Object> param) {
                        final T rowItem = param.getValue();
                        ObservableValue<Object> result = null;
                        try {
                            declaredField.setAccessible(true);
                            result = (ObservableValue<Object>) declaredField.get(rowItem);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        return result;
                    }
                });
            }

            tableView.getColumns().add(column);
        }

        // 不采用操作列的形式，每行都有一个会比较耗费内存
        // 在顶部添加操作栏
//        TableColumn<T, Object> operationColumn = new TableColumn<>("操作");
//        operationColumn.setCellFactory(param -> {
//            TableCell<T, Object> cell = new TableCell<>();
//            cell.setEditable(true);
//            return cell;
//        });
//
//        operationColumn.setReorderable(false);
//        operationColumn.setSortable(false);
//        tableView.getColumns().add(operationColumn);
    }

    private HBox init() {
        HBox hBox = new HBox();
        Button btn1 = new Button("添加");

        btn1.setOnAction(event -> {
            T row = dataSupplier.getRow();
            items.add(row);
        });

        Button btn2 = new Button("删除");

        hBox.getChildren().addAll(btn1, btn2);
        return hBox;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }

    public void setDataSupplier(DataSupplier<T> dataSupplier) {
        this.dataSupplier = dataSupplier;
    }

    public int getPageSize() {
        return pageSize.get();
    }

    public IntegerProperty pageSizeProperty() {
        return pageSize;
    }
}
