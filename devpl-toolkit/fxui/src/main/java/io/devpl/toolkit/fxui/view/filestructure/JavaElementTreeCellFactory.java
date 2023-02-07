package io.devpl.toolkit.fxui.view.filestructure;

import javafx.geometry.Pos;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class JavaElementTreeCellFactory implements Callback<TreeView<String>, TreeCell<String>> {

    @Override
    public TreeCell<String> call(TreeView<String> param) {
        TextFieldTreeCell<String> cell = new TextFieldTreeCell<>(new StringConverter<>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        });
        cell.setAlignment(Pos.CENTER_LEFT);
        cell.setOnMouseClicked(event -> {

        });
        return cell;
    }
}
