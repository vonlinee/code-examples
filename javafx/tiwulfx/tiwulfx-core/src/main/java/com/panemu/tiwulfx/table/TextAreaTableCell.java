package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TextInputConstraint;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import org.jetbrains.annotations.NotNull;

public class TextAreaTableCell<R> extends CustomTableCell<R, String> {

    private TextArea textArea;
    private TextAreaColumn<R> column;

    public TextAreaTableCell(TextAreaColumn<R> column) {
        super(column);
        this.column = column;
    }

    @Override
    protected void updateCellValue(String value) {
        textArea.setText(value);
    }

    @Override
    protected String getEditedValue() {
        if (textArea.getText() == null
                || (column.isEmptyStringAsNull() && textArea.getText().trim().isEmpty())) {
            return null;
        }
        return textArea.getText();
    }

    @Override
    protected @NotNull Control getEditView() {
        if (textArea == null) {
            textArea = new TextArea();

            textArea.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textArea.setPrefRowCount(column.getRowCount());
            textArea.setWrapText(true);
            if (column.isCapitalize()) {
                textArea.textProperty().addListener(new TextInputConstraint(textArea, 0, column.isCapitalize()));
            }

            textArea.textProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String newValue) {
                    for (CellEditorListener<R, String> svl : column.getCellEditorListeners()) {
                        svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), (R) getTableRow().getItem(), newValue);
                    }
                }
            });

            textArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<>() {
				private final KeyCombination SHIFT_ENTER = new KeyCodeCombination(KeyCode.ENTER, KeyCodeCombination.SHIFT_ANY);

				/**
				 * Set Shift+Enter as shortcut for new line
				 * @param event KeyEvent
				 */
				@Override
				public void handle(KeyEvent event) {
					if (SHIFT_ENTER.match(event)) {
						textArea.deleteText(textArea.getSelection());
						textArea.insertText(textArea.getCaretPosition(), "\n");
					}
				}
			});
        }
        return textArea;
    }
}