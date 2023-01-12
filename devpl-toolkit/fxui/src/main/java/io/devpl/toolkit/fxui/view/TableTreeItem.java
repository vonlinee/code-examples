package io.devpl.toolkit.fxui.view;

import lombok.Data;

@Data
public class TableTreeItem implements NavigationItem {

    private String tableName;

    @Override
    public String getDispalyValue() {
        return null;
    }
}
