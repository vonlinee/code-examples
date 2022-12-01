package io.devpl.sdk.beans.impl.flexi;

import io.devpl.sdk.beans.DynamicMetaBean;
import io.devpl.sdk.beans.Property;
import io.devpl.sdk.beans.impl.MapBean;
import io.devpl.sdk.beans.impl.StandardProperty;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Implementation of a fully dynamic {@code Bean}.
 * <p>
 * 属性动态：可以添加删除
 * Properties are dynamic, and can be added and removed at will from the map.
 * The internal storage is created lazily to allow a flexi-bean to be used as
 * a lightweight extension to another bean.
 * <p>
 * Each flexi-bean has a different set of properties.
 * As such, there is one instance of meta-bean for each flexi-bean.
 * <p>
 * The keys of a flexi-bean must be simple identifiers as per '[a-zA-z_][a-zA-z0-9_]*'.
 * Alternate way to implement this would be to create a list/map of real property
 * objects which could then be properly typed
 * 实现这一点的另一种方法是创建属性对象的列表/映射，然后可以使用正确的类型
 * <p>
 * MapBean和FlexiBean的区别
 */
public final class FlexiBean implements MapBean, Serializable {

    /**
     * Serialization version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Valid regex for keys.
     */
    private static final Pattern VALID_KEY_PATTERN = Pattern.compile("[a-zA-z_][a-zA-z0-9_]*");

    /**
     * 确保有效的key
     * @param key key
     */
    private void ensureValidKey(String key) {
        if (!VALID_KEY_PATTERN.matcher(key).matches()) {
            throw new IllegalArgumentException("Invalid key for FlexiBean: " + key);
        }
    }

    /**
     * The meta-bean.
     */
    private final transient FlexiMetaBean metaBean = new FlexiMetaBean(this);

    /**
     * The underlying data.
     */
    volatile Map<String, Object> data = Collections.emptyMap();

    /**
     * Creates a standalone meta-bean.
     * <p>
     * This creates a new instance each time in line with dynamic bean principles.
     * @return the meta-bean, not null
     */
    public static DynamicMetaBean meta() {
        return new FlexiBean().metaBean();
    }

    /**
     * Constructor.
     */
    public FlexiBean() {
    }

    /**
     * Constructor that copies all the data entries from the specified bean.
     * @param copyFrom the bean to copy from, not null
     */
    public FlexiBean(FlexiBean copyFrom) {
        putAll(copyFrom.data);
    }

    // resolve to setup transient field
    private Object readResolve() throws ObjectStreamException {
        return new FlexiBean(this);
    }

    /**
     * Gets the internal data map.
     * @return the data, not null
     */
    private Map<String, Object> dataWritable() {
        if (data == Collections.EMPTY_MAP) {
            data = new LinkedHashMap<>();
        }
        return data;
    }

    //-----------------------------------------------------------------------

    /**
     * Gets the number of properties.
     * @return the number of properties
     */
    public int size() {
        return data.size();
    }

    /**
     * Checks if the bean contains a specific property.
     * @param propertyName the property name, null returns false
     * @return true if the bean contains the property
     */
    public boolean contains(String propertyName) {
        return propertyExists(propertyName);
    }

    /**
     * Gets the value of the property.
     * <p>
     * This returns null if the property does not exist.
     * @param propertyName the property name, not empty
     * @return the value of the property, may be null
     */
    public Object get(String propertyName) {
        return data.get(propertyName);
    }

    /**
     * Gets the value of the property cast to a specific type.
     * <p>
     * This returns null if the property does not exist.
     * @param <T>          the value type
     * @param propertyName the property name, not empty
     * @param type         the type to cast to, not null
     * @return the value of the property, may be null
     * @throws ClassCastException if the type is incorrect
     */
    public <T> T get(String propertyName, Class<T> type) {
        // 如果方法被 @HotSpotIntrinsicCandidate 标注，表示该方法在HotSpot中有一套高效的实现，
        // 运行时可能会替换JDK的源码实现。
        // https://blog.csdn.net/wrz_1028/article/details/107433181
        return type.cast(get(propertyName));
    }

    @Override
    public Object getObject(String key) {
        return data.get(key);
    }

    /**
     * Gets the value of the property as a {@code String}.
     * This will use {@link Object#toString()}.
     * <p>
     * This returns null if the property does not exist.
     * @param propertyName the property name, not empty
     * @return the value of the property, may be null
     */
    public String getString(String propertyName) {
        Object obj = get(propertyName);
        return obj != null ? obj.toString() : null;
    }

    //-----------------------------------------------------------------------

    /**
     * Sets a property in this bean to the specified value.
     * <p>
     * This creates a property if one does not exist.
     * @param propertyName the property name, not empty
     * @param newValue     the new value, may be null
     * @return {@code this} for chaining, not null
     */
    public FlexiBean append(String propertyName, Object newValue) {
        put(propertyName, newValue);
        return this;
    }

    /**
     * Sets a property in this bean to the specified value.
     * <p>
     * This creates a property if one does not exist.
     * @param propertyName the property name, not empty
     * @param newValue     the new value, may be null
     */
    public void set(String propertyName, Object newValue) {
        put(propertyName, newValue);
    }

    /**
     * Sets a property in this bean to the specified value.
     * <p>
     * This creates a property if one does not exist.
     * @param propertyName the property name, not empty
     * @param newValue     the new value, may be null
     * @return the old value of the property, may be null
     */
    public Object put(String propertyName, Object newValue) {
        ensureValidKey(propertyName);
        return dataWritable().put(propertyName, newValue);
    }

    /**
     * Puts the properties in the specified map into this bean.
     * <p>
     * This creates properties if they do not exist.
     * @param map the map of properties to add, not null
     */
    public void putAll(Map<String, ?> map) {
        if (map.size() > 0) {
            for (String key : map.keySet()) {
                ensureValidKey(key);
            }
            if (data == Collections.EMPTY_MAP) {
                data = new LinkedHashMap<>(map);
            } else {
                data.putAll(map);
            }
        }
    }

    /**
     * Puts the properties in the specified bean into this bean.
     * <p>
     * This creates properties if they do not exist.
     * @param other the map of properties to add, not null
     */
    public void putAll(FlexiBean other) {
        if (other.size() > 0) {
            if (data == Collections.EMPTY_MAP) {
                data = new LinkedHashMap<>(other.data);
            } else {
                data.putAll(other.data);
            }
        }
    }

    /**
     * Removes a property.
     * <p>
     * No error occurs if the property does not exist.
     * @param propertyName the property name, not empty
     */
    public void remove(String propertyName) {
        removeProperty(propertyName);
    }

    /**
     * Removes all properties.
     */
    public void clear() {
        if (data != Collections.EMPTY_MAP) {
            data.clear();
        }
    }

    /**
     * Checks if the property exists.
     * @param propertyName the property name, not empty
     * @return true if the property exists
     */
    public boolean propertyExists(String propertyName) {
        return data.containsKey(propertyName);
    }

    /**
     * Gets the value of the property.
     * @param propertyName the property name, not empty
     * @return the value of the property, may be null
     */
    public Object propertyGet(String propertyName) {
        if (!propertyExists(propertyName)) {
            throw new NoSuchElementException("Unknown property: " + propertyName);
        }
        return data.get(propertyName);
    }

    /**
     * Sets the value of the property.
     * @param propertyName the property name, not empty
     * @param newValue     the new value of the property, may be null
     */
    public void propertySet(String propertyName, Object newValue) {
        put(propertyName, newValue);
    }

    //-----------------------------------------------------------------------
    @Override
    public DynamicMetaBean metaBean() {
        return metaBean;
    }

    @Override
    public boolean existsProperty(String name) {
        return data.containsKey(name);
    }

    @Override
    public Property<Object> getProperty(String name) {
        return StandardProperty.of(this, FlexiBeanMetaProperty.of(metaBean, name));
    }

    @Override
    public Set<String> propertyNames() {
        return data.keySet();
    }

    @Override
    public void defineProperty(String propertyName, Class<?> propertyType) {
        if (!propertyExists(propertyName)) {
            put(propertyName, null);
        }
    }

    @Override
    public void removeProperty(String propertyName) {
        if (data != Collections.EMPTY_MAP) {
            data.remove(propertyName);
        }
    }

    /**
     * Returns a map representing the contents of the bean.
     * @return a map representing the contents of the bean, not null
     */
    public Map<String, Object> toMap() {
        if (size() == 0) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(new LinkedHashMap<>(data));
    }

    /**
     * Clones this bean, returning an independent copy.
     * @return the clone, not null
     */
    @Override
    public FlexiBean clone() {
        FlexiBean clone;
        try {
            clone = (FlexiBean) super.clone();
        } catch (CloneNotSupportedException e) {
            clone = this;
        }
        return new FlexiBean(clone);
    }

    /**
     * Compares this bean to another based on the property names and content.
     * @param obj the object to compare to, null returns false
     * @return true if equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof FlexiBean) {
            FlexiBean other = (FlexiBean) obj;
            return this.data.equals(other.data);
        }
        return super.equals(obj);
    }

    /**
     * Returns a suitable hash code.
     * @return a hash code
     */
    @Override
    public int hashCode() {
        return data.hashCode();
    }

    /**
     * Returns a string that summarises the bean.
     * <p>
     * The string contains the class name and properties.
     * @return a summary string, not null
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + data.toString();
    }
}
