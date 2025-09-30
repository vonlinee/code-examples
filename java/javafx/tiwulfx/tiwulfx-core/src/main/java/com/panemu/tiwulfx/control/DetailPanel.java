package com.panemu.tiwulfx.control;

import com.panemu.tiwulfx.common.ObjectExposer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panemu.tiwulfx.utils.ClassUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.commons.beanutils.PropertyUtils;

public class DetailPanel extends VBox implements ObjectExposer {

    private List<String> propertyNames = new ArrayList<>();
    private Object objectToDisplay;
    private final Map<String, Label> componentMap = new HashMap<>();

    public DetailPanel() {
        setPadding(new Insets(10));
        setSpacing(8);
        getStylesheets().add("tiwulfx.css");
    }

    public List<String> getPropertyNames() {
        return propertyNames;
    }

    public void setPropertyNames(List<String> propertyNames) {
        this.propertyNames = propertyNames;
        generateUI();
    }

    @Override
    public void setObjectToDisplay(Object objectToDisplay) {
        this.objectToDisplay = objectToDisplay;
        displayValues();
    }

    private void generateUI() {
        this.getChildren().removeAll(this.getChildren());
        for (String prop : propertyNames) {
            LabelSeparator labelSeparator = new LabelSeparator("");
            labelSeparator.setText(prop);
            VBox.setMargin(labelSeparator, new Insets(10, 0, 0, 0));
            this.getChildren().add(labelSeparator);
            Label lblValue = new Label();
            lblValue.getStyleClass().add("valueLabel");
            lblValue.setWrapText(true);
            this.getChildren().add(lblValue);
            
            componentMap.put(prop, lblValue);
        }
    }

    private void displayValues() {
        for (String prop : propertyNames) {
            Label lblValue = componentMap.get(prop);
            Object obj = ClassUtils.getSimpleProperty(objectToDisplay, prop);
            if (obj != null) {
                /**
                 * There is a bug in Label control. When the text's length
                 * is 1 and the wrapText property is true, the label display
                 * nothing. Workaround, don't wrap is the text's length is 1
                 */
                if (obj.toString().length() == 1) {
                    lblValue.setWrapText(false);
                }

                lblValue.setText(obj.toString());
            } else {
                lblValue.setText("-- undefined --");
            }
        }
    }
}
