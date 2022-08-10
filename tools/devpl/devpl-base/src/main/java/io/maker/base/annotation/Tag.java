package io.maker.base.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Tag {

	String name();

	Class<?> type();

}
