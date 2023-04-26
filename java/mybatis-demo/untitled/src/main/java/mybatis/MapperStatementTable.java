package mybatis;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.Callback;
import ognl.Ognl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Mapper 变量表
 */
public class MapperStatementTable extends TreeTableView<NamedValue> {

    final TreeItem<NamedValue> root;

    public MapperStatementTable() {
        setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        setEditable(true);
        setShowRoot(false);
        TreeTableColumn<NamedValue, String> col_name = new TreeTableColumn<>("名称");
        col_name.setCellValueFactory(param -> {
            TreeItem<NamedValue> item = param.getValue();
            if (item == null) {
                return new SimpleStringProperty();
            }
            NamedValue value = item.getValue();
            if (value == null) {
                return new SimpleStringProperty();
            }
            return new SimpleStringProperty(value.getName());
        });
        TreeTableColumn<NamedValue, String> col_value = new TreeTableColumn<>("值");

        col_value.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NamedValue, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NamedValue, String> param) {
                TreeItem<NamedValue> item = param.getValue();
                if (item == null) {
                    return new SimpleStringProperty();
                }
                // 目录节点
                if (!item.getChildren().isEmpty()) {
                    return new SimpleStringProperty();
                }
                NamedValue value = item.getValue();
                if (value == null) {
                    return new SimpleStringProperty();
                }
                return new SimpleStringProperty(String.valueOf(value.getValue()));
            }
        });

        col_value.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        col_value.setEditable(true);

        setRoot(root = new TreeItem<>());

        TreeItem<NamedValue> name = new TreeItem<>(new NamedValue("name", 1));
        name.getChildren().add(new TreeItem<>(new NamedValue("1", 1)));
        name.getChildren().add(new TreeItem<>(new NamedValue("2", 1)));
        name.getChildren().add(new TreeItem<>(new NamedValue("3", 1)));
        name.getChildren().add(new TreeItem<>(new NamedValue("4", 1)));
        root.getChildren().add(name);
        root.getChildren().add(new TreeItem<>(new NamedValue("name", 1)));

        getColumns().add(col_name);
        getColumns().add(col_value);
    }

    public void addItems(Collection<String> collection) {
        Map<String, String> map = new HashMap<>();
        for (String value : collection) {
            String[] fragments = value.split("\\.");
            for (String fragment : fragments) {

            }
        }
    }
}
