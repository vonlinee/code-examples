package io.devpl.tookit.fxui.view;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableCell;

/**
 * 单元格包含一个ButtonBar
 *
 * @param <S>
 * @param <T>
 */
public class ButtonBarTableCell<S, T> extends TableCell<S, T> {

    private final ButtonBar buttonBar = new ButtonBar();

    public final ObservableList<Node> getButtons() {
        return buttonBar.getButtons();
    }

    public final void addButton(Button button) {
        buttonBar.getButtons().add(button);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(buttonBar);
        }
    }
}
