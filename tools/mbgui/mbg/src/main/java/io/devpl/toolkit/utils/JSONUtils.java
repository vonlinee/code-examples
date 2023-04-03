package io.devpl.toolkit.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJSONString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("将对象转换为JSON时发生错误！", e);
        }
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        try {
            return mapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("将JSON转换为对象时发生错误:" + jsonStr, e);
        }
    }

    public static <T> T parseObject(String jsonStr, TypeReference<T> clazzType) {
        try {
            return mapper.readValue(jsonStr, clazzType);
        } catch (IOException e) {
            throw new IllegalArgumentException("将JSON转换为对象时发生错误:" + jsonStr, e);
        }
    }

    /**
     * 解析指定路径的JSON为对象
     *
     * @param content 整个JSON文本
     * @param path    路径字符串
     * @param clazz   类型
     * @param <T>     类型
     * @return 对象类型
     * @throws IOException JSON操作异常
     */
    public static <T> T parseObject(String content, String path, Class<T> clazz) throws IOException {
        return parseObject(extractContent(content, path), clazz);
    }

    public static String extractContent(String content, String path) throws JsonProcessingException {
        if (!StringUtils.hasLength(path)) {
            JsonNode node = mapper.readTree(content);
            String[] pathes = path.split("\\.");
            for (String p : pathes) {
                node = node.get(p);
            }
            content = node.toString();
        }
        return content;
    }

    public static <T> T parseObject(String content, String path, TypeReference<T> clazzType) throws IOException {
        return parseObject(extractContent(content, path), clazzType);
    }
}
