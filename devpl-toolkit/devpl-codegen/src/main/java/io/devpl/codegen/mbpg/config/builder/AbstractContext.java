package io.devpl.codegen.mbpg.config.builder;

import io.devpl.codegen.mbpg.config.PropertyHolder;

/**
 * 抽象上下文
 */
public abstract class AbstractContext extends PropertyHolder {

    public abstract void prepare();

    public abstract void refresh();
}
