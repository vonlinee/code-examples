package io.maker.base.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Documented
@Target(ElementType.TYPE)
public @interface Interceptor {

	String name() default "";

	String[] value();
}
