package io.maker.base.lang.reflect;

import java.lang.annotation.*;

/**
 * 方法签名
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface MethodMetadata {

    Class<?> declaringClass(); //方法声明位于哪个类中

    Class<?> type(); //实际属于哪个类型

    String methodName(); //方法名

    Class<?>[] args(); //方法参数
}
