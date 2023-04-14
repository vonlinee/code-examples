package io.devpl.codegen.mbpg;

import io.devpl.codegen.mbpg.fill.FieldFill;

public class Column implements IFill {

    private final String columnName;

    private final FieldFill fieldFill;
    public Column(String columnName, FieldFill fieldFill) {
        this.columnName = columnName;
        this.fieldFill = fieldFill;
    }

    public Column(String columnName) {
        this.columnName = columnName;
        this.fieldFill = FieldFill.DEFAULT;
    }

    @Override
    public String getName() {
        return this.columnName;
    }

    @Override
    public FieldFill getFieldFill() {
        return this.fieldFill;
    }
}
