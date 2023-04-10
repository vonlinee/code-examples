package io.devpl.tookit.fxui.view.components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * 选项表
 */
public class OptionTableView extends TableView<Option> {

    public OptionTableView() {
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Option, String> nameColumn = new TableColumn<>("名称");
        TableColumn<Option, Object> valueColumn = new TableColumn<>("值");
        getColumns().add(nameColumn);
        getColumns().add(valueColumn);
    }
}
