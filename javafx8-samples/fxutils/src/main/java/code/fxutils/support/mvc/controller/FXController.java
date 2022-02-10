package code.fxutils.support.mvc.controller;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface FXController {

    String name() default "";
    Class<?> target();
}
