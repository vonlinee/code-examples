package io.devpl.sdk.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

/**
 * Utilities for Properties.
 */
public class PropertiesUtils {

	private final static Log LOG = LogFactory.getLog(PropertiesUtils.class);

	/**
	 * Load properties from the given file into Properties.
	 */
	public static Properties loadProperties(String file) {
		Properties properties = new Properties();
		if (file == null) {
			return properties;
		}
		InputStream is = null;
		try {
			LOG.debug("Trying to load " + file + " from FileSystem.");
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			LOG.debug("Trying to load " + file + " from Classpath.");
			try {
				is = PropertiesUtils.class.getResourceAsStream(file);
			} catch (Exception ex) {
				LOG.warn("Can not load resource " + file, ex);
			}
		}
		if (is != null) {
			try {
				properties.load(is);
			} catch (Exception e) {
				LOG.error("Exception occurred while loading " + file, e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception e) {
						LOG.debug("Can not close Inputstream.", e);
					}
				}
			}
		} else {
			LOG.warn("File " + file + " can't be loaded!");
		}
		return properties;
	}

	/**
	 * Pick the name of a JDBC url. Such as xxx.url, xxx is the name.
	 */
	public static List<String> loadNameList(Properties properties, String propertyPrefix) {
		List<String> nameList = new ArrayList<String>();
		Set<String> names = new HashSet<String>();
		for (String n : properties.stringPropertyNames()) {
			if (propertyPrefix != null && !propertyPrefix.isEmpty() && !n.startsWith(propertyPrefix)) {
				continue;
			}
			if (n.endsWith(".url")) {
				names.add(n.split("\\.url")[0]);
			}
		}
		if (!names.isEmpty()) {
			nameList.addAll(names);
		}
		return nameList;
	}

	public static Properties filterPrefix(Properties properties, String prefix) {
		if (properties == null || prefix == null || prefix.isEmpty()) {
			return properties;
		}
		Properties result = new Properties();
		for (String n : properties.stringPropertyNames()) {
			if (n.startsWith(prefix)) {
				result.setProperty(n, properties.getProperty(n));
			}
		}
		return result;
	}

	public static Map<String, String> asMap(Properties properties) {
		Map<String, String> map = new HashMap<String, String>();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			map.put(entry.getKey().toString(), entry.getValue().toString());
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Properties props, String name, T defaultValue) {
		Object value = props.get(name);
		return value == null ? defaultValue : (T) value;
	}
}
