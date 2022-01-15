package code.fxutils.core.extension;

import code.fxutils.core.db.Column;
import code.fxutils.core.db.Table;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.util.List;

public abstract class TablePane<T> extends BorderPane {

    private TableView<Table> tableView;

    public static void init(List<Column> columns) {

    }

    private TableColumn<String, Table> column1;

}
