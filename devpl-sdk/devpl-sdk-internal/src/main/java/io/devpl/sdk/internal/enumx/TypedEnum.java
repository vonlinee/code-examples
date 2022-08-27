package io.devpl.sdk.internal.enumx;

/**
 * 动态枚举接口
 * 每个实现都是严格的单例
 * @param <T> 枚举值的类型
 */
public interface TypedEnum<T extends TypedEnum<T>> {

    /**
     * 返回枚举的值，可能是基础类型，可能是对象类型
     * @return 枚举值
     */
    T value();

    /**
     * 扩展信息，定义枚举单例的名称表示
     * @return 枚举的名称
     */
    String name();

    /**
     * 唯一ID
     * @return 唯一ID
     */
    String id();
}
