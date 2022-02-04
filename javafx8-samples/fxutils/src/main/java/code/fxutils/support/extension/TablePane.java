package code.fxutils.support.extension;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.util.List;

import code.fxutils.support.db.Column;
import code.fxutils.support.db.Table;

public abstract class TablePane<T> extends BorderPane {

    private TableView<Table> tableView;

    public static void init(List<Column> columns) {

    }

    private TableColumn<String, Table> column1;

}
