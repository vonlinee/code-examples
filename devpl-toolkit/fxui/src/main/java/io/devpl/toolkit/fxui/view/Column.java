package io.devpl.toolkit.fxui.view;

import lombok.Data;

@Data
public class Column implements NavigationItem {

    private String columnName;

    @Override
    public String getDispalyValue() {
        return null;
    }
}
