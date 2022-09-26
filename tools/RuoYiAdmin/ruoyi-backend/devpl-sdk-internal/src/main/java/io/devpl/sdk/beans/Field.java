package io.devpl.sdk.beans;

/**
 * A property that is linked to a specific bean.
 * 标记接口
 * <p>
 * For a JavaBean, this will ultimately wrap a get/set method pair.
 * Alternate implementations may perform any logic to obtain the value.
 *
 * @see org.joda.beans.Property
 * @see java.lang.reflect.Field
 */
public interface Field<V> {

    default String id() {
        return this.getClass().getName();
    }

    String getName();

    V getValue();

    V setValue(Object value);

    Class<V> getType();

    default String description() {
        return "";
    }
}