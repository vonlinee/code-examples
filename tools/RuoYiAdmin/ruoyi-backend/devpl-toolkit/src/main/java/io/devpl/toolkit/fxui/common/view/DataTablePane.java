package io.devpl.toolkit.fxui.common.view;

import io.devpl.toolkit.fxui.common.utils.DataObject;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

/**
 * 数据表格布局类
 */
public class DataTablePane extends BorderPane {

    private final TableView<DataObject> dataTable;

    public DataTablePane() {
        this.dataTable = new TableView<>();
    }

    public void addColumn() {

    }

    @Override
    public ObservableList<Node> getChildren() {
        return super.getChildren();
    }
}
