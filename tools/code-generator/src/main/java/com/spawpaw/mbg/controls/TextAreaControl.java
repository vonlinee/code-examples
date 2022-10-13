package com.spawpaw.mbg.controls;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class TextAreaControl extends AbstractControl<String> {
    protected TextArea textArea;
    protected Label label;

    @Override
    protected void initView() {

        label = new Label();
        textArea = new TextArea();
        layout.getChildren().addAll(label, textArea);
        label.setTooltip(tooltip);
        textArea.setTooltip(tooltip);
    }

    @Override
    protected void bindProperties() {
        label.setMinWidth(MIN_WIDTH_LEFT);
        textArea.setMinWidth(MIN_WIDTH_RIGHT);
        label.textProperty().bindBidirectional(this.labelTextProperty);
        textArea.textProperty().bindBidirectional(this.value);
    }
}
