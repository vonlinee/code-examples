package code.pocket.base.lang.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Target(value = ElementType.FIELD)
public @interface Name {
	String value() default "";
}
