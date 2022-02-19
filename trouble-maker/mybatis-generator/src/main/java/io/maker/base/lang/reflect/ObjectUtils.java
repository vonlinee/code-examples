package io.maker.base.lang.reflect;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

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
	
	public static <T> void setFieldValues(T obj, Map<String, Object> map) throws Exception {
		obj = Objects.requireNonNull(obj);
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propDescs = beanInfo.getPropertyDescriptors();
		for(String fieldName : map.keySet()) {
	        for (PropertyDescriptor propDesc : propDescs) {
	            if (propDesc.getName().equals(fieldName)) {
	                Method methodSet = propDesc.getWriteMethod();
	                methodSet.invoke(obj, map.get(fieldName));
	            }
	        }
		}
	}
}
