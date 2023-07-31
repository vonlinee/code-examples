package io.devpl.codegen.meta;

import java.lang.annotation.*;

/**
 * 声明反省参数，应用于辅助判断泛型类型，解决类型擦除
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(value = RetentionPolicy.SOURCE)
public @interface GenericParam {

    /**
     * 数据类型
     * @return 类型Class
     */
    Class<?> value();
}
