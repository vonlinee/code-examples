package io.devpl.sdk.support;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DevplComponent {

    boolean enabled() default false;

    String id();
}
