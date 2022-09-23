package io.devpl.sdk.internal.beans;

/**
 * A dynamic bean that allows properties to be added and removed.
 * <p>
 * A JavaBean is defined at compile-time and cannot have additional properties added.
 * Instances of this interface allow additional properties to be added and removed
 * probably by wrapping a map
 */
public interface DynamicBean extends Bean {

    default String id() {
        return "";
    }

    boolean equals(Bean obj);

    int hashCode();
}
