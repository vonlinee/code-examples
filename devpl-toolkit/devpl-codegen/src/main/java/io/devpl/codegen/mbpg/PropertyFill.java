package io.devpl.codegen.mbpg;

import io.devpl.codegen.mbpg.fill.FieldFill;

public class PropertyFill implements IFill {

    private final String propertyName;

    private final FieldFill fieldFill;

    public PropertyFill(String propertyName, FieldFill fieldFill) {
        this.propertyName = propertyName;
        this.fieldFill = fieldFill;
    }

    public PropertyFill(String propertyName) {
        this.propertyName = propertyName;
        this.fieldFill = FieldFill.DEFAULT;
    }

    @Override
    public String getName() {
        return this.propertyName;
    }

    @Override
    public FieldFill getFieldFill() {
        return this.fieldFill;
    }
}
