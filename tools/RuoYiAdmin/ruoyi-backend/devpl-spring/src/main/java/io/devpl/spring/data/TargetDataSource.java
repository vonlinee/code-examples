package io.devpl.spring.data;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

    /**
     * 数据源的唯一标识
     *
     * @return 数据源的唯一标识
     */
    String value();
}