package io.devpl.sdk.beans.impl.map;

import io.devpl.sdk.beans.DynamicMetaBean;
import io.devpl.sdk.beans.Property;
import io.devpl.sdk.beans.impl.MapBean;
import io.devpl.sdk.beans.impl.StandardProperty;
import io.devpl.sdk.beans.impl.flexi.FlexiBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of a fully dynamic {@code Bean} based on an exposed {@code Map}.
 * <p>
 * Properties are dynamic, and can be added and removed at will from the map.
 * <p>
 * This class extends {@link HashMap}, allowing it to be used wherever a map is.
 * See {@link FlexiBean} for a map-like bean implementation that is more controlled.
 */
public class BeanMap extends HashMap<String, Object> implements MapBean {

    /**
     * Serialization version.
     */
    private static final long serialVersionUID = 1L;

    //-----------------------------------------------------------------------

    /**
     * Creates a standalone meta-bean.
     * <p>
     * This creates a new instance each time in line with dynamic bean principles.
     * @return the meta-bean, not null
     */
    public static DynamicMetaBean meta() {
        return new BeanMap().metaBean();
    }

    //-----------------------------------------------------------------------

    /**
     * Creates an instance.
     */
    public BeanMap() {
    }

    /**
     * Creates an instance.
     * @param map the map to copy, not null
     */
    public BeanMap(Map<String, Object> map) {
        super(map);
    }

    //-----------------------------------------------------------------------
    @Override
    public DynamicMetaBean metaBean() {
        return new MapMetaBean(this);
    }

    @Override
    public boolean existsProperty(String name) {
        return containsKey(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Property<Object> getProperty(String name) {
        return StandardProperty.of(this, MapBeanMetaProperty.of(metaBean(), name));
    }

    @Override
    public Set<String> propertyNames() {
        return keySet();
    }

    @Override
    public void defineProperty(String propertyName, Class<?> propertyType) {
        if (!containsKey(propertyName)) {
            put(propertyName, null);
        }
    }

    @Override
    public void removeProperty(String propertyName) {
        remove(propertyName);
    }

    @Override
    public BeanMap clone() {
        return (BeanMap) super.clone();
    }

    //-----------------------------------------------------------------------

    /**
     * Returns a string that summarises the bean.
     * <p>
     * The string contains the class name and properties.
     * @return a summary string, not null
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + super.toString();
    }
}
