package io.devpl.eventbus.util;

public interface HasExecutionScope {
    Object getExecutionScope();

    void setExecutionScope(Object executionScope);

}
