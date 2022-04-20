package io.maker.base.lang.reflect;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.lang.Nullable;

public final class ObjectUtils {

    @SuppressWarnings("unchecked")
    public static <T> T[] cast(Object[] array) {
        try {
            return (T[]) array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <A, B> B unsafeCast(A target, Class<B> dstType) {
        return (B) target;
    }

    /**
     * 可能会存在并未转换，而是返回了父类引用，指向子类
     *
     * @param target
     * @param dstType
     * @param <A>
     * @param <B>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <A, B> B safeCast(A target, Class<B> dstType) {
        if (target == null) {
            return null;
        }
        if (dstType.isAssignableFrom(target.getClass())) { // 向上转换
            return (B) target;
        }
        if (target.getClass().isAssignableFrom(dstType)) { // 向下转换
            if (dstType == String.class) {
                return (B) String.valueOf(target);
            }
        }
        return null;
    }

    /**
     * @param array
     * @return
     */
    public static String[] cast2Strings(Object[] array) {
        try {
            if (array[0] instanceof String) {
                return (String[]) array;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        try {
            return (T) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否是数组
     *
     * @param obj Object
     * @return
     */
    public static boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    public static <T> void setFieldValues(T obj, Map<String, Object> map) throws Exception {
        obj = Objects.requireNonNull(obj);
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propDescs = beanInfo.getPropertyDescriptors();
        for (String fieldName : map.keySet()) {
            for (PropertyDescriptor propDesc : propDescs) {
                if (propDesc.getName().equals(fieldName)) {
                    Method methodSet = propDesc.getWriteMethod();
                    methodSet.invoke(obj, map.get(fieldName));
                }
            }
        }
    }

    public static boolean isPrimitive(Object obj) {
        return obj != null && obj.getClass().isPrimitive();
    }

    /**
     * JavaBean -> Map
     *
     * @param bean
     * @return
     */
    public static Map<String, Object> bean2Map(Object bean) {
        Objects.requireNonNull(bean);
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propDescs = beanInfo.getPropertyDescriptors();
            Map<String, Object> map = new HashMap<>(propDescs.length);
            for (PropertyDescriptor propDesc : propDescs) {
                map.put(propDesc.getDisplayName(), propDesc.getReadMethod().invoke(bean));
            }
            return map;
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

	/**
	 * Determine whether the given array is empty:
	 * i.e. {@code null} or of zero length.
	 * @param array the array to check
	 * @see #isEmpty(Object)
	 */
	public static boolean isEmpty(@Nullable Object[] array) {
		return (array == null || array.length == 0);
	}

}
