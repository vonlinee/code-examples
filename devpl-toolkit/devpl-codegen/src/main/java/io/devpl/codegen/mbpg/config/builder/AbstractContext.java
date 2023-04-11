package io.devpl.codegen.mbpg.config.builder;

import io.devpl.codegen.mbpg.config.PropertyHolder;

public abstract class AbstractContext extends PropertyHolder {

    public abstract void prepare();

    public abstract void refresh();
}
