package io.devpl.spring.boot.factories.env;

import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 此类用于加载PropertySource
 * <p>
 * SpringBoot加载配置文件的入口是ConfigFileApplicationListener，
 * 这个类实现了ApplicationListener和EnvironmentPostProcessor两个接口。
 * SpringApplication在初始化的时候会加载spring.factories配置的ApplicationListener接口的实现类
 * <p>
 * https://www.cnblogs.com/zhao1949/p/6226288.html
 * @see org.springframework.boot.env.PropertiesPropertySourceLoader
 * @see org.springframework.boot.env.YamlPropertySourceLoader
 */
public class MultitypePropertySourceLoader implements PropertySourceLoader {

    /**
     * 支持的配置文件扩展名
     */
    private final String[] supportedExtensions = new String[]{
            "json", "toml", "ini", "properties", "xml", "yaml", "yml", "txt"
    };

    /**
     * 返回支持的文件扩展名
     * @return
     */
    @Override
    public String[] getFileExtensions() {
        return supportedExtensions;
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        if (!StringUtils.hasLength(name)) {
            return new ArrayList<>();
        }
        String filename = resource.getFilename();
        if (!StringUtils.hasLength(filename)) {
            return new ArrayList<>();
        }
        if (filename.endsWith("properties")) {
            PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();
            return loader.load(name, resource);
        }
        if (filename.endsWith("json")) {
            Map<String, Object> result = mapPropertySource(resource);
            MapPropertySource propertySource = new MapPropertySource(name, result);
            return Collections.singletonList(propertySource);
        }
        return new ArrayList<>();
    }

    private Map<String, Object> mapPropertySource(Resource resource) throws IOException {
        Map<String, Object> result = new HashMap<>();
        // SpringBoot内置的JSON解析
        JsonParser parser = JsonParserFactory.getJsonParser();
        Map<String, Object> map = parser.parseMap(readFile(resource));
        nestMap("", result, map);
        return result;
    }

    private String readFile(Resource resource) throws IOException {
        InputStream inputStream = resource.getInputStream();
        List<Byte> byteList = new LinkedList<>();
        byte[] readByte = new byte[1024];
        int length;
        while ((length = inputStream.read(readByte)) > 0) {
            for (int i = 0; i < length; i++) {
                byteList.add(readByte[i]);
            }
        }
        byte[] allBytes = new byte[byteList.size()];
        int index = 0;
        for (Byte soloByte : byteList) {
            allBytes[index] = soloByte;
            index += 1;
        }
        return new String(allBytes);
    }

    private void nestMap(String prefix, Map<String, Object> result, Map<String, Object> map) {
        if (prefix.length() > 0) {
            prefix += ".";
        }
        for (Map.Entry<String, Object> entrySet : map.entrySet()) {
            if (entrySet.getValue() instanceof Map) {
                nestMap(prefix + entrySet.getKey(), result, (Map<String, Object>) entrySet.getValue());
            } else {
                result.put(prefix + entrySet.getKey().toString(), entrySet.getValue());
            }
        }
    }
}
