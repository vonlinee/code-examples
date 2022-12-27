package io.devpl.toolkit.fxui.utils;

/**
 * JSON和对象之间互相转换
 */
public interface ObjectJSONConverter {

    /**
     * 对象转为JSON字符串
     * @param obj 对象
     * @return JSON字符串
     */
    String toJSONString(Object obj);

    /**
     * JSON字符串反序列化为对象
     * @param jsonString JSON字符串
     * @param type       对象类型
     * @param <T>        对象类型
     * @return 对象实例
     */
    <T> T toObject(String jsonString, Class<T> type);
}
