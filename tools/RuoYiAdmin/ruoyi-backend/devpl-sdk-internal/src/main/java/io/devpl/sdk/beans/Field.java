package io.devpl.sdk.beans;

/**
 * A property that is linked to a specific bean.
 * 标记接口
 * <p>
 * For a JavaBean, this will ultimately wrap a get/set method pair.
 * Alternate implementations may perform any logic to obtain the value.
 *
 * @see java.lang.reflect.Field
 */
public interface Field<V> {

    default String id() {
        return String.valueOf(this.hashCode());
    }

    String getName();

    V getValue();

    /**
     * 设置该字段的值，同时返回旧值
     *
     * @param value
     * @return
     */
    V setValue(Object value);

    Class<V> getType();

    default String description() {
        return getName();
    }
}