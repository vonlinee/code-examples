package io.devpl.sdk.beans;

import java.util.regex.Pattern;

/**
 * 标记接口
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
    Pattern FILED_NAME_PATTERN = Pattern.compile("[a-zA-z_][a-zA-z0-9_]*");

    /**
     * 校验Bean的字段是否符合要求
     *
     * @param filedName
     * @return
     */
    default boolean isKeyValid(String filedName) {
        return FILED_NAME_PATTERN.matcher(filedName).matches();
    }

    default String id() {
        return this.getClass().getName();
    }
}
