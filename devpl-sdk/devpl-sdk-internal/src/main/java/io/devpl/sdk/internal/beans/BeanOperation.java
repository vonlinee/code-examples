package io.devpl.sdk.internal.beans;

public interface BeanOperation {

    /**
     * @param fieldName
     * @param <V>
     * @return
     */
    <V> V get(String fieldName);

    <V> void set(String fieldName, V newValue);


}
