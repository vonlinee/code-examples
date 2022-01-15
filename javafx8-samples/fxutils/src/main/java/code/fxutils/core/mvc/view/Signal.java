package code.fxutils.core.mvc.view;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Signal {
    boolean flag() default false;

    String message() default "";

    String[] props() default {};
}
