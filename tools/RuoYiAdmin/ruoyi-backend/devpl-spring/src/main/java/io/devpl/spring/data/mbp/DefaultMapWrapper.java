package io.devpl.spring.data.mbp;

import java.util.Map;

import cn.hutool.core.map.MapWrapper;
import org.apache.ibatis.reflection.MetaObject;

public class DefaultMapWrapper extends MapWrapper {

	public DefaultMapWrapper(MetaObject metaObject, Map<String, Object> map) {
		super(map);
	}

	public String findProperty(String name, boolean useCamelCaseMapping) {
		if (useCamelCaseMapping 
				&& ((name.charAt(0) >= 'A' && name.charAt(0) <= 'Z') || name.contains("_"))) {
			return underlineToCamelhump(name);
		}
		return name;
	}

	/**
	 * 将下划线风格替换为驼峰风格
	 * 
	 * @param inputString
	 * @return
	 */
	private String underlineToCamelhump(String inputString) {
		StringBuilder sb = new StringBuilder();
		boolean nextUpperCase = false;
		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);
			if (c == '_') {
				if (sb.length() > 0) {
					nextUpperCase = true;
				}
			} else {
				if (nextUpperCase) {
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				} else {
					sb.append(Character.toLowerCase(c));
				}
			}
		}
		return sb.toString();
	}
}
