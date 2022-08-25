package io.devpl.sdk.internal.beans;

/**
 * A property that is linked to a specific bean.
 * 标记接口
 * <p>
 * For a JavaBean, this will ultimately wrap a get/set method pair.
 * Alternate implementations may perform any logic to obtain the value.
 * @see org.joda.beans.Property
 */
public interface Field {

    String id();

    boolean equals(Object obj);

    int hashCode();
}