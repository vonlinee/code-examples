package io.devpl.sdk.beans;

/**
 * A property that is linked to a specific bean.
 * 标记接口
 * <p>
 * For a JavaBean, this will ultimately wrap a get/set method pair.
 * Alternate implementations may perform any logic to obtain the value.
 * @see org.joda.beans.Property
 */
public interface Field<V> {

    String id();

    String name();

    String description();

    boolean equals(Object obj);

    int hashCode();
}