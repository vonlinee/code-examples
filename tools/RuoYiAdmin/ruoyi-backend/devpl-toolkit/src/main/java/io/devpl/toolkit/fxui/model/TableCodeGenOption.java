package io.devpl.toolkit.fxui.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * 针对单个表的代码生成配置项
 */
public class TableCodeGenOption {

    private final BooleanProperty useExample = new SimpleBooleanProperty(false);
}
