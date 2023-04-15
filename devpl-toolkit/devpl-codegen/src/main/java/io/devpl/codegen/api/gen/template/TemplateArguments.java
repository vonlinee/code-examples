package io.devpl.codegen.api.gen.template;

import java.util.Map;
import java.util.Set;

/**
 * 模板参数，类似于实体类，将数据转换为Map以兼容多种模板引擎
 * 尽量将模板参数在模板渲染之前准备好，减少模板中的逻辑操作,视图与逻辑分离
 */
public interface TemplateArguments {

    /**
     * Adds a name/value pair to the context.
     * @param key   The name to key the provided value with.
     * @param value The corresponding value.
     * @return The old object or null if there was no old object.
     */
    Object put(String key, Object value);

    void putAll(Map<String, Object> map);

    /**
     * Gets the value corresponding to the provided key from the context.
     * @param key The name of the desired value.
     * @return The value corresponding to the provided key.
     */
    Object get(Object key);

    /**
     * Indicates whether the specified key is in the context.
     * @param key The key to look for.
     * @return Whether the key is in the context.
     */
    boolean containsKey(Object key);

    /**
     * Get all the keys for the values in the context.
     * @return All the keys for the values in the context.
     */
    Set<String> keySet();

    /**
     * Removes the value associated with the specified key from the context.
     * @param key The name of the value to remove.
     * @return The value that the key was mapped to, or <code>null</code>
     * if unmapped.
     */
    Object remove(Object key);

    /**
     * 将所有模板参数放进一个Map内
     * @return Map
     */
    Map<String, Object> asMap();
}
