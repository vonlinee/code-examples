package io.spring.boot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {

    public static Map<String, String> asMap(Properties properties) {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            map.put((String) entry.getKey(), (String) entry.getValue());
        }
        return map;
    }

    public static Properties read(File file) {
        Properties properties = new Properties();
        if (!file.exists()) {
            return properties;
        }
        try (InputStream is = new FileInputStream(file)) {
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
