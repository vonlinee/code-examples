package org.springboot.sample.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {
	
	public static Map<String, String> asMap(Properties properties) {
		Map<String, String> map = new HashMap<>();
		for(Map.Entry<Object, Object> entry : properties.entrySet()) {
			map.put((String) entry.getKey(), (String) entry.getValue());
		}
		return map;
	}
	
}
