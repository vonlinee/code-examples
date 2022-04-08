package org.setamv.shardingsphere.sample.dynamic.spel;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 动态路由注解。用于声明式的指定动态路由的条件，借助Spring表达式实现。
 *
 * @author setamv
 * @date 2021-04-20
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DynamicDataSourceRouter {

    /**
     * {@link #path}的别名.
     */
    @AliasFor("path")
    String[] value() default {};

    /**
     * 用于获取日期区间段的SpEL表达式。如果是一个值，表示精确的日期值，如果是两个值，表示日期区间
     */
    String[] path() default {};

}
