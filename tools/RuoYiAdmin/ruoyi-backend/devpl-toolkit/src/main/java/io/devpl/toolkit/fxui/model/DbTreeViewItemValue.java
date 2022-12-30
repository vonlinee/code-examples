package io.devpl.toolkit.fxui.model;

import lombok.Data;

@Data
public class DbTreeViewItemValue {

    private boolean isSelected;
    private String tableName;
    private String tableComment;
}
