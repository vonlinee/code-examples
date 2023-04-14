package io.devpl.codegen.mbpg;

import io.devpl.codegen.mbpg.fill.FieldFill;

public class Property implements IFill {

    private final String propertyName;

    private final FieldFill fieldFill;

    public Property(String propertyName, FieldFill fieldFill) {
        this.propertyName = propertyName;
        this.fieldFill = fieldFill;
    }

    public Property(String propertyName) {
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
