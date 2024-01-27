package library.jfoenix.controls;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class TestJFXTreeTableView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        JFXTreeTableView<RowData> treeTableView = new JFXTreeTableView<>();
        treeTableView.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);
        treeTableView.setEditable(true);

        // treeTableView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        // 第一列
        JFXTreeTableColumn<RowData, String> name = new JFXTreeTableColumn<>("name");
        treeTableView.getColumns().add(name);
        name.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        name.setCellValueFactory(param -> {
            String value = String.valueOf(param.getValue().getValue().getGroupedValue());
            return new SimpleStringProperty(value);
        });
        name.setEditable(true);

        // 第二列
        JFXTreeTableColumn<RowData, String> idColumn = new JFXTreeTableColumn<>("id");
        treeTableView.getColumns().add(idColumn);
        idColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        idColumn.setCellValueFactory(param -> {
            String value = String.valueOf(param.getValue().getValue().getId());
            return new SimpleStringProperty(value);
        });

        name.setCellFactory(param -> new JFXTextFieldCell<>());
        idColumn.setCellFactory(param -> new JFXTextFieldCell<>());

        final RowData rootRow = new RowData();
        rootRow.setName("张三");
        rootRow.setId(1);

        TreeItem<RowData> root = new TreeItem<>(rootRow);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(true);

        // 添加层级

        final RowData level1R1 = new RowData();
        level1R1.setName("level 1 row 1");
        level1R1.setId(3);
        TreeItem<RowData> level1TreeItem1 = new TreeItem<>(level1R1);
        root.getChildren().add(level1TreeItem1);

        final RowData level2R1 = new RowData();
        level2R1.setName("level 2 row 1");
        level2R1.setId(3);
        TreeItem<RowData> level2TreeItem1 = new TreeItem<>(level2R1);
        level1TreeItem1.getChildren().add(level2TreeItem1);

        Scene scene = new Scene(treeTableView, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

class RowData extends RecursiveTreeObject<RowData> {
    String name;
    Integer id;
    boolean gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}

class B extends RowData {

}

/**
 * @param <S>
 * @param <T>
 * @see TextFieldTreeTableCell
 */
class JFXTextFieldCell<S, T> extends TreeTableCell<S, T> {

    JFXTextField textField;

    // --- converter
    private final ObjectProperty<StringConverter<T>> converter =
            new SimpleObjectProperty<StringConverter<T>>(this, "converter");

    /**
     * The {@link StringConverter} property.
     */
    public final ObjectProperty<StringConverter<T>> converterProperty() {
        return converter;
    }

    /**
     * Sets the {@link StringConverter} to be used in this cell.
     */
    public final void setConverter(StringConverter<T> value) {
        converterProperty().set(value);
    }

    /**
     * Returns the {@link StringConverter} used in this cell.
     */
    public final StringConverter<T> getConverter() {
        return converterProperty().get();
    }


    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    /**
     * {@inheritDoc}
     */
    @Override
    public void startEdit() {
        if (!isEditable()
                || !getTreeTableView().isEditable()
                || !getTableColumn().isEditable()) {
            return;
        }
        super.startEdit();

        if (isEditing()) {
            if (textField == null) {
                textField = CellUtils.createJFXTextField(this, getConverter());
            }

            CellUtils.startEdit(this, getConverter(), null, null, textField);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        CellUtils.cancelEdit(this, getConverter(), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        CellUtils.updateItem(this, getConverter(), null, null, textField);
    }
}