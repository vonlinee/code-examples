package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TextInputConstraint;
import com.panemu.tiwulfx.utils.SceneGraph;
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

/**
 * @see javafx.scene.control.cell.TextFieldTableCell
 * @param <R>
 */
public class TextTableCell<R> extends CustomTableCell<R, String> {

    private TextField textField;
    private TextColumn<R> column;

    public TextTableCell(TextColumn<R> column) {
        super(column);
        this.column = column;
    }

    @Override
    protected void updateCellValue(String value) {
        textField.setText(value);
    }

    @Override
    protected String getEditedValue() {
        if (textField.getText() == null || (column.isEmptyStringAsNull() && textField.getText().trim().isEmpty())) {
            return null;
        }
        return textField.getText();
    }

    @Override
    protected @NotNull Control getEditView() {
        if (textField == null) {
            textField = new TextField();
            // textField.setBackground(SceneGraph.background(Color.TRANSPARENT, 0, 0));
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            if (column.isCapitalize() || column.getMaxLength() > 0) {
                textField.textProperty()
                        .addListener(new TextInputConstraint(textField, column.getMaxLength(), column.isCapitalize()));
            }
            textField.textProperty().addListener((ov, t, newValue) -> {
                for (CellEditorListener<R, String> svl : column.getCellEditorListeners()) {
                    svl.valueChanged(getTableRow().getIndex(), column.getPropertyName(), (R) getTableRow().getItem(), newValue);
                }
            });
        }
        return textField;
    }
}
