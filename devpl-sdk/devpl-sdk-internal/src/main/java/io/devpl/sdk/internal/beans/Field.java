package io.devpl.sdk.internal.beans;

/**
 * A property that is linked to a specific bean.
 * <p>
 * For a JavaBean, this will ultimately wrap a get/set method pair.
 * Alternate implementations may perform any logic to obtain the value.
 * @param <V> the type of the property content
 * @see org.joda.beans.Property
 */
public interface Field<V> {

    String name();

    boolean equals(Object obj);

    int hashCode();
}