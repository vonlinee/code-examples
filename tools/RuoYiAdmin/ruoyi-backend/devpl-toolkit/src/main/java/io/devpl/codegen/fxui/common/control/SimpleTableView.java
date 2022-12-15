package io.devpl.codegen.fxui.common.control;

import io.devpl.codegen.fxui.common.utils.DataObject;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * 数据展示表格，所有展示的数据都是字符串
 * @see TableView
 */
public class SimpleTableView extends TableView<DataObject> {

    public <V> void addColumn(TableColumn<DataObject, V> column) {
        getColumns().add(column);
    }
}
