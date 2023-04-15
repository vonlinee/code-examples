package io.devpl.codegen.jdbc.meta;

import lombok.Data;

@Data
public class Table {

    private String name;

    private String remarks;

    private String tableType;

    public String getRemarks() {
        return remarks;
    }

    public String getTableType() {
        return tableType;
    }

    public String getName() {
        return name;
    }

    public boolean isView() {
        return "VIEW".equals(tableType);
    }
}