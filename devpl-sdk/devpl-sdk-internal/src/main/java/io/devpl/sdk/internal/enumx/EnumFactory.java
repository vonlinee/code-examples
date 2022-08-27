package io.devpl.sdk.internal.enumx;

import io.devpl.sdk.internal.Constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举工厂，提供创建枚举，获取枚举值的方法
 * 工厂本身是单例的
 * @param <T> 枚举值类型，单例
 */
public abstract class EnumFactory<T extends TypedEnum<T>> {

    private EnumNamingStrategy<T> namingStrategy;
    private final Map<String, T> enums = new ConcurrentHashMap<>();

    /**
     * Get existing constant by name or creates new one if not exists. Threadsafe
     * @param name the name of the {@link Constant}
     */
    private T getOrCreate(String name) {
        // 从常量池中获取
        T enumeration = enums.get(name);
        if (enumeration == null) {
            // 创建枚举

        }
        return enumeration;
    }

    /**
     * TODO 枚举类的字段不确定
     * @return 枚举类实例对象
     */
    abstract T createEnum();
}
