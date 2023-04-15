package io.devpl.codegen.mbpg.config;

import io.devpl.codegen.api.ProgressCallback;
import io.devpl.codegen.mbpg.config.PropertyHolder;

import java.util.List;
import java.util.Set;

/**
 * 抽象上下文
 */
public abstract class AbstractContext extends PropertyHolder {

    public abstract void introspectTables(ProgressCallback callback, List<String> warnings, Set<String> fullyQualifiedTableNames);

    public abstract void refresh();
}
