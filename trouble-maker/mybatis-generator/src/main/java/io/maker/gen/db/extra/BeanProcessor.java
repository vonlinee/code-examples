package io.maker.gen.db.extra;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * <p>
 * <code>BeanProcessor</code> matches column names to bean property names and
 * converts <code>ResultSet</code> columns into objects for those bean
 * properties. Subclasses should override the methods in the processing chain to
 * customize behavior.
 * </p>
 *
 * <p>
 * This class is thread-safe.
 * </p>
 * @see BasicRowProcessor
 * @since DbUtils 1.1
 */
public class BeanProcessor {

    /**
     * Special array value used by <code>mapColumnsToProperties</code> that
     * indicates there is no bean property that matches a column from a
     * <code>ResultSet</code>.
     */
    protected static final int PROPERTY_NOT_FOUND = -1;

    /**
     * Set a bean's primitive properties to these defaults when SQL NULL is
     * returned. These are the same as the defaults that ResultSet get* methods
     * return in the event of a NULL column.
     */
    private static final Map<Class<?>, Object> primitiveDefaults = new HashMap<Class<?>, Object>();

    /**
     * ServiceLoader to find <code>ColumnHandler</code> implementations on the
     * classpath. The iterator for this is lazy and each time
     * <code>iterator()</code> is called.
     */
    // FIXME: I think this instantiates new handlers on each iterator() call. This
    // might be worth caching upfront.
    private static final ServiceLoader<ColumnHandler> columnHandlers = ServiceLoader.load(ColumnHandler.class);

    /**
     * ServiceLoader to find <code>PropertyHandler</code> implementations on the
     * classpath. The iterator for this is lazy and each time
     * <code>iterator()</code> is called.
     */
    // FIXME: I think this instantiates new handlers on each iterator() call. This
    // might be worth caching upfront.
    private static final ServiceLoader<PropertyHandler> propertyHandlers = ServiceLoader.load(PropertyHandler.class);

    /**
     * ResultSet column to bean property name overrides.
     */
    private final Map<String, String> columnToPropertyOverrides;

    static {
        primitiveDefaults.put(Integer.TYPE, Integer.valueOf(0));
        primitiveDefaults.put(Short.TYPE, Short.valueOf((short) 0));
        primitiveDefaults.put(Byte.TYPE, Byte.valueOf((byte) 0));
        primitiveDefaults.put(Float.TYPE, Float.valueOf(0f));
        primitiveDefaults.put(Double.TYPE, Double.valueOf(0d));
        primitiveDefaults.put(Long.TYPE, Long.valueOf(0L));
        primitiveDefaults.put(Boolean.TYPE, Boolean.FALSE);
        primitiveDefaults.put(Character.TYPE, Character.valueOf((char) 0));
    }

    /**
     * Constructor for BeanProcessor.
     */
    public BeanProcessor() {
        this(new HashMap<String, String>());
    }

    /**
     * Constructor for BeanProcessor configured with column to property name
     * overrides.
     * @param columnToPropertyOverrides ResultSet column to bean property name
     *                                  overrides
     * @since 1.5
     */
    public BeanProcessor(Map<String, String> columnToPropertyOverrides) {
        super();
        if (columnToPropertyOverrides == null) {
            throw new IllegalArgumentException("columnToPropertyOverrides map cannot be null");
        }
        this.columnToPropertyOverrides = columnToPropertyOverrides;
    }

    /**
     * Convert a <code>ResultSet</code> row into a JavaBean. This implementation
     * uses reflection and <code>BeanInfo</code> classes to match column names to
     * bean property names. Properties are matched to columns based on several
     * factors: &lt;br/&gt; &lt;ol&gt; &lt;li&gt; The class has a writable property
     * with the same name as a column. The name comparison is case insensitive.
     * &lt;/li&gt;
     * <p>
     * &lt;li&gt; The column type can be converted to the property's set method
     * parameter type with a ResultSet.get* method. If the conversion fails (ie. the
     * property was an int and the column was a Timestamp) an SQLException is
     * thrown. &lt;/li&gt; &lt;/ol&gt;
     * <p>
     * &lt;p&gt; Primitive bean properties are set to their defaults when SQL NULL
     * is returned from the <code>ResultSet</code>. Numeric fields are set to 0 and
     * booleans are set to false. Object bean properties are set to
     * <code>null</code> when SQL NULL is returned. This is the same behavior as the
     * <code>ResultSet</code> get* methods. &lt;/p&gt;
     * @param <T>  The type of bean to create
     * @param rs   ResultSet that supplies the bean data
     * @param type Class from which to create the bean instance
     * @return the newly created bean
     * @throws SQLException if a database access error occurs
     */
    public <T> T toBean(ResultSet rs, Class<? extends T> type) throws SQLException {
        T bean = this.newInstance(type);
        return this.populateBean(rs, bean);
    }

    /**
     * Convert a <code>ResultSet</code> into a <code>List</code> of JavaBeans. This
     * implementation uses reflection and <code>BeanInfo</code> classes to match
     * column names to bean property names. Properties are matched to columns based
     * on several factors: &lt;br/&gt; &lt;ol&gt; &lt;li&gt; The class has a
     * writable property with the same name as a column. The name comparison is case
     * insensitive. &lt;/li&gt;
     * <p>
     * &lt;li&gt; The column type can be converted to the property's set method
     * parameter type with a ResultSet.get* method. If the conversion fails (ie. the
     * property was an int and the column was a Timestamp) an SQLException is
     * thrown. &lt;/li&gt; &lt;/ol&gt;
     *
     * <p>
     * Primitive bean properties are set to their defaults when SQL NULL is returned
     * from the <code>ResultSet</code>. Numeric fields are set to 0 and booleans are
     * set to false. Object bean properties are set to <code>null</code> when SQL
     * NULL is returned. This is the same behavior as the <code>ResultSet</code>
     * get* methods. &lt;/p&gt;
     * @param <T>  The type of bean to create
     * @param rs   ResultSet that supplies the bean data
     * @param type Class from which to create the bean instance
     * @return the newly created List of beans
     * @throws SQLException if a database access error occurs
     */
    public <T> List<T> toBeanList(ResultSet rs, Class<? extends T> type) throws SQLException {
        List<T> results = new ArrayList<T>();

        if (!rs.next()) {
            return results;
        }

        PropertyDescriptor[] props = this.propertyDescriptors(type);
        ResultSetMetaData rsmd = rs.getMetaData();
        int[] columnToProperty = this.mapColumnsToProperties(rsmd, props);

        do {
            results.add(this.createBean(rs, type, props, columnToProperty));
        } while (rs.next());

        return results;
    }

    /**
     * Creates a new object and initializes its fields from the ResultSet.
     * @param <T>              The type of bean to create
     * @param rs               The result set.
     * @param type             The bean type (the return type of the object).
     * @param props            The property descriptors.
     * @param columnToProperty The column indices in the result set.
     * @return An initialized object.
     * @throws SQLException if a database error occurs.
     */
    private <T> T createBean(ResultSet rs, Class<T> type, PropertyDescriptor[] props, int[] columnToProperty)
            throws SQLException {

        T bean = this.newInstance(type);
        return populateBean(rs, bean, props, columnToProperty);
    }

    /**
     * Initializes the fields of the provided bean from the ResultSet.
     * @param <T>  The type of bean
     * @param rs   The result set.
     * @param bean The bean to be populated.
     * @return An initialized object.
     * @throws SQLException if a database error occurs.
     */
    public <T> T populateBean(ResultSet rs, T bean) throws SQLException {
        PropertyDescriptor[] props = this.propertyDescriptors(bean.getClass());
        ResultSetMetaData rsmd = rs.getMetaData();
        int[] columnToProperty = this.mapColumnsToProperties(rsmd, props);

        return populateBean(rs, bean, props, columnToProperty);
    }

    /**
     * This method populates a bean from the ResultSet based upon the underlying
     * meta-data.
     * @param <T>              The type of bean
     * @param rs               The result set.
     * @param bean             The bean to be populated.
     * @param props            The property descriptors.
     * @param columnToProperty The column indices in the result set.
     * @return An initialized object.
     * @throws SQLException if a database error occurs.
     */
    private <T> T populateBean(ResultSet rs, T bean, PropertyDescriptor[] props, int[] columnToProperty)
            throws SQLException {

        for (int i = 1; i < columnToProperty.length; i++) {

            if (columnToProperty[i] == PROPERTY_NOT_FOUND) {
                continue;
            }

            PropertyDescriptor prop = props[columnToProperty[i]];
            Class<?> propType = prop.getPropertyType();

            Object value = null;
            if (propType != null) {
                value = this.processColumn(rs, i, propType);

                if (value == null && propType.isPrimitive()) {
                    value = primitiveDefaults.get(propType);
                }
            }

            this.callSetter(bean, prop, value);
        }

        return bean;
    }

    /**
     * Calls the setter method on the target object for the given property. If no
     * setter method exists for the property, this method does nothing.
     * @param target The object to set the property on.
     * @param prop   The property to set.
     * @param value  The value to pass into the setter.
     * @throws SQLException if an error occurs setting the property.
     */
    private void callSetter(Object target, PropertyDescriptor prop, Object value) throws SQLException {

        Method setter = getWriteMethod(target, prop, value);

        if (setter == null || setter.getParameterTypes().length != 1) {
            return;
        }

        try {
            Class<?> firstParam = setter.getParameterTypes()[0];
            for (PropertyHandler handler : propertyHandlers) {
                if (handler.match(firstParam, value)) {
                    value = handler.apply(firstParam, value);
                    break;
                }
            }

            // Don't call setter if the value object isn't the right type
            if (this.isCompatibleType(value, firstParam)) {
                setter.invoke(target, new Object[]{value});
            } else {
                throw new SQLException("Cannot set " + prop.getName() + ": incompatible types, cannot convert "
                        + value.getClass().getName() + " to " + firstParam.getName());
                // value cannot be null here because isCompatibleType allows null
            }

        } catch (IllegalArgumentException e) {
            throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());

        } catch (IllegalAccessException e) {
            throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());

        } catch (InvocationTargetException e) {
            throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());
        }
    }

    /**
     * ResultSet.getObject() returns an Integer object for an INT column. The setter
     * method for the property might take an Integer or a primitive int. This method
     * returns true if the value can be successfully passed into the setter method.
     * Remember, Method.invoke() handles the unwrapping of Integer into an int.
     * @param value The value to be passed into the setter method.
     * @param type  The setter's parameter type (non-null)
     * @return boolean True if the value is compatible (null => true)
     */
    private boolean isCompatibleType(Object value, Class<?> type) {
        // Do object check first, then primitives
        if (value == null || type.isInstance(value) || matchesPrimitive(type, value.getClass())) {
            return true;

        }
        return false;

    }

    /**
     * Check whether a value is of the same primitive type as
     * <code>targetType</code>.
     * @param targetType The primitive type to target.
     * @param valueType  The value to match to the primitive type.
     * @return Whether <code>valueType</code> can be coerced (e.g. autoboxed) into
     * <code>targetType</code>.
     */
    private boolean matchesPrimitive(Class<?> targetType, Class<?> valueType) {
        if (!targetType.isPrimitive()) {
            return false;
        }

        try {
            // see if there is a "TYPE" field. This is present for primitive wrappers.
            Field typeField = valueType.getField("TYPE");
            Object primitiveValueType = typeField.get(valueType);

            if (targetType == primitiveValueType) {
                return true;
            }
        } catch (NoSuchFieldException e) {
            // lacking the TYPE field is a good sign that we're not working with a primitive
            // wrapper.
            // we can't match for compatibility
        } catch (IllegalAccessException e) {
            // an inaccessible TYPE field is a good sign that we're not working with a
            // primitive wrapper.
            // nothing to do. we can't match for compatibility
        }
        return false;
    }

    /**
     * Get the write method to use when setting {@code value} to the {@code target}.
     * @param target Object where the write method will be called.
     * @param prop   BeanUtils information.
     * @param value  The value that will be passed to the write method.
     * @return The {@link Method} to call on {@code target} to
     * write {@code value} or {@code null} if there is no suitable write
     * method.
     */
    protected Method getWriteMethod(Object target, PropertyDescriptor prop, Object value) {
        Method method = prop.getWriteMethod();
        return method;
    }

    /**
     * Factory method that returns a new instance of the given Class. This is called
     * at the start of the bean creation process and may be overridden to provide
     * custom behavior like returning a cached bean instance.
     * @param <T> The type of object to create
     * @param c   The Class to create an object from.
     * @return A newly created object of the Class.
     * @throws SQLException if creation failed.
     */
    protected <T> T newInstance(Class<T> c) throws SQLException {
        try {
            return c.newInstance();

        } catch (InstantiationException e) {
            throw new SQLException("Cannot create " + c.getName() + ": " + e.getMessage());

        } catch (IllegalAccessException e) {
            throw new SQLException("Cannot create " + c.getName() + ": " + e.getMessage());
        }
    }

    /**
     * Returns a PropertyDescriptor[] for the given Class.
     * @param c The Class to retrieve PropertyDescriptors for.
     * @return A PropertyDescriptor[] describing the Class.
     * @throws SQLException if introspection failed.
     */
    private PropertyDescriptor[] propertyDescriptors(Class<?> c) throws SQLException {
        // Introspector caches BeanInfo classes for better performance
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(c);

        } catch (IntrospectionException e) {
            throw new SQLException("Bean introspection failed: " + e.getMessage());
        }

        return beanInfo.getPropertyDescriptors();
    }

    /**
     * The positions in the returned array represent column numbers. The values
     * stored at each position represent the index in the
     * <code>PropertyDescriptor[]</code> for the bean property that matches the
     * column name. If no bean property was found for a column, the position is set
     * to <code>PROPERTY_NOT_FOUND</code>.
     * @param rsmd  The <code>ResultSetMetaData</code> containing column
     *              information.
     * @param props The bean property descriptors.
     * @return An int[] with column index to property index mappings. The 0th
     * element is meaningless because JDBC column indexing starts at 1.
     * @throws SQLException if a database access error occurs
     */
    protected int[] mapColumnsToProperties(ResultSetMetaData rsmd, PropertyDescriptor[] props) throws SQLException {

        int cols = rsmd.getColumnCount();
        int[] columnToProperty = new int[cols + 1];
        Arrays.fill(columnToProperty, PROPERTY_NOT_FOUND);

        for (int col = 1; col <= cols; col++) {
            String columnName = rsmd.getColumnLabel(col);
            if (null == columnName || 0 == columnName.length()) {
                columnName = rsmd.getColumnName(col);
            }
            String propertyName = columnToPropertyOverrides.get(columnName);
            if (propertyName == null) {
                propertyName = columnName;
            }
            for (int i = 0; i < props.length; i++) {

                if (propertyName.equalsIgnoreCase(props[i].getName())) {
                    columnToProperty[col] = i;
                    break;
                }
            }
        }

        return columnToProperty;
    }

    /**
     * Convert a <code>ResultSet</code> column into an object. Simple
     * implementations could just call <code>rs.getObject(index)</code> while more
     * complex implementations could perform type manipulation to match the column's
     * type to the bean property type.
     *
     * <p>
     * This implementation calls the appropriate <code>ResultSet</code> getter
     * method for the given property type to perform the type conversion. If the
     * property type doesn't match one of the supported <code>ResultSet</code>
     * types, <code>getObject</code> is called.
     * </p>
     * @param rs       The <code>ResultSet</code> currently being processed. It is
     *                 positioned on a valid row before being passed into this
     *                 method.
     * @param index    The current column index being processed.
     * @param propType The bean property type that this column needs to be converted
     *                 into.
     * @return The object from the <code>ResultSet</code> at the given column index
     * after optional type processing or <code>null</code> if the column
     * value was SQL NULL.
     * @throws SQLException if a database access error occurs
     */
    protected Object processColumn(ResultSet rs, int index, Class<?> propType) throws SQLException {
        Object retval = rs.getObject(index);
        if (!propType.isPrimitive() && retval == null) {
            return null;
        }
        for (ColumnHandler handler : columnHandlers) {
            if (handler.match(propType)) {
                retval = handler.apply(rs, index);
                break;
            }
        }
        return retval;
    }
}
