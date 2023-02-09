package io.devpl.toolkit.fxui.view.filestructure;

import javafx.scene.control.cell.TextFieldTreeCell;

public class JavaElementTreeCell extends TextFieldTreeCell<String> {

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
        } else {
            setText(item);
        }
        System.out.println("update");
    }
}
