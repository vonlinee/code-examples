package io.devpl.log;

/**
 * Defines the interface for creating Log implementations.
 */
public interface AbstractLogFactory {
    Log getLog(Class<?> targetClass);
}
