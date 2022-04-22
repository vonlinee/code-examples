package io.maker.extension.utils;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.cglib.beans.BeanMap;

import io.maker.base.utils.ClassUtils;
import io.maker.base.utils.Lists;

public final class BeanUtils {

	private BeanUtils() {
	}

	/**
	 * 将对象装换为 map,对象转成 map，key肯定是字符串
	 * 
	 * @param bean 转换对象
	 * @return 返回转换后的 map 对象
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> beanToMap(Object bean) {
		return null == bean ? null : BeanMap.create(bean);
	}

	/**
	 * map 转换为 java bean 对象
	 *
	 * @param map   转换 MAP
	 * @param clazz 对象 Class
	 * @return 返回 bean 对象
	 */
	public static <T> T mapToBean(Map<String, ?> map, Class<T> clazz) {
		T bean = null;
		try {
			bean = ClassUtils.newInstance(clazz);
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * List&lt;T&gt; 转换为 List&lt;Map&lt;String, Object&gt;&gt;
	 *
	 * @param beans 转换对象集合
	 * @return 返回转换后的 bean 列表
	 */
	public static <T> List<Map<String, Object>> beansToMaps(List<T> beans) {
		if (Lists.isEmpty(beans)) {
			return Collections.emptyList();
		}
		return beans.stream().map(BeanUtils::beanToMap).collect(toList());
	}

	/**
	 *
	 * @param maps  转换 MAP 集合
	 * @param clazz 对象 Class
	 * @return 返回转换后的 bean 集合
	 */
	public static <T> List<T> mapsToBeans(List<? extends Map<String, ?>> mapList, Class<T> clazz) {
		if (Lists.isEmpty(mapList)) {
			return Collections.emptyList();
		}
		return mapList.stream().map(e -> mapToBean(e, clazz)).collect(toList());
	}
}
