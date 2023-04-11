package io.devpl.codegen.mbpg.config;

/**
 * 子类应为枚举类
 *
 * @param <K>
 * @param <E>
 */
public interface TypeEnum<K, E extends Enum<E>> {

    /**
     * 类型标识，一般是数字或字符串
     *
     * @return 类型标识
     */
    K getType();

    /**
     * 类型名称
     *
     * @return 类型名称
     */
    String getTypeName();

    /**
     * 获取枚举类型实例
     *
     * @return 枚举类型
     */
    E getEnum();
}
