package io.maker.codegen.core;

import io.maker.base.lang.MapBean;

/**
 * 代码生成器配置项
 */
public class CodeGenConfiguration {

    private MapBean configItems;

    public CodeGenConfiguration() {
        this.configItems = new MapBean();
    }

    public final void refresh() {

    }
}
