package io.devpl.sdk.beans;

import java.util.regex.Pattern;

/**
 * A dynamic bean that allows properties to be added and removed.
 * <p>
 * A JavaBean is defined at compile-time and cannot have additional properties added.
 * Instances of this interface allow additional properties to be added and removed
 * probably by wrapping a map
 */
public interface DynamicBean extends Bean {

    /**
     * Valid regex for keys.
     */
    Pattern KEY_PATTERN = Pattern.compile("[a-zA-z_][a-zA-z0-9_]*");

    default String id() {
        return this.getClass().getName();
    }
}
